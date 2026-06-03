package com.ia.core.llm.service.agente;

import com.ia.core.llm.service.audit.AiInteractionAuditService;
import com.ia.core.llm.service.chat.ChatApplicationService;
import com.ia.core.llm.service.config.LlmModuleProperties;
import com.ia.core.llm.service.model.agent.AgentConfirmationDTO;
import com.ia.core.llm.service.model.agent.AgentSessionRequestDTO;
import com.ia.core.llm.service.model.agent.AgentSessionResponseDTO;
import com.ia.core.llm.service.model.chat.ChatRequestDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaActivationDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaMetadataDTO;
import com.ia.core.llm.service.model.ferramenta.FerramentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serviço de orquestração de agentes.
 * <p>
 * Responsável por orquestrar a execução de sessões de agentes, gerenciando
 * confirmações pendentes e integração com ferramentas e chat.
 * <p>
 * **Padrão de encapsulamento:** Este serviço delega todas as operações de chat
 * ao {@link ChatApplicationService}, que encapsula a implementação do spring-ai-agent-utils.
 * Isso permite trocar por outra biblioteca/framework se necessário, sem afetar
 * a interface do AgentOrchestratorService.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class AgentOrchestratorService {

  private final FerramentaUseCase ferramentaUseCase;
  private final ChatApplicationService chatApplicationService;
  private final LlmModuleProperties properties;
  private final AiInteractionAuditService auditService;

  private final Map<String, PendingSession> pendingSessions = new ConcurrentHashMap<>();

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
}
