package com.ia.core.resilience4j.exception;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para ResilienceException baseados nos casos de teste documentados.
 */
class ResilienceExceptionTestCore extends CoreBaseUnitTest {

    @Test
    void deveCriarExcecaoComMensagem() {
        // Arrange
        String message = "Test error message";

        // Act
        ResilienceException exception = new ResilienceException(message);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrorCode()).isEqualTo("RESILIENCE_ERROR");
        assertThat(exception.isRetryable()).isTrue();
    }

    @Test
    void deveCriarExcecaoComMensagemECausa() {
        // Arrange
        String message = "Test error message";
        Throwable cause = new RuntimeException("Cause");

        // Act
        ResilienceException exception = new ResilienceException(message, cause);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
        assertThat(exception.getErrorCode()).isEqualTo("RESILIENCE_ERROR");
        assertThat(exception.isRetryable()).isTrue();
    }

    @Test
    void deveCriarExcecaoComCodigoMensagemERetryable() {
        // Arrange
        String errorCode = "CUSTOM_ERROR";
        String message = "Test error message";
        boolean retryable = false;

        // Act
        ResilienceException exception = new ResilienceException(errorCode, message, retryable);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getErrorCode()).isEqualTo(errorCode);
        assertThat(exception.isRetryable()).isEqualTo(retryable);
    }

    @Test
    void deveCriarExcecaoComCodigoMensagemCausaERetryable() {
        // Arrange
        String errorCode = "CUSTOM_ERROR";
        String message = "Test error message";
        Throwable cause = new RuntimeException("Cause");
        boolean retryable = false;

        // Act
        ResilienceException exception = new ResilienceException(errorCode, message, cause, retryable);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
        assertThat(exception.getErrorCode()).isEqualTo(errorCode);
        assertThat(exception.isRetryable()).isEqualTo(retryable);
    }

    @Test
    void deveVerificarGetErrorCode() {
        // Arrange
        String errorCode = "TEST_ERROR";
        ResilienceException exception = new ResilienceException(errorCode, "Message", true);

        // Act
        String result = exception.getErrorCode();

        // Assert
        assertThat(result).isEqualTo(errorCode);
    }

    @Test
    void deveVerificarIsRetryable() {
        // Arrange
        boolean retryable = false;
        ResilienceException exception = new ResilienceException("CODE", "Message", retryable);

        // Act
        boolean result = exception.isRetryable();

        // Assert
        assertThat(result).isEqualTo(retryable);
    }
}
