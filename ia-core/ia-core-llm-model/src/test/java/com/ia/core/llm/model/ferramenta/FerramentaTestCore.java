package com.ia.core.llm.model.ferramenta;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe Ferramenta.
 */
@DisplayName("Testes de Ferramenta")
class FerramentaTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("Deve criar Ferramenta com builder")
  void deveCriarFerramentaComBuilder() {
    // Arrange
    // Act
    Ferramenta ferramenta = Ferramenta.builder()
        .identificador("ferramenta.test")
        .titulo("Ferramenta de Teste")
        .descricao("Descrição da ferramenta")
        .build();

    // Assert
    assertThat(ferramenta).isNotNull();
    assertThat(ferramenta.getIdentificador()).isEqualTo("ferramenta.test");
    assertThat(ferramenta.getTitulo()).isEqualTo("Ferramenta de Teste");
    assertThat(ferramenta.getDescricao()).isEqualTo("Descrição da ferramenta");
  }

  @Test
  @DisplayName("Deve inicializar valores padrão")
  void deveInicializarValoresPadrao() {
    // Arrange
    // Act
    Ferramenta ferramenta = Ferramenta.builder()
        .identificador("ferramenta.test")
        .titulo("Ferramenta de Teste")
        .build();

    // Assert
    assertThat(ferramenta).isNotNull();
  }
}
