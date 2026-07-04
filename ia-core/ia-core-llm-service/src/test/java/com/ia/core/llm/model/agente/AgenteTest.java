package com.ia.core.llm.model.agente;

import com.ia.core.llm.model.ferramenta.Ferramenta;
import com.ia.core.llm.model.skill.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Agente Entity Tests")
class AgenteTest {

  private Agente agente;

  @BeforeEach
  void setUp() {
    agente = Agente.builder()
        .identificador("llm.core")
        .titulo("Core LLM Agent")
        .descricao("Agente principal para processamento de linguagem natural")
        .instrucoes("Você é um assistente útil.")
        .modelo("gpt-4")
        .ativo(true)
        .moduloOrigem("ia-core-llm")
        .temperature(0.7)
        .maxTokens(2048)
        .build();
  }

  @Nested
  @DisplayName("Builder Pattern")
  class BuilderTests {

    @Test
    @DisplayName("Should create agente with all fields")
    void shouldCreateAgenteWithAllFields() {
      assertThat(agente).isNotNull();
      assertThat(agente.getIdentificador()).isEqualTo("llm.core");
      assertThat(agente.getTitulo()).isEqualTo("Core LLM Agent");
      assertThat(agente.getDescricao()).isEqualTo("Agente principal para processamento de linguagem natural");
      assertThat(agente.getInstrucoes()).isEqualTo("Você é um assistente útil.");
      assertThat(agente.getModelo()).isEqualTo("gpt-4");
      assertThat(agente.getAtivo()).isTrue();
      assertThat(agente.getModuloOrigem()).isEqualTo("ia-core-llm");
      assertThat(agente.getTemperature()).isEqualTo(0.7);
      assertThat(agente.getMaxTokens()).isEqualTo(2048);
    }

    @Test
    @DisplayName("Should use default values for optional fields")
    void shouldUseDefaultValuesForOptionalFields() {
      Agente agenteDefaults = Agente.builder()
          .identificador("test.agent")
          .titulo("Test Agent")
          .build();

      assertThat(agenteDefaults.getAtivo()).isTrue();
      assertThat(agenteDefaults.getTemperature()).isEqualTo(0.7);
      assertThat(agenteDefaults.getMaxTokens()).isEqualTo(2048);
      assertThat(agenteDefaults.getFerramentas()).isNotNull().isEmpty();
      assertThat(agenteDefaults.getSkills()).isNotNull().isEmpty();
    }
  }

  @Nested
  @DisplayName("Ferramenta Management")
  class FerramentaManagementTests {

    @Test
    @DisplayName("Should add ferramenta to agente")
    void shouldAddFerramentaToAgente() {
      Ferramenta ferramenta = Ferramenta.builder()
          .identificador("tool.search")
          .titulo("Search Tool")
          .build();

      agente.adicionarFerramenta(ferramenta);

      assertThat(agente.getFerramentas()).hasSize(1);
      assertThat(agente.getFerramentas()).contains(ferramenta);
    }

    @Test
    @DisplayName("Should remove ferramenta from agente")
    void shouldRemoveFerramentaFromAgente() {
      Ferramenta ferramenta = Ferramenta.builder()
          .identificador("tool.search")
          .titulo("Search Tool")
          .build();

      agente.adicionarFerramenta(ferramenta);
      agente.removerFerramenta(ferramenta);

      assertThat(agente.getFerramentas()).isEmpty();
    }
  }

  @Nested
  @DisplayName("Skill Management")
  class SkillManagementTests {

    @Test
    @DisplayName("Should add skill to agente")
    void shouldAddSkillToAgente() {
      Skill skill = Skill.builder()
          .identificador("skill.ontology")
          .titulo("Ontology Skill")
          .build();

      agente.adicionarSkill(skill);

      assertThat(agente.getSkills()).hasSize(1);
      assertThat(agente.getSkills()).contains(skill);
    }

    @Test
    @DisplayName("Should remove skill from agente")
    void shouldRemoveSkillFromAgente() {
      Skill skill = Skill.builder()
          .identificador("skill.ontology")
          .titulo("Ontology Skill")
          .build();

      agente.adicionarSkill(skill);
      agente.removerSkill(skill);

      assertThat(agente.getSkills()).isEmpty();
    }
  }
}
