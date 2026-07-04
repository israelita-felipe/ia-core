package com.ia.core.service.exception;

import com.ia.core.service.validators.ValidationError;
import com.ia.core.service.validators.ValidationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para ValidationException.
 */
@DisplayName("ValidationException Tests")
class ValidationExceptionTest {

  @Test
  @DisplayName("deve criar exceção com resultado de validação")
  void testConstructorWithValidationResult() {
    ValidationResult result = ValidationResult.create();
    result.addError(new ValidationError("field", "message", com.ia.core.service.validators.Severity.ERROR, null));

    ValidationException exception = new ValidationException(result);

    assertThat(exception.getValidationResult()).isEqualTo(result);
    assertThat(exception.getErrorCode()).isEqualTo("VALIDATION_ERROR");
    assertThat(exception.getMessage()).isEqualTo("message");
  }

  @Test
  @DisplayName("deve criar exceção com mensagem e resultado de validação")
  void testConstructorWithMessageAndValidationResult() {
    ValidationResult result = ValidationResult.create();
    result.addError(new ValidationError("field", "message", com.ia.core.service.validators.Severity.ERROR, null));

    ValidationException exception = new ValidationException("Custom message", result);

    assertThat(exception.getValidationResult()).isEqualTo(result);
    assertThat(exception.getErrorCode()).isEqualTo("VALIDATION_ERROR");
    assertThat(exception.getMessage()).isEqualTo("message");
  }

  @Test
  @DisplayName("deve criar exceção com código de erro, mensagem e resultado de validação")
  void testConstructorWithErrorCodeMessageAndValidationResult() {
    ValidationResult result = ValidationResult.create();
    result.addError(new ValidationError("field", "message", com.ia.core.service.validators.Severity.ERROR, null));

    ValidationException exception = new ValidationException("CUSTOM_ERROR", "Custom message", result);

    assertThat(exception.getValidationResult()).isEqualTo(result);
    assertThat(exception.getErrorCode()).isEqualTo("CUSTOM_ERROR");
    assertThat(exception.getMessage()).isEqualTo("message");
  }

  @Test
  @DisplayName("hasErros deve retornar true quando há erros")
  void testHasErrosReturnsTrueWhenErrorsExist() {
    ValidationResult result = ValidationResult.create();
    result.addError(new ValidationError("field", "message", com.ia.core.service.validators.Severity.ERROR, null));

    ValidationException exception = new ValidationException(result);

    assertThat(exception.hasErros()).isTrue();
  }

  @Test
  @DisplayName("hasErros deve retornar false quando não há erros")
  void testHasErrosReturnsFalseWhenNoErrors() {
    ValidationResult result = ValidationResult.empty();

    ValidationException exception = new ValidationException(result);

    assertThat(exception.hasErros()).isFalse();
  }

  @Test
  @DisplayName("hasErros deve retornar false quando resultado é nulo")
  void testHasErrosReturnsFalseWhenResultIsNull() {
    ValidationException exception = new ValidationException(null);

    assertThat(exception.hasErros()).isFalse();
  }

  @Test
  @DisplayName("getErrors deve retornar stream de mensagens de erro")
  void testGetErrorsReturnsErrorMessages() {
    ValidationResult result = ValidationResult.create();
    result.addError(new ValidationError("field1", "message1", com.ia.core.service.validators.Severity.ERROR, null));
    result.addError(new ValidationError("field2", "message2", com.ia.core.service.validators.Severity.ERROR, null));

    ValidationException exception = new ValidationException(result);

    assertThat(exception.getErrors()).containsExactly("message1", "message2");
  }

  @Test
  @DisplayName("getErrors deve retornar stream vazio quando não há erros")
  void testGetErrorsReturnsEmptyStreamWhenNoErrors() {
    ValidationResult result = ValidationResult.empty();

    ValidationException exception = new ValidationException(result);

    assertThat(exception.getErrors()).isEmpty();
  }
}
