package com.ia.core.service.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para SearchRequestTranslator.
 */
@DisplayName("SearchRequestTranslator Tests")
class SearchRequestTranslatorTest {

  @Test
  @DisplayName("deve ter construtor privado")
  void testPrivateConstructor() throws Exception {
    var constructor = SearchRequestTranslator.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    var instance = constructor.newInstance();
    assertThat(instance).isNotNull();
  }

  @Test
  @DisplayName("FILTER_REQUEST_CLASS deve ser definido")
  void testFilterRequestClass() {
    assertThat(SearchRequestTranslator.FILTER_REQUEST_CLASS).isEqualTo("com.ia.core.service.dto.request.SearchRequestDTO");
  }

  @Test
  @DisplayName("deve ter constantes de campo definidas")
  void testFieldConstants() {
    assertThat(SearchRequestTranslator.FILTER_REQUEST).isEqualTo("search.request");
  }

  @Test
  @DisplayName("HELP deve ter constantes definidas")
  void testHelpConstants() {
    assertThat(SearchRequestTranslator.HELP.FILTER_REQUEST).isEqualTo("search.request.help");
  }

  @Test
  @DisplayName("VALIDATION deve ter constantes definidas")
  void testValidationConstants() {
    assertThat(SearchRequestTranslator.VALIDATION.PAGE_REQUIRED).isEqualTo("search.request.validation.page.required");
    assertThat(SearchRequestTranslator.VALIDATION.PAGE_SIZE_REQUIRED).isEqualTo("search.request.validation.pageSize.required");
  }

  @Test
  @DisplayName("RULE deve ter constantes definidas")
  void testRuleConstants() {
    assertThat(SearchRequestTranslator.RULE.PAGE_INVALIDA).isEqualTo("search.request.rule.pagina.invalida");
    assertThat(SearchRequestTranslator.RULE.TAMANHO_PAGINA_INVALIDO).isEqualTo("search.request.rule.tamanho.pagina.invalido");
  }

  @Test
  @DisplayName("MESSAGE deve ter constantes definidas")
  void testMessageConstants() {
    assertThat(SearchRequestTranslator.MESSAGE.PESQUISA_REALIZADA).isEqualTo("search.request.message.pesquisa.realizada");
  }

  @Test
  @DisplayName("EVENT deve ter constantes definidas")
  void testEventConstants() {
    assertThat(SearchRequestTranslator.EVENT.PESQUISA_EFETUADA).isEqualTo("search.request.event.pesquisa.efetuada");
  }
}
