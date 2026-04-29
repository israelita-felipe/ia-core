package com.ia.core.communication.service.mensagem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Resultado do envio de mensagem.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa os serviços de negócio para resultado envio.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a ResultadoEnvio
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
