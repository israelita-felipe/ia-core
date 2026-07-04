package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO para resposta de sessão de agente.
 * <p>
 * Contém o identificador da sessão, mensagem e flag de confirmação pendente.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentSessionResponseDTO implements DTO<String> {

  private String sessionId;
  private String message;
  private boolean pendingConfirmation;

  @Override
  public AgentSessionResponseDTO cloneObject() {
    return AgentSessionResponseDTO.builder()
        .sessionId(sessionId)
        .message(message)
        .pendingConfirmation(pendingConfirmation)
        .build();
  }

  public static class CAMPOS {
    public static final String SESSION_ID = "sessionId";
    public static final String MESSAGE = "message";
    public static final String PENDING_CONFIRMATION = "pendingConfirmation";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(SESSION_ID, MESSAGE, PENDING_CONFIRMATION, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
