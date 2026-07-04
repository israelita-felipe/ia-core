package com.ia.core.communication.service.model.contatomensagem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe ContatoMensagemTranslator.
 */
@DisplayName("Testes de ContatoMensagemTranslator")
class ContatoMensagemTranslatorTest {

  @Test
  @DisplayName("Deve ter classe HELP com constantes")
  void deveTerClasseHELPComConstantes() {
    assertThat(ContatoMensagemTranslator.HELP.CONTATO_MENSAGEM).isNotNull();
    assertThat(ContatoMensagemTranslator.HELP.GRUPO_CONTATO).isNotNull();
    assertThat(ContatoMensagemTranslator.HELP.TELEFONE).isNotNull();
    assertThat(ContatoMensagemTranslator.HELP.NOME).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe VALIDATION com constantes")
  void deveTerClasseVALIDATIONComConstantes() {
    assertThat(ContatoMensagemTranslator.VALIDATION.GRUPO_CONTATO_NOT_NULL).isNotNull();
    assertThat(ContatoMensagemTranslator.VALIDATION.TELEFONE_NOT_BLANK).isNotNull();
    assertThat(ContatoMensagemTranslator.VALIDATION.TELEFONE_SIZE).isNotNull();
    assertThat(ContatoMensagemTranslator.VALIDATION.NOME_SIZE).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe RULE com constantes")
  void deveTerClasseRULEComConstantes() {
    assertThat(ContatoMensagemTranslator.RULE.GRUPO_NAO_ENCONTRADO).isNotNull();
    assertThat(ContatoMensagemTranslator.RULE.TELEFONE_DUPLICADO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe MESSAGE com constantes")
  void deveTerClasseMESSAGEComConstantes() {
    assertThat(ContatoMensagemTranslator.MESSAGE.CRIADO_SUCESSO).isNotNull();
    assertThat(ContatoMensagemTranslator.MESSAGE.ATUALIZADO_SUCESSO).isNotNull();
    assertThat(ContatoMensagemTranslator.MESSAGE.DELETADO_SUCESSO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe EVENT com constantes")
  void deveTerClasseEVENTComConstantes() {
    assertThat(ContatoMensagemTranslator.EVENT.CONTATO_ADICIONADO).isNotNull();
    assertThat(ContatoMensagemTranslator.EVENT.CONTATO_REMOVIDO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter constante de classe canônica")
  void deveTerConstanteDeClasseCanonica() {
    assertThat(ContatoMensagemTranslator.CONTATO_MENSAGEM_CLASS)
        .isEqualTo(ContatoMensagemDTO.class.getCanonicalName());
  }

  @Test
  @DisplayName("Deve ter constantes de nomes de campos")
  void deveTerConstantesDeNomesDeCampos() {
    assertThat(ContatoMensagemTranslator.CONTATO_MENSAGEM).isNotNull();
    assertThat(ContatoMensagemTranslator.GRUPO_CONTATO).isNotNull();
    assertThat(ContatoMensagemTranslator.TELEFONE).isNotNull();
    assertThat(ContatoMensagemTranslator.NOME).isNotNull();
  }
}
