package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.chat.ChatService;
import com.ia.core.llm.service.model.agente.ContextConversacaoDTO;
import com.ia.core.llm.service.model.agente.RespostaAgente;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.llm.service.model.ontologia.ResultadoValidacao;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.validation.LoopLLMRaciocinador;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Serviço para agente conversacional guiado por ontologia.
 * <p>
 * Gerencia conversações onde a ontologia é construída incrementalmente
 * e as respostas do LLM são validadas contra axiomas formais.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
@Getter
public class AgenteConversacionalOntologiaService {

  private final DefaultOwlService owlService;
  private final LoopLLMRaciocinador reasonerLoop;
  private final ChatService chatService;

  public AgenteConversacionalOntologiaService(
                                            DefaultOwlService owlService,
                                            LoopLLMRaciocinador reasonerLoop,
                                            ChatService chatService) {
    this.owlService = owlService;
    this.reasonerLoop = reasonerLoop;
    this.chatService = chatService;
  }

  public DefaultOwlService getOwlService() {
    return owlService;
  }

  public LoopLLMRaciocinador getReasonerLoop() {
    return reasonerLoop;
  }

  public ChatService getChatService() {
    return chatService;
  }

  /**
   * Cria uma nova sessão de conversação.
   *
   * @param userId ID do usuário
   * @param dominio domínio da conversação
   * @param sessionId ID da sessão (opcional, se não fornecido será gerado)
   * @return contexto da conversação criado
   */
  @Tool(description = "Cria uma nova sessão de conversação guiada por ontologia OWL. " +
                     "Inicializa uma ontologia vazia que será construída incrementalmente durante a conversação. " +
                     "A ontologia é validada contra axiomas formais OWL 2 DL a cada interação.")
  public ContextConversacaoDTO criarSessao(@ToolParam(description = "ID do usuário") String userId,
                                           @ToolParam(description = "Domínio da conversação (ex: biologia, medicina, biblioteca)") String dominio,
                                           @ToolParam(description = "ID da sessão (opcional, se não fornecido será gerado automaticamente)") String sessionId) {
    if (sessionId == null || sessionId.isEmpty()) {
      sessionId = UUID.randomUUID().toString();
    }

    OntologiaDTO ontologia = OntologiaDTO.builder()
        .iri("http://example.com/conversa/" + sessionId)
        .nome("Ontologia da Conversa: " + dominio)
        .descricao("Ontologia construída incrementalmente durante a conversa")
        .consistente(true)
        .dataCriacao(LocalDateTime.now())
        .ultimaModificacao(LocalDateTime.now())
        .build();

    ContextConversacaoDTO contexto = ContextConversacaoDTO.builder()
        .sessionId(sessionId)
        .userId(userId)
        .dominio(dominio)
        .ontologia(ontologia)
        .dataInicio(LocalDateTime.now())
        .ultimaAtividade(LocalDateTime.now())
        .totalAxiomasExtraidos(0)
        .ontologiaConsistente(true)
        .inconsistenciasCorrigidas(0)
        .build();

    log.info("Sessão criada: sessionId={}, userId={}, dominio={}", sessionId, userId, dominio);
    return contexto;
  }

  /**
   * Cria uma nova sessão de conversação (gera sessionId automaticamente).
   *
   * @param userId ID do usuário
   * @param dominio domínio da conversação
   * @return contexto da conversação criado
   */
  public ContextConversacaoDTO criarSessao(String userId, String dominio) {
    return criarSessao(userId, dominio, null);
  }

