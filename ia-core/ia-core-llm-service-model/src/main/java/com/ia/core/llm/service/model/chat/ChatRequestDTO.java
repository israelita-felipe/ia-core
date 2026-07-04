package com.ia.core.llm.service.model.chat;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * DTO para requisição de chat.
 * <p>
 * Contém o prompt opcional, texto da requisição e validações.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDTO
  implements DTO<String> {

  private Long promptId;

  @NotNull(message = ChatTranslator.VALIDATION.CHAT_NOT_BLANK)
  @Size(min = 1, max = 5000, message = ChatTranslator.VALIDATION.CHAT_NOT_BLANK)
  private String request;

  private String text;

  private String sessionId;

  @Override
  public ChatRequestDTO cloneObject() {
    return toBuilder().build();
  }

  public static class CAMPOS {
    public static final String PROMPT_ID = "promptId";
    public static final String REQUEST = "request";
    public static final String TEXT = "text";
    public static final String SESSION_ID = "sessionId";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(PROMPT_ID, REQUEST, TEXT, SESSION_ID, PROPERTY_CHANGE_SUPPORT);
    }
  }

}
