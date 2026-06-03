package com.ia.core.nlp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Node")
class NodeTest {

    @Nested
    @DisplayName("valueOf")
    class ValueOf {

        @Test
        @DisplayName("Deve criar nó com valores corretos")
        void deveCriarNoComValoresCorretos() {
            Node node = Node.valueOf(
                BigDecimal.ONE, BigDecimal.valueOf(2),
                BigDecimal.valueOf(3), BigDecimal.valueOf(4));

            assertThat(node.getG()).isEqualByComparingTo(BigDecimal.ONE);
            assertThat(node.getA()).isEqualByComparingTo(BigDecimal.valueOf(2));
            assertThat(node.getT()).isEqualByComparingTo(BigDecimal.valueOf(3));
            assertThat(node.getC()).isEqualByComparingTo(BigDecimal.valueOf(4));
        }

        @Test
        @DisplayName("Deve criar nó não negado por padrão")
        void deveCriarNaoNegadoPorPadrao() {
            Node node = Node.valueOf(BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO);
            assertThat(node.isNegated()).isFalse();
        }
    }

    @Nested
    @DisplayName("add")
    class Add {

        @Test
        @DisplayName("Deve somar dois nós corretamente")
        void deveSomarDoisNosCorretamente() {
            Node a = Node.valueOf(BigDecimal.ONE, BigDecimal.valueOf(2),
                BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            Node b = Node.valueOf(BigDecimal.valueOf(10), BigDecimal.valueOf(20),
                BigDecimal.valueOf(30), BigDecimal.valueOf(40));

            Node result = a.add(b);

            assertThat(result.getG()).isEqualByComparingTo(BigDecimal.valueOf(11));
            assertThat(result.getA()).isEqualByComparingTo(BigDecimal.valueOf(22));
            assertThat(result.getT()).isEqualByComparingTo(BigDecimal.valueOf(33));
            assertThat(result.getC()).isEqualByComparingTo(BigDecimal.valueOf(44));
        }
    }

    @Nested
    @DisplayName("multiply escalar")
    class MultiplyEscalar {

        @Test
        @DisplayName("Deve multiplicar por escalar corretamente")
        void deveMultiplicarPorEscalarCorretamente() {
            Node node = Node.valueOf(BigDecimal.valueOf(2), BigDecimal.valueOf(3),
                BigDecimal.valueOf(4), BigDecimal.valueOf(5));

            Node result = node.multiply(BigDecimal.valueOf(10));

            assertThat(result.getG()).isEqualByComparingTo(BigDecimal.valueOf(20));
            assertThat(result.getA()).isEqualByComparingTo(BigDecimal.valueOf(30));
            assertThat(result.getT()).isEqualByComparingTo(BigDecimal.valueOf(40));
            assertThat(result.getC()).isEqualByComparingTo(BigDecimal.valueOf(50));
        }

        @Test
        @DisplayName("Deve multiplicar por zero")
        void deveMultiplicarPorZero() {
            Node node = Node.valueOf(BigDecimal.valueOf(5), BigDecimal.valueOf(5),
                BigDecimal.valueOf(5), BigDecimal.valueOf(5));

            Node result = node.multiply(BigDecimal.ZERO);

            assertThat(result.getG()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(result.getA()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(result.getT()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(result.getC()).isEqualByComparingTo(BigDecimal.ZERO);
        }
    }

    @Nested
    @DisplayName("multiply matricial")
    class MultiplyMatricial {

        @Test
        @DisplayName("Deve multiplicar matrizes 2x2 corretamente")
        void deveMultiplicarMatrizesCorretamente() {
            // Identity matrix
            Node identity = Node.valueOf(BigDecimal.ONE, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ONE);
            Node other = Node.valueOf(BigDecimal.valueOf(2), BigDecimal.valueOf(3),
                BigDecimal.valueOf(4), BigDecimal.valueOf(5));

            Node result = identity.multiply(other);

            assertThat(result.getG()).isEqualByComparingTo(BigDecimal.valueOf(2));
            assertThat(result.getA()).isEqualByComparingTo(BigDecimal.valueOf(3));
            assertThat(result.getT()).isEqualByComparingTo(BigDecimal.valueOf(4));
            assertThat(result.getC()).isEqualByComparingTo(BigDecimal.valueOf(5));
        }
    }

    @Nested
    @DisplayName("compact")
    class Compact {

        @Test
        @DisplayName("Deve compactar nó sem erro")
        void deveCompactarSemErro() {
            Node node = Node.valueOf(BigDecimal.ONE, BigDecimal.valueOf(2),
                BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            Node compacted = node.compact();
            assertThat(compacted).isNotNull();
            assertThat(compacted.getG()).isNotNull();
            assertThat(compacted.getA()).isNotNull();
            assertThat(compacted.getT()).isNotNull();
            assertThat(compacted.getC()).isNotNull();
        }
    }
}
