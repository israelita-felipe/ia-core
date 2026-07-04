package com.ia.core.service.exception;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para ServiceException baseados nos casos de teste documentados.
 */
class ServiceExceptionTestCore extends CoreBaseUnitTest {

    @Test
    void deveCriarComConstrutorPadrao() {
        // Act
        ServiceException exception = new ServiceException();

        // Assert
        assertThat(exception.getErrorCode()).isEqualTo("SERVICE_ERROR");
        assertThat(exception.hasErros()).isFalse();
    }

    @Test
    void deveCriarComErro() {
        // Arrange
        String error = "Erro de validação";

        // Act
        ServiceException exception = new ServiceException(error);

        // Assert
        assertThat(exception.hasErros()).isTrue();
        assertThat(exception.getErrors()).anyMatch(e -> e.contains(error));
    }

    @Test
    void deveCriarComCodigoEMensagem() {
        // Arrange
        String errorCode = "VALIDATION_ERROR";
        String message = "Erro de validação";

        // Act
        ServiceException exception = new ServiceException(errorCode, message);

        // Assert
        assertThat(exception.getErrorCode()).isEqualTo(errorCode);
    }

    @Test
    void deveCriarComCodigoMensagemECausa() {
        // Arrange
        String errorCode = "VALIDATION_ERROR";
        String message = "Erro de validação";
        Throwable cause = new RuntimeException("Causa original");

        // Act
        ServiceException exception = new ServiceException(errorCode, message, cause);

        // Assert
        assertThat(exception.getErrorCode()).isEqualTo(errorCode);
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    void deveAdicionarExcecao() {
        // Arrange
        ServiceException exception = new ServiceException();
        Exception ex = new RuntimeException("Erro 1");

        // Act
        exception.add(ex);

        // Assert
        assertThat(exception.hasErros()).isTrue();
        assertThat(exception.getCause()).isEqualTo(ex);
    }

    @Test
    void deveAdicionarMensagemDeErro() {
        // Arrange
        ServiceException exception = new ServiceException();
        String error = "Erro de validação";

        // Act
        exception.add(error);

        // Assert
        assertThat(exception.hasErros()).isTrue();
        assertThat(exception.getErrors()).anyMatch(e -> e.contains(error));
    }

    @Test
    void deveVerificarHasErrosSemErros() {
        // Arrange
        ServiceException exception = new ServiceException();

        // Act
        boolean hasErrors = exception.hasErros();

        // Assert
        assertThat(hasErrors).isFalse();
    }

    @Test
    void deveVerificarHasErrosComErros() {
        // Arrange
        ServiceException exception = new ServiceException("Erro");

        // Act
        boolean hasErrors = exception.hasErros();

        // Assert
        assertThat(hasErrors).isTrue();
    }

    @Test
    void deveObterErros() {
        // Arrange
        ServiceException exception = new ServiceException();
        exception.add("Erro 1");
        exception.add("Erro 2");

        // Act
        var errors = exception.getErrors().toList();

        // Assert
        assertThat(errors).hasSize(2);
        assertThat(errors).contains("Erro 1", "Erro 2");
    }

    @Test
    void deveObterMensagemComErros() {
        // Arrange
        ServiceException exception = new ServiceException();
        exception.add("Erro 1");
        exception.add("Erro 2");

        // Act
        var errors = exception.getErrors().toList();

        // Assert
        assertThat(errors).hasSize(2);
        assertThat(errors).contains("Erro 1", "Erro 2");
    }
}