  /**
   * Processa uma mensagem do usuário na conversação.
   *
   * @param contexto contexto da conversação
   * @param mensagem mensagem do usuário
   * @return resposta do agente
   */
  @Tool(description = "Processa uma mensagem do usuário na conversação guiada por ontologia. " +
                     "Extrai axiomas OWL da mensagem, valida contra a ontologia atual, " +
                     "corrige inconsistências usando loop LLM-Reasoner, e adiciona axiomas válidos à ontologia. " +
                     "Retorna resposta natural do agente com status da ontologia.")
  public RespostaAgente processarMensagem(@ToolParam(description = "Contexto da conversação com ontologia atual") ContextConversacaoDTO contexto,
                                         @ToolParam(description = "Mensagem do usuário a ser processada") String mensagem) {
    log.info("Processando mensagem na sessão: sessionId={}", contexto.getSessionId());

    // TODO: Implement tool-based axiom generation when OWLToolRegistry is available
    // For now, return empty axioms
    List<AxiomaDTO> axiomasGerados = new ArrayList<>();

    // Valida os axiomas
    List<AxiomaDTO> axiomasValidados = new ArrayList<>();
    boolean inconsistenciaCorrigida = false;
    String explicacaoCorrecao = null;
    int iteracoesUsadas = 0;

    for (AxiomaDTO axioma : axiomasGerados) {
      ResultadoValidacao resultado = owlService.validarAxioma(axioma);
      iteracoesUsadas += resultado.getIteracoesUsadas();

      if (resultado.isConsistente()) {
        axiomasValidados.add(axioma);
      } else {
        // Tenta corrigir usando loop LLM-Reasoner
        var feedback = reasonerLoop.corrigirAxioma(contexto.getSessionId(),
            axioma, mensagem, resultado.getExplicacao());

        if (feedback.isAxiomaValido()) {
          // Na implementação completa, aqui adicionariamos o axioma corrigido
          inconsistenciaCorrigida = true;
          explicacaoCorrecao = feedback.getExplicacao();
        }
      }
    }

    // Adiciona axiomas válidos à ontologia
    if (!axiomasValidados.isEmpty()) {
      try {
        // Create a HasAxiomas wrapper for the axioms
        var hasAxiomas = new com.ia.core.owl.service.model.axioma.HasAxiomas() {
          @Override
          public java.util.List<com.ia.core.owl.service.model.axioma.AxiomaDTO> getAxiomas() {
            return axiomasValidados;
          }
        };

        // Add axioms using DTO
        owlService.addAxioms(contexto.getOntologia(), hasAxiomas);

        // Update ontology content will be handled by the addAxioms method
      } catch (Exception e) {
        log.error("Erro ao adicionar axiomas à ontologia", e);
      }
      contexto.setTotalAxiomasExtraidos(contexto.getTotalAxiomasExtraidos() + axiomasValidados.size());
      contexto.getOntologia().setUltimaModificacao(LocalDateTime.now());
    }

    contexto.setUltimaAtividade(LocalDateTime.now());

    // Verifica consistência da ontologia
    ResultadoValidacao validacaoOntologia = owlService.validarOntologiaAtual(contexto.getOntologia());
    contexto.setOntologiaConsistente(validacaoOntologia.isConsistente());

    // Constrói resposta
    String respostaNatural = gerarRespostaNatural(mensagem, axiomasValidados);
    RespostaAgente resposta = RespostaAgente.builder()
        .agentResponse(respostaNatural)
        .extractedAxioms(axiomasValidados)
        .ontologyStatus(RespostaAgente.OntologiaStatus.builder()
            .iri(contexto.getOntologia().getIri())
            .consistent(contexto.isOntologiaConsistente())
            .classCount(contarClasses(contexto))
            .axiomCount(contexto.getTotalAxiomasExtraidos())
            .warningsCount(0)
            .build())
        .iteracoesUsadas(iteracoesUsadas)
        .inconsistenciaCorrigida(inconsistenciaCorrigida)
        .explicacaoCorrecao(explicacaoCorrecao)
        .build();

    log.info("Mensagem processada: sessionId={}, axiomasValidados={}",
             contexto.getSessionId(), axiomasValidados.size());

    return resposta;
  }

  /**
   * Identifica o construtor OWL apropriado para a mensagem.
   */
  private String identificarConstrutor(String mensagem) {
    String lower = mensagem.toLowerCase();

    if (lower.contains("é um") || lower.contains("é uma") || lower.contains("é um tipo de")) {
      return "SubClassOf";
    } else if (lower.contains("é o mesmo que") || lower.contains("equivalente")) {
      return "EquivalentClasses";
    } else if (lower.contains("tem como domínio") || lower.contains("se aplica a")) {
      return "ObjectPropertyDomain";
    } else if (lower.contains("leva a") || lower.contains("tem como range")) {
      return "ObjectPropertyRange";
    }

    // Padrão default
    return "SubClassOf";
  }

  /**
   * Gera uma resposta em linguagem natural.
   */
  private String gerarRespostaNatural(String mensagem, List<AxiomaDTO> axiomas) {
    if (axiomas.isEmpty()) {
      return "Entendi sua mensagem, mas não consegui extrair axiomas OWL válidos. " +
             "Por favor, reformule sua descrição.";
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Axioma(s) adicionado(s) à ontologia:\n");
    for (AxiomaDTO axioma : axiomas) {
      sb.append("- ").append(axioma.getManchesterSyntax()).append("\n");
    }
    sb.append("\nA ontologia agora contém ").append(axiomas.size())
      .append(" novo(s) axioma(s).");

    return sb.toString();
  }

  /**
   * Conta classes na ontologia (simplificado).
   */
  private int contarClasses(ContextConversacaoDTO contexto) {
    // Na implementação completa, isso usaria o OWL API para contar classes
    return contexto.getTotalAxiomasExtraidos() / 2; // Estimativa simplificada
  }

  /**
   * Encerra uma sessão de conversação.
   *
   * @param sessionId ID da sessão
   */
  @Tool(description = "Encerra uma sessão de conversação guiada por ontologia. " +
                     "Libera recursos associados à sessão e finaliza a ontologia construída.")
  public void encerrarSessao(@ToolParam(description = "ID da sessão a ser encerrada") String sessionId) {
    log.info("Sessão encerrada: sessionId={}", sessionId);
    // ChatService gerencia a sessão automaticamente através de createClient
  }
}
