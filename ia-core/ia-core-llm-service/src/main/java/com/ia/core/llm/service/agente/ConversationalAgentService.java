package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.agente.RespostaAgenteDTO;
import com.ia.core.llm.service.model.ontologia.EstatisticasOntologiaDTO;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Serviço para agente conversacional guiado por ontologia.
 * <p>
 * Implementa agente conversacional que:
 * - Extrai conceitos da mensagem do usuário
 * - Atualiza ontologia de contexto dinamicamente usando tools OWL
 * - Constrói prompt enriquecido com contexto da ontologia
 * - Usa templates do banco para prompts
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationalAgentService {

  private final ChatService chatService;
  private final ContextoConversacaoService contextoConversacaoService;
  private final DefaultOwlService owlService;
  private final TemplateService templateService;
  private final List<OwlConstructorTool> owlTools;

  public ContextConversacaoDTO createSession(String userId, String dominio) {
    String sessionId = java.util.UUID.randomUUID().toString();
    log.debug("Criando sessão conversacional: sessionId={}, userId={}, dominio={}",
              sessionId, userId, dominio);

    return contextoConversacaoService.createContextOntology(sessionId, userId, dominio);
  }

  public RespostaAgenteDTO processMessage(String sessionId, String mensagem) {
    log.debug("Processando mensagem: sessionId={}", sessionId);

    Optional<ContextConversacaoDTO> optionalContext = contextoConversacaoService.getContextOntology(sessionId);
    if (optionalContext.isEmpty()) {
      throw new IllegalArgumentException("Sessão não encontrada: " + sessionId);
    }

    ContextConversacaoDTO contexto = optionalContext.get();

    String enrichedPrompt = buildEnrichedPrompt(contexto, mensagem);

    String response = chatService.ask(
        enrichedPrompt,Map.of(),
        buildSystemPrompt(contexto.getDominio()),
        contexto.getSessionId(),owlTools.toArray()
    );

    contexto.setUltimaAtividade(LocalDateTime.now());

    return RespostaAgenteDTO.builder()
        .agentResponse(response)
        .ontologyStatus(EstatisticasOntologiaDTO.builder()
            .classeCount(contexto.getTotalAxiomasExtraidos() / 2)
            .axiomCount(contexto.getTotalAxiomasExtraidos())
            .build())
        .build();
  }

  private String buildEnrichedPrompt(ContextConversacaoDTO contexto, String mensagem) {
    try {
      return templateService.loadById("conversational-context-enrichment")
          .map(template -> templateService.processTemplate(
          template,
          Map.of(
              "domain", contexto.getDominio(),
              "ontologyContent", getOntologyContent(contexto),
              "userMessage", mensagem,
              "historySize", 0
          )
      )).orElseGet(() -> buildDefaultEnrichedPrompt(contexto, mensagem))    ;
    } catch (Exception e) {
      log.warn("Não foi possível obter template do banco, usando prompt padrão: {}", e.getMessage());
      return buildDefaultEnrichedPrompt(contexto, mensagem);
    }
  }

  private String buildDefaultEnrichedPrompt(ContextConversacaoDTO contexto, String mensagem) {
    StringBuilder sb = new StringBuilder();
    sb.append("Contexto da ontologia atual:\n");
    sb.append(getOntologyContent(contexto));
    sb.append("\n\n");
    sb.append("Mensagem do usuário: ").append(mensagem);
    sb.append("\n\n");
    sb.append("Use as tools OWL disponíveis para extrair conceitos e relações da mensagem ");
    sb.append("e adicioná-los à ontologia de contexto.");
    return sb.toString();
  }

  private String getOntologyContent(ContextConversacaoDTO contexto) {
    if (contexto.getOntologia() == null || contexto.getOntologia().getConteudo() == null) {
      return "Ontologia vazia.";
    }
    return contexto.getOntologia().getConteudo();
  }

  private String buildSystemPrompt(String dominio) {
    try {
      return templateService.loadById("conversational-system")
          .map(template -> templateService.processTemplate(
              template,
              Map.of("domain", dominio)
          ))
          .orElseGet(() -> {
            log.warn("Template 'conversational-system' não encontrado, usando prompt padrão");
            return String.format("""
                Você é um especialista em ontologias OWL 2 DL no domínio de %s.

                Sua tarefa é:
                1. Analisar a mensagem do usuário
                """, dominio);
          });
    } catch (Exception e) {
      log.warn("Não foi possível obter template system, usando prompt padrão: {}", e.getMessage());
      return String.format("""
          Você é um especialista em ontologias OWL 2 DL no domínio de %s.

          Sua tarefa é:
          1. Analisar a mensagem do usuário
          2. Extrair conceitos e relações relevantes
          3. Usar as tools OWL disponíveis para adicionar axiomas à ontologia
          4. Responder ao usuário de forma clara e concisa

          Use as tools OWL disponíveis para criar:
          - Classes (SubClassOfTool, OwlClassCreatorTool)
          - Propriedades (ObjectPropertyDomainTool, DataPropertyDomainTool, etc.)
          - Axiomas de classe e restrições

          Garanta que a ontologia permaneça consistente após cada modificação.
          """, dominio);
    }
  }

  public void endSession(String sessionId) {
    log.debug("Encerrando sessão: sessionId={}", sessionId);
    contextoConversacaoService.deleteContextOntology(sessionId);
  }
}