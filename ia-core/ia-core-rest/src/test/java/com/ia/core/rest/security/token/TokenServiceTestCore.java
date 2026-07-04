package com.ia.core.rest.security.token;

import com.ia.core.security.model.authentication.JwtToken;
import com.ia.core.security.model.authentication.TokenValidationResult;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para TokenService baseados nos casos de teste documentados.
 */
class TokenServiceTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerMetodoGenerateToken() {
        // Arrange
        TokenService tokenService = createMockTokenService();

        // Act
        JwtToken token = tokenService.generateToken("username", "userCode");

        // Assert
        assertThat(token).isNotNull();
    }

    @Test
    void deveTerMetodoValidateToken() {
        // Arrange
        TokenService tokenService = createMockTokenService();
        JwtToken token = JwtToken.from("token");

        // Act
        TokenValidationResult result = tokenService.validateToken(token);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    void deveTerMetodoGetUsernameFromToken() {
        // Arrange
        TokenService tokenService = createMockTokenService();
        JwtToken token = JwtToken.from("token");

        // Act
        String username = tokenService.getUsernameFromToken(token);

        // Assert
        assertThat(username).isNotNull();
    }

    @Test
    void deveTerMetodoGetUserCodeFromToken() {
        // Arrange
        TokenService tokenService = createMockTokenService();
        JwtToken token = JwtToken.from("token");

        // Act
        String userCode = tokenService.getUserCodeFromToken(token);

        // Assert
        assertThat(userCode).isNotNull();
    }

    private TokenService createMockTokenService() {
        return new TokenService() {
            @Override
            public JwtToken generateToken(String username, String userCode) {
                return JwtToken.from("mock-token");
            }

            @Override
            public TokenValidationResult validateToken(JwtToken token) {
                return TokenValidationResult.success();
            }

            @Override
            public String getUsernameFromToken(JwtToken token) {
                return "username";
            }

            @Override
            public String getUserCodeFromToken(JwtToken token) {
                return "userCode";
            }
        };
    }
}
