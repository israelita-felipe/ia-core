package com.ia.core.communication.service.model.enviomensagem.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe EnvioMensagemTranslator.
 */
@DisplayName("Testes de EnvioMensagemTranslator")
class EnvioMensagemTranslatorTest {

  @Test
  @DisplayName("Deve ter classe HELP com constantes")
  void deveTerClasseHELPComConstantes() {
    assertThat(EnvioMensagemTranslator.HELP.ENVIO_MENSAGEM).isNotNull();
    assertThat(EnvioMensagemTranslator.HELP.TIPO_CANAL).isNotNull();
    assertThat(EnvioMensagemTranslator.HELP.CORPO_MENSAGEM).isNotNull();
    assertThat(EnvioMensagemTranslator.HELP.MODELO_MENSAGEM_ID).isNotNull();
    assertThat(EnvioMensagemTranslator.HELP.PARAMETROS_TEMPLATE).isNotNull();
    assertThat(EnvioMensagemTranslator.HELP.TELEFONES).isNotNull();
    assertThat(EnvioMensagemTranslator.HELP.GRUPOS_CONTATO_IDS).isNotNull();
    assertThat(EnvioMensagemTranslator.HELP.AGENDADO).isNotNull();
    assertThat(EnvioMensagemTranslator.HELP.DATA_AGENDAMENTO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe VALIDATION com constantes")
  void deveTerClasseVALIDATIONComConstantes() {
    assertThat(EnvioMensagemTranslator.VALIDATION.TIPO_CANAL_NOT_NULL).isNotNull();
    assertThat(EnvioMensagemTranslator.VALIDATION.CORPO_MENSAGEM_NOT_BLANK).isNotNull();
    assertThat(EnvioMensagemTranslator.VALIDATION.MODELO_MENSAGEM_ID_NULL).isNotNull();
    assertThat(EnvioMensagemTranslator.VALIDATION.TELEFONES_NOT_EMPTY).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe RULE com constantes")
  void deveTerClasseRULEComConstantes() {
    assertThat(EnvioMensagemTranslator.RULE.CANAL_NAO_SUPORTADO).isNotNull();
    assertThat(EnvioMensagemTranslator.RULE.MODELO_NAO_ENCONTRADO).isNotNull();
    assertThat(EnvioMensagemTranslator.RULE.TELEFONE_INVALIDO).isNotNull();
    assertThat(EnvioMensagemTranslator.RULE.AGENDAMENTO_PASSADO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe MESSAGE com constantes")
  void deveTerClasseMESSAGEComConstantes() {
    assertThat(EnvioMensagemTranslator.MESSAGE.ENVIADO_SUCESSO).isNotNull();
    assertThat(EnvioMensagemTranslator.MESSAGE.AGENDADO_SUCESSO).isNotNull();
    assertThat(EnvioMensagemTranslator.MESSAGE.ERRO_ENVIO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter classe EVENT com constantes")
  void deveTerClasseEVENTComConstantes() {
    assertThat(EnvioMensagemTranslator.EVENT.MENSAGEM_ENVIADA).isNotNull();
    assertThat(EnvioMensagemTranslator.EVENT.MENSAGEM_AGENDADA).isNotNull();
    assertThat(EnvioMensagemTranslator.EVENT.MENSAGEM_ERRO).isNotNull();
  }

  @Test
  @DisplayName("Deve ter constante de classe canônica")
  void deveTerConstanteDeClasseCanonica() {
    assertThat(EnvioMensagemTranslator.ENVIO_MENSAGEM_CLASS)
        .isEqualTo(EnvioMensagemRequestDTO.class.getCanonicalName());
  }

  @Test
  @DisplayName("Deve ter constantes de nomes de campos")
  void deveTerConstantesDeNomesDeCampos() {
    assertThat(EnvioMensagemTranslator.ENVIO_MENSAGEM).isNotNull();
    assertThat(EnvioMensagemTranslator.TIPO_CANAL).isNotNull();
    assertThat(EnvioMensagemTranslator.CORPO_MENSAGEM).isNotNull();
    assertThat(EnvioMensagemTranslator.MODELO_MENSAGEM_ID).isNotNull();
    assertThat(EnvioMensagemTranslator.PARAMETROS_TEMPLATE).isNotNull();
    assertThat(EnvioMensagemTranslator.TELEFONES).isNotNull();
    assertThat(EnvioMensagemTranslator.GRUPOS_CONTATO_IDS).isNotNull();
    assertThat(EnvioMensagemTranslator.AGENDADO).isNotNull();
    assertThat(EnvioMensagemTranslator.DATA_AGENDAMENTO).isNotNull();
  }
}
