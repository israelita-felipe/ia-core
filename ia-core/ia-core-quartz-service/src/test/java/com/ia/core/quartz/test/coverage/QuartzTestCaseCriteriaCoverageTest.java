package com.ia.core.quartz.test.coverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("Quartz test case criteria coverage")
class QuartzTestCaseCriteriaCoverageTest {

  private static final Path TEST_CASES = Path.of("src/test/resources/test-cases");
  private static final Path JAVA_TESTS = Path.of("src/test/java");
  private static final Pattern FILE_NAME = Pattern.compile("^(.+)-(Model|Repository|Mapper|ServiceModel|Service|API|View)-Layer\\.md$");
  private static final List<String> CRITERIA = List.of(
      "O caso informa objetivo, classe/componente testado, tipo de teste, domínio e CDU relacionado.",
      "O fluxo cobre cenário feliz, entradas inválidas, exceções e dependências relevantes.",
      "Os nomes de classes, métodos, arquivos e mensagens seguem ADR-010.",
      "Os asserts são explícitos, legíveis e preferencialmente usam AssertJ/JUnit 5 conforme ADR-012.",
      "Mocks, stubs e verificações de interação são documentados sem expor dados sensíveis.",
      "O documento está em UTF-8 e usa linguagem clara e consistente com ADR-050/ADR-052.",
      "Validar campos obrigatórios, tamanhos, valores padrão, imutabilidade/cópia e serialização quando aplicável.",
      "Validar que DTOs, modelos, mappers e serviços não contêm lógica de negócio complexa fora do escopo esperado."
  );
  private static final List<String> REQUIRED_SECTIONS = List.of(
      "## Descrição",
      "## Classe Testada",
      "## Stack do Quartz",
      "## Objetivo",
      "## Fluxo do Teste",
      "## Cenários",
      "## Dependências",
      "## Referências",
      "## Aderência a ADRs",
      "### Metadados de contexto",
      "### Matriz de conformidade",
      "### Critérios de aceitação obrigatórios",
      "### Evidências esperadas",
      "### Referências ADR",
      "### Referências do projeto"
  );
private static final Set<String> DTO_AND_MODEL_CLASSES = Set.of(
        "QuartzJobDTO",
        "QuartzJobInstanceDTO",
        "QuartzJobTriggerDTO",
        "IntervaloTemporalDTO",
        "PeriodicidadeDTO",
        "ExclusaoRecorrenciaDTO",
        "RecorrenciaDTO",
        "SchedulerConfigDTO",
        "SchedulerConfigTriggerDTO"
    );
  private static final Set<String> BEAN_CLASSES = Set.of(
      "ExclusaoRecorrencia",
      "IntervaloTemporal",
      "Periodicidade",
      "Recorrencia",
      "SchedulerConfig",
      "SchedulerConfigTrigger"
  );
  private static final Set<String> VALUE_OBJECT_CLASSES = Set.of(
      "Frequencia",
      "IntervaloTemporalDTO"
  );
  private static final Set<String> INTERFACE_CLASSES = Set.of(
      "QuartzJobUseCase",
      "SchedulerUseCase",
      "OccurrenceCalculator"
  );

  @Test
  @DisplayName("Should declare all mandatory acceptance criteria in every Markdown case")
  void shouldDeclareAllMandatoryAcceptanceCriteriaInEveryMarkdownCase() throws Exception {
    for (Path testCase : markdownCases().toList()) {
      String content = Files.readString(testCase);

      assertThat(content).as(testCase + " required sections").contains(REQUIRED_SECTIONS.toArray(String[]::new));
      for (String criterion : CRITERIA) {
        assertThat(content).as(testCase + " criterion: " + criterion).contains(criterion);
      }
      assertThat(Pattern.compile("### Cenário \\d+:").matcher(content).results().count())
          .as(testCase + " scenario count")
          .isEqualTo(4);
      assertThat(content.split("\\n\\*\\*Given\\*\\*:").length - 1).as(testCase + " Given count").isEqualTo(4);
      assertThat(content.split("\\n\\*\\*When\\*\\*:").length - 1).as(testCase + " When count").isEqualTo(4);
      assertThat(content.split("\\n\\*\\*Then\\*\\*:").length - 1).as(testCase + " Then count").isEqualTo(4);
    }
  }

