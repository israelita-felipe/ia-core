package com.ia.core.llm.service.model.chat;

import com.ia.core.llm.model.chat.ChatSession;
import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.model.HasVersion;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO para sessão de chat.
 * <p>
 * Representa uma sessão de conversação interativa entre usuário e agente LLM.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatSessionDTO
  extends AbstractBaseEntityDTO<ChatSession> {

  public static SearchRequestDTO getSearchRequest() {
    return new ChatSessionSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Identificador único da sessão (UUID).
   */
  @NotNull(message = ChatTranslator.VALIDATION.CHAT_NOT_BLANK)
  @Size(min = 1, max = 100)
  private String sessionId;

  /**
   * Título da sessão.
   */
  @Size(max = 200)
  private String titulo;

  /**
   * Data e hora de início da sessão.
   */
  @NotNull
  private LocalDateTime dataInicio;

  /**
   * Data e hora de fim da sessão.
   */
  private LocalDateTime dataFim;

  /**
   * Status da sessão.
   */
  @Default
  private ChatSessionStatus status = ChatSessionStatus.ATIVA;

  /**
   * Agente associado à sessão.
   */
  @NotNull
  private AgenteDTO agente;

  /**
   * ID do usuário que iniciou a sessão.
   */
  @Size(max = 100)
  private String usuarioId;

  /**
   * Status da sessão de chat.
   */
  public enum ChatSessionStatus {
    ATIVA,
    ENCERRADA,
    PAUSADA
  }

  @Override
  public void setVersion(Long version) {
    super.setVersion(version);
  }

  @Override
  public ChatSessionDTO cloneObject() {
    return toBuilder()
        .id(null)
        .version(HasVersion.DEFAULT_VERSION)
        .build();
  }

  /**
   * Constantes de campos para referência type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
    public static final String SESSION_ID = "sessionId";
    public static final String TITULO = "titulo";
    public static final String DATA_INICIO = "dataInicio";
    public static final String DATA_FIM = "dataFim";
    public static final String STATUS = "status";
    public static final String AGENTE = "agente";
    public static final String USUARIO_ID = "usuarioId";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(SESSION_ID, TITULO, DATA_INICIO, DATA_FIM, STATUS, AGENTE, USUARIO_ID, PROPERTY_CHANGE_SUPPORT);
    }
  }
}
