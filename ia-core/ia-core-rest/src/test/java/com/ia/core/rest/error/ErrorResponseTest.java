package com.ia.core.rest.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ErrorResponse")
class ErrorResponseTest {

    @Nested
    @DisplayName("of - factory básico")
    class Of {

        @Test
        @DisplayName("Deve criar ErrorResponse básico com todos os campos")
        void deveCriarErroBasico() {
            ErrorResponse response = ErrorResponse.of(404, "NOT_FOUND",
                "Recurso não encontrado", "/api/users/1", "trace-123");

            assertThat(response.getStatus()).isEqualTo(404);
            assertThat(response.getErrorCode()).isEqualTo("NOT_FOUND");
            assertThat(response.getMessage()).isEqualTo("Recurso não encontrado");
            assertThat(response.getPath()).isEqualTo("/api/users/1");
            assertThat(response.getTraceId()).isEqualTo("trace-123");
            assertThat(response.getTimestamp()).isNotNull();
            assertThat(response.getDetails()).isNull();
            assertThat(response.getFieldErrors()).isNull();
            assertThat(response.getException()).isNull();
        }
    }

    @Nested
    @DisplayName("withDetails")
    class WithDetails {

        @Test
        @DisplayName("Deve criar ErrorResponse com detalhes de validação")
        void deveCriarComDetalhes() {
            ErrorResponse.ErrorDetail detail = ErrorResponse.ErrorDetail.builder()
                .withCode("INVALID_FIELD")
                .withMessage("Campo obrigatório")
                .withField("email")
                .withRejectedValue("")
                .build();

            ErrorResponse response = ErrorResponse.withDetails(400, "VALIDATION_ERROR",
                "Erro de validação", "/api/users", "trace-456", List.of(detail));

            assertThat(response.getStatus()).isEqualTo(400);
            assertThat(response.getDetails()).hasSize(1);
            assertThat(response.getDetails().getFirst().getCode()).isEqualTo("INVALID_FIELD");
            assertThat(response.getDetails().getFirst().getField()).isEqualTo("email");
        }
    }

    @Nested
    @DisplayName("withFieldErrors")
    class WithFieldErrors {

        @Test
        @DisplayName("Deve criar ErrorResponse com erros de campo")
        void deveCriarComErrosDeCampo() {
            Map<String, Set<String>> fieldErrors = Map.of(
                "email", Set.of("deve ser válido", "não pode ser vazio"),
                "nome", Set.of("obrigatório")
            );

            ErrorResponse response = ErrorResponse.withFieldErrors(400, "VALIDATION_ERROR",
                "Erro de validação", "/api/users", "trace-789", fieldErrors);

            assertThat(response.getFieldErrors()).hasSize(2);
            assertThat(response.getFieldErrors().get("email")).hasSize(2);
            assertThat(response.getFieldErrors().get("nome")).hasSize(1);
        }
    }

    @Nested
    @DisplayName("ErrorDetail")
    class ErrorDetailTest {

        @Test
        @DisplayName("Deve criar ErrorDetail com builder")
        void deveCriarComBuilder() {
            ErrorResponse.ErrorDetail detail = ErrorResponse.ErrorDetail.builder()
                .withCode("ERR_001")
                .withMessage("mensagem de erro")
                .withField("campo")
                .withRejectedValue("valor")
                .build();

            assertThat(detail.getCode()).isEqualTo("ERR_001");
            assertThat(detail.getMessage()).isEqualTo("mensagem de erro");
            assertThat(detail.getField()).isEqualTo("campo");
            assertThat(detail.getRejectedValue()).isEqualTo("valor");
        }

        @Test
        @DisplayName("toString deve conter informações do detalhe")
        void toStringDeveConterInformacoes() {
            ErrorResponse.ErrorDetail detail = ErrorResponse.ErrorDetail.builder()
                .withCode("CODE")
                .withMessage("msg")
                .build();

            assertThat(detail.toString()).contains("CODE").contains("msg");
        }
    }

    @Nested
    @DisplayName("builder")
    class BuilderTest {

        @Test
        @DisplayName("Deve criar ErrorResponse com builder completo")
        void deveCriarComBuilderCompleto() {
            ErrorResponse response = ErrorResponse.builder()
                .withStatus(500)
                .withErrorCode("INTERNAL_ERROR")
                .withMessage("Erro interno")
                .withPath("/api/test")
                .withTraceId("trace-000")
                .withException("java.lang.NullPointerException")
                .build();

            assertThat(response.getStatus()).isEqualTo(500);
            assertThat(response.getException()).isEqualTo("java.lang.NullPointerException");
        }
    }

    @Nested
    @DisplayName("toString")
    class ToStringTest {

        @Test
        @DisplayName("toString deve conter informações relevantes")
        void toStringDeveConterInformacoes() {
            ErrorResponse response = ErrorResponse.of(400, "ERR", "msg", "/path", "tid");
            assertThat(response.toString()).contains("400").contains("ERR");
        }
    }
}
