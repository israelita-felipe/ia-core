package com.ia.core.service.dto.filter;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("OperatorDTO")
class OperatorDTOTestCore extends CoreBaseUnitTest {

    @Nested
    @DisplayName("build")
    class Build {

        @Test
        @DisplayName("CT001 - Deve construir Predicate para EQUAL")
        void deveConstruirPredicateEqual() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("nome")
                .value("João")
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.EQUAL.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }

        @Test
        @DisplayName("CT002 - Deve construir Predicate para LIKE")
        void deveConstruirPredicateLike() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("nome")
                .value("João%")
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.LIKE.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }

        @Test
        @DisplayName("CT003 - Deve construir Predicate para IN")
        void deveConstruirPredicateIn() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("nome")
                .value("João")
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.IN.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }

        @Test
        @DisplayName("CT004 - Deve construir Predicate para GREATER_THAN")
        void deveConstruirPredicateGreaterThan() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("idade")
                .value(10)
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.GREATER_THAN.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }

        @Test
        @DisplayName("CT005 - Deve construir Predicate para GREATER_THAN_OR_EQUAL_TO")
        void deveConstruirPredicateGreaterThanOrEqual() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("idade")
                .value(10)
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.GREATER_THAN_OR_EQUAL_TO.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }

        @Test
        @DisplayName("CT006 - Deve construir Predicate para LESS_THAN")
        void deveConstruirPredicateLessThan() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("idade")
                .value(10)
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.LESS_THAN.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }

        @Test
        @DisplayName("CT008 - Deve construir Predicate para LESS_THAN_OR_EQUAL_TO")
        void deveConstruirPredicateLessThanOrEqual() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("idade")
                .value(10)
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.LESS_THAN_OR_EQUAL_TO.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }

        @Test
        @DisplayName("CT009 - Deve construir Predicate para NOT_EQUAL")
        void deveConstruirPredicateNotEqual() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("nome")
                .value("João")
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.NOT_EQUAL.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }
    }

    @Nested
    @DisplayName("valores")
    class Valores {

        @Test
        @DisplayName("CT010 - Deve ter todos os operadores esperados")
        void deveTerTodosOperadoresEsperados() {
            // Act & Assert
            assertThat(OperatorDTO.values()).contains(
                OperatorDTO.EQUAL,
                OperatorDTO.NOT_EQUAL,
                OperatorDTO.LIKE,
                OperatorDTO.IN,
                OperatorDTO.GREATER_THAN,
                OperatorDTO.GREATER_THAN_OR_EQUAL_TO,
                OperatorDTO.LESS_THAN,
                OperatorDTO.LESS_THAN_OR_EQUAL_TO
            );
        }
    }

    @Nested
    @DisplayName("negate")
    class Negate {

        @Test
        @DisplayName("CT011 - Deve construir Predicate com negate true")
        void deveConstruirPredicateComNegate() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("nome")
                .value("João")
                .negate(true)
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.EQUAL.build(request, initialPredicate, false);

            // Assert
            assertThat(predicate).isNotNull();
        }
    }

    @Nested
    @DisplayName("disjunction")
    class Disjunction {

        @Test
        @DisplayName("CT012 - Deve construir Predicate com disjunction true")
        void deveConstruirPredicateComDisjunction() {
            // Arrange
            FilterRequestDTO request = FilterRequestDTO.builder()
                .key("nome")
                .value("João")
                .build();
            java.util.function.Predicate<Object> initialPredicate = obj -> true;

            // Act
            java.util.function.Predicate predicate = OperatorDTO.EQUAL.build(request, initialPredicate, true);

            // Assert
            assertThat(predicate).isNotNull();
        }
    }

    @Nested
    @DisplayName("cloneObject")
    class CloneObject {

        @Test
        @DisplayName("CT013 - Deve retornar a mesma instância")
        void deveRetornarMesmaInstancia() {
            // Act
            var clone = OperatorDTO.EQUAL.cloneObject();

            // Assert
            assertThat(clone).isSameAs(OperatorDTO.EQUAL);
        }
    }
}