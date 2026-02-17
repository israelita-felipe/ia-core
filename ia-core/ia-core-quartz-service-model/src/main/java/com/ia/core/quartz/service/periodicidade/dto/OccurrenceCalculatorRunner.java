package com.ia.core.quartz.service.periodicidade.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ia.core.quartz.model.periodicidade.Frequencia;

/**
 * Runner para testar a classe OccurrenceCalculator.
 */
public class OccurrenceCalculatorRunner {

  private final OccurrenceCalculator calculator = new OccurrenceCalculator();

  public static void main(String[] args) {
    OccurrenceCalculatorRunner runner = new OccurrenceCalculatorRunner();
    runner.exemploEventoSemanalDiasEspecificos();
    runner.exemploComDataTermino();
    runner.exemploSegundaSemanaMes();
    runner.exemploSegundaSemanaMesComPeriodo();
    System.out.println("\n=== Todos os exemplos executados! ===");
  }

  /**
   * Exemplo 2: Evento semanal em dias específicos
   */
  public void exemploEventoSemanalDiasEspecificos() {
    System.out.println("\n=== Exemplo 2: Evento Semanal (Seg/Qua) ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2026, 1, 13)) // Segunda-feira
        .startTime(LocalTime.of(14, 0))
        .endDate(LocalDate.of(2026, 1, 13))
        .endTime(LocalTime.of(16, 0))
        .build();

    Set<DayOfWeek> diasSemana = new HashSet<>();
    diasSemana.add(DayOfWeek.MONDAY);
    diasSemana.add(DayOfWeek.WEDNESDAY);

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.WEEKLY)
        .intervalValue(1)
        .byDay(diasSemana)
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime from = ZonedDateTime.of(2026, 1, 13, 14, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 6);

    System.out.println("Próximas 6 ocorrências:");
    for (IntervaloTemporalDTO occ : occurrences) {
      String endStr = (occ.getEndDate() != null ? occ.getEndDate() : occ.getStartDate()) 
          + " " + (occ.getEndTime() != null ? occ.getEndTime() : occ.getStartTime());
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime() + " até " + endStr 
          + " (" + occ.getStartDate().getDayOfWeek() + ")");
    }

    // Esperado:
    //   - 2026-01-14 14:00 até 2026-01-14 16:00 (WEDNESDAY)
    //   - 2026-01-19 14:00 até 2026-01-19 16:00 (MONDAY)
    //   - 2026-01-21 14:00 até 2026-01-21 16:00 (WEDNESDAY)
  }

  /**
   * Exemplo 4: Evento com data de término (until)
   */
  public void exemploComDataTermino() {
    System.out.println("\n=== Exemplo 4: Evento com Data de Término ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2026, 1, 1))
        .startTime(LocalTime.of(8, 0))
        .endDate(LocalDate.of(2026, 1, 1))
        .endTime(LocalTime.of(9, 0))
        .build();

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.DAILY)
        .intervalValue(1)
        .untilDate(LocalDate.of(2026, 1, 10)) // Termina em 10/01/2026
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    // Usa o método com count - começa do horário do evento para incluir o primeiro dia
    ZonedDateTime from = ZonedDateTime.of(2026, 1, 1, 8, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 15);

    System.out.println("Ocorrências (until 10/01/2026):");
    for (IntervaloTemporalDTO occ : occurrences) {
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime());
    }

    // Esperado: de 01/01 a 10/01 (10 ocorrências)
  }

  /**
   * Exemplo 11: Evento na segunda semana do mês
   */
  public void exemploSegundaSemanaMes() {
    System.out.println("\n=== Exemplo 11: Evento na Segunda Semana do Mês ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2026, 1, 1))
        .startTime(LocalTime.of(18, 30))
        .endDate(LocalDate.of(2026, 1, 1))
        .endTime(LocalTime.of(20, 30))
        .build();

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.MONTHLY)
        .intervalValue(1)
        .byDay(Set.of(DayOfWeek.TUESDAY))
        .bySetPosition(2)
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime from = ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 6);

    System.out.println("Próximas 6 ocorrências (segunda terça do mês):");
    for (IntervaloTemporalDTO occ : occurrences) {
      String endStr = (occ.getEndDate() != null ? occ.getEndDate() : occ.getStartDate()) 
          + " " + (occ.getEndTime() != null ? occ.getEndTime() : occ.getStartTime());
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime() 
          + " até " + endStr + " (" + occ.getStartDate().getDayOfWeek() + ")");
    }

    // Esperado:
    //   - 2026-01-14 18:30 até 2026-01-14 20:30 (TUESDAY)
    //   - 2026-02-11 18:30 até 2026-02-11 20:30 (TUESDAY)
    //   - 2026-03-11 18:30 até 2026-03-11 20:30 (TUESDAY)
    //   - 2026-04-08 18:30 até 2026-04-08 20:30 (TUESDAY)
    //   - 2026-05-13 18:30 até 2026-05-13 20:30 (TUESDAY)
    //   - 2026-06-10 18:30 até 2026-06-10 20:30 (TUESDAY)
  }

  /**
   * Exemplo 12: Evento na segunda semana do mês com período definido
   */
  public void exemploSegundaSemanaMesComPeriodo() {
    System.out.println("\n=== Exemplo 12: Segunda Terça com Período ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2026, 1, 1))
        .startTime(LocalTime.of(18, 30))
        .endDate(LocalDate.of(2026, 1, 1))
        .endTime(LocalTime.of(20, 30))
        .build();

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.MONTHLY)
        .intervalValue(1)
        .byDay(Set.of(DayOfWeek.TUESDAY))
        .bySetPosition(2)
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime start = ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0,
        ZoneId.of("America/Recife"));
    ZonedDateTime end = ZonedDateTime.of(2026, 12, 31, 23, 59, 59, 0,
        ZoneId.of("America/Recife"));

    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, start, end);

    System.out.println("Ocorrências de 01/01/2026 até 31/12/2026 (" + occurrences.size() + " ocorrências):");
    for (IntervaloTemporalDTO occ : occurrences) {
      String endStr = (occ.getEndDate() != null ? occ.getEndDate() : occ.getStartDate()) 
          + " " + (occ.getEndTime() != null ? occ.getEndTime() : occ.getStartTime());
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime() 
          + " até " + endStr);
    }
  }
}
