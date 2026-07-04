package com.ia.core.security.model.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Security Exceptions")
class SecurityExceptionTest {

    @Nested
    @DisplayName("AccessDeniedException")
    class AccessDeniedTests {

        @Test
        @DisplayName("Deve criar com mensagem")
        void deveCriarComMensagem() {
            AccessDeniedException ex = new AccessDeniedException("Acesso negado");
            assertThat(ex.getMessage()).isEqualTo("Acesso negado");
            assertThat(ex).isInstanceOf(RuntimeException.class);
            assertThat(ex.getErrorCode()).isNotNull();
        }

        @Test
        @DisplayName("Deve criar com recurso e permissão")
        void deveCriarComRecursoEPermissao() {
            AccessDeniedException ex = new AccessDeniedException("Acesso negado", "/api/admin", "ADMIN");
            assertThat(ex.getMessage()).isEqualTo("Acesso negado");
        }
    }

    @Nested
    @DisplayName("AuthenticationException")
    class AuthenticationTests {

        @Test
        @DisplayName("Deve criar com mensagem")
        void deveCriarComMensagem() {
            AuthenticationException ex = new AuthenticationException("Falha na autenticação");
            assertThat(ex.getMessage()).isEqualTo("Falha na autenticação");
            assertThat(ex.getErrorCode()).isNotNull();
        }

        @Test
        @DisplayName("Deve criar com mensagem e identificador")
        void deveCriarComIdentificador() {
            AuthenticationException ex = new AuthenticationException("Falha", "user@test.com");
            assertThat(ex.getMessage()).isEqualTo("Falha");
        }

        @Test
        @DisplayName("Deve criar com causa")
        void deveCriarComCausa() {
            RuntimeException cause = new RuntimeException("root");
            AuthenticationException ex = new AuthenticationException("Falha", cause);
            assertThat(ex.getCause()).isSameAs(cause);
        }
    }

    @Nested
    @DisplayName("InvalidTokenException")
    class InvalidTokenTests {

        @Test
        @DisplayName("Deve criar com mensagem")
        void deveCriarComMensagem() {
            InvalidTokenException ex = new InvalidTokenException("Token inválido");
            assertThat(ex.getMessage()).isEqualTo("Token inválido");
            assertThat(ex.getErrorCode()).isNotNull();
        }

        @Test
        @DisplayName("Deve criar com causa")
        void deveCriarComCausa() {
            RuntimeException cause = new RuntimeException("parse error");
            InvalidTokenException ex = new InvalidTokenException("Token inválido", cause);
            assertThat(ex.getCause()).isSameAs(cause);
        }
    }

    @Nested
    @DisplayName("TokenExpiredException")
    class TokenExpiredTests {

        @Test
        @DisplayName("Deve criar com Instant de expiração")
        void deveCriarComInstantDeExpiracao() {
            Instant expiration = Instant.parse("2025-01-01T00:00:00Z");
            TokenExpiredException ex = new TokenExpiredException(expiration);
            assertThat(ex.getMessage()).contains("Token expirado em:");
        }

        @Test
        @DisplayName("Deve criar com mensagem e Instant")
        void deveCriarComMensagemEInstant() {
            Instant expiration = Instant.parse("2025-06-01T12:00:00Z");
            TokenExpiredException ex = new TokenExpiredException("Custom msg", expiration);
            assertThat(ex.getMessage()).isEqualTo("Custom msg");
        }
    }

    @Nested
    @DisplayName("UserNotFoundException")
    class UserNotFoundTests {

        @Test
        @DisplayName("Deve criar com identificador")
        void deveCriarComIdentificador() {
            UserNotFoundException ex = new UserNotFoundException("user@test.com");
            assertThat(ex.getMessage()).contains("user@test.com");
            assertThat(ex.getErrorCode()).isNotNull();
        }

        @Test
        @DisplayName("Deve criar com identificador e mensagem")
        void deveCriarComIdentificadorEMensagem() {
            UserNotFoundException ex = new UserNotFoundException("admin", "Admin não encontrado");
            assertThat(ex.getMessage()).contains("admin");
        }
    }

    @Nested
    @DisplayName("SecurityException.determineErrorCode")
    class DetermineErrorCode {

        @Test
        @DisplayName("Deve converter CamelCase para SCREAMING_SNAKE_CASE sem sufixo Exception")
        void deveConverterParaSnakeCase() {
            AccessDeniedException ex = new AccessDeniedException("com/ia/test");
            assertThat(ex.getErrorCode()).isEqualTo("ACCESS_DENIED_EXCEPTION");
        }
    }
}
