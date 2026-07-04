package com.ia.core.model.exception;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes para ValidationException baseados nos casos de teste documentados.
 */
class ValidationExceptionTestCore extends CoreBaseUnitTest {

    @Test
    void deveCriarExcecaoComMensagem() {
        // Arrange
        String mensagem = "Erro de validação";

        // Act
        ValidationException exception = new ValidationException(mensagem);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode()).isEqualTo("VALIDATION_ERROR");
        assertThat(exception.getMessage()).isEqualTo(mensagem);
        assertThat(exception.getField()).isNull();
        assertThat(exception.getRejectedValue()).isNull();
    }

    @Test
    void deveCriarExcecaoComCampoValorRejeitadoEMensagem() {
        // Arrange
        String campo = "email";
        Object valorRejeitado = "invalido";
        String mensagem = "Email em formato inválido";

        // Act
        ValidationException exception = new ValidationException(campo, valorRejeitado, mensagem);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode()).isEqualTo("VALIDATION_ERROR");
        assertThat(exception.getMessage()).isEqualTo(mensagem);
        assertThat(exception.getField()).isEqualTo(campo);
        assertThat(exception.getRejectedValue()).isEqualTo(valorRejeitado);
    }

    @Test
    void deveRetornarCampoEValorRejeitado() {
        // Arrange
        String campo = "cpf";
        Object valorRejeitado = "123";
        String mensagem = "CPF inválido";
        ValidationException exception = new ValidationException(campo, valorRejeitado, mensagem);

        // Act
        String campoRetornado = exception.getField();
        Object valorRetornado = exception.getRejectedValue();

        // Assert
        assertThat(campoRetornado).isEqualTo(campo);
        assertThat(valorRetornado).isEqualTo(valorRejeitado);
    }

    @Test
    void deveLancarExcecaoComCampoEValorRejeitado() {
        // Arrange
        String campo = "cpf";
        Object valorRejeitado = "123";
        String mensagem = "CPF inválido";

        // Act & Assert
        assertThatThrownBy(() -> {
            throw new ValidationException(campo, valorRejeitado, mensagem);
        }).isInstanceOf(ValidationException.class)
          .hasMessage(mensagem)
          .extracting("errorCode")
          .isEqualTo("VALIDATION_ERROR");
    }
}
