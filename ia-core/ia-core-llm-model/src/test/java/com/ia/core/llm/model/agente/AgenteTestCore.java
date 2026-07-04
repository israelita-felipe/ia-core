package com.ia.core.llm.model.agente;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.skill.Skill;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a classe Agente.
 */
@DisplayName("Testes de Agente")
class AgenteTestCore extends CoreBaseUnitTest {

  @Test
  @DisplayName("Deve criar Agente com builder")
  void deveCriarAgenteComBuilder() {
    // Arrange
    // Act
    Agente agente = Agente.builder()
        .identificador("llm.core")
        .titulo("Agente Principal")
        .descricao("Agente para processamento principal")
        .instrucoes("Instruções do sistema")
        .modelo("gpt-4")
        .ativo(true)
        .moduloOrigem("ia-core-llm")
        .temperature(0.7)
        .maxTokens(2048)
        .build();

    // Assert
    assertThat(agente).isNotNull();
    assertThat(agente.getIdentificador()).isEqualTo("llm.core");
    assertThat(agente.getTitulo()).isEqualTo("Agente Principal");
    assertThat(agente.getDescricao()).isEqualTo("Agente para processamento principal");
    assertThat(agente.getInstrucoes()).isEqualTo("Instruções do sistema");
    assertThat(agente.getModelo()).isEqualTo("gpt-4");
    assertThat(agente.getAtivo()).isTrue();
    assertThat(agente.getModuloOrigem()).isEqualTo("ia-core-llm");
    assertThat(agente.getTemperature()).isEqualTo(0.7);
    assertThat(agente.getMaxTokens()).isEqualTo(2048);
  }

  @Test
  @DisplayName("Deve inicializar valores padrão")
  void deveInicializarValoresPadrao() {
    // Arrange
    // Act
    Agente agente = Agente.builder()
        .identificador("llm.core")
        .titulo("Agente Principal")
        .build();

    // Assert
    assertThat(agente.getAtivo()).isTrue();
    assertThat(agente.getTemperature()).isEqualTo(0.7);
    assertThat(agente.getMaxTokens()).isEqualTo(2048);
    assertThat(agente.getFerramentas()).isNotNull();
    assertThat(agente.getSkills()).isNotNull();
  }

  @Test
  @DisplayName("Deve adicionar ferramenta ao agente")
  void deveAdicionarFerramentaAoAgente() {
    // Arrange
    Agente agente = Agente.builder()
        .identificador("llm.core")
        .titulo("Agente Principal")
        .build();
    Ferramenta ferramenta = Ferramenta.builder()
        .identificador("ferramenta.test")
        .titulo("Ferramenta de Teste")
        .build();

    // Act
    agente.adicionarFerramenta(ferramenta);

    // Assert
    assertThat(agente.getFerramentas()).contains(ferramenta);
    assertThat(agente.getFerramentas()).hasSize(1);
  }

  @Test
  @DisplayName("Deve remover ferramenta do agente")
  void deveRemoverFerramentaDoAgente() {
    // Arrange
    Agente agente = Agente.builder()
        .identificador("llm.core")
        .titulo("Agente Principal")
        .build();
    Ferramenta ferramenta = Ferramenta.builder()
        .identificador("ferramenta.test")
        .titulo("Ferramenta de Teste")
        .build();

    // Act
    agente.adicionarFerramenta(ferramenta);
    agente.removerFerramenta(ferramenta);

    // Assert
    assertThat(agente.getFerramentas()).doesNotContain(ferramenta);
    assertThat(agente.getFerramentas()).isEmpty();
  }

  @Test
  @DisplayName("Deve adicionar skill ao agente")
  void deveAdicionarSkillAoAgente() {
    // Arrange
    Agente agente = Agente.builder()
        .identificador("llm.core")
        .titulo("Agente Principal")
        .build();
    Skill skill = Skill.builder()
        .identificador("skill.test")
        .titulo("Skill de Teste")
        .build();

    // Act
    agente.adicionarSkill(skill);

    // Assert
    assertThat(agente.getSkills()).contains(skill);
    assertThat(agente.getSkills()).hasSize(1);
  }

  @Test
  @DisplayName("Deve remover skill do agente")
  void deveRemoverSkillDoAgente() {
    // Arrange
    Agente agente = Agente.builder()
        .identificador("llm.core")
        .titulo("Agente Principal")
        .build();
    Skill skill = Skill.builder()
        .identificador("skill.test")
        .titulo("Skill de Teste")
        .build();

    // Act
    agente.adicionarSkill(skill);
    agente.removerSkill(skill);

    // Assert
    assertThat(agente.getSkills()).doesNotContain(skill);
    assertThat(agente.getSkills()).isEmpty();
  }
}
