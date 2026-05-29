package com.ia.core.communication.service.model.enviomensagem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para resposta de envio de mensagem em massa.
 * <p>
 * Representa os dados de transferência para resposta de envio de mensagens,
 * incluindo total de enviados, total de falhas, mensagens de falha, data e status geral.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvioMensagemResponseDTO {
  private int totalEnviados;
  private int totalFalhas;
  private List<String> mensagensFalhas;
  private LocalDateTime dataEnvio;
  private String statusGeral;

  public static EnvioMensagemResponseDTO sucesso(int total) {
    return EnvioMensagemResponseDTO.builder()
        .totalEnviados(total)
        .totalFalhas(0)
        .dataEnvio(LocalDateTime.now())
        .statusGeral("SUCESSO")
        .build();
  }

  public static EnvioMensagemResponseDTO parcial(int enviados, int falhas, List<String> falhasList) {
    return EnvioMensagemResponseDTO.builder()
        .totalEnviados(enviados)
        .totalFalhas(falhas)
        .mensagensFalhas(falhasList)
        .dataEnvio(LocalDateTime.now())
        .statusGeral("PARCIAL")
        .build();
  }

  public static EnvioMensagemResponseDTO comFalhas(int enviados, List<String> falhasList) {
    return EnvioMensagemResponseDTO.builder()
        .totalEnviados(enviados)
        .totalFalhas(falhasList.size())
        .mensagensFalhas(falhasList)
        .dataEnvio(LocalDateTime.now())
        .statusGeral("PARCIAL")
        .build();
  }
}
