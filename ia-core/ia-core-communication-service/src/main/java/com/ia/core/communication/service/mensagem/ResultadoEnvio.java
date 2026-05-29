package com.ia.core.communication.service.mensagem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Resultado do envio de mensagem.
 * <p>
 * Encapsula o resultado de uma operação de envio de mensagem, incluindo
 * status de sucesso/erro, ID da mensagem e timestamp da operação.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoEnvio {
  private boolean sucesso;
  private String messageId;
  private String erro;
  private LocalDateTime timestamp;

  public static ResultadoEnvio sucesso(String messageId) {
    return ResultadoEnvio.builder()
        .sucesso(true)
        .messageId(messageId)
        .timestamp(LocalDateTime.now())
        .build();
  }

  public static ResultadoEnvio falha(String erro) {
    return ResultadoEnvio.builder()
        .sucesso(false)
        .erro(erro)
        .timestamp(LocalDateTime.now())
        .build();
  }
}
