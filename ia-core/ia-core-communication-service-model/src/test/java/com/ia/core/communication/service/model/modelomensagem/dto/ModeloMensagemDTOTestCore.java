package com.ia.core.communication.service.model.modelomensagem.dto;

import com.ia.core.communication.model.mensagem.TipoCanal;
import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe ModeloMensagemDTO.
 */
@DisplayName("Testes de ModeloMensagemDTO")
class ModeloMensagemDTOTestCore extends CoreBaseDTOUnitTest<ModeloMensagemDTO> {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Override
  protected Class<?> getDtoInterface() {
    return com.ia.core.service.dto.DTO.class;
  }

  @Test
  @DisplayName("Deve criar DTO com campos obrigatórios")
  void deveCriarDTOComCamposObrigatorios() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    assertThat(dto).isNotNull();
    assertThat(dto.getNome()).isEqualTo("Modelo de Boas Vindas");
    assertThat(dto.getCorpoModelo()).isEqualTo("Olá {{nome}}, bem-vindo!");
    assertThat(dto.getTipoCanal()).isEqualTo(TipoCanal.WHATSAPP);
  }

  @Test
  @DisplayName("Deve validar campo nome obrigatório")
  void deveValidarCampoNomeObrigatorio() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    Set<ConstraintViolation<ModeloMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ModeloMensagemTranslator.VALIDATION.NOME_NOT_BLANK));
  }

  @Test
  @DisplayName("Deve validar tamanho máximo do nome")
  void deveValidarTamanhoMaximoDoNome() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("a".repeat(101))
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    Set<ConstraintViolation<ModeloMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ModeloMensagemTranslator.VALIDATION.NOME_SIZE));
  }

  @Test
  @DisplayName("Deve validar tamanho máximo da descricao")
  void deveValidarTamanhoMaximoDaDescricao() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .descricao("a".repeat(501))
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    Set<ConstraintViolation<ModeloMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ModeloMensagemTranslator.VALIDATION.DESCRICAO_SIZE));
  }

  @Test
  @DisplayName("Deve validar campo corpoModelo obrigatório")
  void deveValidarCampoCorpoModeloObrigatorio() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    Set<ConstraintViolation<ModeloMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ModeloMensagemTranslator.VALIDATION.CORPO_MODELO_NOT_BLANK));
  }

  @Test
  @DisplayName("Deve validar campo tipoCanal obrigatório")
  void deveValidarCampoTipoCanalObrigatorio() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .build();

    Set<ConstraintViolation<ModeloMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ModeloMensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL));
  }

  @Test
  @DisplayName("Deve clonar DTO usando toBuilder")
  void deveClonarDTOUsandoToBuilder() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .descricao("Modelo para novos membros")
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .ativo(true)
        .build();

    ModeloMensagemDTO clone = dto.toBuilder().build();

    assertThat(clone).isNotNull();
    assertThat(clone.getNome()).isEqualTo(dto.getNome());
    assertThat(clone.getDescricao()).isEqualTo(dto.getDescricao());
    assertThat(clone.getAtivo()).isEqualTo(dto.getAtivo());
  }


  @Test
  @DisplayName("Deve implementar HasVariavel")
  void deveImplementarHasVariavel() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    assertThat(dto).isInstanceOf(HasVariavel.class);
  }

  @Test
  @DisplayName("Deve retornar contexto com variáveis")
  void deveRetornarContextoComVariaveis() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .descricao("Modelo para novos membros")
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .ativo(true)
        .build();

    Map<Variavel, Object> contexto = dto.getContext();

    assertThat(contexto).isNotNull();
    assertThat(contexto).containsKey(VariavelTemplate.NOME_MODELO);
    assertThat(contexto).containsKey(VariavelTemplate.DESCRICAO_MODELO);
    assertThat(contexto).containsKey(VariavelTemplate.CORPO_MODELO);
    assertThat(contexto).containsKey(VariavelTemplate.TIPO_CANAL);
    assertThat(contexto).containsKey(VariavelTemplate.ATIVO_MODELO);
  }

  @Test
  @DisplayName("Deve ter toString formatado")
  void deveTerToStringFormatado() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .build();

    String resultado = dto.toString();

    assertThat(resultado).isEqualTo("Modelo de Boas Vindas");
  }

  @Test
  @DisplayName("Deve ter classe CAMPOS com constantes")
  void deveTerClasseCAMPOSComConstantes() {
    assertThat(ModeloMensagemDTO.CAMPOS.NOME).isNotNull();
    assertThat(ModeloMensagemDTO.CAMPOS.DESCRICAO).isNotNull();
    assertThat(ModeloMensagemDTO.CAMPOS.CORPO_MODELO).isNotNull();
    assertThat(ModeloMensagemDTO.CAMPOS.TIPO_CANAL).isNotNull();
    assertThat(ModeloMensagemDTO.CAMPOS.ATIVO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter método getSearchRequest")
  void deveTerMetodoGetSearchRequest() {
    var searchRequest = ModeloMensagemDTO.getSearchRequest();

    assertThat(searchRequest).isNotNull();
  }

  @Test
  @DisplayName("Deve ter método propertyFilters")
  void deveTerMetodoPropertyFilters() {
    var filters = ModeloMensagemDTO.propertyFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve clonar DTO usando cloneObject")
  void deveClonarDTOUsandoCloneObject() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .nome("Modelo de Boas Vindas")
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .ativo(true)
        .build();

    ModeloMensagemDTO clone = dto.cloneObject();

    assertThat(clone).isNotNull();
    assertThat(clone.getNome()).isEqualTo(dto.getNome());
    assertThat(clone.getCorpoModelo()).isEqualTo(dto.getCorpoModelo());
  }

  @Test
  @DisplayName("Deve copiar DTO usando copyObject")
  void deveCopiarDTOUsandoCopyObject() {
    ModeloMensagemDTO dto = ModeloMensagemDTO.builder()
        .id(1L)
        .version(1L)
        .nome("Modelo de Boas Vindas")
        .corpoModelo("Olá {{nome}}, bem-vindo!")
        .tipoCanal(TipoCanal.WHATSAPP)
        .ativo(true)
        .build();

    ModeloMensagemDTO copy = dto.copyObject();

    assertThat(copy).isNotNull();
    assertThat(copy.getId()).isNull();
    assertThat(copy.getVersion()).isNull();
    assertThat(copy.getNome()).isEqualTo(dto.getNome());
  }

  @Test
  @DisplayName("Deve ter método CAMPOS.values()")
  void deveTerMetodoCAMPOSValues() {
    var values = ModeloMensagemDTO.CAMPOS.values();

    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
    assertThat(values).contains(ModeloMensagemDTO.CAMPOS.NOME);
    assertThat(values).contains(ModeloMensagemDTO.CAMPOS.CORPO_MODELO);
    assertThat(values).contains(ModeloMensagemDTO.CAMPOS.TIPO_CANAL);
  }
}
