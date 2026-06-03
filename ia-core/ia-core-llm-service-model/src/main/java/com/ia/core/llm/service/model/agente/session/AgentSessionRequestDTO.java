package com.ia.core.llm.service.model.agente.session;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @NotBlank
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
}
