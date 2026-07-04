package com.ia.core.llm.service.model.session;

import com.ia.core.llm.service.model.chat.ChatTranslator;
import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO para requisição de sessão de agente.
 * <p>
 * Contém a mensagem do usuário, ferramenta opcional e identificador de sessão.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentSessionRequestDTO
  implements DTO<String> {

  @NotBlank(message = ChatTranslator.VALIDATION.CHAT_NOT_BLANK)
  private String userMessage;

  private Long ferramentaId;

  private String sessionId;

  @Override
  public AgentSessionRequestDTO cloneObject() {
    return AgentSessionRequestDTO.builder()
        .userMessage(userMessage)
        .ferramentaId(ferramentaId)
        .sessionId(sessionId)
        .build();
  }

  public static class CAMPOS {
    public static final String USER_MESSAGE = "userMessage";
    public static final String FERRAMENTA_ID = "ferramentaId";
    public static final String SESSION_ID = "sessionId";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(USER_MESSAGE, FERRAMENTA_ID, SESSION_ID, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
