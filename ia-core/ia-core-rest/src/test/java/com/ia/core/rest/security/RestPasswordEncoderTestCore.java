package com.ia.core.rest.security;

import com.ia.core.security.service.model.user.UserPasswordEncoder;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para RestPasswordEncoder baseados nos casos de teste documentados.
 */
class RestPasswordEncoderTestCore extends CoreBaseUnitTest {

    @Test
    void deveHerdarDeBCryptPasswordEncoder() {
        // Arrange
        RestPasswordEncoder encoder = new RestPasswordEncoder();

        // Act & Assert
        assertThat(encoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    void deveImplementarUserPasswordEncoder() {
        // Arrange
        RestPasswordEncoder encoder = new RestPasswordEncoder();

        // Act & Assert
        assertThat(encoder).isInstanceOf(UserPasswordEncoder.class);
    }

    @Test
    void deveCodificarSenha() {
        // Arrange
        RestPasswordEncoder encoder = new RestPasswordEncoder();
        String plainPassword = "password123";

        // Act
        String encodedPassword = encoder.encode(plainPassword);

        // Assert
        assertThat(encodedPassword).isNotEqualTo(plainPassword);
        assertThat(encodedPassword).matches("\\$2[ab]\\$.+");
    }

    @Test
    void deveVerificarSenhaCorreta() {
        // Arrange
        RestPasswordEncoder encoder = new RestPasswordEncoder();
        String plainPassword = "password123";
        String encodedPassword = encoder.encode(plainPassword);

        // Act
        boolean matches = encoder.matches(plainPassword, encodedPassword);

        // Assert
        assertThat(matches).isTrue();
    }

    @Test
    void deveVerificarSenhaIncorreta() {
        // Arrange
        RestPasswordEncoder encoder = new RestPasswordEncoder();
        String plainPassword = "password123";
        String encodedPassword = encoder.encode(plainPassword);
        String wrongPassword = "wrongpassword";

        // Act
        boolean matches = encoder.matches(wrongPassword, encodedPassword);

        // Assert
        assertThat(matches).isFalse();
    }
}