  @Test
  @DisplayName("Should have executable or coverage test for every documented Quartz class")
  void shouldHaveExecutableOrCoverageTestForEveryDocumentedClass() throws Exception {
    String coverageSource = Files.readString(Path.of("src/test/java/com/ia/core/quartz/test/coverage/QuartzLayerTestCaseCoverageTest.java"));

    for (Path testCase : markdownCases().toList()) {
      String className = className(testCase);
      assertThat(className).as(testCase + " file name").isNotBlank();
      assertThat(coverageSource).as(testCase + " coverage class registration").contains("\"" + className + "\"");
      assertThat(javaTestSource()).as(testCase + " Java test reference").contains(className);
    }
  }

  @Test
  @DisplayName("Should exercise contract criteria for DTO, bean, value object and interface cases")
  void shouldExerciseContractCriteriaForDtoBeanValueObjectAndInterfaceCases() throws Exception {
    String contractSource = contractTestSource();

    for (String className : DTO_AND_MODEL_CLASSES) {
      assertThat(contractSource).as(className + " DTO/search contract").contains(className);
    }
    for (String className : BEAN_CLASSES) {
      assertThat(contractSource).as(className + " bean contract").contains(className);
    }
    for (String className : VALUE_OBJECT_CLASSES) {
      assertThat(contractSource).as(className + " value object contract").contains(className);
    }
    for (String className : INTERFACE_CLASSES) {
      assertThat(contractSource).as(className + " interface contract").contains(className);
    }
  }

  @Test
  @DisplayName("Should use explicit assertions and JUnit/AssertJ in Quartz Java tests")
  void shouldUseExplicitAssertionsAndJUnitAssertJInQuartzJavaTests() throws Exception {
    for (Path javaTest : javaTestFiles().toList()) {
      String source = Files.readString(javaTest);
      assertThat(source).as(javaTest + " test annotation").containsPattern("@Test|@ParameterizedTest|@Nested");
      boolean hasExplicitAssertion = source.contains("assertThat") ||
          source.contains("assertEquals") ||
          source.contains("assertTrue") ||
          source.contains("assertFalse") ||
          source.contains("assertNotNull") ||
          source.contains("assertNull") ||
          source.contains("assertSame") ||
          source.contains("assertNotSame") ||
          source.contains("assertArrayEquals") ||
          source.contains("assertBeanContract") ||
          source.contains("assertCampos") ||
          source.contains("assertPropertyFilters") ||
          source.contains("assertDtoContract");
      assertThat(hasExplicitAssertion).as(javaTest + " explicit assertion").isTrue();
      assertThat(Pattern.compile("(?m)^\\s*@Disabled").matcher(source).find()).as(javaTest + " no disabled tests").isFalse();
    }
  }

  private static Stream<Path> markdownCases() throws Exception {
    return Files.list(TEST_CASES)
        .filter(path -> FILE_NAME.matcher(path.getFileName().toString()).matches());
  }

  private static Stream<Path> javaTestFiles() throws Exception {
    return Files.walk(JAVA_TESTS)
        .filter(path -> path.getFileName().toString().endsWith("Test.java"));
  }

  private static String javaTestSource() throws Exception {
    StringBuilder source = new StringBuilder();
    for (Path javaTest : javaTestFiles().toList()) {
      source.append(Files.readString(javaTest)).append('\n');
    }
    return source.toString();
  }

  private static String contractTestSource() throws Exception {
    var builder = new StringBuilder();
    builder.append(Files.readString(Path.of("src/test/java/com/ia/core/quartz/service/model/QuartzDtoContractTest.java")));
    for (String className : DTO_AND_MODEL_CLASSES) {
      var path = Path.of("src/test/java/com/ia/core/quartz/service/model/" + className.toLowerCase() + "/" + className + "Test.java");
      if (Files.isRegularFile(path)) {
        builder.append(Files.readString(path));
      }
    }
    builder.append(Files.readString(Path.of("src/test/java/com/ia/core/quartz/model/QuartzBeanContractTest.java")));
    builder.append(Files.readString(Path.of("src/test/java/com/ia/core/quartz/model/QuartzValueObjectContractTest.java")));
    builder.append(Files.readString(Path.of("src/test/java/com/ia/core/quartz/model/QuartzInterfaceContractTest.java")));
    return builder.toString();
  }

  private static String className(Path testCase) {
    var matcher = FILE_NAME.matcher(testCase.getFileName().toString());
    return matcher.matches() ? matcher.group(1) : "";
  }
}
