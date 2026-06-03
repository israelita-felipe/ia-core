package com.ia.core.service.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ValidationError")
class ValidationErrorTest {

    @Nested
    @DisplayName("construtor dois argumentos")
    class ConstrutorDoisArgumentos {

        @Test
        @DisplayName("Deve criar com severidade ERROR por padrão")
        void deveCriarComSeveridadeErrorPorPadrao() {
            ValidationError error = new ValidationError("email", "Email inválido");
            assertThat(error.getField()).isEqualTo("email");
            assertThat(error.getMessage()).isEqualTo("Email inválido");
            assertThat(error.getSeverity()).isEqualTo(Severity.ERROR);
            assertThat(error.getRejectedValue()).isNull();
        }
    }

    @Nested
    @DisplayName("construtor com rejectedValue")
    class ConstrutorComRejectedValue {

        @Test
        @DisplayName("Deve criar com valor rejeitado")
        void deveCriarComValorRejeitado() {
            ValidationError error = new ValidationError("cpf", "CPF inválido", "123");
            assertThat(error.getRejectedValue()).isEqualTo("123");
            assertThat(error.getSeverity()).isEqualTo(Severity.ERROR);
        }
    }

    @Nested
    @DisplayName("construtor completo")
    class ConstrutorCompleto {

        @Test
        @DisplayName("Deve criar com todos os parâmetros")
        void deveCriarComTodosParametros() {
            ValidationError error = new ValidationError("campo", "aviso", Severity.WARNING, null);
            assertThat(error.getField()).isEqualTo("campo");
            assertThat(error.getMessage()).isEqualTo("aviso");
            assertThat(error.getSeverity()).isEqualTo(Severity.WARNING);
            assertThat(error.getRejectedValue()).isNull();
        }
    }

    @Nested
    @DisplayName("toString")
    class ToStringTest {

        @Test
        @DisplayName("Deve conter campo, mensagem e severidade")
        void deveConterCampoMensagemESeveridade() {
            ValidationError error = new ValidationError("email", "inválido");
            String str = error.toString();
            assertThat(str).contains("email");
            assertThat(str).contains("inválido");
            assertThat(str).contains("ERROR");
        }
    }

    @Nested
    @DisplayName("record accessors")
    class RecordAccessors {

        @Test
        @DisplayName("Deve ter acessores consistentes para record")
        void deveSerConsistente() {
            ValidationError error = new ValidationError("f", "m", Severity.INFO, "val");
            assertThat(error.field()).isEqualTo("f");
            assertThat(error.message()).isEqualTo("m");
            assertThat(error.severity()).isEqualTo(Severity.INFO);
            assertThat(error.rejectedValue()).isEqualTo("val");
        }
    }
}
