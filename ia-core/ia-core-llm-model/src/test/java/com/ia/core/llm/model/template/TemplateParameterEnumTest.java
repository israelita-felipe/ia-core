package com.ia.core.llm.model.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe TemplateParameterEnum.
 */
@DisplayName("Testes de TemplateParameterEnum")
class TemplateParameterEnumTest {

  @Test
  @DisplayName("Deve verificar valores do enum")
  void deveVerificarValoresDoEnum() {
    // Arrange
    // Act
    TemplateParameterEnum[] values = TemplateParameterEnum.values();

    // Assert
    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
  }

  @Test
  @DisplayName("Deve verificar método values()")
  void deveVerificarMetodoValues() {
    // Arrange
    // Act
    TemplateParameterEnum[] values = TemplateParameterEnum.values();

    // Assert
    assertThat(values).isNotNull();
    assertThat(values).isNotEmpty();
  }

  @Test
  @DisplayName("Deve verificar método valueOf()")
  void deveVerificarMetodoValueOf() {
    // Arrange
    // Act & Assert
    for (TemplateParameterEnum tipo : TemplateParameterEnum.values()) {
      TemplateParameterEnum result = TemplateParameterEnum.valueOf(tipo.name());
      assertThat(result).isEqualTo(tipo);
    }
  }
}
