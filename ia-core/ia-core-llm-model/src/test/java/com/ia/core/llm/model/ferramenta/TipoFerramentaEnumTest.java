package com.ia.core.llm.model.ferramenta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe TipoFerramentaEnum.
 */
@DisplayName("Testes de TipoFerramentaEnum")
class TipoFerramentaEnumTest {

  @Test
  @DisplayName("Deve verificar valores do enum")
  void deveVerificarValoresDoEnum() {
    // Arrange
    // Act
    TipoFerramentaEnum[] values = TipoFerramentaEnum.values();

    // Assert
    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
  }

  @Test
  @DisplayName("Deve verificar método values()")
  void deveVerificarMetodoValues() {
    // Arrange
    // Act
    TipoFerramentaEnum[] values = TipoFerramentaEnum.values();

    // Assert
    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
  }

  @Test
  @DisplayName("Deve verificar método valueOf()")
  void deveVerificarMetodoValueOf() {
    // Arrange
    // Act & Assert
    for (TipoFerramentaEnum tipo : TipoFerramentaEnum.values()) {
      TipoFerramentaEnum result = TipoFerramentaEnum.valueOf(tipo.name());
      assertThat(result).isEqualTo(tipo);
    }
  }
}
