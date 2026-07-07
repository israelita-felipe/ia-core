package com.ia.core.communication.service.model.mensagem.dto;

import com.ia.core.communication.model.mensagem.StatusMensagem;
import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.test.dto.CoreDTOUnitTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe MensagemDTO.
 */
@DisplayName("Testes de MensagemDTO")
class MensagemDTOTestCore extends CoreDTOUnitTest<MensagemDTO> {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Override
  protected Class<?> getDtoInterface() {
    return com.ia.core.service.dto.DTO.class;
  }

  @Test
  @DisplayName("Deve criar DTO com campos obrigatórios")
  void deveCriarDTOComCamposObrigatorios() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    assertThat(dto).isNotNull();
    assertThat(dto.getTelefoneDestinatario()).isEqualTo("11999999999");
    assertThat(dto.getCorpoMensagem()).isEqualTo("Olá, bem-vindo!");
    assertThat(dto.getTipoCanal()).isEqualTo(TipoCanal.WHATSAPP);
    assertThat(dto.getStatusMensagem()).isEqualTo(StatusMensagem.ENVIADA);
  }

  @Test
  @DisplayName("Deve validar campo telefoneDestinatario obrigatório")
  void deveValidarCampoTelefoneDestinatarioObrigatorio() {
    MensagemDTO dto = MensagemDTO.builder()
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    Set<ConstraintViolation<MensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(MensagemTranslator.VALIDATION.TELEFONE_DESTINATARIO_NOT_BLANK));
  }

  @Test
  @DisplayName("Deve validar tamanho máximo do telefoneDestinatario")
  void deveValidarTamanhoMaximoDoTelefoneDestinatario() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("1".repeat(21))
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    Set<ConstraintViolation<MensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(MensagemTranslator.VALIDATION.TELEFONE_DESTINATARIO_SIZE));
  }

  @Test
  @DisplayName("Deve validar tamanho máximo do nomeDestinatario")
  void deveValidarTamanhoMaximoDoNomeDestinatario() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .nomeDestinatario("a".repeat(101))
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    Set<ConstraintViolation<MensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(MensagemTranslator.VALIDATION.NOME_DESTINATARIO_SIZE));
  }

  @Test
  @DisplayName("Deve validar campo corpoMensagem obrigatório")
  void deveValidarCampoCorpoMensagemObrigatorio() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    Set<ConstraintViolation<MensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(MensagemTranslator.VALIDATION.CORPO_MENSAGEM_NOT_BLANK));
  }

  @Test
  @DisplayName("Deve validar campo tipoCanal obrigatório")
  void deveValidarCampoTipoCanalObrigatorio() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .corpoMensagem("Olá, bem-vindo!")
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    Set<ConstraintViolation<MensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(MensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL));
  }

  @Test
  @DisplayName("Deve validar campo statusMensagem obrigatório")
  void deveValidarCampoStatusMensagemObrigatorio() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    Set<ConstraintViolation<MensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(MensagemTranslator.VALIDATION.STATUS_MENSAGEM_NOT_NULL));
  }

  @Test
  @DisplayName("Deve clonar DTO usando toBuilder")
  void deveClonarDTOUsandoToBuilder() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .nomeDestinatario("João")
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    MensagemDTO clone = dto.toBuilder().build();

    assertThat(clone).isNotNull();
    assertThat(clone.getTelefoneDestinatario()).isEqualTo(dto.getTelefoneDestinatario());
    assertThat(clone.getNomeDestinatario()).isEqualTo(dto.getNomeDestinatario());
  }

  @Test
  @DisplayName("Deve copiar DTO usando copyObject")
  void deveCopiarDTOUsandoCopyObject() {
    MensagemDTO dto = MensagemDTO.builder()
        .id(1L)
        .version(1L)
        .telefoneDestinatario("11999999999")
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    MensagemDTO copy = dto.copyObject();

    assertThat(copy).isNotNull();
    assertThat(copy.getId()).isNull();
    assertThat(copy.getVersion()).isNull();
    assertThat(copy.getTelefoneDestinatario()).isEqualTo(dto.getTelefoneDestinatario());
  }

  @Test
  @DisplayName("Deve ter toString formatado")
  void deveTerToStringFormatado() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .nomeDestinatario("João")
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    String resultado = dto.toString();

    assertThat(resultado).isEqualTo("11999999999 -> WHATSAPP");
  }

  @Test
  @DisplayName("Deve ter classe CAMPOS com constantes")
  void deveTerClasseCAMPOSComConstantes() {
    assertThat(MensagemDTO.CAMPOS.TELEFONE_DESTINATARIO).isNotNull();
    assertThat(MensagemDTO.CAMPOS.NOME_DESTINATARIO).isNotNull();
    assertThat(MensagemDTO.CAMPOS.CORPO_MENSAGEM).isNotNull();
    assertThat(MensagemDTO.CAMPOS.TIPO_CANAL).isNotNull();
    assertThat(MensagemDTO.CAMPOS.STATUS_MENSAGEM).isNotNull();
    assertThat(MensagemDTO.CAMPOS.ID_EXTERNO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter método getSearchRequest")
  void deveTerMetodoGetSearchRequest() {
    var searchRequest = MensagemDTO.getSearchRequest();

    assertThat(searchRequest).isNotNull();
  }

  @Test
  @DisplayName("Deve ter método propertyFilters")
  void deveTerMetodoPropertyFilters() {
    var filters = MensagemDTO.propertyFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar contexto com variáveis")
  void deveRetornarContextoComVariaveis() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .nomeDestinatario("João")
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .dataEnvio(java.time.LocalDateTime.of(2024, 1, 15, 10, 30))
        .build();

    var context = dto.getContext();

    assertThat(context).isNotNull();
    assertThat(context).containsKey(com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate.TELEFONE);
    assertThat(context).containsKey(com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate.NOME);
    assertThat(context).containsKey(com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate.CORPO_MENSAGEM);
    assertThat(context).containsKey(com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate.DATA_ENVIO);
    assertThat(context).containsKey(com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate.STATUS);
  }

  @Test
  @DisplayName("Deve clonar DTO usando cloneObject")
  void deveClonarDTOUsandoCloneObject() {
    MensagemDTO dto = MensagemDTO.builder()
        .telefoneDestinatario("11999999999")
        .nomeDestinatario("João")
        .corpoMensagem("Olá, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .statusMensagem(StatusMensagem.ENVIADA)
        .build();

    MensagemDTO clone = dto.cloneObject();

    assertThat(clone).isNotNull();
    assertThat(clone.getTelefoneDestinatario()).isEqualTo(dto.getTelefoneDestinatario());
    assertThat(clone.getNomeDestinatario()).isEqualTo(dto.getNomeDestinatario());
  }

  @Test
  @DisplayName("Deve ter método CAMPOS.values()")
  void deveTerMetodoCAMPOSValues() {
    var values = MensagemDTO.CAMPOS.values();

    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
    assertThat(values).contains(MensagemDTO.CAMPOS.TELEFONE_DESTINATARIO);
    assertThat(values).contains(MensagemDTO.CAMPOS.NOME_DESTINATARIO);
    assertThat(values).contains(MensagemDTO.CAMPOS.CORPO_MENSAGEM);
  }
}
