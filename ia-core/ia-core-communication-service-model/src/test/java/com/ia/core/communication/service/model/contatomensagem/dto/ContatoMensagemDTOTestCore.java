package com.ia.core.communication.service.model.contatomensagem.dto;

import com.ia.core.communication.service.model.grupocontato.dto.GrupoContatoDTO;
import com.ia.core.communication.service.model.modelomensagem.dto.Variavel;
import com.ia.core.communication.service.model.modelomensagem.dto.VariavelTemplate;
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
 * Testes para a classe ContatoMensagemDTO.
 */
@DisplayName("Testes de ContatoMensagemDTO")
class ContatoMensagemDTOTestCore extends CoreBaseDTOUnitTest<ContatoMensagemDTO> {

  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Override
  protected Class<?> getDtoInterface() {
    return com.ia.core.service.dto.DTO.class;
  }

  @Test
  @DisplayName("Deve criar DTO com campos obrigatórios")
  void deveCriarDTOComCamposObrigatorios() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(grupoContato)
        .telefone("11999999999")
        .build();

    assertThat(dto).isNotNull();
    assertThat(dto.getGrupoContato()).isNotNull();
    assertThat(dto.getTelefone()).isEqualTo("11999999999");
  }

  @Test
  @DisplayName("Deve validar campo grupoContato obrigatório")
  void deveValidarCampoGrupoContatoObrigatorio() {
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .telefone("11999999999")
        .build();

    Set<ConstraintViolation<ContatoMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ContatoMensagemTranslator.VALIDATION.GRUPO_CONTATO_NOT_NULL));
  }

  @Test
  @DisplayName("Deve validar campo telefone obrigatório")
  void deveValidarCampoTelefoneObrigatorio() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(grupoContato)
        .build();

    Set<ConstraintViolation<ContatoMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ContatoMensagemTranslator.VALIDATION.TELEFONE_NOT_BLANK));
  }

  @Test
  @DisplayName("Deve validar tamanho máximo do telefone")
  void deveValidarTamanhoMaximoDoTelefone() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(grupoContato)
        .telefone("1".repeat(21))
        .build();

    Set<ConstraintViolation<ContatoMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ContatoMensagemTranslator.VALIDATION.TELEFONE_SIZE));
  }

  @Test
  @DisplayName("Deve validar tamanho máximo do nome")
  void deveValidarTamanhoMaximoDoNome() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(grupoContato)
        .telefone("11999999999")
        .nome("a".repeat(101))
        .build();

    Set<ConstraintViolation<ContatoMensagemDTO>> violations = validator.validate(dto);

    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(v -> v.getMessage().contains(ContatoMensagemTranslator.VALIDATION.NOME_SIZE));
  }

  @Test
  @DisplayName("Deve clonar DTO usando toBuilder")
  void deveClonarDTOUsandoToBuilder() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(grupoContato)
        .telefone("11999999999")
        .nome("João")
        .build();

    ContatoMensagemDTO clone = dto.toBuilder().build();

    assertThat(clone).isNotNull();
    assertThat(clone.getTelefone()).isEqualTo(dto.getTelefone());
    assertThat(clone.getNome()).isEqualTo(dto.getNome());
  }

  @Test
  @DisplayName("Deve copiar DTO usando copyObject")
  void deveCopiarDTOUsandoCopyObject() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .id(1L)
        .version(1L)
        .grupoContato(grupoContato)
        .telefone("11999999999")
        .nome("João")
        .build();

    ContatoMensagemDTO copy = dto.copyObject();

    assertThat(copy).isNotNull();
    assertThat(copy.getId()).isNull();
    assertThat(copy.getVersion()).isNull();
    assertThat(copy.getTelefone()).isEqualTo(dto.getTelefone());
  }

  @Test
  @DisplayName("Deve implementar HasVariavel")
  void deveImplementarHasVariavel() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(grupoContato)
        .telefone("11999999999")
        .nome("João")
        .build();

    assertThat(dto).isInstanceOf(com.ia.core.communication.service.model.modelomensagem.dto.HasVariavel.class);
  }

  @Test
  @DisplayName("Deve retornar contexto com variáveis")
  void deveRetornarContextoComVariaveis() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(grupoContato)
        .telefone("11999999999")
        .nome("João")
        .build();

    Map<Variavel, Object> contexto = dto.getContext();

    assertThat(contexto).isNotNull();
    assertThat(contexto).containsKey(VariavelTemplate.TELEFONE);
    assertThat(contexto).containsKey(VariavelTemplate.NOME);
    assertThat(contexto.get(VariavelTemplate.TELEFONE)).isEqualTo("11999999999");
    assertThat(contexto.get(VariavelTemplate.NOME)).isEqualTo("João");
  }

  @Test
  @DisplayName("Deve ter toString formatado")
  void deveTerToStringFormatado() {
    GrupoContatoDTO grupoContato = GrupoContatoDTO.builder().nome("Grupo Teste").build();
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(grupoContato)
        .telefone("11999999999")
        .nome("João")
        .build();

    String resultado = dto.toString();

    assertThat(resultado).isEqualTo("11999999999 - João");
  }

  @Test
  @DisplayName("Deve ter classe CAMPOS com constantes")
  void deveTerClasseCAMPOSComConstantes() {
    assertThat(ContatoMensagemDTO.CAMPOS.GRUPO_CONTATO).isNotNull();
    assertThat(ContatoMensagemDTO.CAMPOS.TELEFONE).isNotNull();
    assertThat(ContatoMensagemDTO.CAMPOS.NOME).isNotNull();
  }

  @Test
  @DisplayName("Deve ter método getSearchRequest")
  void deveTerMetodoGetSearchRequest() {
    var searchRequest = ContatoMensagemDTO.getSearchRequest();

    assertThat(searchRequest).isNotNull();
  }

  @Test
  @DisplayName("Deve ter método propertyFilters")
  void deveTerMetodoPropertyFilters() {
    var filters = ContatoMensagemDTO.propertyFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).isNotEmpty();
  }

  @Test
  @DisplayName("Deve clonar DTO usando cloneObject")
  void deveClonarDTOUsandoCloneObject() {
    ContatoMensagemDTO dto = ContatoMensagemDTO.builder()
        .grupoContato(GrupoContatoDTO.builder().nome("Grupo Teste").build())
        .telefone("11999999999")
        .nome("João")
        .build();

    ContatoMensagemDTO clone = dto.cloneObject();

    assertThat(clone).isNotNull();
    assertThat(clone.getTelefone()).isEqualTo(dto.getTelefone());
    assertThat(clone.getNome()).isEqualTo(dto.getNome());
  }

}
