package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.audit.AiInteractionAuditService;
import com.ia.core.llm.service.chat.ChatApplicationService;
import com.ia.core.llm.service.config.LlmModuleProperties;
import com.ia.core.llm.service.model.agente.ContextoConversacao;
import com.ia.core.llm.service.model.agente.RequisicaoConstrucaoOntologia;
import com.ia.core.llm.service.model.agente.RespostaAgente;
import com.ia.core.llm.service.model.agente.ResultadoConstrucaoOntologia;
import com.ia.core.llm.service.model.agente.actions.AgentConfirmationDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.agente.session.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaActivationDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaMetadataDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaUseCase;
import com.ia.core.llm.service.model.validacao.ResultadoValidacao;
import com.ia.core.owl.service.DefaultOwlService;
import com.ia.core.owl.service.LLMCommunicator;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.owl.service.tool.base.OWLToolRegistry;
import com.ia.core.owl.service.validation.LoopLLMRaciocinador;
import com.ia.core.owl.service.validation.ValidadorOntologia;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço de orquestração de agentes.
 * <p>
 * Responsável por orquestrar a execução de sessões de agentes, gerenciando
 * confirmações pendentes e integração com skills e chat.
 * <p>
 * Também orquestra agentes guiados por ontologias, gerenciando sessões de conversação
 * e jobs de construção de ontologias.
 * <p>
 * **Padrão de encapsulamento:** Este serviço delega todas as operações de chat
 * ao {@link ChatApplicationService}, que encapsula a implementação do spring-ai-agent-utils.
 * Isso permite trocar por outra biblioteca/framework se necessário, sem afetar
 * a interface do AgentOrchestratorService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Service
public class AgentOrchestratorService {

  private final FerramentaUseCase ferramentaUseCase;
  private final ChatApplicationService chatApplicationService;
  private final LlmModuleProperties properties;
  private final AiInteractionAuditService auditService;

  private final Map<String, PendingSession> pendingSessions = new ConcurrentHashMap<>();
  private final Map<String, AgentTask> activeTasks = new ConcurrentHashMap<>();
  private final Map<String, ContextoConversacao> sessoesAtivas = new ConcurrentHashMap<>();

  // OWL-specific dependencies
  private AgenteConversacionalOntologiaService agenteConversacional;
  private AgenteConstrutorOntologiaService agenteConstrutor;
  private ValidadorOntologia validador;
  private OWLToolRegistry toolRegistry;

  // Constructor for general agent orchestration (without OWL)
  public AgentOrchestratorService(FerramentaUseCase ferramentaUseCase,
                                  ChatApplicationService chatApplicationService,
                                  LlmModuleProperties properties,
                                  AiInteractionAuditService auditService) {
    this.ferramentaUseCase = ferramentaUseCase;
    this.chatApplicationService = chatApplicationService;
    this.properties = properties;
    this.auditService = auditService;
    this.validador = null;
    this.toolRegistry = null;
  }

  // Constructor for OWL agent orchestration
  public AgentOrchestratorService(FerramentaUseCase ferramentaUseCase,
                                  ChatApplicationService chatApplicationService,
                                  LlmModuleProperties properties,
                                  AiInteractionAuditService auditService,
                                  ValidadorOntologia validador,
                                  OWLToolRegistry toolRegistry,
                                  ChatModel chatModel,
                                  LLMCommunicator llmCommunicator,
                                  DefaultOwlService owlService,
                                  LoopLLMRaciocinador reasonerLoop) {
    this(ferramentaUseCase, chatApplicationService, properties, auditService);
    this.validador = validador;
    this.toolRegistry = toolRegistry;
    this.agenteConversacional = new AgenteConversacionalOntologiaService(
        chatModel, llmCommunicator, owlService, validador, reasonerLoop, toolRegistry);
    this.agenteConstrutor = new AgenteConstrutorOntologiaService(
        chatModel, llmCommunicator, owlService, validador, reasonerLoop, toolRegistry);
  }

