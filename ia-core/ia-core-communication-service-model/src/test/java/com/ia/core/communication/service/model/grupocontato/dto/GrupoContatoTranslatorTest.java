package com.ia.core.communication.service.model.grupocontato.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe GrupoContatoTranslator.
 */
@DisplayName("Testes de GrupoContatoTranslator")
class GrupoContatoTranslatorTest {

  @Test
  @DisplayName("Deve ter classe HELP com constantes")
  void deveTerClasseHELPComConstantes() {
    assertThat(GrupoContatoTranslator.HELP.GRUPO_CONTATO).isNotNull();
    assertThat(GrupoContatoTranslator.HELP.NOME).isNotNull();
    assertThat(GrupoContatoTranslator.HELP.DESCRICAO).isNotNull();
    assertThat(GrupoContatoTranslator.HELP.ATIVO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe VALIDATION com constantes")
  void deveTerClasseVALIDATIONComConstantes() {
    assertThat(GrupoContatoTranslator.VALIDATION.NOME_NOT_BLANK).isNotNull();
    assertThat(GrupoContatoTranslator.VALIDATION.NOME_SIZE).isNotNull();
    assertThat(GrupoContatoTranslator.VALIDATION.DESCRICAO_SIZE).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe RULE com constantes")
  void deveTerClasseRULEComConstantes() {
    assertThat(GrupoContatoTranslator.RULE.GRUPO_SEM_CONTATOS).isNotNull();
    assertThat(GrupoContatoTranslator.RULE.CONTATO_DUPLICADO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe MESSAGE com constantes")
  void deveTerClasseMESSAGEComConstantes() {
    assertThat(GrupoContatoTranslator.MESSAGE.CRIADO_SUCESSO).isNotNull();
    assertThat(GrupoContatoTranslator.MESSAGE.ATUALIZADO_SUCESSO).isNotNull();
    assertThat(GrupoContatoTranslator.MESSAGE.DELETADO_SUCESSO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe EVENT com constantes")
  void deveTerClasseEVENTComConstantes() {
    assertThat(GrupoContatoTranslator.EVENT.GRUPO_CRIADO).isNotNull();
    assertThat(GrupoContatoTranslator.EVENT.GRUPO_ATUALIZADO).isNotNull();
    assertThat(GrupoContatoTranslator.EVENT.CONTATO_ADICIONADO_AO_GRUPO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter constante de classe canônica")
  void deveTerConstanteDeClasseCanonica() {
    assertThat(GrupoContatoTranslator.GRUPO_CONTATO_CLASS)
        .isEqualTo(GrupoContatoDTO.class.getCanonicalName());
  }

  @Test
  @DisplayName("Deve ter constantes de nomes de campos")
  void deveTerConstantesDeNomesDeCampos() {
    assertThat(GrupoContatoTranslator.GRUPO_CONTATO).isNotNull();
    assertThat(GrupoContatoTranslator.NOME).isNotNull();
    assertThat(GrupoContatoTranslator.DESCRICAO).isNotNull();
    assertThat(GrupoContatoTranslator.ATIVO).isNotNull();
  }
}
