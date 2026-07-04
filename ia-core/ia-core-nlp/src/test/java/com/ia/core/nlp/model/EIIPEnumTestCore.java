package com.ia.core.nlp.model;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para EIIPEnum baseados nos casos de teste documentados.
 */
class EIIPEnumTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerValoresDoEnum() {
        // Act & Assert
        assertThat(EIIPEnum.values()).isNotEmpty();
        assertThat(EIIPEnum.values()).contains(
            EIIPEnum.G,
            EIIPEnum.A,
            EIIPEnum.T,
            EIIPEnum.C
        );
    }

    @Test
    void deveTerValorEIIPDeG() {
        // Arrange
        BigDecimal valorEsperado = new BigDecimal("0.0806");

        // Act
        BigDecimal valor = EIIPEnum.G.getValue();

        // Assert
        assertThat(valor).isEqualTo(valorEsperado);
    }

    @Test
    void deveTerValorEIIPDeA() {
        // Arrange
        BigDecimal valorEsperado = new BigDecimal("0.1260");

        // Act
        BigDecimal valor = EIIPEnum.A.getValue();

        // Assert
        assertThat(valor).isEqualTo(valorEsperado);
    }

    @Test
    void deveTerValorEIIPDeT() {
        // Arrange
        BigDecimal valorEsperado = new BigDecimal("0.1335");

        // Act
        BigDecimal valor = EIIPEnum.T.getValue();

        // Assert
        assertThat(valor).isEqualTo(valorEsperado);
    }

    @Test
    void deveTerValorEIIPDeC() {
        // Arrange
        BigDecimal valorEsperado = new BigDecimal("0.1340");

        // Act
        BigDecimal valor = EIIPEnum.C.getValue();

        // Assert
        assertThat(valor).isEqualTo(valorEsperado);
    }
}
