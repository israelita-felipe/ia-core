package com.ia.core.llm.service.model.chat;

import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
/**
 * Classe que representa o objeto de transferência de dados para chat request.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ChatRequestDTO
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDTO
  implements DTO<String> {

  private Long comandoSistemaID;

  @NotNull(message = "{validation.chat.request.required}")
  @Size(min = 1, max = 5000, message = "{validation.chat.request.size}")
  private String request;

  private String text;

  @Override
  public ChatRequestDTO cloneObject() {
    return toBuilder().build();
  }

}