  @PostConstruct
  public void initializeDefaultAgents() {
    log.info("Inicializando agentes, ferramentas, skills e prompts padrão para OWL...");
    // TODO: Implementar inicialização de agentes OWL conforme CDU Seção 6.3.2
    // Este método será implementado em fases subsequentes
    log.info("Inicialização de agentes OWL pendente de implementação completa.");
  }

  public AgentSessionResponseDTO run(AgentSessionRequestDTO request) {
    String sessionId = request.getSessionId() != null ? request.getSessionId() : UUID.randomUUID().toString();
    String systemPrompt = buildSystemPrompt(request.getFerramentaId());
    ChatRequestDTO chatRequest = ChatRequestDTO.builder()
        .request(request.getUserMessage())
        .text(request.getUserMessage())
        .build();
    String response = chatApplicationService.ask(chatRequest, systemPrompt);
    auditService.record(request.getUserMessage(), null, null, response, request.getFerramentaId());
    if (requiresConfirmation(response)) {
      pendingSessions.put(sessionId, new PendingSession(request, response));
      return AgentSessionResponseDTO.builder()
          .sessionId(sessionId)
          .message(response)
          .pendingConfirmation(true)
          .build();
    }
    return AgentSessionResponseDTO.builder()
        .sessionId(sessionId)
        .message(response)
        .pendingConfirmation(false)
        .build();
  }

  public AgentSessionResponseDTO confirm(AgentConfirmationDTO confirmation) {
    PendingSession pending = pendingSessions.remove(confirmation.getSessionId());
    if (pending == null) {
      return AgentSessionResponseDTO.builder()
          .sessionId(confirmation.getSessionId())
          .message("Sessão expirada ou inexistente.")
          .pendingConfirmation(false)
          .build();
    }
    if (!confirmation.isConfirmed()) {
      return AgentSessionResponseDTO.builder()
          .sessionId(confirmation.getSessionId())
          .message("Ação cancelada pelo utilizador.")
          .pendingConfirmation(false)
          .build();
    }
    return run(AgentSessionRequestDTO.builder()
        .sessionId(confirmation.getSessionId())
        .userMessage(confirmation.getUserMessage() != null
            ? confirmation.getUserMessage()
            : pending.request().getUserMessage())
        .ferramentaId(pending.request().getFerramentaId())
        .build());
  }

  public List<FerramentaMetadataDTO> listAvailableFerramentas() {
    return ferramentaUseCase.listMetadata();
  }

  /**
   * Cria e inicia uma task assíncrona para um agente.
   * Pode ser usado por qualquer agente para operações de longa duração.
   *
   * @param agenteId ID do agente
   * @param taskData Dados específicos da task
   * @return Task criada
   */
  public AgentTask createTask(String agenteId, Map<String, Object> taskData) {
    String taskId = UUID.randomUUID().toString();

    AgentTask task = AgentTask.builder()
        .taskId(taskId)
        .agenteId(agenteId)
        .status("QUEUED")
        .progress(0)
        .currentPhase("INITIALIZATION")
        .taskData(taskData)
        .startTime(LocalDateTime.now())
        .build();

    activeTasks.put(taskId, task);

    // Executa task de forma assíncrona (simplificado - na prática usar @Async)
    executeTask(task);

    return task;
  }

  /**
   * Obtém o progresso de uma task.
   *
   * @param taskId ID da task
   * @return Task com progresso atual
   */
  public AgentTask getTaskProgress(String taskId) {
    return activeTasks.get(taskId);
  }

  /**
   * Cancela uma task em execução.
   *
   * @param taskId ID da task
   * @return Task cancelada
   */
  public AgentTask cancelTask(String taskId) {
    AgentTask task = activeTasks.get(taskId);
    if (task != null && !isTerminalStatus(task.getStatus())) {
      task.setStatus("CANCELLED");
      task.setEndTime(LocalDateTime.now());
    }
    return task;
  }

  private void executeTask(AgentTask task) {
    // Implementação genérica que pode ser estendida
    // por cada tipo de agente através de estratégias
    // Por enquanto, apenas marca como RUNNING
    task.setStatus("RUNNING");
  }

