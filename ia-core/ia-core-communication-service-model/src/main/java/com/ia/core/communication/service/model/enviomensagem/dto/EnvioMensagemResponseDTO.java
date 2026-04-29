package com.ia.core.communication.service.model.enviomensagem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para resposta de envio de mensagem em massa.
 *
 * @author Israel Araújo
 */
/**
 * Classe que representa o objeto de transferência de dados para envio mensagem response.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a EnvioMensagemResponseDTO
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
