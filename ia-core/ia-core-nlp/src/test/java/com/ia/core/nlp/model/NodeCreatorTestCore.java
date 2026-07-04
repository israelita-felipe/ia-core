package com.ia.core.nlp.model;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes para NodeCreator baseados nos casos de teste documentados.
 */
class NodeCreatorTestCore extends CoreBaseUnitTest {

    @Test
    void deveConverterCaractereParaNos() {
        // Arrange
        Character caractere = 'A';

        // Act
        Node[] nodes = NodeCreator.fromChar(caractere);

        // Assert
        assertThat(nodes).isNotNull();
        assertThat(nodes).hasSize(4);
        assertThat(nodes).doesNotContainNull();
    }

    @Test
    void deveConverterTextoParaNos() {
        // Arrange
        String texto = "teste";

        // Act
        Node[] nodes = NodeCreator.fromText(texto);

        // Assert
        assertThat(nodes).isNotNull();
        assertThat(nodes).hasSize(1);
    }

    @Test
    void deveConverterArrayDeStringsParaNos() {
        // Arrange
        String[] texto = {"teste", "palavra"};

        // Act
        Node[] nodes = NodeCreator.fromText(texto);

        // Assert
        assertThat(nodes).isNotNull();
        assertThat(nodes).hasSize(2);
    }

    @Test
    void deveConverterPalavraParaNos() {
        // Arrange
        String palavra = "ola";

        // Act
        Node[] nodes = NodeCreator.fromWord(palavra);

        // Assert
        assertThat(nodes).isNotNull();
        assertThat(nodes).hasSize(3);
    }

    @Test
    void deveLancarExcecaoParaPalavraComEspacos() {
        // Arrange
        String palavra = "ola mundo";

        // Act & Assert
        assertThatThrownBy(() -> NodeCreator.fromWord(palavra))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("espaços");
    }

}