  private boolean isTerminalStatus(String status) {
    return "COMPLETED".equals(status) || "FAILED".equals(status) || "CANCELLED".equals(status);
  }

  private String buildSystemPrompt(Long ferramentaId) {
    if (ferramentaId == null) {
      return "Você é o orquestrador " + properties.getAgent().getOrchestratorId()
          + ". Delegue tarefas às ferramentas disponíveis quando necessário.";
    }
    FerramentaActivationDTO ferramenta = ferramentaUseCase.loadForActivation(ferramentaId);
    StringBuilder sb = new StringBuilder();
    sb.append("# Ferramenta: ").append(ferramenta.getTitulo()).append("\n\n");
    if (ferramenta.getInstrucoes() != null) {
      sb.append(ferramenta.getInstrucoes()).append("\n\n");
    }
    if (ferramenta.getSubFerramentas() != null && !ferramenta.getSubFerramentas().isEmpty()) {
      sb.append("Ferramentas autorizadas:\n");
      ferramenta.getSubFerramentas().forEach(f ->
          sb.append("- ").append(f.getIdentificador()).append(": ").append(f.getDescricao()).append("\n"));
    }
    return sb.toString();
  }

  private boolean requiresConfirmation(String response) {
    return response != null && response.contains("[[CONFIRM]]");
  }

  private record PendingSession(AgentSessionRequestDTO request, String lastResponse) {}

  /**
   * Classe genérica para representar tasks de agentes.
   * Substitui o conceito específico de Job do AgenteConstrutorOntologiaService.
   */
  @lombok.Builder
  @lombok.Data
  @lombok.NoArgsConstructor
  @lombok.AllArgsConstructor
  public static class AgentTask {
    private String taskId;
    private String agenteId;
    private String status;
    private int progress;
    private String currentPhase;
    private Map<String, Object> taskData;
    private Map<String, Object> resultData;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long processingTimeMs;
    private String errorMessage;
  }

  // ==================== OWL-Specific Methods ====================

  /**
   * Cria uma nova sessão de conversação.
   *
   * @param userId ID do usuário
   * @param dominio domínio da conversação
   * @return contexto da conversação criado
   */
  public ContextoConversacao criarSessaoConversacional(String userId, String dominio) {
    if (agenteConversacional == null) {
      throw new IllegalStateException("Agente conversacional não inicializado. Forneça dependências OWL no construtor.");
    }
    ContextoConversacao contexto = agenteConversacional.criarSessao(userId, dominio);
    sessoesAtivas.put(contexto.getSessionId(), contexto);
    log.info("Sessão conversacional criada: sessionId={}, userId={}",
             contexto.getSessionId(), userId);
    return contexto;
  }

  /**
   * Processa uma mensagem em uma sessão de conversação.
   *
   * @param sessionId ID da sessão
   * @param mensagem mensagem do usuário
   * @return resposta do agente
   */
  public RespostaAgente processarMensagemConversacional(String sessionId, String mensagem) {
    if (agenteConversacional == null) {
      throw new IllegalStateException("Agente conversacional não inicializado. Forneça dependências OWL no construtor.");
    }
    ContextoConversacao contexto = sessoesAtivas.get(sessionId);
    if (contexto == null) {
      throw new IllegalArgumentException("Sessão não encontrada: " + sessionId);
    }

    RespostaAgente resposta = agenteConversacional.processarMensagem(contexto, mensagem);
    sessoesAtivas.put(sessionId, contexto); // Atualiza contexto
    return resposta;
  }

  /**
   * Obtém o contexto de uma sessão de conversação.
   *
   * @param sessionId ID da sessão
   * @return contexto da conversação
   */
  public ContextoConversacao obterContextoSessao(String sessionId) {
    return sessoesAtivas.get(sessionId);
  }

  /**
   * Encerra uma sessão de conversação.
   *
   * @param sessionId ID da sessão
   */
  public void encerrarSessaoConversacional(String sessionId) {
    if (agenteConversacional == null) {
      throw new IllegalStateException("Agente conversacional não inicializado. Forneça dependências OWL no construtor.");
    }
    ContextoConversacao contexto = sessoesAtivas.remove(sessionId);
    if (contexto != null) {
      agenteConversacional.encerrarSessao(sessionId);
      log.info("Sessão conversacional encerrada: sessionId={}", sessionId);
    }
  }

