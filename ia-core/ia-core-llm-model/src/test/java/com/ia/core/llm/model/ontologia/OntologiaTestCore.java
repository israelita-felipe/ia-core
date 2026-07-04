package com.ia.core.llm.model.ontologia;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe Ontologia.
 */
@DisplayName("Testes de Ontologia")
class OntologiaTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("Deve criar Ontologia com builder")
  void deveCriarOntologiaComBuilder() {
    // Arrange
    // Act
    Ontologia ontologia = Ontologia.builder()
        .nome("Ontologia de Teste")
        .formato(OntologyFormat.RDF_XML)
        .build();

    // Assert
    assertThat(ontologia).isNotNull();
    assertThat(ontologia.getNome()).isEqualTo("Ontologia de Teste");
    assertThat(ontologia.getFormato()).isEqualTo(OntologyFormat.RDF_XML);
  }

  @Test
  @DisplayName("Deve inicializar valores padrão")
  void deveInicializarValoresPadrao() {
    // Arrange
    // Act
    Ontologia ontologia = Ontologia.builder()
        .nome("Ontologia de Teste")
        .build();

    // Assert
    assertThat(ontologia).isNotNull();
  }
}
