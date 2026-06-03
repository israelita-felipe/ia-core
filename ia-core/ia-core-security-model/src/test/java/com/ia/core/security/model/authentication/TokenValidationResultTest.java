package com.ia.core.security.model.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TokenValidationResult")
class TokenValidationResultTest {

    @Nested
    @DisplayName("success")
    class Success {

        @Test
        @DisplayName("Deve criar resultado válido")
        void deveCriarResultadoValido() {
            TokenValidationResult result = TokenValidationResult.success();
            assertThat(result.isValid()).isTrue();
            assertThat(result.isInvalid()).isFalse();
            assertThat(result.getErrorMessage()).isEmpty();
            assertThat(result.getErrorCode()).isNull();
            assertThat(result.getDetails()).isEmpty();
        }
    }

    @Nested
    @DisplayName("failure")
    class Failure {

        @Test
        @DisplayName("Deve criar resultado com mensagem de erro")
        void deveCriarComMensagemDeErro() {
            TokenValidationResult result = TokenValidationResult.failure("Token inválido");
            assertThat(result.isValid()).isFalse();
            assertThat(result.isInvalid()).isTrue();
            assertThat(result.getErrorMessage()).contains("Token inválido");
            assertThat(result.getErrorCode()).isEqualTo("VALIDATION_FAILED");
        }

        @Test
        @DisplayName("Deve criar resultado com código de erro personalizado")
        void deveCriarComCodigoPersonalizado() {
            TokenValidationResult result = TokenValidationResult.failure("Erro", "CUSTOM_CODE");
            assertThat(result.getErrorCode()).isEqualTo("CUSTOM_CODE");
        }

        @Test
        @DisplayName("Deve criar resultado com detalhes")
        void deveCriarComDetalhes() {
            Map<String, Object> details = Map.of("key", "value");
            TokenValidationResult result = TokenValidationResult.failure("Erro", "CODE", details);
            assertThat(result.getDetails()).containsEntry("key", "value");
        }
    }

    @Nested
    @DisplayName("expired")
    class Expired {

        @Test
        @DisplayName("Deve criar resultado de token expirado")
        void deveCriarTokenExpirado() {
            Instant expiration = Instant.parse("2025-01-01T00:00:00Z");
            TokenValidationResult result = TokenValidationResult.expired(expiration);

            assertThat(result.isValid()).isFalse();
            assertThat(result.getErrorCode()).isEqualTo("TOKEN_EXPIRED");
            assertThat(result.getErrorMessage()).isPresent();
            assertThat(result.getDetails()).containsKey("expirationTime");
        }
    }

    @Nested
    @DisplayName("malformed")
    class Malformed {

        @Test
        @DisplayName("Deve criar resultado de token malformado")
        void deveCriarTokenMalformado() {
            TokenValidationResult result = TokenValidationResult.malformed("header inválido");

            assertThat(result.isValid()).isFalse();
            assertThat(result.getErrorCode()).isEqualTo("TOKEN_MALFORMED");
            assertThat(result.getDetails()).containsEntry("reason", "header inválido");
        }
    }

    @Nested
    @DisplayName("invalidSignature")
    class InvalidSignature {

        @Test
        @DisplayName("Deve criar resultado de assinatura inválida")
        void deveCriarAssinaturaInvalida() {
            TokenValidationResult result = TokenValidationResult.invalidSignature();

            assertThat(result.isValid()).isFalse();
            assertThat(result.getErrorCode()).isEqualTo("TOKEN_INVALID_SIGNATURE");
            assertThat(result.getErrorMessage()).isPresent();
            assertThat(result.getErrorMessage().get()).contains("Assinatura");
        }
    }

    @Nested
    @DisplayName("combine")
    class Combine {

        @Test
        @DisplayName("Deve retornar outro quando este é válido")
        void deveRetornarOutroQuandoEsteValido() {
            TokenValidationResult valid = TokenValidationResult.success();
            TokenValidationResult invalid = TokenValidationResult.failure("Erro");

            TokenValidationResult result = valid.combine(invalid);
            assertThat(result.isValid()).isFalse();
        }

        @Test
        @DisplayName("Deve retornar este quando outro é válido")
        void deveRetornarEsteQuandoOutroValido() {
            TokenValidationResult invalid = TokenValidationResult.failure("Erro");
            TokenValidationResult valid = TokenValidationResult.success();

            TokenValidationResult result = invalid.combine(valid);
            assertThat(result.isValid()).isFalse();
            assertThat(result.getErrorMessage()).contains("Erro");
        }

        @Test
        @DisplayName("Deve combinar mensagens quando ambos inválidos")
        void deveCombinarMensagensQuandoAmbosInvalidos() {
            TokenValidationResult first = TokenValidationResult.failure("Erro1");
            TokenValidationResult second = TokenValidationResult.failure("Erro2");

            TokenValidationResult result = first.combine(second);
            assertThat(result.isValid()).isFalse();
            assertThat(result.getErrorMessage()).isPresent();
            assertThat(result.getErrorMessage().get()).contains("Erro1").contains("Erro2");
        }

        @Test
        @DisplayName("Deve retornar válido quando ambos válidos")
        void deveRetornarValidoQuandoAmbosValidos() {
            TokenValidationResult a = TokenValidationResult.success();
            TokenValidationResult b = TokenValidationResult.success();

            assertThat(a.combine(b).isValid()).isTrue();
        }
    }

    @Nested
    @DisplayName("getDetail")
    class GetDetail {

        @Test
        @DisplayName("Deve retornar detalhe específico")
        void deveRetornarDetalheEspecifico() {
            Map<String, Object> details = Map.of("key", "value");
            TokenValidationResult result = TokenValidationResult.failure("Erro", "CODE", details);

            assertThat(result.<String>getDetail("key")).contains("value");
            assertThat(result.<String>getDetail("missing")).isEmpty();
        }
    }

    @Nested
    @DisplayName("equals e hashCode")
    class EqualsHashCode {

        @Test
        @DisplayName("Deve ser igual a si mesmo")
        void deveSerIgualASiMesmo() {
            TokenValidationResult result = TokenValidationResult.success();
            assertThat(result).isEqualTo(result);
        }

        @Test
        @DisplayName("Dois success devem ser iguais")
        void doisSuccessDevemSerIguais() {
            assertThat(TokenValidationResult.success()).isEqualTo(TokenValidationResult.success());
        }

        @Test
        @DisplayName("Success e failure devem ser diferentes")
        void successEFailureDevemSerDiferentes() {
            assertThat(TokenValidationResult.success()).isNotEqualTo(TokenValidationResult.failure("err"));
        }

        @Test
        @DisplayName("Não deve ser igual a null")
        void naoDeveSerIgualANull() {
            assertThat(TokenValidationResult.success()).isNotEqualTo(null);
        }

        @Test
        @DisplayName("hashCode deve ser consistente")
        void hashCodeDeveSerConsistente() {
            TokenValidationResult a = TokenValidationResult.success();
            TokenValidationResult b = TokenValidationResult.success();
            assertThat(a.hashCode()).isEqualTo(b.hashCode());
        }
    }

    @Nested
    @DisplayName("toString")
    class ToStringTest {

        @Test
        @DisplayName("toString de success deve indicar valid=true")
        void toStringSuccessDeveIndicarValidTrue() {
            assertThat(TokenValidationResult.success().toString()).contains("valid=true");
        }

        @Test
        @DisplayName("toString de failure deve conter mensagem de erro")
        void toStringFailureDeveConterMensagem() {
            String str = TokenValidationResult.failure("erro teste").toString();
            assertThat(str).contains("valid=false").contains("erro teste");
        }
    }
}
