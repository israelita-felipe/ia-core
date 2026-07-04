package com.ia.core.service.rules;

import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ValidationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Testes para BusinessRule.
 */
@DisplayName("BusinessRule Tests")
class BusinessRuleTest {

  @Test
  @DisplayName("isApplicable deve retornar true por padrão")
  void testIsApplicableReturnsTrueByDefault() {
    Translator translator = mock(Translator.class);
    TestBusinessRule rule = new TestBusinessRule(translator);

    assertThat(rule.isApplicable(null)).isTrue();
  }

  static class TestBusinessRule implements BusinessRule<String> {
    private final Translator translator;

    TestBusinessRule(Translator translator) {
      this.translator = translator;
    }

    @Override
    public String getCode() {
      return "TEST_001";
    }

    @Override
    public String getName() {
      return "Test Rule";
    }

    @Override
    public String getDescription() {
      return "Test Description";
    }

    @Override
    public Translator getTranslator() {
      return translator;
    }

    @Override
    public void validate(String object, ValidationResult result) {
    }
  }
}
