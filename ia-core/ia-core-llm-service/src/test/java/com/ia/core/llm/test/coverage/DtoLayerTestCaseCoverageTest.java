package com.ia.core.llm.test.coverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("DTO Layer Test Case Coverage")
class DtoLayerTestCaseCoverageTest {

  private static final Path TEST_CASES = Path.of("src/test/resources/test-cases");
  private static final List<String> DTOS = List.of("AgentConfirmationDTO",
    "AgentSessionRequestDTO",
    "AgentSessionResponseDTO",
    "AgenteDTO",
    "AnaliseInferenciaDTO",
    "AxiomaDTO",
    "ChatRequestDTO",
    "ChatSessionDTO",
    "ContextConversacaoDTO",
    "EstatisticasOntologiaDTO",
    "FerramentaActivationDTO",
    "FerramentaDTO",
    "FerramentaMetadataDTO",
    "OntologiaDTO",
    "PromptDTO",
    "SkillDTO",
    "TemplateDTO",
    "TemplateParameterDTO",
    "TransformacaoResultDTO");
  private static final List<String> LAYERS = List.of("Model", "Repository", "Mapper", "ServiceModel", "Service", "API", "View");

  @Test
  @DisplayName("Should have one Markdown test case for every DTO and layer")
  void shouldHaveOneMarkdownTestCaseForEveryDtoAndLayer() {
    for (String dto : DTOS) {
      for (String layer : LAYERS) {
        Path file = TEST_CASES.resolve(dto + "-" + layer + "-Layer.md");
        assertThat(Files.isRegularFile(file))
            .as("Caso de teste ausente: " + file)
            .isTrue();
      }
    }
  }

  @Test
  @DisplayName("Should include ADR adherence section in every DTO test case")
  void shouldIncludeAdrAdherenceSectionInEveryDtoTestCase() throws Exception {
    for (String dto : DTOS) {
      for (String layer : LAYERS) {
        Path file = TEST_CASES.resolve(dto + "-" + layer + "-Layer.md");
        String content = Files.readString(file);

        assertThat(content).contains("## Aderência a ADRs");
        assertThat(content).contains("### Matriz de conformidade");
        assertThat(content).contains("### Referências ADR");
      }
    }
  }
}
