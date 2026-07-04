package com.ia.core.quartz.model;

import com.ia.core.quartz.service.model.job.QuartzJobUseCase;
import com.ia.core.quartz.service.model.recorrencia.dto.OccurrenceCalculator;
import com.ia.core.quartz.service.model.scheduler.SchedulerUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("Quartz interface contract tests")
class QuartzInterfaceContractTest {

  @Test
  @DisplayName("Should expose Quartz job use case operations")
  void shouldExposeQuartzJobUseCaseOperations() throws Exception {
    assertThat(QuartzJobUseCase.class.getMethod("findAllJobs").getReturnType().getSimpleName())
        .isEqualTo("List");
    assertThat(QuartzJobUseCase.class.getMethod("findJob", String.class, String.class).getReturnType().getSimpleName())
        .isEqualTo("QuartzJobDTO");
    assertThat(QuartzJobUseCase.class.getMethod("pauseJob", String.class, String.class).getReturnType())
        .isEqualTo(boolean.class);
  }

  @Test
  @DisplayName("Should expose Scheduler use case operations")
  void shouldExposeSchedulerUseCaseOperations() throws Exception {
    assertThat(SchedulerUseCase.class.getMethod("iniciarJobs").getReturnType())
        .isEqualTo(void.class);
    assertThat(SchedulerUseCase.class.getMethod("findAtivos").getReturnType().getSimpleName())
        .isEqualTo("List");
    assertThat(SchedulerUseCase.class.getMethod("verificarAtualizacoes").getReturnType())
        .isEqualTo(void.class);
  }

  @Test
  @DisplayName("Should expose occurrence calculator factory")
  void shouldExposeOccurrenceCalculatorFactory() {
    assertThat(OccurrenceCalculator.defaultCalculator()).isNotNull();
    assertThat(OccurrenceCalculator.libRecurCalculator()).isNotNull();
  }
}
