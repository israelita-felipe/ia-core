package com.ia.core.communication.service.model.grupocontato.dto;

import com.ia.core.service.dto.CoreBaseDTOUnitTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe GrupoContatoDTO.
 */
@DisplayName("Testes de GrupoContatoDTO")
class GrupoContatoDTOTestCore extends CoreBaseDTOUnitTest<GrupoContatoDTO> {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Override
  protected Class<?> getDtoInterface() {
    return com.ia.core.service.dto.DTO.class;
  }

  @Test
  @DisplayName("Deve criar DTO com campos obrigatórios")
  void deveCriarDTOComCamposObrigatorios() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .nome("Grupo Teste")
        .build();

    assertThat(dto).isNotNull();
    assertThat(dto.getNome()).isEqualTo("Grupo Teste");
  }

  @Test
  @DisplayName("Deve validar campo nome obrigatório")
  void deveValidarCampoNomeObrigatorio() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .build();

    Set<ConstraintViolation<GrupoContatoDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(GrupoContatoTranslator.VALIDATION.NOME_NOT_BLANK));
  }

  @Test
  @DisplayName("Deve validar tamanho máximo do nome")
  void deveValidarTamanhoMaximoDoNome() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .nome("a".repeat(101))
        .build();

    Set<ConstraintViolation<GrupoContatoDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(GrupoContatoTranslator.VALIDATION.NOME_SIZE));
  }

  @Test
  @DisplayName("Deve validar tamanho máximo da descricao")
  void deveValidarTamanhoMaximoDaDescricao() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .nome("Grupo Teste")
        .descricao("a".repeat(501))
        .build();

    Set<ConstraintViolation<GrupoContatoDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(GrupoContatoTranslator.VALIDATION.DESCRICAO_SIZE));
  }

  @Test
  @DisplayName("Deve clonar DTO usando toBuilder")
  void deveClonarDTOUsandoToBuilder() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .nome("Grupo Teste")
        .descricao("Descrição do grupo")
        .ativo(true)
        .build();

    GrupoContatoDTO clone = dto.toBuilder().build();

    assertThat(clone).isNotNull();
    assertThat(clone.getNome()).isEqualTo(dto.getNome());
    assertThat(clone.getDescricao()).isEqualTo(dto.getDescricao());
    assertThat(clone.getAtivo()).isEqualTo(dto.getAtivo());
  }

  @Test
  @DisplayName("Deve copiar DTO usando copyObject")
  void deveCopiarDTOUsandoCopyObject() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .id(1L)
        .version(1L)
        .nome("Grupo Teste")
        .descricao("Descrição do grupo")
        .ativo(true)
        .build();

    GrupoContatoDTO copy = dto.copyObject();

    assertThat(copy).isNotNull();
    assertThat(copy.getId()).isNull();
    assertThat(copy.getVersion()).isNull();
    assertThat(copy.getNome()).isEqualTo(dto.getNome());
  }

  @Test
  @DisplayName("Deve ter toString formatado")
  void deveTerToStringFormatado() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .nome("Grupo Teste")
        .ativo(true)
        .build();

    String resultado = dto.toString();

    assertThat(resultado).isEqualTo("Grupo Teste");
  }

  @Test
  @DisplayName("Deve ter classe CAMPOS com constantes")
  void deveTerClasseCAMPOSComConstantes() {
    assertThat(GrupoContatoDTO.CAMPOS.NOME).isNotNull();
    assertThat(GrupoContatoDTO.CAMPOS.DESCRICAO).isNotNull();
    assertThat(GrupoContatoDTO.CAMPOS.ATIVO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter método getSearchRequest")
  void deveTerMetodoGetSearchRequest() {
    var searchRequest = GrupoContatoDTO.getSearchRequest();

    assertThat(searchRequest).isNotNull();
  }

  @Test
  @DisplayName("Deve ter método propertyFilters")
  void deveTerMetodoPropertyFilters() {
    var filters = GrupoContatoDTO.propertyFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve retornar contexto com variáveis")
  void deveRetornarContextoComVariaveis() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .nome("Grupo Teste")
        .descricao("Descrição do grupo")
        .ativo(true)
        .build();

    var context = dto.getContext();

    assertThat(context).isNotNull();
    assertThat(context).containsKey(com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate.NOME);
    assertThat(context).containsKey(com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate.DESCRICAO_GRUPO);
    assertThat(context).containsKey(com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate.ATIVO_GRUPO);
  }

  @Test
  @DisplayName("Deve clonar DTO usando cloneObject")
  void deveClonarDTOUsandoCloneObject() {
    GrupoContatoDTO dto = GrupoContatoDTO.builder()
        .nome("Grupo Teste")
        .descricao("Descrição do grupo")
        .ativo(true)
        .build();

    GrupoContatoDTO clone = dto.cloneObject();

    assertThat(clone).isNotNull();
    assertThat(clone.getNome()).isEqualTo(dto.getNome());
    assertThat(clone.getDescricao()).isEqualTo(dto.getDescricao());
  }

  @Test
  @DisplayName("Deve ter método CAMPOS.values()")
  void deveTerMetodoCAMPOSValues() {
    var values = GrupoContatoDTO.CAMPOS.values();

    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
    assertThat(values).contains(GrupoContatoDTO.CAMPOS.NOME);
    assertThat(values).contains(GrupoContatoDTO.CAMPOS.DESCRICAO);
    assertThat(values).contains(GrupoContatoDTO.CAMPOS.ATIVO);
  }
}
