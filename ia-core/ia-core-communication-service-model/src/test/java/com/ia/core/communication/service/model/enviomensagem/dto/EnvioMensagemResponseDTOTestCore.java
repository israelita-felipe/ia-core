package com.ia.core.communication.service.model.enviomensagem.dto;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe EnvioMensagemResponseDTO.
 */
@DisplayName("Testes de EnvioMensagemResponseDTO")
class EnvioMensagemResponseDTOTestCore extends CoreBaseDTOUnitTest<EnvioMensagemResponseDTO> {

  @Override
  protected Class<?> getDtoInterface() {
    return com.ia.core.service.dto.DTO.class;
  }

  @Test
  @DisplayName("Deve criar DTO usando builder")
  void deveCriarDTOUsandoBuilder() {
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.builder()
        .totalEnviados(10)
        .totalFalhas(2)
        .mensagensFalhas(List.of("Erro 1", "Erro 2"))
        .dataEnvio(LocalDateTime.now())
        .statusGeral("PARCIAL")
        .build();

    assertThat(dto).isNotNull();
    assertThat(dto.getTotalEnviados()).isEqualTo(10);
    assertThat(dto.getTotalFalhas()).isEqualTo(2);
    assertThat(dto.getStatusGeral()).isEqualTo("PARCIAL");
  }

  @Test
  @DisplayName("Deve ter campo totalEnviados")
  void deveTerCampoTotalEnviados() {
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.builder()
        .totalEnviados(10)
        .build();

    assertThat(dto.getTotalEnviados()).isEqualTo(10);
  }

  @Test
  @DisplayName("Deve ter campo totalFalhas")
  void deveTerCampoTotalFalhas() {
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.builder()
        .totalFalhas(2)
        .build();

    assertThat(dto.getTotalFalhas()).isEqualTo(2);
  }

  @Test
  @DisplayName("Deve ter campo mensagensFalhas")
  void deveTerCampoMensagensFalhas() {
    List<String> falhas = List.of("Erro 1", "Erro 2");
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.builder()
        .mensagensFalhas(falhas)
        .build();

    assertThat(dto.getMensagensFalhas()).isEqualTo(falhas);
  }

  @Test
  @DisplayName("Deve ter campo dataEnvio")
  void deveTerCampoDataEnvio() {
    LocalDateTime dataEnvio = LocalDateTime.now();
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.builder()
        .dataEnvio(dataEnvio)
        .build();

    assertThat(dto.getDataEnvio()).isEqualTo(dataEnvio);
  }

  @Test
  @DisplayName("Deve ter campo statusGeral")
  void deveTerCampoStatusGeral() {
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.builder()
        .statusGeral("SUCESSO")
        .build();

    assertThat(dto.getStatusGeral()).isEqualTo("SUCESSO");
  }

  @Test
  @DisplayName("Deve criar resposta de sucesso")
  void deveCriarRespostaDeSucesso() {
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.sucesso(10);

    assertThat(dto).isNotNull();
    assertThat(dto.getTotalEnviados()).isEqualTo(10);
    assertThat(dto.getTotalFalhas()).isEqualTo(0);
    assertThat(dto.getDataEnvio()).isNotNull();
    assertThat(dto.getStatusGeral()).isEqualTo("SUCESSO");
  }

  @Test
  @DisplayName("Deve criar resposta parcial")
  void deveCriarRespostaParcial() {
    List<String> falhas = List.of("Erro 1", "Erro 2");
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.parcial(8, 2, falhas);

    assertThat(dto).isNotNull();
    assertThat(dto.getTotalEnviados()).isEqualTo(8);
    assertThat(dto.getTotalFalhas()).isEqualTo(2);
    assertThat(dto.getMensagensFalhas()).isEqualTo(falhas);
    assertThat(dto.getDataEnvio()).isNotNull();
    assertThat(dto.getStatusGeral()).isEqualTo("PARCIAL");
  }

  @Test
  @DisplayName("Deve criar resposta com falhas")
  void deveCriarRespostaComFalhas() {
    List<String> falhas = List.of("Erro 1", "Erro 2", "Erro 3");
    EnvioMensagemResponseDTO dto = EnvioMensagemResponseDTO.comFalhas(5, falhas);

    assertThat(dto).isNotNull();
    assertThat(dto.getTotalEnviados()).isEqualTo(5);
    assertThat(dto.getTotalFalhas()).isEqualTo(3);
    assertThat(dto.getMensagensFalhas()).isEqualTo(falhas);
    assertThat(dto.getDataEnvio()).isNotNull();
    assertThat(dto.getStatusGeral()).isEqualTo("PARCIAL");
  }
}
