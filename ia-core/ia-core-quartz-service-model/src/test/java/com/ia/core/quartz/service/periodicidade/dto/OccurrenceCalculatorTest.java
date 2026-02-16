package com.ia.core.quartz.service.periodicidade.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.ia.core.quartz.model.periodicidade.Frequencia;

/**
 * Testes unitários para OccurrenceCalculator.
 * <p>
 * Valida o cálculo de ocorrências de eventos periódicos
 * conforme as regras de recorrência RFC 5545.
 *
 * @author Israel Araújo
 */
@DisplayName("OccurrenceCalculator")
class OccurrenceCalculatorTest {

  private OccurrenceCalculator calculator;

  @BeforeEach
  void setUp() {
    calculator = new OccurrenceCalculator();
  }

  @Nested
  @DisplayName("nextOccurrence")
  class NextOccurrenceTests {

    @Test
    @DisplayName("Deve calcular próxima ocorrência diária")
    void testNextDailyOccurrence() {
      // Given: Periodicidade diária a partir de hoje
      IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.now())
          .startTime(LocalTime.of(10, 0))
          .endDate(LocalDate.now())
          .endTime(LocalTime.of(11, 0))
          .build();

      PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
          .intervaloBase(intervalo)
          .regra(RecorrenciaDTO.builder()
              .frequency(Frequencia.DAILY)
              .build())
          .zoneId(ZoneId.systemDefault().getId())
          .build();

      ZonedDateTime from = ZonedDateTime.now();

      // When
      var result = calculator.nextOccurrence(periodicidade, from);

      // Then
      assertTrue(result.isPresent());
      assertNotNull(result.get().getStartDateTime());
    }

    @Test
    @DisplayName("Deve calcular próxima ocorrência semanal")
    void testNextWeeklyOccurrence() {
      // Given: Periodicidade toda segunda-feira
      IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.now())
          .startTime(LocalTime.of(9, 0))
          .endDate(LocalDate.now())
          .endTime(LocalTime.of(10, 0))
          .build();

      Set<DayOfWeek> byDay = new HashSet<>();
      byDay.add(DayOfWeek.MONDAY);

      PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
          .intervaloBase(intervalo)
          .regra(RecorrenciaDTO.builder()
              .frequency(Frequencia.WEEKLY)
              .byDay(byDay)
              .build())
          .zoneId(ZoneId.systemDefault().getId())
          .build();

      ZonedDateTime from = ZonedDateTime.now();

      // When
      var result = calculator.nextOccurrence(periodicidade, from);

      // Then
      assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Deve retornar vazio para periodicidade sem regra de recorrência")
    void testNoRecurrence() {
      // Given: Periodicidade sem recorrência
      IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.now().minusDays(1))
          .startTime(LocalTime.of(10, 0))
          .endDate(LocalDate.now().minusDays(1))
          .endTime(LocalTime.of(11, 0))
          .build();

      PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
          .intervaloBase(intervalo)
          .regra(null)
          .zoneId(ZoneId.systemDefault().getId())
          .build();

      ZonedDateTime from = ZonedDateTime.now();

      // When
      var result = calculator.nextOccurrence(periodicidade, from);

      // Then
      assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve calcular com interval maior que 1")
    void testIntervalGreaterThanOne() {
      // Given: A cada 2 semanas
      IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.now())
          .startTime(LocalTime.of(14, 0))
          .endDate(LocalDate.now())
          .endTime(LocalTime.of(15, 0))
          .build();

      PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
          .intervaloBase(intervalo)
          .regra(RecorrenciaDTO.builder()
              .frequency(Frequencia.WEEKLY)
              .intervalValue(2)
              .build())
          .zoneId(ZoneId.systemDefault().getId())
          .build();

      ZonedDateTime from = ZonedDateTime.now();

      // When
      var result = calculator.nextOccurrence(periodicidade, from);

      // Then
      assertTrue(result.isPresent());
    }
  }

  @Nested
  @DisplayName("generateOccurrences")
  class GenerateOccurrencesTests {

    @Test
    @DisplayName("Deve gerar ocorrências diárias")
    void testGenerateDailyOccurrences() {
      // Given
      IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.now())
          .startTime(LocalTime.of(8, 0))
          .endDate(LocalDate.now())
          .endTime(LocalTime.of(9, 0))
          .build();

      PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
          .intervaloBase(intervalo)
          .regra(RecorrenciaDTO.builder()
              .frequency(Frequencia.DAILY)
              .countLimit(5)
              .build())
          .zoneId(ZoneId.systemDefault().getId())
          .build();

      ZonedDateTime from = ZonedDateTime.now();

      // When
      List<IntervaloTemporalDTO> results = calculator.generateOccurrences(
          periodicidade, from, 5);

      // Then
      assertFalse(results.isEmpty());
      assertTrue(results.size() <= 5);
    }

    @Test
    @DisplayName("Deve gerar ocorrências mensais")
    void testGenerateMonthlyOccurrences() {
      // Given
      IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.now().withDayOfMonth(1))
          .startTime(LocalTime.of(10, 0))
          .endDate(LocalDate.now().withDayOfMonth(1))
          .endTime(LocalTime.of(11, 0))
          .build();

      Set<Integer> monthDays = new HashSet<>();
      monthDays.add(1);
      monthDays.add(15);

      PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
          .intervaloBase(intervalo)
          .regra(RecorrenciaDTO.builder()
              .frequency(Frequencia.MONTHLY)
              .byMonthDay(monthDays)
              .countLimit(4)
              .build())
          .zoneId(ZoneId.systemDefault().getId())
          .build();

      ZonedDateTime from = ZonedDateTime.now();

      // When
      List<IntervaloTemporalDTO> results = calculator.generateOccurrences(
          periodicidade, from, 4);

      // Then
      assertFalse(results.isEmpty());
    }

    @Test
    @DisplayName("Deve respeitar limite de ocorrências")
    void testRespectsCountLimit() {
      // Given
      IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
          .startDate(LocalDate.now())
          .startTime(LocalTime.of(12, 0))
          .endDate(LocalDate.now())
          .endTime(LocalTime.of(13, 0))
          .build();

      PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
          .intervaloBase(intervalo)
          .regra(RecorrenciaDTO.builder()
              .frequency(Frequencia.DAILY)
              .countLimit(3)
              .build())
          .zoneId(ZoneId.systemDefault().getId())
          .build();

      ZonedDateTime from = ZonedDateTime.now();

      // When
      List<IntervaloTemporalDTO> results = calculator.generateOccurrences(
          periodicidade, from, 10);

      // Then - deve respeitar o menor limite (3 do countLimit ou 10 do método)
      assertTrue(results.size() <= 3);
    }
  }
}
