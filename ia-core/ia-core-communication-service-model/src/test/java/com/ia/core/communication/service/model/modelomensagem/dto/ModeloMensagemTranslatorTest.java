package com.ia.core.communication.service.model.modelomensagem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe ModeloMensagemTranslator.
 */
@DisplayName("Testes de ModeloMensagemTranslator")
class ModeloMensagemTranslatorTest {

  @Test
  @DisplayName("Deve ter classe HELP com constantes")
  void deveTerClasseHELPComConstantes() {
    assertThat(ModeloMensagemTranslator.HELP.MODELO_MENSAGEM).isNotNull();
    assertThat(ModeloMensagemTranslator.HELP.NOME).isNotNull();
    assertThat(ModeloMensagemTranslator.HELP.DESCRICAO).isNotNull();
    assertThat(ModeloMensagemTranslator.HELP.CORPO_MODELO).isNotNull();
    assertThat(ModeloMensagemTranslator.HELP.TIPO_CANAL).isNotNull();
    assertThat(ModeloMensagemTranslator.HELP.ATIVO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe VALIDATION com constantes")
  void deveTerClasseVALIDATIONComConstantes() {
    assertThat(ModeloMensagemTranslator.VALIDATION.NOME_NOT_BLANK).isNotNull();
    assertThat(ModeloMensagemTranslator.VALIDATION.NOME_SIZE).isNotNull();
    assertThat(ModeloMensagemTranslator.VALIDATION.DESCRICAO_SIZE).isNotNull();
    assertThat(ModeloMensagemTranslator.VALIDATION.CORPO_MODELO_NOT_BLANK).isNotNull();
    assertThat(ModeloMensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe RULE com constantes")
  void deveTerClasseRULEComConstantes() {
    assertThat(ModeloMensagemTranslator.RULE.NOME_DUPLICADO).isNotNull();
    assertThat(ModeloMensagemTranslator.RULE.MODELO_INATIVO).isNotNull();
    assertThat(ModeloMensagemTranslator.RULE.VARIAVEIS_INVALIDAS).isNotNull();
    assertThat(ModeloMensagemTranslator.RULE.MODELO_NAO_ENCONTRADO).isNotNull();
    assertThat(ModeloMensagemTranslator.RULE.CONTATO_NAO_ENCONTRADO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe MESSAGE com constantes")
  void deveTerClasseMESSAGEComConstantes() {
    assertThat(ModeloMensagemTranslator.MESSAGE.CRIADO_SUCESSO).isNotNull();
    assertThat(ModeloMensagemTranslator.MESSAGE.ATUALIZADO_SUCESSO).isNotNull();
    assertThat(ModeloMensagemTranslator.MESSAGE.DELETADO_SUCESSO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe EVENT com constantes")
  void deveTerClasseEVENTComConstantes() {
    assertThat(ModeloMensagemTranslator.EVENT.MODELO_CRIADO).isNotNull();
    assertThat(ModeloMensagemTranslator.EVENT.MODELO_ATUALIZADO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter constante de classe canônica")
  void deveTerConstanteDeClasseCanonica() {
    assertThat(ModeloMensagemTranslator.MODELO_MENSAGEM_CLASS)
        .isEqualTo(ModeloMensagemDTO.class.getCanonicalName());
  }

  @Test
  @DisplayName("Deve ter constantes de nomes de campos")
  void deveTerConstantesDeNomesDeCampos() {
    assertThat(ModeloMensagemTranslator.MODELO_MENSAGEM).isNotNull();
    assertThat(ModeloMensagemTranslator.NOME).isNotNull();
    assertThat(ModeloMensagemTranslator.DESCRICAO).isNotNull();
    assertThat(ModeloMensagemTranslator.CORPO_MODELO).isNotNull();
    assertThat(ModeloMensagemTranslator.TIPO_CANAL).isNotNull();
    assertThat(ModeloMensagemTranslator.ATIVO).isNotNull();
  }
}
