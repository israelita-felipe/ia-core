package com.ia.core.quartz.service.model;

import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobInstanceDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobTriggerDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.model.recorrencia.dto.ExclusaoRecorrenciaDTO;
import com.ia.core.quartz.service.model.recorrencia.dto.RecorrenciaDTO;
import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.dto.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.test.support.QuartzTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@DisplayName("Quartz DTO contract tests")
class QuartzDtoContractTest {

  @ParameterizedTest(name = "{0}")
  @MethodSource("dtoContracts")
  void shouldExposeDtoContract(Class<?> type, List<String> fields, List<String> propertyFilters) throws Exception {
    QuartzTestSupport.assertDtoContract(type, fields);
    QuartzTestSupport.assertCampos(type, fields);
    QuartzTestSupport.assertPropertyFilters(type, propertyFilters);
  }

  static Stream<Arguments> dtoContracts() {
    return Stream.of(
        Arguments.of(QuartzJobDTO.class, List.of(
            "jobName", "jobGroup", "description", "jobClassName", "durable",
            "requestsRecovery", "jobData", "jobState", "numberOfExecutions",
            "lastExecutionTime", "nextExecutionTime", "prevFireTime"), List.of("jobName", "jobGroup", "jobState")),
        Arguments.of(QuartzJobInstanceDTO.class, List.of(
            "instanceId", "jobName", "jobGroup", "triggerName", "triggerGroup",
            "fireTime", "scheduledFireTime", "completedExecutionTime", "result",
            "jobExecuted", "exceptionMessage", "jobDataMap", "recovered"), List.of("instanceId", "jobName", "jobGroup")),
        Arguments.of(QuartzJobTriggerDTO.class, List.of(
            "triggerName", "triggerGroup", "jobName", "jobGroup", "description",
            "triggerState", "triggerType", "calendarName", "nextFireTime",
            "prevFireTime", "startTime", "endTime", "finalFireTime",
            "misFireInstr", "priority", "repeatCount", "repeatInterval",
            "timesTriggered", "jobData"), List.of("triggerName", "triggerGroup", "jobName", "triggerState")),
        Arguments.of(IntervaloTemporalDTO.class, List.of("startDate", "startTime", "endDate", "endTime"), List.of()),
        Arguments.of(PeriodicidadeDTO.class, List.of(
            "intervaloBase", "regra", "exclusaoRecorrencia", "zoneId",
            "exceptionDates", "includeDates", "ativo"), List.of("zoneId")),
        Arguments.of(ExclusaoRecorrenciaDTO.class, List.of(
            "frequency", "intervalValue", "byDay", "byMonthDay", "byMonth",
            "bySetPosition", "untilDate", "countLimit", "weekStartDay",
            "byYearDay", "byWeekNo", "byHour", "byMinute", "bySecond"), List.of()),
        Arguments.of(RecorrenciaDTO.class, List.of(
            "frequency", "intervalValue", "byDay", "byMonthDay", "byMonth",
            "bySetPosition", "untilDate", "countLimit", "weekStartDay",
            "byYearDay", "byWeekNo", "byHour", "byMinute", "bySecond"), List.of()),
        Arguments.of(SchedulerConfigDTO.class, List.of("jobClassName", "periodicidade", "triggers"), List.of("jobClassName")),
        Arguments.of(SchedulerConfigTriggerDTO.class, List.of(
            "triggerName", "schedulerName", "triggerGroup", "jobName", "jobGroup",
            "description", "nextFireTime", "prevFireTime", "priority", "triggerState",
            "triggerType", "startTime", "endTime", "calendarName", "misFireInstr",
            "jobData"), List.of("triggerName"))
    );
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("dtosWithNullingCopy")
  void shouldNullDataMapsWhenCopyingJobDto(Class<?> type, String field) throws Exception {
    Object dto = QuartzTestSupport.createInstance(type);
    QuartzTestSupport.populate(dto, List.of(field));

    Object copied = type.getMethod("copyObject").invoke(dto);

    assertThat(copied).isNotNull().isNotSameAs(dto);
    assertThat(QuartzTestSupport.getValue(copied, field)).isNull();
  }

  static Stream<Arguments> dtosWithNullingCopy() {
    return Stream.of(
        Arguments.of(QuartzJobDTO.class, "jobData"),
        Arguments.of(QuartzJobInstanceDTO.class, "jobDataMap"),
        Arguments.of(QuartzJobTriggerDTO.class, "jobData")
    );
  }
}