  /**
   * Inicia um job de construção de ontologia.
   *
   * @param requisicao requisição de construção
   * @return resultado inicial com jobId
   */
  public ResultadoConstrucaoOntologia iniciarConstrucaoOntologia(RequisicaoConstrucaoOntologia requisicao) {
    if (agenteConstrutor == null) {
      throw new IllegalStateException("Agente construtor não inicializado. Forneça dependências OWL no construtor.");
    }
    log.info("Iniciando construção de ontologia: dominio={}", requisicao.getDomain());
    return agenteConstrutor.iniciarConstrucao(requisicao);
  }

  /**
   * Obtém o progresso de um job de construção.
   *
   * @param jobId ID do job
   * @return resultado com progresso atual
   */
  public ResultadoConstrucaoOntologia obterProgressoConstrucao(String jobId) {
    if (agenteConstrutor == null) {
      throw new IllegalStateException("Agente construtor não inicializado. Forneça dependências OWL no construtor.");
    }
    return agenteConstrutor.obterProgresso(jobId);
  }

  /**
   * Obtém o resultado final de um job de construção.
   *
   * @param jobId ID do job
   * @return resultado final com ontologia
   */
  public ResultadoConstrucaoOntologia obterResultadoConstrucao(String jobId) {
    if (agenteConstrutor == null) {
      throw new IllegalStateException("Agente construtor não inicializado. Forneça dependências OWL no construtor.");
    }
    return agenteConstrutor.obterResultado(jobId);
  }

  /**
   * Cancela um job de construção em execução.
   *
   * @param jobId ID do job
   * @return resultado do cancelamento
   */
  public ResultadoConstrucaoOntologia cancelarConstrucao(String jobId) {
    if (agenteConstrutor == null) {
      throw new IllegalStateException("Agente construtor não inicializado. Forneça dependências OWL no construtor.");
    }
    log.info("Cancelando construção: jobId={}", jobId);
    return agenteConstrutor.cancelarJob(jobId);
  }

  /**
   * Valida uma ontologia atual.
   *
   * @return resultado da validação
   */
  public ResultadoValidacao validarOntologiaAtual() {
    if (validador == null) {
      throw new IllegalStateException("Validador não inicializado. Forneça dependências OWL no construtor.");
    }
    log.debug("Validando ontologia atual");
    return validador.validarOntologiaAtual();
  }

  /**
   * Valida uma lista de axiomas.
   *
   * @param axiomas lista de axiomas a validar
   * @return resultado da validação
   */
  public ResultadoValidacao validarAxiomas(List<AxiomaDTO> axiomas) {
    if (validador == null) {
      throw new IllegalStateException("Validador não inicializado. Forneça dependências OWL no construtor.");
    }
    log.debug("Validando {} axiomas", axiomas.size());
    return validador.validarAxiomas(axiomas);
  }

  /**
   * Obtém a lista de construtores OWL 2 DL disponíveis.
   *
   * @return lista de nomes de construtores
   */
  public List<String> obterConstrutoresDisponiveis() {
    if (toolRegistry == null) {
      throw new IllegalStateException("ToolRegistry não inicializado. Forneça dependências OWL no construtor.");
    }
    return toolRegistry.getAvailableConstructors();
  }

  /**
   * Obtém estatísticas do orquestrador.
   *
   * @return mapa com estatísticas
   */
  public Map<String, Object> obterEstatisticas() {
    Map<String, Object> stats = new ConcurrentHashMap<>();
    stats.put("sessoesAtivas", sessoesAtivas.size());
    stats.put("tasksAtivas", activeTasks.size());
    if (toolRegistry != null) {
      stats.put("construtoresDisponiveis", toolRegistry.getAvailableConstructors().size());
    }
    stats.put("validadorAtivo", validador != null);
    return stats;
  }
}
