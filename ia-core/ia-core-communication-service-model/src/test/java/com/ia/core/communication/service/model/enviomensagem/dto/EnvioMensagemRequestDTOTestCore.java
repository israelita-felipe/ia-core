package com.ia.core.communication.service.model.enviomensagem.dto;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe EnvioMensagemRequestDTO.
 */
@DisplayName("Testes de EnvioMensagemRequestDTO")
class EnvioMensagemRequestDTOTestCore extends CoreBaseDTOUnitTest<EnvioMensagemRequestDTO> {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Override
  protected Class<?> getDtoInterface() {
    return com.ia.core.service.dto.DTO.class;
  }

  @Test
  @DisplayName("Deve criar DTO com campos obrigatórios")
  void deveCriarDTOComCamposObrigatorios() {
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .build();

    assertThat(dto).isNotNull();
    assertThat(dto.getTipoCanal()).isEqualTo(TipoCanal.WHATSAPP);
    assertThat(dto.getCorpoMensagem()).isEqualTo("Olá, bem-vindo!");
  }

  @Test
  @DisplayName("Deve validar campo tipoCanal obrigatório")
  void deveValidarCampoTipoCanalObrigatorio() {
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .corpoMensagem("Olá, bem-vindo!")
        .build();

    Set<ConstraintViolation<EnvioMensagemRequestDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(EnvioMensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL));
  }

  @Test
  @DisplayName("Deve validar campo corpoMensagem obrigatório")
  void deveValidarCampoCorpoMensagemObrigatorio() {
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    Set<ConstraintViolation<EnvioMensagemRequestDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(EnvioMensagemTranslator.VALIDATION.CORPO_MENSAGEM_NOT_BLANK));
  }

  @Test
  @DisplayName("Deve ter valor padrão para agendado")
  void deveTerValorPadraoParaAgendado() {
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .build();

    assertThat(dto.getAgendado()).isFalse();
  }

  @Test
  @DisplayName("Deve implementar HasVariavel")
  void deveImplementarHasVariavel() {
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .build();

    assertThat(dto).isInstanceOf(com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel.class);
  }

  @Test
  @DisplayName("Deve retornar contexto com variáveis")
  void deveRetornarContextoComVariaveis() {
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .build();

    Map<Variavel, Object> contexto = dto.getContext();

    assertThat(contexto).isNotNull();
    assertThat(contexto).containsKey(VariavelTemplate.TIPO_CANAL);
    assertThat(contexto).containsKey(VariavelTemplate.CORPO_MENSAGEM);
    assertThat(contexto.get(VariavelTemplate.TIPO_CANAL)).isEqualTo(TipoCanal.WHATSAPP);
    assertThat(contexto.get(VariavelTemplate.CORPO_MENSAGEM)).isEqualTo("Olá, bem-vindo!");
  }

  @Test
  @DisplayName("Deve ter campo modeloMensagemId")
  void deveTerCampoModeloMensagemId() {
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .modeloMensagemId(1L)
        .build();

    assertThat(dto.getModeloMensagemId()).isEqualTo(1L);
  }

  @Test
  @DisplayName("Deve ter campo parametrosTemplate")
  void deveTerCampoParametrosTemplate() {
    Map<String, String> parametros = Map.of("nome", "João");
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá {{nome}}, bem-vindo!")
        .parametrosTemplate(parametros)
        .build();

    assertThat(dto.getParametrosTemplate()).isEqualTo(parametros);
  }

  @Test
  @DisplayName("Deve ter campo telefones")
  void deveTerCampoTelefones() {
    List<String> telefones = List.of("11999999999", "11999999998");
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .telefones(telefones)
        .build();

    assertThat(dto.getTelefones()).isEqualTo(telefones);
  }

  @Test
  @DisplayName("Deve ter campo gruposContatoIds")
  void deveTerCampoGruposContatoIds() {
    List<Long> grupos = List.of(1L, 2L);
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .gruposContatoIds(grupos)
        .build();

    assertThat(dto.getGruposContatoIds()).isEqualTo(grupos);
  }

  @Test
  @DisplayName("Deve ter campo dataAgendamento")
  void deveTerCampoDataAgendamento() {
    LocalDateTime dataAgendamento = LocalDateTime.now().plusDays(1);
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .agendado(true)
        .dataAgendamento(dataAgendamento)
        .build();

    assertThat(dto.getDataAgendamento()).isEqualTo(dataAgendamento);
  }

  @Test
  @DisplayName("Deve clonar DTO usando toBuilder")
  void deveClonarDTOUsandoToBuilder() {
    EnvioMensagemRequestDTO dto = EnvioMensagemRequestDTO.builder()
        .tipoCanal(TipoCanal.WHATSAPP)
        .corpoMensagem("Olá, bem-vindo!")
        .modeloMensagemId(1L)
        .build();

    EnvioMensagemRequestDTO clone = dto.toBuilder().build();

    assertThat(clone).isNotNull();
    assertThat(clone.getTipoCanal()).isEqualTo(dto.getTipoCanal());
    assertThat(clone.getCorpoMensagem()).isEqualTo(dto.getCorpoMensagem());
    assertThat(clone.getModeloMensagemId()).isEqualTo(dto.getModeloMensagemId());
  }
}
