package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
import com.ia.core.llm.service.template.TemplateService;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.tool.base.OwlConstructorTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Serviço para construção de ontologias usando ChatService com tools OWL.
 * <p>
 * Este serviço utiliza o ChatService com as tools OWL 2 DL implementadas
 * para construir ontologias a partir de texto/corpus fornecido pelo usuário.
 * <p>
 * O LLM identifica automaticamente quais tools usar e quais parâmetros passar
 * baseado nas descrições @Tool e @ToolParam das tools.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OntologyBuilderService {

  private final ChatService chatService;
  private final DefaultOwlService owlService;
  private final TemplateService templateService;
  private final List<OwlConstructorTool> owlTools;

  /**
   * Constrói uma ontologia a partir de texto/corpus fornecido.
   *
   * @param sessionId ID da sessão de conversação
   * @param corpus Texto/corpus para extração de conceitos e relações
   * @param domain Domínio da ontologia (ex: biologia, biblioteca, medicina)
   * @return Resultado da construção com ontologia gerada
   */
  public String buildOntology(String sessionId, String corpus, String domain) {
    log.debug("Iniciando construção de ontologia: sessionId={}, domain={}", sessionId, domain);

    // Constrói prompt system específico para construção de ontologias
    String systemPrompt = buildSystemPrompt(domain);

    // Usa ChatService com tools OWL disponíveis
    // O LLM decide automaticamente quais tools usar e quais parâmetros passar
    String response = chatService.ask(
        corpus,Map.of(),
        systemPrompt,sessionId,
        owlTools.toArray()
    );

    log.debug("Ontologia construída: sessionId={}, response length={}", sessionId, response.length());
    return response;
  }

  /**
   * Constrói o prompt system específico para construção de ontologias.
   *
   * @param domain Domínio da ontologia
   * @return Prompt system
   */
  private String buildSystemPrompt(String domain) {
    // Tenta obter template do banco
    try {
      return templateService.loadById("ontology-builder-system")
          .map(template -> templateService.processTemplate(
              template,
              Map.of("domain", domain)
          ))
          .orElseGet(() -> {
            log.warn("Template 'ontology-builder-system' não encontrado, usando prompt padrão");
            return buildDefaultSystemPrompt(domain);
          });
    } catch (Exception e) {
      log.warn("Não foi possível obter template do banco, usando prompt padrão: {}", e.getMessage());
      return buildDefaultSystemPrompt(domain);
    }
  }

  /**
   * Constrói prompt system padrão caso template não esteja disponível.
   *
   * @param domain Domínio da ontologia
   * @return Prompt system padrão
   */
  private String buildDefaultSystemPrompt(String domain) {
    return String.format("""
        Você é um especialista em ontologias OWL 2 DL no domínio de %s.

        Sua tarefa é analisar o texto fornecido e extrair conceitos e relações
        para construir uma ontologia OWL 2 DL completa.

        Use as tools OWL disponíveis para criar:
        - Classes (SubClassOfTool, OwlClassCreatorTool)
        - Propriedades de objeto (ObjectPropertyDomainTool, ObjectPropertyRangeTool, etc.)
        - Propriedades de dados (DataPropertyDomainTool, DataPropertyRangeTool, etc.)
        - Axiomas de classe (ObjectSomeValuesFromTool, ObjectAllValuesFromTool, etc.)
        - Restrições de cardinalidade (ObjectMinCardinalityTool, etc.)

        Para cada conceito ou relação identificada no texto, use a tool OWL apropriada.
        O LLM identificará automaticamente quais parâmetros passar para cada tool.

        Retorne um resumo da ontologia construída, incluindo:
        - Número de classes criadas
        - Número de propriedades criadas
        - Número de axiomas adicionados
        """, domain);
  }

  /**
   * Refina uma ontologia existente com base em feedback.
   *
   * @param sessionId ID da sessão de conversação
   * @param feedback Feedback para refinamento
   * @return Resultado do refinamento
   */
  public String refineOntology(String sessionId, String feedback) {
    log.debug("Refinando ontologia: sessionId={}", sessionId);

    String systemPrompt = """
        Você é um especialista em refino de ontologias OWL 2 DL.

        Analise o feedback fornecido e use as tools OWL disponíveis para
        corrigir, adicionar ou remover axiomas da ontologia existente.

        Garanta que a ontologia permaneça consistente após as modificações.
        """;

    String response = chatService.ask(
        feedback,Map.of(),
        systemPrompt,sessionId,
        owlTools.toArray()
    );

    log.debug("Ontologia refinada: sessionId={}", sessionId);
    return response;
  }
}
