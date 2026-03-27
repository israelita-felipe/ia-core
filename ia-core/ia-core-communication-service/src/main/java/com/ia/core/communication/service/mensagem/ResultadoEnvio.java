package com.ia.core.communication.service.mensagem;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resultado do envio de mensagem.
 *
 * @author Israel Araújo
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
