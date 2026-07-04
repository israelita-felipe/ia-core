package com.ia.core.model.util;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para Patterns baseados nos casos de teste documentados.
 */
class PatternsTestCore extends CoreBaseUnitTest {

    @Test
    void deveValidarEmailValido() {
        // Arrange
        String emailValido = "usuario@exemplo.com";

        // Act
        boolean isValid = emailValido.matches(Patterns.EMAIL);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void deveInvalidarEmailInvalido() {
        // Arrange
        String emailInvalido = "usuarioexemplo";

        // Act
        boolean isValid = emailInvalido.matches(Patterns.EMAIL);

        // Assert
        assertThat(isValid).isFalse();
    }

    @Test
    void deveValidarTelefoneCelularValido() {
        // Arrange
        String telefoneValido = "(11) 99999-9999";

        // Act
        boolean isValid = telefoneValido.matches(Patterns.TELEFONE_CELULAR);

        // Assert
        assertThat(isValid).isTrue();
    }

    @Test
    void deveValidarTelefoneFixoValido() {
        // Arrange
        String telefoneValido = "(11) 1234-5678";

        // Act
        boolean isValid = telefoneValido.matches(Patterns.TELEFONE_FIXO);

        // Assert
        assertThat(isValid).isTrue();
    }
}
