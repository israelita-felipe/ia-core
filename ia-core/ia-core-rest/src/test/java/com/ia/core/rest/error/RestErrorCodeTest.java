package com.ia.core.rest.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("RestErrorCode")
class RestErrorCodeTest {

    @Nested
    @DisplayName("códigos de erro")
    class CodigosDeErro {

        @Test
        @DisplayName("AUTHENTICATION_ERROR deve ter status 401")
        void authenticationErrorDeveSerStatus401() {
            assertThat(RestErrorCode.AUTHENTICATION_ERROR.getHttpStatus()).isEqualTo(401);
            assertThat(RestErrorCode.AUTHENTICATION_ERROR.getCode()).isEqualTo("AUTHENTICATION_ERROR");
            assertThat(RestErrorCode.AUTHENTICATION_ERROR.getTranslationKey()).isEqualTo("error.authentication");
        }

        @Test
        @DisplayName("ACCESS_DENIED deve ter status 403")
        void accessDeniedDeveSerStatus403() {
            assertThat(RestErrorCode.ACCESS_DENIED.getHttpStatus()).isEqualTo(403);
        }

        @Test
        @DisplayName("ENTITY_NOT_FOUND deve ter status 404")
        void entityNotFoundDeveSerStatus404() {
            assertThat(RestErrorCode.ENTITY_NOT_FOUND.getHttpStatus()).isEqualTo(404);
        }

        @Test
        @DisplayName("VALIDATION_ERROR deve ter status 400")
        void validationErrorDeveSerStatus400() {
            assertThat(RestErrorCode.VALIDATION_ERROR.getHttpStatus()).isEqualTo(400);
        }

        @Test
        @DisplayName("DATA_INTEGRITY_VIOLATION deve ter status 409")
        void dataIntegrityViolationDeveSerStatus409() {
            assertThat(RestErrorCode.DATA_INTEGRITY_VIOLATION.getHttpStatus()).isEqualTo(409);
        }

        @Test
        @DisplayName("SERVICE_ERROR deve ter status 400")
        void serviceErrorDeveSerStatus400() {
            assertThat(RestErrorCode.SERVICE_ERROR.getHttpStatus()).isEqualTo(400);
        }

        @Test
        @DisplayName("INTERNAL_ERROR deve ter status 500")
        void internalErrorDeveSerStatus500() {
            assertThat(RestErrorCode.INTERNAL_ERROR.getHttpStatus()).isEqualTo(500);
        }
    }

    @Nested
    @DisplayName("propriedades")
    class Propriedades {

        @ParameterizedTest
        @EnumSource(RestErrorCode.class)
        @DisplayName("Todos os códigos devem ter propriedades não nulas")
        void todosOsCodigosDevemTerPropriedadesNaoNulas(RestErrorCode code) {
            assertThat(code.getCode()).isNotBlank();
            assertThat(code.getTranslationKey()).isNotBlank();
            assertThat(code.getHttpStatus()).isPositive();
        }

        @ParameterizedTest
        @EnumSource(RestErrorCode.class)
        @DisplayName("getHttpStatusValue deve ser igual a getHttpStatus")
        void httpStatusValueDeveSerIgual(RestErrorCode code) {
            assertThat(code.getHttpStatusValue()).isEqualTo(code.getHttpStatus());
        }
    }

    @Test
    @DisplayName("Deve ter 7 códigos de erro")
    void deveTerSeteCodigosDeErro() {
        assertThat(RestErrorCode.values()).hasSize(7);
    }
}
