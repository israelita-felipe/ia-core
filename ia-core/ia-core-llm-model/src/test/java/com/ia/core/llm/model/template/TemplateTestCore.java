package com.ia.core.llm.model.template;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe Template.
 */
@DisplayName("Testes de Template")
class TemplateTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("Deve criar Template com builder")
  void deveCriarTemplateComBuilder() {
    // Arrange
    // Act
    Template template = Template.builder()
        .titulo("Template de Teste")
        .identificador("template.test")
        .conteudo("Conteúdo do template")
        .build();

    // Assert
    assertThat(template).isNotNull();
    assertThat(template.getTitulo()).isEqualTo("Template de Teste");
    assertThat(template.getIdentificador()).isEqualTo("template.test");
    assertThat(template.getConteudo()).isEqualTo("Conteúdo do template");
  }

  @Test
  @DisplayName("Deve inicializar valores padrão")
  void deveInicializarValoresPadrao() {
    // Arrange
    // Act
    Template template = Template.builder()
        .titulo("Template de Teste")
        .identificador("template.test")
        .build();

    // Assert
    assertThat(template).isNotNull();
    assertThat(template.isExigeContexto()).isFalse();
  }
}
