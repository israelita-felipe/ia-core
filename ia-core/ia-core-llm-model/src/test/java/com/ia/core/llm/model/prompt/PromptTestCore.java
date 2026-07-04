package com.ia.core.llm.model.prompt;

import com.ia.core.llm.model.template.Template;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe Prompt.
 */
@DisplayName("Testes de Prompt")
class PromptTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("Deve criar Prompt com builder")
  void deveCriarPromptComBuilder() {
    // Arrange
    Template template = Template.builder()
        .titulo("Template de Teste")
        .identificador("template.test")
        .conteudo("Conteúdo do template")
        .build();

    // Act
    Prompt prompt = Prompt.builder()
        .titulo("Prompt de Teste")
        .entrada("Entrada do prompt")
        .template(template)
        .build();

    // Assert
    assertThat(prompt).isNotNull();
    assertThat(prompt.getTitulo()).isEqualTo("Prompt de Teste");
    assertThat(prompt.getEntrada()).isEqualTo("Entrada do prompt");
    assertThat(prompt.getTemplate()).isEqualTo(template);
  }

  @Test
  @DisplayName("Deve inicializar valores padrão")
  void deveInicializarValoresPadrao() {
    // Arrange
    Template template = Template.builder()
        .titulo("Template de Teste")
        .identificador("template.test")
        .conteudo("Conteúdo do template")
        .build();

    // Act
    Prompt prompt = Prompt.builder()
        .titulo("Prompt de Teste")
        .template(template)
        .build();

    // Assert
    assertThat(prompt).isNotNull();
    assertThat(prompt.getFinalidade()).isEqualTo(FinalidadePromptEnum.RESPOSTA_TEXTUAL);
  }
}
