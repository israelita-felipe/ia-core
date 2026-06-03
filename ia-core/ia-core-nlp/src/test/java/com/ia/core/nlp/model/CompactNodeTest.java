package com.ia.core.nlp.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CompactNode")
class CompactNodeTest {

    @Nested
    @DisplayName("valueOf")
    class ValueOf {

        @Test
        @DisplayName("Deve criar nó compacto com valores corretos")
        void deveCriarComValoresCorretos() {
            CompactNode node = CompactNode.valueOf(BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            assertThat(node.getX()).isEqualByComparingTo(BigDecimal.valueOf(3));
            assertThat(node.getY()).isEqualByComparingTo(BigDecimal.valueOf(4));
            assertThat(node.isNegated()).isFalse();
        }
    }

    @Nested
    @DisplayName("multiply escalar")
    class MultiplyEscalar {

        @Test
        @DisplayName("Deve multiplicar por escalar corretamente")
        void deveMultiplicarCorretamente() {
            CompactNode node = CompactNode.valueOf(BigDecimal.valueOf(2), BigDecimal.valueOf(3));
            CompactNode result = node.multiply(BigDecimal.TEN);

            assertThat(result.getX()).isEqualByComparingTo(BigDecimal.valueOf(20));
            assertThat(result.getY()).isEqualByComparingTo(BigDecimal.valueOf(30));
        }
    }

    @Nested
    @DisplayName("multiply com CompactNode")
    class MultiplyComNode {

        @Test
        @DisplayName("Deve multiplicar dois nós compactos")
        void deveMultiplicarDoisNos() {
            CompactNode a = CompactNode.valueOf(BigDecimal.valueOf(2), BigDecimal.valueOf(3));
            CompactNode b = CompactNode.valueOf(BigDecimal.valueOf(4), BigDecimal.valueOf(5));

            CompactNode result = a.multiply(b);

            assertThat(result.getX()).isEqualByComparingTo(BigDecimal.valueOf(8));
            assertThat(result.getY()).isEqualByComparingTo(BigDecimal.valueOf(15));
        }
    }

    @Nested
    @DisplayName("negate")
    class Negate {

        @Test
        @DisplayName("Deve negar valores do nó")
        void deveNegarValores() {
            CompactNode node = CompactNode.valueOf(BigDecimal.valueOf(5), BigDecimal.valueOf(3));
            CompactNode negated = node.negate();

            assertThat(negated.getX()).isEqualByComparingTo(BigDecimal.valueOf(-5));
            assertThat(negated.getY()).isEqualByComparingTo(BigDecimal.valueOf(-3));
            assertThat(negated.isNegated()).isTrue();
        }
    }

    @Nested
    @DisplayName("normalize")
    class Normalize {

        @Test
        @DisplayName("Deve normalizar nó usando norma euclidiana")
        void deveNormalizarComNormaEuclidiana() {
            CompactNode node = CompactNode.valueOf(BigDecimal.valueOf(3), BigDecimal.valueOf(4));
            CompactNode normalized = node.normalize();

            // sqrt(9 + 16) = 5; 3/5 = 0.6; 4/5 = 0.8
            assertThat(normalized.getX().doubleValue()).isCloseTo(0.6, org.assertj.core.data.Offset.offset(0.0001));
            assertThat(normalized.getY().doubleValue()).isCloseTo(0.8, org.assertj.core.data.Offset.offset(0.0001));
        }
    }

    @Nested
    @DisplayName("toString")
    class ToStringTest {

        @Test
        @DisplayName("Deve conter valores x e y")
        void deveConterValores() {
            CompactNode node = CompactNode.valueOf(BigDecimal.valueOf(1), BigDecimal.valueOf(2));
            String str = node.toString();
            assertThat(str).contains("1").contains("2");
        }
    }
}
