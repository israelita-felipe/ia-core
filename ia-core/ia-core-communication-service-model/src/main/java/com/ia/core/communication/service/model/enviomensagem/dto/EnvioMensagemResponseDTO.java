package com.ia.core.communication.service.model.enviomensagem.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de envio de mensagem em massa.
 *
 * @author Israel Araújo
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
}
