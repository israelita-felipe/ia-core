package com.ia.core.model.filter;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para Operator baseados nos casos de teste documentados.
 */
class OperatorTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerOperadoresBasicosDisponiveis() {
        // Act & Assert
        assertThat(Operator.values()).isNotEmpty();
        assertThat(Operator.values()).contains(
            Operator.EQUAL,
            Operator.NOT_EQUAL,
            Operator.GREATER_THAN,
            Operator.LESS_THAN,
            Operator.GREATER_THAN_OR_EQUAL_TO,
            Operator.LESS_THAN_OR_EQUAL_TO,
            Operator.LIKE,
            Operator.IN
        );
    }

    @Test
    void deveTerOperadorDeIgualdade() {
        // Act
        Operator operador = Operator.EQUAL;

        // Assert
        assertThat(operador).isNotNull();
        assertThat(operador.name()).isEqualTo("EQUAL");
    }

    @Test
    void deveTerOperadorDeDesigualdade() {
        // Act
        Operator operador = Operator.NOT_EQUAL;

        // Assert
        assertThat(operador).isNotNull();
        assertThat(operador.name()).isEqualTo("NOT_EQUAL");
    }

    @Test
    void deveTerOperadoresDeComparacaoNumerica() {
        // Act & Assert
        assertThat(Operator.GREATER_THAN.name()).isEqualTo("GREATER_THAN");
        assertThat(Operator.LESS_THAN.name()).isEqualTo("LESS_THAN");
        assertThat(Operator.GREATER_THAN_OR_EQUAL_TO.name()).isEqualTo("GREATER_THAN_OR_EQUAL_TO");
        assertThat(Operator.LESS_THAN_OR_EQUAL_TO.name()).isEqualTo("LESS_THAN_OR_EQUAL_TO");
    }

    @Test
    void deveTerOperadorLike() {
        // Act
        Operator operador = Operator.LIKE;

        // Assert
        assertThat(operador).isNotNull();
        assertThat(operador.name()).isEqualTo("LIKE");
    }

    @Test
    void deveTerOperadorIn() {
        // Act
        Operator operador = Operator.IN;

        // Assert
        assertThat(operador).isNotNull();
        assertThat(operador.name()).isEqualTo("IN");
    }
}
