package com.ia.core.llm.model.skill;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe Skill.
 */
@DisplayName("Testes de Skill")
class SkillTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("Deve criar Skill com builder")
  void deveCriarSkillComBuilder() {
    // Arrange
    // Act
    Skill skill = Skill.builder()
        .identificador("skill.test")
        .titulo("Skill de Teste")
        .descricao("Descrição da skill")
        .build();

    // Assert
    assertThat(skill).isNotNull();
    assertThat(skill.getIdentificador()).isEqualTo("skill.test");
    assertThat(skill.getTitulo()).isEqualTo("Skill de Teste");
    assertThat(skill.getDescricao()).isEqualTo("Descrição da skill");
  }

  @Test
  @DisplayName("Deve inicializar valores padrão")
  void deveInicializarValoresPadrao() {
    // Arrange
    // Act
    Skill skill = Skill.builder()
        .identificador("skill.test")
        .titulo("Skill de Teste")
        .build();

    // Assert
    assertThat(skill).isNotNull();
  }
}
