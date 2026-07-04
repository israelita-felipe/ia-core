package com.ia.core.service.dto.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para FilterRequestTranslator.
 */
@DisplayName("FilterRequestTranslator Tests")
class FilterRequestTranslatorTest {

  @Test
  @DisplayName("deve ter construtor privado")
  void testPrivateConstructor() throws Exception {
    var constructor = FilterRequestTranslator.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    var instance = constructor.newInstance();
    assertThat(instance).isNotNull();
  }

  @Test
  @DisplayName("FILTER_REQUEST_CLASS deve ser definido")
  void testFilterRequestClass() {
    assertThat(FilterRequestTranslator.FILTER_REQUEST_CLASS).isEqualTo("com.ia.core.service.dto.filter.FilterRequestDTO");
  }

  @Test
  @DisplayName("HELP deve ter constantes definidas")
  void testHelpConstants() {
    assertThat(FilterRequestTranslator.HELP.FILTER_REQUEST).isEqualTo("filter.request.help");
    assertThat(FilterRequestTranslator.HELP.VALUE).isEqualTo("filter.request.help.value");
    assertThat(FilterRequestTranslator.HELP.KEY).isEqualTo("filter.request.help.key");
  }

  @Test
  @DisplayName("VALIDATION deve ter constantes definidas")
  void testValidationConstants() {
    assertThat(FilterRequestTranslator.VALIDATION.KEY_REQUIRED).isEqualTo("filter.request.validation.key.required");
    assertThat(FilterRequestTranslator.VALIDATION.OPERATOR_REQUIRED).isEqualTo("filter.request.validation.operator.required");
  }

  @Test
  @DisplayName("RULE deve ter constantes definidas")
  void testRuleConstants() {
    assertThat(FilterRequestTranslator.RULE.CAMPO_INEXISTENTE).isEqualTo("filter.request.rule.campo.inexistente");
    assertThat(FilterRequestTranslator.RULE.OPERADOR_INVALIDO).isEqualTo("filter.request.rule.operador.invalido");
  }

  @Test
  @DisplayName("MESSAGE deve ter constantes definidas")
  void testMessageConstants() {
    assertThat(FilterRequestTranslator.MESSAGE.FILTRO_APLICADO).isEqualTo("filter.request.message.filtro.aplicado");
  }

  @Test
  @DisplayName("EVENT deve ter constantes definidas")
  void testEventConstants() {
    assertThat(FilterRequestTranslator.EVENT.FILTRO_CRIADO).isEqualTo("filter.request.event.criado");
  }

  @Test
  @DisplayName("deve ter constantes de campo definidas")
  void testFieldConstants() {
    assertThat(FilterRequestTranslator.FILTER_REQUEST).isEqualTo("filter.request");
    assertThat(FilterRequestTranslator.VALUE).isEqualTo("filter.request.value");
    assertThat(FilterRequestTranslator.KEY).isEqualTo("filter.request.key");
    assertThat(FilterRequestTranslator.NEGATE).isEqualTo("filter.request.negate");
    assertThat(FilterRequestTranslator.OPERATOR).isEqualTo("filter.request.operator");
    assertThat(FilterRequestTranslator.FIELD_TYPE).isEqualTo("filter.request.fieldType");
    assertThat(FilterRequestTranslator.TEXT_FILTER).isEqualTo("filter.request.text.filter");
    assertThat(FilterRequestTranslator.DOUBLE_FILTER).isEqualTo("filter.request.double.filter");
    assertThat(FilterRequestTranslator.INTEGER_FILTER).isEqualTo("filter.request.integer.filter");
    assertThat(FilterRequestTranslator.LONG_FILTER).isEqualTo("filter.request.long.filter");
    assertThat(FilterRequestTranslator.BOOLEAN_FILTER).isEqualTo("filter.request.boolean.filter");
    assertThat(FilterRequestTranslator.CHARACTER_FILTER).isEqualTo("filter.request.character.filter");
    assertThat(FilterRequestTranslator.DATE_FILTER).isEqualTo("filter.request.date.filter");
    assertThat(FilterRequestTranslator.TIME_FILTER).isEqualTo("filter.request.time.filter");
    assertThat(FilterRequestTranslator.DATE_TIME_FILTER).isEqualTo("filter.request.date.time.filter");
    assertThat(FilterRequestTranslator.ENUM_FILTER).isEqualTo("filter.request.enum.filter");
  }

  @Test
  @DisplayName("HELP deve ter todas as constantes definidas")
  void testAllHelpConstants() {
    assertThat(FilterRequestTranslator.HELP.OPERATOR).isEqualTo("filter.request.help.operator");
    assertThat(FilterRequestTranslator.HELP.FIELD_TYPE).isEqualTo("filter.request.help.fieldType");
  }

  @Test
  @DisplayName("VALIDATION deve ter todas as constantes definidas")
  void testAllValidationConstants() {
    assertThat(FilterRequestTranslator.VALIDATION.VALUE_REQUIRED).isEqualTo("filter.request.validation.value.required");
  }
}
