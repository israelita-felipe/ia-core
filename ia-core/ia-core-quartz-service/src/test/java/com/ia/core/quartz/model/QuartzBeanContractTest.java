package com.ia.core.quartz.model;

import com.ia.core.quartz.model.periodicidade.ExclusaoRecorrencia;
import com.ia.core.quartz.model.periodicidade.IntervaloTemporal;
import com.ia.core.quartz.model.periodicidade.Periodicidade;
import com.ia.core.quartz.model.periodicidade.Recorrencia;
import com.ia.core.quartz.model.scheduler.SchedulerConfig;
import com.ia.core.quartz.model.scheduler.SchedulerConfigTrigger;
import com.ia.core.quartz.test.support.QuartzTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@Tag("unit")
@DisplayName("Quartz bean contract tests")
class QuartzBeanContractTest {

  @ParameterizedTest(name = "{0}")
  @MethodSource("beans")
  void shouldExposeBeanContract(Class<?> type, List<String> fields) throws Exception {
    QuartzTestSupport.assertBeanContract(type, fields);
  }

  static Stream<Arguments> beans() {
    return Stream.of(
        Arguments.of(ExclusaoRecorrencia.class, List.of(
            "frequency", "intervalValue", "byDay", "byMonthDay", "byMonth",
            "bySetPosition", "untilDate", "countLimit", "weekStartDay",
            "byYearDay", "byWeekNo", "byHour", "byMinute", "bySecond")),
        Arguments.of(IntervaloTemporal.class, List.of("startDate", "startTime", "endDate", "endTime")),
        Arguments.of(Periodicidade.class, List.of("intervaloBase", "regra", "exclusaoRecorrencia", "zoneId", "exceptionDates", "includeDates", "ativo")),
        Arguments.of(Recorrencia.class, List.of(
            "frequency", "intervalValue", "byDay", "byMonthDay", "byMonth",
            "bySetPosition", "untilDate", "countLimit", "weekStartDay",
            "byYearDay", "byWeekNo", "byHour", "byMinute", "bySecond")),
        Arguments.of(SchedulerConfig.class, List.of("jobClassName", "periodicidade")),
        Arguments.of(SchedulerConfigTrigger.class, List.of("schedulerName", "triggerName", "triggerGroup", "jobName", "jobGroup", "description", "nextFireTime", "prevFireTime", "priority", "triggerState", "triggerType", "triggerStartTime", "endTime", "calendarName", "misFireInstr", "jobData"))
    );
  }
}
