package com.ia.core.service.translator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para CoreApplicationTranslator.
 */
@DisplayName("CoreApplicationTranslator Tests")
class CoreApplicationTranslatorTest {

  @Test
  @DisplayName("deve ter constantes definidas")
  void testConstants() {
    assertThat(CoreApplicationTranslator.APPLICATION_NAME).isEqualTo("application.name");
    assertThat(CoreApplicationTranslator.DETALHAR).isEqualTo("detalhar");
    assertThat(CoreApplicationTranslator.PESQUSIAR).isEqualTo("pesquisar");
  }

  @Test
  @DisplayName("ACTION deve ter constantes definidas")
  void testActionConstants() {
    assertThat(CoreApplicationTranslator.ACTION.SAVE).isEqualTo("action.save");
    assertThat(CoreApplicationTranslator.ACTION.DELETE).isEqualTo("action.delete");
    assertThat(CoreApplicationTranslator.ACTION.CANCEL).isEqualTo("action.cancel");
  }

  @Test
  @DisplayName("MESSAGE deve ter constantes definidas")
  void testMessageConstants() {
    assertThat(CoreApplicationTranslator.MESSAGE.DELETE_SUCCESS).isEqualTo("dialog.message.success.delete");
    assertThat(CoreApplicationTranslator.MESSAGE.CONFIRM_DELETE).isEqualTo("dialog.message.confirm.delete");
    assertThat(CoreApplicationTranslator.MESSAGE.SAVE_SUCCESS).isEqualTo("dialog.message.success.save");
  }

  @Test
  @DisplayName("TITLE deve ter constantes definidas")
  void testTitleConstants() {
    assertThat(CoreApplicationTranslator.TITLE.ERROR).isEqualTo("title.error");
  }

  @Test
  @DisplayName("deve ter constantes de erro definidas")
  void testErrorConstants() {
    assertThat(CoreApplicationTranslator.ERROR_AUTHENTICATION).isEqualTo("error.authentication");
    assertThat(CoreApplicationTranslator.ERROR_ACCESS_DENIED).isEqualTo("error.access.denied");
    assertThat(CoreApplicationTranslator.ERROR_ENTITY_NOT_FOUND).isEqualTo("error.entity.not.found");
  }

  @Test
  @DisplayName("deve ter todas as constantes principais definidas")
  void testAllMainConstants() {
    assertThat(CoreApplicationTranslator.INICIO).isEqualTo("inicio");
    assertThat(CoreApplicationTranslator.CANCELAR).isEqualTo("cancelar");
    assertThat(CoreApplicationTranslator.HOJE).isEqualTo("hoje");
    assertThat(CoreApplicationTranslator.DEFAULT_ACTION).isEqualTo("action");
    assertThat(CoreApplicationTranslator.FILTER).isEqualTo("filter");
    assertThat(CoreApplicationTranslator.REPORTS).isEqualTo("reports");
  }

  @Test
  @DisplayName("ACTION deve ter todas as constantes definidas")
  void testAllActionConstants() {
    assertThat(CoreApplicationTranslator.ACTION.OK).isEqualTo("action.ok");
    assertThat(CoreApplicationTranslator.ACTION.YES).isEqualTo("action.yes");
    assertThat(CoreApplicationTranslator.ACTION.NO).isEqualTo("action.no");
  }

  @Test
  @DisplayName("deve ter constantes de erro REST API definidas")
  void testRestApiErrorConstants() {
    assertThat(CoreApplicationTranslator.ERROR_AUTHENTICATION_MESSAGE).isEqualTo("error.authentication.message");
    assertThat(CoreApplicationTranslator.ERROR_ACCESS_DENIED_MESSAGE).isEqualTo("error.access.denied.message");
    assertThat(CoreApplicationTranslator.ERROR_ENTITY_NOT_FOUND_MESSAGE).isEqualTo("error.entity.not.found.message");
    assertThat(CoreApplicationTranslator.ERROR_VALIDATION).isEqualTo("error.validation");
    assertThat(CoreApplicationTranslator.ERROR_VALIDATION_MESSAGE).isEqualTo("error.validation.message");
    assertThat(CoreApplicationTranslator.ERROR_DATA_INTEGRITY).isEqualTo("error.data.integrity");
    assertThat(CoreApplicationTranslator.ERROR_DATA_INTEGRITY_MESSAGE).isEqualTo("error.data.integrity.message");
    assertThat(CoreApplicationTranslator.ERROR_SERVICE).isEqualTo("error.service");
    assertThat(CoreApplicationTranslator.ERROR_SERVICE_MESSAGE).isEqualTo("error.service.message");
    assertThat(CoreApplicationTranslator.ERROR_INTERNAL).isEqualTo("error.internal");
    assertThat(CoreApplicationTranslator.ERROR_INTERNAL_MESSAGE).isEqualTo("error.internal.message");
  }

  @Test
  @DisplayName("deve ter constante de erro de integridade definida")
  void testIntegrityErrorConstant() {
    assertThat(CoreApplicationTranslator.ERRO_INTEGRIDADE_OBJETO_POSSUI_DEPENDENCIAS).isEqualTo("error.integridade.objeto.dependencias");
  }
}
