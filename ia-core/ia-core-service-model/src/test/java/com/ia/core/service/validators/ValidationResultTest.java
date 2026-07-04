package com.ia.core.service.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ValidationResult")
class ValidationResultTest {

    @Nested
    @DisplayName("empty()")
    class Empty {

        @Test
        @DisplayName("Deve retornar resultado sem erros")
        void deveRetornarResultadoSemErros() {
            ValidationResult result = ValidationResult.empty();
            assertThat(result.hasErrors()).isFalse();
            assertThat(result.hasErrorLevelErrors()).isFalse();
            assertThat(result.getErrors()).isEmpty();
        }

        @Test
        @DisplayName("Deve retornar mapa vazio para getErrorsByField")
        void deveRetornarMapaVazio() {
            ValidationResult result = ValidationResult.empty();
            assertThat(result.getErrorsByField()).isEmpty();
        }

        @Test
        @DisplayName("Deve retornar lista vazia para campo específico")
        void deveRetornarListaVaziaParaCampo() {
            ValidationResult result = ValidationResult.empty();
            assertThat(result.getErrorsByField("qualquer")).isEmpty();
        }

        @Test
        @DisplayName("Deve retornar lista vazia para severidade específica")
        void deveRetornarListaVaziaParaSeveridade() {
            ValidationResult result = ValidationResult.empty();
            assertThat(result.getErrorsBySeverity(Severity.ERROR)).isEmpty();
        }
    }

    @Nested
    @DisplayName("create()")
    class Create {

        @Test
        @DisplayName("Deve criar resultado mutável vazio")
        void deveCriarResultadoMutavelVazio() {
            ValidationResult result = ValidationResult.create();
            assertThat(result.hasErrors()).isFalse();
        }

        @Test
        @DisplayName("Deve permitir adicionar erros")
        void devePermitirAdicionarErros() {
            ValidationResult result = ValidationResult.create();
            result.addError(new ValidationError("campo", "mensagem"));
            assertThat(result.hasErrors()).isTrue();
            assertThat(result.getErrors()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("of(List)")
    class OfList {

        @Test
        @DisplayName("Deve criar resultado com lista de erros")
        void deveCriarComListaDeErros() {
            List<ValidationError> errors = List.of(
                new ValidationError("campo1", "erro1"),
                new ValidationError("campo2", "erro2")
            );
            ValidationResult result = ValidationResult.of(errors);
            assertThat(result.hasErrors()).isTrue();
            assertThat(result.getErrors()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("error(field, message)")
    class ErrorFactory {

        @Test
        @DisplayName("Deve criar resultado com um único erro ERROR")
        void deveCriarComUnicoErro() {
            ValidationResult result = ValidationResult.error("email", "Email inválido");
            assertThat(result.hasErrors()).isTrue();
            assertThat(result.hasErrorLevelErrors()).isTrue();
            assertThat(result.getErrors()).hasSize(1);
            assertThat(result.getErrors().getFirst().getField()).isEqualTo("email");
        }
    }

    @Nested
    @DisplayName("of(field, message, severity)")
    class OfFieldMessageSeverity {

        @Test
        @DisplayName("Deve criar com severidade WARNING")
        void deveCriarComSeveridadeWarning() {
            ValidationResult result = ValidationResult.of("campo", "aviso", Severity.WARNING);
            assertThat(result.hasErrors()).isTrue();
            assertThat(result.hasErrorLevelErrors()).isFalse();
            assertThat(result.getErrorsBySeverity(Severity.WARNING)).hasSize(1);
        }
    }

    @Nested
    @DisplayName("DefaultValidationResult")
    class DefaultValidation {

        @Test
        @DisplayName("Deve ordenar erros por severidade")
        void deveOrdenarPorSeveridade() {
            ValidationResult result = ValidationResult.create();
            result.addError(new ValidationError("f1", "info", Severity.INFO, null));
            result.addError(new ValidationError("f2", "error", Severity.ERROR, null));
            result.addError(new ValidationError("f3", "warn", Severity.WARNING, null));

            List<ValidationError> errors = result.getErrors();
            assertThat(errors.get(0).getSeverity()).isEqualTo(Severity.ERROR);
            assertThat(errors.get(1).getSeverity()).isEqualTo(Severity.WARNING);
            assertThat(errors.get(2).getSeverity()).isEqualTo(Severity.INFO);
        }

        @Test
        @DisplayName("Deve agrupar erros por campo")
        void deveAgruparPorCampo() {
            ValidationResult result = ValidationResult.create();
            result.addError(new ValidationError("email", "obrigatório"));
            result.addError(new ValidationError("email", "formato inválido"));
            result.addError(new ValidationError("nome", "obrigatório"));

            assertThat(result.getErrorsByField("email")).hasSize(2);
            assertThat(result.getErrorsByField("nome")).hasSize(1);
            assertThat(result.getErrorsByField("inexistente")).isEmpty();
        }

        @Test
        @DisplayName("Deve filtrar erros por severidade")
        void deveFiltrarPorSeveridade() {
            ValidationResult result = ValidationResult.create();
            result.addError(new ValidationError("f1", "e1", Severity.ERROR, null));
            result.addError(new ValidationError("f2", "w1", Severity.WARNING, null));
            result.addError(new ValidationError("f3", "e2", Severity.ERROR, null));

            assertThat(result.getErrorsBySeverity(Severity.ERROR)).hasSize(2);
            assertThat(result.getErrorsBySeverity(Severity.WARNING)).hasSize(1);
            assertThat(result.getErrorsBySeverity(Severity.INFO)).isEmpty();
        }

        @Test
        @DisplayName("Deve recalcular errorsByField após addError")
        void deveRecalcularAposAddError() {
            ValidationResult result = ValidationResult.create();
            result.addError(new ValidationError("campo", "erro1"));
            assertThat(result.getErrorsByField("campo")).hasSize(1);

            result.addError(new ValidationError("campo", "erro2"));
            assertThat(result.getErrorsByField("campo")).hasSize(2);
        }

        @Test
        @DisplayName("hasErrorLevelErrors retorna true quando há erros ERROR")
        void hasErrorLevelErrors() {
            ValidationResult result = ValidationResult.create();
            result.addError(new ValidationError("f", "m", Severity.WARNING, null));
            assertThat(result.hasErrorLevelErrors()).isFalse();

            result.addError(new ValidationError("f", "m", Severity.ERROR, null));
            assertThat(result.hasErrorLevelErrors()).isTrue();
        }
    }

    @Nested
    @DisplayName("EmptyValidationResult addError")
    class EmptyAddError {

        @Test
        @DisplayName("Deve lançar UnsupportedOperationException ao tentar addError no empty")
        void deveLancarUnsupportedOperationException() {
            ValidationResult result = ValidationResult.empty();
            assertThatThrownBy(() -> result.addError(new ValidationError("f", "m")))
                .isInstanceOf(UnsupportedOperationException.class);
        }
    }
}
