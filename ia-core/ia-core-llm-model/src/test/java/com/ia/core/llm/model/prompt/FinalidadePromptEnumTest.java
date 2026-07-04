package com.ia.core.llm.model.prompt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe FinalidadePromptEnum.
 */
@DisplayName("Testes de FinalidadePromptEnum")
class FinalidadePromptEnumTest {

  @Test
  @DisplayName("Deve verificar valores do enum")
  void deveVerificarValoresDoEnum() {
    // Arrange
    // Act
    FinalidadePromptEnum[] values = FinalidadePromptEnum.values();

    // Assert
    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
  }

  @Test
  @DisplayName("Deve verificar método values()")
  void deveVerificarMetodoValues() {
    // Arrange
    // Act
    FinalidadePromptEnum[] values = FinalidadePromptEnum.values();

    // Assert
    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
  }

  @Test
  @DisplayName("Deve verificar método valueOf()")
  void deveVerificarMetodoValueOf() {
    // Arrange
    // Act & Assert
    for (FinalidadePromptEnum tipo : FinalidadePromptEnum.values()) {
      FinalidadePromptEnum result = FinalidadePromptEnum.valueOf(tipo.name());
      assertThat(result).isEqualTo(tipo);
    }
  }
}
