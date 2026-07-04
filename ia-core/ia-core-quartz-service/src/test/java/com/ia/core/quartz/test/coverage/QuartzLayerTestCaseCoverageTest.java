package com.ia.core.quartz.test.coverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("Quartz Layer Test Case Coverage")
class QuartzLayerTestCaseCoverageTest {

  private static final Path TEST_CASES = Path.of("src/test/resources/test-cases");
  private static final List<String> QUARTZ_CLASSES = List.of(
      "AbstractJob",
      "CoreQuartzConfig",
      "CoreSpringBeanJobFactory",
      "DateSetView",
      "DateTimeAdapter",
      "DayOfWeekConverter",
      "ExclusaoRecorrencia",
      "ExclusaoRecorrenciaDTO",
      "Frequencia",
      "FrequenciaConverter",
      "ICalendarSerializer",
      "IntegerSetView",
      "IntervaloTemporal",
      "IntervaloTemporalDTO",
      "IntervaloTemporalFormView",
      "IntervaloTemporalFormViewModel",
      "IntervaloTemporalFormViewModelConfig",
      "JobSchedulerChecker",
      "JobsListener",
      "LibRecurOccurrenceCalculator",
      "MonthConverter",
      "OccurrenceCalculator",
      "OccurrenceCalculatorRunner",
      "Periodicidade",
      "PeriodicidadeDTO",
      "PeriodicidadeFormView",
      "PeriodicidadeFormViewModel",
      "PeriodicidadeFormViewModelConfig",
      "PeriodicidadeFormatter",
      "PeriodicidadeMapper",
      "PeriodicidadeRepository",
      "PeriodicidadeScheduleBuilder",
      "PeriodicidadeSearchRequest",
      "PeriodicidadeTranslator",
      "PeriodicidadeTrigger",
      "QuartzClient",
      "QuartzJobClient",
      "QuartzJobDTO",
      "QuartzJobFormView",
      "QuartzJobFormViewModel",
      "QuartzJobFormViewModelConfig",
      "QuartzJobInstanceDTO",
      "QuartzJobInstanceSearchRequest",
      "QuartzJobListView",
      "QuartzJobManager",
      "QuartzJobManagerConfig",
      "QuartzJobPageView",
      "QuartzJobPageViewModel",
      "QuartzJobPageViewModelConfig",
      "QuartzJobSearchRequest",
      "QuartzJobTranslator",
      "QuartzJobTriggerDTO",
      "QuartzJobTriggerSearchRequest",
      "QuartzJobUseCase",
      "QuartzManager",
      "QuartzManagerConfig",
      "QuartzModel",
      "RRuleValidator",
      "Recorrencia",
      "RecorrenciaDTO",
      "RecorrenciaFormView",
      "RecorrenciaFormViewModel",
      "RecorrenciaFormViewModelConfig",
      "RecurrenceRuleAdapter",
      "SchedulerConfig",
      "SchedulerConfigDTO",
      "SchedulerConfigFormView",
      "SchedulerConfigFormViewModel",
      "SchedulerConfigFormViewModelConfig",
      "SchedulerConfigListView",
      "SchedulerConfigMapper",
      "SchedulerConfigPageView",
      "SchedulerConfigPageViewModel",
      "SchedulerConfigPageViewModelConfig",
      "SchedulerConfigRepository",
      "SchedulerConfigSearchRequest",
      "SchedulerConfigService",
      "SchedulerConfigServiceConfig",
      "SchedulerConfigSummary",
      "SchedulerConfigTranslator",
      "SchedulerConfigTrigger",
      "SchedulerConfigTriggerCollectionPageView",
      "SchedulerConfigTriggerCollectionPageViewModel",
      "SchedulerConfigTriggerCollectionPageViewModelConfig",
      "SchedulerConfigTriggerDTO",
      "SchedulerConfigTriggerFormView",
      "SchedulerConfigTriggerListView",
      "SchedulerConfigTriggerSearchRequest",
      "SchedulerConfigTriggerTranslator",
      "SchedulerRegistry",
      "SchedulerUseCase",
      "TriggersListener"
  );
  private static final Set<String> SERVICE_MODEL_CLASSES = Set.of(
      "ICalendarSerializer"
  );

  private static final Set<String> DTO_AND_MODEL_CLASSES = Set.of(
      "QuartzJobDTO",
      "QuartzJobInstanceDTO",
      "QuartzJobInstanceSearchRequest",
      "QuartzJobSearchRequest",
      "QuartzJobTriggerDTO",
      "QuartzJobTriggerSearchRequest",
      "IntervaloTemporalDTO",
      "PeriodicidadeDTO",
      "PeriodicidadeSearchRequest",
      "ExclusaoRecorrenciaDTO",
      "RecorrenciaDTO",
      "SchedulerConfigDTO",
      "SchedulerConfigSearchRequest",
      "SchedulerConfigTriggerDTO",
      "SchedulerConfigTriggerSearchRequest"
  );
  private static final List<String> STACK_LAYERS = List.of("Model", "Repository", "Mapper", "ServiceModel", "Service", "API", "View");

  @Test
  @DisplayName("Should have one Markdown test case for every Quartz DTO/model and layer")
  void shouldHaveOneMarkdownTestCaseForEveryQuartzDtoModelAndLayer() {
    for (String targetClass : QUARTZ_CLASSES) {
      for (String layer : expectedLayers(targetClass)) {
        Path file = TEST_CASES.resolve(targetClass + "-" + layer + "-Layer.md");
        assertThat(Files.isRegularFile(file))
            .as("Caso de teste ausente: " + file)
            .isTrue();
      }
    }
  }

  @Test
  @DisplayName("Should include ADR adherence section in every Quartz test case")
  void shouldIncludeAdrAdherenceSectionInEveryQuartzTestCase() throws Exception {
    for (String targetClass : QUARTZ_CLASSES) {
      for (String layer : expectedLayers(targetClass)) {
        Path file = TEST_CASES.resolve(targetClass + "-" + layer + "-Layer.md");
        String content = Files.readString(file);

        assertThat(content).contains("## Aderência a ADRs");
        assertThat(content).contains("### Matriz de conformidade");
        assertThat(content).contains("### Referências ADR");
      }
    }
  }

  private static List<String> expectedLayers(String targetClass) {
    if (DTO_AND_MODEL_CLASSES.contains(targetClass)) {
      return STACK_LAYERS;
    }
    if (targetClass.endsWith("View") || targetClass.endsWith("ViewModel") || targetClass.endsWith("ViewModelConfig") || targetClass.endsWith("Client") || targetClass.endsWith("Manager") || targetClass.endsWith("ManagerConfig")) {
      return List.of("View");
    }
    if (targetClass.equals("JobSchedulerChecker") || targetClass.equals("JobsListener")) {
      return List.of("Model");
    }
    if (targetClass.endsWith("Service") || targetClass.endsWith("ServiceConfig") || targetClass.endsWith("Registry") || targetClass.endsWith("Listener") || targetClass.endsWith("Mapper") || targetClass.endsWith("ScheduleBuilder") || targetClass.endsWith("Trigger")) {
      return List.of("Service");
    }
    if (SERVICE_MODEL_CLASSES.contains(targetClass) || targetClass.endsWith("Translator") || targetClass.endsWith("Formatter") || targetClass.endsWith("Validator") || targetClass.endsWith("Adapter") || targetClass.endsWith("Calculator") || targetClass.endsWith("CalculatorRunner") || targetClass.endsWith("Summary") || targetClass.endsWith("UseCase")) {
      return List.of("ServiceModel");
    }
    return List.of("Model");
  }
}
