package com.ia.core.llm.service.model.session;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO para confirmação de ação de agente.
 * <p>
 * Utilizado para confirmar ou cancelar ações que requerem aprovação do usuário.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentConfirmationDTO implements DTO<String> {

  private String sessionId;
  private boolean confirmed;
  private String userMessage;

  @Override
  public AgentConfirmationDTO cloneObject() {
    return AgentConfirmationDTO.builder()
        .sessionId(sessionId)
        .confirmed(confirmed)
        .userMessage(userMessage)
        .build();
  }

  public static class CAMPOS {
    public static final String SESSION_ID = "sessionId";
    public static final String CONFIRMED = "confirmed";
    public static final String USER_MESSAGE = "userMessage";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(SESSION_ID, CONFIRMED, USER_MESSAGE, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
