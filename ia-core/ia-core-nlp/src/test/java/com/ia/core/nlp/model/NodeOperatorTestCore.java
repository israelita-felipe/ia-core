package com.ia.core.nlp.model;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes para NodeOperator baseados nos casos de teste documentados.
 */
class NodeOperatorTestCore extends CoreBaseUnitTest {

    @Test
    void deveCalcularCossenoDeSimilaridadeEntreDoisNos() {
        // Arrange
        Node node1 = Node.valueOf(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        Node node2 = Node.valueOf(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

        // Act
        BigDecimal coss = NodeOperator.coss(node1, node2);

        // Assert
        assertThat(coss).isNotNull();
        assertThat(coss.doubleValue()).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.0001));
    }

    @Test
    void deveCalcularCossenoDeNosIdenticos() {
        // Arrange
        Node node = Node.valueOf(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

        // Act
        BigDecimal coss = NodeOperator.coss(node, node);

        // Assert
        assertThat(coss).isNotNull();
        assertThat(coss.doubleValue()).isCloseTo(1.0, org.assertj.core.data.Offset.offset(0.0001));
    }

    @Test
    void deveOperarArrayComUmNo() {
        // Arrange
        Node[] nodes = {Node.valueOf(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE)};

        // Act
        Node result = NodeOperator.operate(nodes);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    void deveOperarArrayComDoisNos() {
        // Arrange
        Node[] nodes = {
            Node.valueOf(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE),
            NodeOperator.IDENTITY
        };

        // Act
        Node result = NodeOperator.operate(nodes);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    void deveOperarArrayComMultiplosNos() {
        // Arrange
        Node[] nodes = {
            Node.valueOf(BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(5)),
            Node.valueOf(BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1)),
            NodeOperator.IDENTITY
        };

        // Act
        Node result = NodeOperator.operate(nodes);

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    void deveVerificarConstanteIdentity() {
        // Act
        Node identity = NodeOperator.IDENTITY;

        // Assert
        assertThat(identity).isNotNull();
        assertThat(identity.getG()).isEqualByComparingTo(BigDecimal.ONE);
        assertThat(identity.getA()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(identity.getT()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(identity.getC()).isEqualByComparingTo(BigDecimal.ONE);
    }

}
