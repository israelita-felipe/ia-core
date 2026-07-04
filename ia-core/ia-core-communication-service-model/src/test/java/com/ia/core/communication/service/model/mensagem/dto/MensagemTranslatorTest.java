package com.ia.core.communication.service.model.mensagem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe MensagemTranslator.
 */
@DisplayName("Testes de MensagemTranslator")
class MensagemTranslatorTest {

  @Test
  @DisplayName("Deve ter classe HELP com constantes")
  void deveTerClasseHELPComConstantes() {
    assertThat(MensagemTranslator.HELP.MENSAGEM).isNotNull();
    assertThat(MensagemTranslator.HELP.TELEFONE_DESTINATARIO).isNotNull();
    assertThat(MensagemTranslator.HELP.NOME_DESTINATARIO).isNotNull();
    assertThat(MensagemTranslator.HELP.CORPO_MENSAGEM).isNotNull();
    assertThat(MensagemTranslator.HELP.TIPO_CANAL).isNotNull();
    assertThat(MensagemTranslator.HELP.STATUS_MENSAGEM).isNotNull();
    assertThat(MensagemTranslator.HELP.ID_EXTERNO).isNotNull();
    assertThat(MensagemTranslator.HELP.DATA_ENVIO).isNotNull();
    assertThat(MensagemTranslator.HELP.DATA_ENTREGA).isNotNull();
    assertThat(MensagemTranslator.HELP.DATA_LEITURA).isNotNull();
    assertThat(MensagemTranslator.HELP.MOTIVO_FALHA).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe VALIDATION com constantes")
  void deveTerClasseVALIDATIONComConstantes() {
    assertThat(MensagemTranslator.VALIDATION.TELEFONE_DESTINATARIO_NOT_BLANK).isNotNull();
    assertThat(MensagemTranslator.VALIDATION.TELEFONE_DESTINATARIO_SIZE).isNotNull();
    assertThat(MensagemTranslator.VALIDATION.NOME_DESTINATARIO_SIZE).isNotNull();
    assertThat(MensagemTranslator.VALIDATION.CORPO_MENSAGEM_NOT_BLANK).isNotNull();
    assertThat(MensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL).isNotNull();
    assertThat(MensagemTranslator.VALIDATION.STATUS_MENSAGEM_NOT_NULL).isNotNull();
    assertThat(MensagemTranslator.VALIDATION.ID_EXTERNO_SIZE).isNotNull();
    assertThat(MensagemTranslator.VALIDATION.MOTIVO_FALHA_SIZE).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe RULE com constantes")
  void deveTerClasseRULEComConstantes() {
    assertThat(MensagemTranslator.RULE.CANAL_NAO_SUPORTADO).isNotNull();
    assertThat(MensagemTranslator.RULE.TELEFONE_INVALIDO).isNotNull();
    assertThat(MensagemTranslator.RULE.MODELO_NAO_ENCONTRADO).isNotNull();
    assertThat(MensagemTranslator.RULE.GRUPO_NAO_ENCONTRADO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe MESSAGE com constantes")
  void deveTerClasseMESSAGEComConstantes() {
    assertThat(MensagemTranslator.MESSAGE.ENVIADO_SUCESSO).isNotNull();
    assertThat(MensagemTranslator.MESSAGE.ENVIADO_EM_MASSA_SUCESSO).isNotNull();
    assertThat(MensagemTranslator.MESSAGE.STATUS_ATUALIZADO).isNotNull();
    assertThat(MensagemTranslator.MESSAGE.FALHA_ENVIO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe EVENT com constantes")
  void deveTerClasseEVENTComConstantes() {
    assertThat(MensagemTranslator.EVENT.MENSAGEM_ENVIADA).isNotNull();
    assertThat(MensagemTranslator.EVENT.MENSAGEM_ENTREGUE).isNotNull();
    assertThat(MensagemTranslator.EVENT.MENSAGEM_LIDA).isNotNull();
    assertThat(MensagemTranslator.EVENT.MENSAGEM_FALHOU).isNotNull();
  }

  @Test
  @DisplayName("Deve ter constante de classe canônica")
  void deveTerConstanteDeClasseCanonica() {
    assertThat(MensagemTranslator.MENSAGEM_CLASS)
        .isEqualTo(MensagemDTO.class.getCanonicalName());
  }

  @Test
  @DisplayName("Deve ter constantes de nomes de campos")
  void deveTerConstantesDeNomesDeCampos() {
    assertThat(MensagemTranslator.MENSAGEM).isNotNull();
    assertThat(MensagemTranslator.TELEFONE_DESTINATARIO).isNotNull();
    assertThat(MensagemTranslator.NOME_DESTINATARIO).isNotNull();
    assertThat(MensagemTranslator.CORPO_MENSAGEM).isNotNull();
    assertThat(MensagemTranslator.TIPO_CANAL).isNotNull();
    assertThat(MensagemTranslator.STATUS_MENSAGEM).isNotNull();
    assertThat(MensagemTranslator.ID_EXTERNO).isNotNull();
    assertThat(MensagemTranslator.DATA_ENVIO).isNotNull();
    assertThat(MensagemTranslator.DATA_ENTREGA).isNotNull();
    assertThat(MensagemTranslator.DATA_LEITURA).isNotNull();
    assertThat(MensagemTranslator.MOTIVO_FALHA).isNotNull();
  }
}
