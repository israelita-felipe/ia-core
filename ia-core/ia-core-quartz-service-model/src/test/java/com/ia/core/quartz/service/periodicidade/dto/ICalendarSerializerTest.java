package com.ia.core.quartz.service.periodicidade.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.model.periodicidade.Recorrencia;

/**
 * Testes unitários para ICalendarSerializer.
 * <p>
 * Valida a serialização de objetos Recorrencia e ExclusaoRecorrencia
 * para o formato RRULE/EXRULE conforme RFC 5545.
 *
 * @author Israel Araújo
 */
@DisplayName("ICalendarSerializer")
class ICalendarSerializerTest {

  @Nested
  @DisplayName("toRRule - RecorrenciaDTO")
  class RecorrenciaDTOTests {

    @Test
    @DisplayName("Deve serializar recorrência diária simples")
    void testDailyFrequency() {
      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.DAILY)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=DAILY", result);
    }

    @Test
    @DisplayName("Deve serializar recorrência semanal")
    void testWeeklyFrequency() {
      Set<DayOfWeek> days = new HashSet<>();
      days.add(DayOfWeek.MONDAY);
      days.add(DayOfWeek.WEDNESDAY);
      days.add(DayOfWeek.FRIDAY);

      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.WEEKLY)
          .byDay(days)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=WEEKLY;BYDAY=MO,WE,FR", result);
    }

    @Test
    @DisplayName("Deve serializar recorrência mensal por dia do mês")
    void testMonthlyByMonthDay() {
      Set<Integer> monthDays = new HashSet<>();
      monthDays.add(1);
      monthDays.add(15);

      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.MONTHLY)
          .byMonthDay(monthDays)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=MONTHLY;BYMONTHDAY=1,15", result);
    }

    @Test
    @DisplayName("Deve serializar recorrência anual")
    void testYearlyFrequency() {
      Set<Month> months = new HashSet<>();
      months.add(Month.JANUARY);
      months.add(Month.JULY);

      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.YEARLY)
          .byMonth(months)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=YEARLY;BYMONTH=1,7", result);
    }

    @Test
    @DisplayName("Deve serializar com INTERVAL maior que 1")
    void testInterval() {
      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.WEEKLY)
          .intervalValue(2)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=WEEKLY;INTERVAL=2", result);
    }

    @Test
    @DisplayName("Deve serializar com COUNT")
    void testCount() {
      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.DAILY)
          .countLimit(10)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=DAILY;COUNT=10", result);
    }

    @Test
    @DisplayName("Deve serializar com UNTIL")
    void testUntil() {
      LocalDate untilDate = LocalDate.of(2025, 12, 31);

      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.DAILY)
          .untilDate(untilDate)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=DAILY;UNTIL=20251231T000000Z", result);
    }

    @Test
    @DisplayName("Deve serializar com WKST")
    void testWeekStartDay() {
      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.WEEKLY)
          .weekStartDay(DayOfWeek.SUNDAY)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=WEEKLY;WKST=SU", result);
    }

    @Test
    @DisplayName("Deve serializar com BYSETPOS")
    void testBySetPosition() {
      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.MONTHLY)
          .byMonthDay(Set.of(1, 8, 15, 22))
          .bySetPosition(1)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=MONTHLY;BYMONTHDAY=1,8,15,22;BYSETPOS=1", result);
    }

    @Test
    @DisplayName("Deve retornar string vazia para recorrência nula")
    void testNullRecurrence() {
      String result = ICalendarSerializer.toRRule((RecorrenciaDTO) null);

      assertEquals("", result);
    }

    @Test
    @DisplayName("Deve serializar com BYYEARDAY")
    void testByYearDay() {
      Set<Integer> yearDays = new HashSet<>();
      yearDays.add(1);
      yearDays.add(100);
      yearDays.add(200);

      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.YEARLY)
          .byYearDay(yearDays)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=YEARLY;BYYEARDAY=1,100,200", result);
    }

    @Test
    @DisplayName("Deve serializar com BYWEEKNO")
    void testByWeekNo() {
      Set<Integer> weekNos = new HashSet<>();
      weekNos.add(1);
      weekNos.add(52);

      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.YEARLY)
          .byWeekNo(weekNos)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=YEARLY;BYWEEKNO=1,52", result);
    }

    @Test
    @DisplayName("Deve serializar com BYHOUR e BYMINUTE")
    void testByHourAndMinute() {
      Set<Integer> hours = new HashSet<>();
      hours.add(9);
      hours.add(12);
      hours.add(18);

      Set<Integer> minutes = new HashSet<>();
      minutes.add(0);
      minutes.add(30);

      RecorrenciaDTO recur = RecorrenciaDTO.builder()
          .frequency(Frequencia.DAILY)
          .byHour(hours)
          .byMinute(minutes)
          .build();

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=DAILY;BYHOUR=9,12,18;BYMINUTE=0,30", result);
    }
  }

  @Nested
  @DisplayName("toRRule - Recorrencia (Entity)")
  class RecorrenciaEntityTests {

    @Test
    @DisplayName("Deve serializar recorrência semanal com entity")
    void testWeeklyWithEntity() {
      Set<DayOfWeek> days = new HashSet<>();
      days.add(DayOfWeek.TUESDAY);
      days.add(DayOfWeek.THURSDAY);

      Recorrencia recur = new Recorrencia();
      recur.setFrequency(Frequencia.WEEKLY);
      recur.setByDay(days);
      recur.setIntervalValue(1);

      String result = ICalendarSerializer.toRRule(recur);

      assertEquals("FREQ=WEEKLY;BYDAY=TU,TH", result);
    }

    @Test
    @DisplayName("Deve retornar string vazia para recorrência nula (entity)")
    void testNullRecurrenceEntity() {
      String result = ICalendarSerializer.toRRule((Recorrencia) null);

      assertEquals("", result);
    }
  }

  @Nested
  @DisplayName("toExRule - ExclusaoRecorrenciaDTO")
  class ExclusaoRecorrenciaDTOTests {

    @Test
    @DisplayName("Deve serializar exclusão de recorrência diária")
    void testDailyExclusion() {
      ExclusaoRecorrenciaDTO ex = ExclusaoRecorrenciaDTO.builder()
          .frequency(Frequencia.DAILY)
          .build();

      String result = ICalendarSerializer.toExRule(ex);

      assertEquals("FREQ=DAILY", result);
    }

    @Test
    @DisplayName("Deve serializar exclusão com BYDAY")
    void testExclusionByDay() {
      Set<DayOfWeek> days = new HashSet<>();
      days.add(DayOfWeek.SATURDAY);
      days.add(DayOfWeek.SUNDAY);

      ExclusaoRecorrenciaDTO ex = ExclusaoRecorrenciaDTO.builder()
          .frequency(Frequencia.WEEKLY)
          .byDay(days)
          .build();

      String result = ICalendarSerializer.toExRule(ex);

      assertEquals("FREQ=WEEKLY;BYDAY=SA,SU", result);
    }

    @Test
    @DisplayName("Deve retornar string vazia para exclusão nula")
    void testNullExclusion() {
      String result = ICalendarSerializer.toExRule((ExclusaoRecorrenciaDTO) null);

      assertEquals("", result);
    }
  }
}
