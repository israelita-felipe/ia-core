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
 * Exemplos de uso da classe OccurrenceCalculator.
 * <p>
 * Esta classe demonstra различные cenários de uso para cálculo de
 * ocorrências de eventos periódicos conforme RFC 5545.
 *
 * @author Israel Araújo
 */
public class OccurrenceCalculatorExamples {

  private final OccurrenceCalculator calculator = new OccurrenceCalculator();

  /**
   * Exemplo 1: Evento diário simples
   * <p>
   * Cria um evento que ocorre todos os dias às 10:00,
   * com duração de 1 hora.
   */
  public void exemploEventoDiario() {
    System.out.println("=== Exemplo 1: Evento Diário ===");

    // Definição do intervalo base (primeira ocorrência)
    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2026, 1, 15))
        .startTime(LocalTime.of(10, 0))
        .endDate(LocalDate.of(2026, 1, 15))
        .endTime(LocalTime.of(11, 0))
        .build();

    // Regra de recorrência: diário
    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.DAILY)
        .intervalValue(1)
        .build();

    // Periodicidade completa
    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    // Busca próximas 5 ocorrências a partir de hoje
    ZonedDateTime from = ZonedDateTime.now(ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 5);

    System.out.println("Próximas 5 ocorrências:");
    for (IntervaloTemporalDTO occ : occurrences) {
      // Formata data e hora separadamente
      String startStr = occ.getStartDate() + " " + occ.getStartTime();
      String endStr = (occ.getEndDate() != null ? occ.getEndDate() : occ.getStartDate()) 
          + " " + (occ.getEndTime() != null ? occ.getEndTime() : occ.getStartTime());
      System.out.println("  - " + startStr + " até " + endStr);
    }

    // Exemplo de saída:
    // Próximas ocorrências:
    //   - 2026-01-16T10:00 até 2026-01-16T11:00
    //   - 2026-01-17T10:00 até 2026-01-17T11:00
    //   - 2026-01-18T10:00 até 2026-01-18T11:00
    //   - 2026-01-19T10:00 até 2026-01-19T11:00
    //   - 2026-01-20T10:00 até 2026-01-20T11:00
  }

  /**
   * Exemplo 2: Evento semanal em dias específicos
   * <p>
   * Cria um evento que ocorre toda segunda e quarta-feira,
   * às 14:00, com duração de 2 horas.
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
      // Formata endDate e endTime
      String endStr = (occ.getEndDate() != null ? occ.getEndDate() : occ.getStartDate()) 
          + " " + (occ.getEndTime() != null ? occ.getEndTime() : occ.getStartTime());
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime() + " até " + endStr 
          + " (" + occ.getStartDate().getDayOfWeek() + ")");
    }

    // Exemplo de saída:
    // Próximas ocorrências:
    //   - 2026-01-14T14:00 até 2026-01-14T16:00 (WEDNESDAY)
    //   - 2026-01-19T14:00 até 2026-01-19T16:00 (MONDAY)
    //   - 2026-01-21T14:00 até 2026-01-21T16:00 (WEDNESDAY)
    //   - ...
  }

  /**
   * Exemplo 3: Evento mensal em dias específicos do mês
   * <p>
   * Cria um evento que ocorre no 1º e 15º dia de cada mês,
   * às 09:00.
   */
  public void exemploEventoMensalDiasEspecificos() {
    System.out.println("\n=== Exemplo 3: Evento Mensal (1º e 15º) ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2026, 1, 1))
        .startTime(LocalTime.of(9, 0))
        .endDate(LocalDate.of(2026, 1, 1))
        .endTime(LocalTime.of(10, 0))
        .build();

    Set<Integer> diasMes = new HashSet<>();
    diasMes.add(1);
    diasMes.add(15);

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.MONTHLY)
        .intervalValue(1)
        .byMonthDay(diasMes)
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime from = ZonedDateTime.of(2026, 1, 1, 9, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 6);

    System.out.println("Próximas 6 ocorrências:");
    for (IntervaloTemporalDTO occ : occurrences) {
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime());
    }

    // Exemplo de saída:
    // Próximas ocorrências:
    //   - 2026-01-15T09:00
    //   - 2026-02-01T09:00
    //   - 2026-02-15T09:00
    //   - 2025-03-01T09:00
    //   - ...
  }

  /**
   * Exemplo 4: Evento com data de término (until)
   * <p>
   * Cria um evento diário que termina em uma data específica.
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

    // Usa 00:00 para pegar o primeiro dia também
    ZonedDateTime from = ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0,
        ZoneId.of("America/Recife"));
    ZonedDateTime end = ZonedDateTime.of(2026, 1, 31, 23, 59, 59, 0,
        ZoneId.of("America/Recife"));

    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, end);

    System.out.println("Ocorrências até 31/01/2026:");
    for (IntervaloTemporalDTO occ : occurrences) {
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime());
    }

    // Exemplo de saída:
    // Ocorrências até 31/01/2026:
    //   - 2026-01-01T08:00
    //   - 2026-01-02T08:00
    //   - ...
    //   - 2026-01-10T08:00 (última)
  }

  /**
   * Exemplo 5: Evento com limite de ocorrências (count)
   * <p>
   * Cria um evento que ocorre um número específico de vezes.
   */
  public void exemploComLimiteOccorrências() {
    System.out.println("\n=== Exemplo 5: Evento com Limite de Ocorrências ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2025, 1, 1))
        .startTime(LocalTime.of(19, 0))
        .endDate(LocalDate.of(2025, 1, 1))
        .endTime(LocalTime.of(22, 0))
        .build();

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.WEEKLY)
        .intervalValue(1)
        .countLimit(4) // Apenas 4 ocorrências
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime from = ZonedDateTime.of(2025, 1, 1, 19, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 10); // Request 10, mas limite é 4

    System.out.println("Próximas ocorrências (máximo 4):");
    for (IntervaloTemporalDTO occ : occurrences) {
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime());
    }

    // Exemplo de saída:
    // Próximas ocorrências (máximo 4):
    //   - 2025-01-01T19:00
    //   - 2025-01-08T19:00
    //   - 2025-01-15T19:00
    //   - 2025-01-22T19:00
  }

  /**
   * Exemplo 6: Evento anual em mês específico
   * <p>
   * Cria um evento que ocorre uma vez por ano,
   * em um mês específico.
   */
  public void exemploEventoAnual() {
    System.out.println("\n=== Exemplo 6: Evento Anual ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2025, 3, 20))
        .startTime(LocalTime.of(10, 0))
        .endDate(LocalDate.of(2025, 3, 20))
        .endTime(LocalTime.of(12, 0))
        .build();

    Set<Month> meses = new HashSet<>();
    meses.add(Month.MARCH);

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.YEARLY)
        .intervalValue(1)
        .byMonth(meses)
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime from = ZonedDateTime.of(2025, 1, 1, 0, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 3);

    System.out.println("Próximas 3 ocorrências anuais:");
    for (IntervaloTemporalDTO occ : occurrences) {
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime());
    }

    // Exemplo de saída:
    // Próximas 3 ocorrências anuais:
    //   - 2025-03-20T10:00
    //   - 2026-03-20T10:00
    //   - 2027-03-20T10:00
  }

  /**
   * Exemplo 7: Evento com datas de exceção
   * <p>
   * Cria um evento diário exceto em datas específicas.
   */
  public void exemploComExcecoes() {
    System.out.println("\n=== Exemplo 7: Evento com Datas de Exceção ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2025, 1, 1))
        .startTime(LocalTime.of(12, 0))
        .endDate(LocalDate.of(2025, 1, 1))
        .endTime(LocalTime.of(13, 0))
        .build();

    // Datas a excluir
    Set<LocalDate> exceptionDates = new HashSet<>();
    exceptionDates.add(LocalDate.of(2025, 1, 5));
    exceptionDates.add(LocalDate.of(2025, 1, 12));

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.DAILY)
        .intervalValue(1)
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .exceptionDates(exceptionDates)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime from = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 15);

    System.out.println("Próximas 15 ocorrências (com exceções em 05 e 12/01):");
    for (IntervaloTemporalDTO occ : occurrences) {
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime());
    }

    // Exemplo de saída (5 e 12 de janeiro serão pulados):
    //   - 2025-01-01T12:00
    //   - 2025-01-02T12:00
    //   - 2025-01-03T12:00
    //   - 2025-01-04T12:00
    //   - (05/01 PULADO - exceção)
    //   - 2025-01-06T12:00
    //   ...
  }

  /**
   * Exemplo 8: Evento com datas de inclusão (RDATE)
   * <p>
   * Adiciona datas específicas além da recorrência regular.
   */
  public void exemploComDatasInclusao() {
    System.out.println("\n=== Exemplo 8: Evento com Datas de Inclusão ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2025, 1, 1))
        .startTime(LocalTime.of(15, 0))
        .endDate(LocalDate.of(2025, 1, 1))
        .endTime(LocalTime.of(16, 0))
        .build();

    // Datas adicionais a incluir
    Set<LocalDate> includeDates = new HashSet<>();
    includeDates.add(LocalDate.of(2025, 1, 20));
    includeDates.add(LocalDate.of(2025, 2, 14));

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.WEEKLY)
        .intervalValue(1)
        .byDay(Set.of(DayOfWeek.MONDAY))
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .includeDates(includeDates)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime from = ZonedDateTime.of(2025, 1, 1, 15, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 10);

    System.out.println("Próximas ocorrências (incluindo datas específicas):");
    for (IntervaloTemporalDTO occ : occurrences) {
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime());
    }
  }

  /**
   * Exemplo 9: Verificar interseção entre dois eventos
   * <p>
   * Verifica se dois eventos periódicos têm interseção
   * em um determinado período.
   */
  public void exemploVerificarInterseccao() {
    System.out.println("\n=== Exemplo 9: Verificar Interseção ===");

    // Evento A: Todo dia às 10:00
    IntervaloTemporalDTO intervaloA = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2025, 1, 1))
        .startTime(LocalTime.of(10, 0))
        .endDate(LocalDate.of(2025, 1, 1))
        .endTime(LocalTime.of(11, 0))
        .build();

    RecorrenciaDTO regraA = RecorrenciaDTO.builder()
        .frequency(Frequencia.DAILY)
        .build();

    PeriodicidadeDTO eventoA = PeriodicidadeDTO.builder()
        .intervaloBase(intervaloA)
        .regra(regraA)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    // Evento B: Toda terça-feira às 10:30
    IntervaloTemporalDTO intervaloB = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2025, 1, 7)) // Primeira terça
        .startTime(LocalTime.of(10, 30))
        .endDate(LocalDate.of(2025, 1, 7))
        .endTime(LocalTime.of(11, 30))
        .build();

    RecorrenciaDTO regraB = RecorrenciaDTO.builder()
        .frequency(Frequencia.WEEKLY)
        .byDay(Set.of(DayOfWeek.TUESDAY))
        .build();

    PeriodicidadeDTO eventoB = PeriodicidadeDTO.builder()
        .intervaloBase(intervaloB)
        .regra(regraB)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    // Verifica interseção no período de 01/01 a 31/01/2025
    var inicio = java.time.LocalDateTime.of(2025, 1, 1, 0, 0);
    var fim = java.time.LocalDateTime.of(2025, 1, 31, 23, 59);
    var zone = ZoneId.of("America/Recife");

    boolean intersecta = calculator.intersects(eventoA, eventoB, inicio, fim,
        zone);

    System.out.println("Eventos se intersectam no período? " + intersecta);

    // Exemplo de saída:
    // Evento A ocorre todos os dias às 10:00
    // Evento B ocorre toda terça-feira às 10:30
    // Não há interseção porque os horários não se sobrepõem
    // Resultado: false
  }

  /**
   * Exemplo 10: Evento a cada 2 semanas
   */
  public void exemploIntervaloMaiorQueUm() {
    System.out.println("\n=== Exemplo 10: Evento a cada 2 semanas ===");

    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2025, 1, 6)) // Primeira segunda
        .startTime(LocalTime.of(18, 0))
        .endDate(LocalDate.of(2025, 1, 6))
        .endTime(LocalTime.of(20, 0))
        .build();

    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.WEEKLY)
        .intervalValue(2) // A cada 2 semanas
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    ZonedDateTime from = ZonedDateTime.of(2025, 1, 6, 18, 0, 0, 0,
        ZoneId.of("America/Recife"));
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, from, 5);

    System.out.println("Próximas 5 ocorrências (a cada 2 semanas):");
    for (IntervaloTemporalDTO occ : occurrences) {
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime());
    }

    // Exemplo de saída:
    //   - 2025-01-06T18:00
    //   - 2025-01-20T18:00
    //   - 2025-02-03T18:00
    //   - 2025-02-17T18:00
    //   - 2025-03-03T18:00
  }

  /**
   * Exemplo 11: Evento na segunda semana do mês
   * <p>
   * Cria um evento que ocorre toda terça-feira na segunda semana do mês,
   * das 18:30 às 20:30.
   * <p>
   * Usa bySetPosition=2 (segunda ocorrência) e byDay=TUESDAY.
   */
  public void exemploSegundaSemanaMes() {
    System.out.println("\n=== Exemplo 11: Evento na Segunda Semana do Mês ===");

    // Define o intervalo base (primeira ocorrência)
    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2026, 1, 1))  // Primeira terça do mês
        .startTime(LocalTime.of(18, 30))
        .endDate(LocalDate.of(2026, 1, 1))
        .endTime(LocalTime.of(20, 30))
        .build();

    // Regra de recorrência: mensal, na segunda terça-feira
    // BYSETPOS=2 significa "a segunda ocorrência" do dia especificado
    // BYDAY=TU significa "todas as terças"
    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.MONTHLY)
        .intervalValue(1)
        .byDay(Set.of(DayOfWeek.TUESDAY))
        .bySetPosition(2)  // Segunda terça do mês
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    // Usa 00:00 para incluir janeiro
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

    // Exemplo de saída:
    // Próximas 6 ocorrências (segunda terça do mês):
    //   - 2026-01-14 18:30 até 2026-01-14 20:30 (TUESDAY)
    //   - 2026-02-11 18:30 até 2026-02-11 20:30 (TUESDAY)
    //   - 2026-03-11 18:30 até 2026-03-11 20:30 (TUESDAY)
    //   - 2026-04-08 18:30 até 2026-04-08 20:30 (TUESDAY)
    //   - 2026-05-13 18:30 até 2026-05-13 20:30 (TUESDAY)
    //   - 2026-06-10 18:30 até 2026-06-10 20:30 (TUESDAY)
  }

  /**
   * Exemplo 12: Evento na segunda semana do mês com período definido
   * <p>
   * Cria um evento que ocorre toda terça-feira na segunda semana do mês,
   * das 18:30 às 20:30, gerando ocorrências entre 01/01/2026 e 31/12/2026.
   * <p>
   * Usa o método generateOccurrences(periodicidade, start, end).
   */
  public void exemploSegundaSemanaMesComPeriodo() {
    System.out.println("\n=== Exemplo 12: Segunda Terça com Período ===");

    // Define o intervalo base
    IntervaloTemporalDTO intervalo = IntervaloTemporalDTO.builder()
        .startDate(LocalDate.of(2026, 1, 1))
        .startTime(LocalTime.of(18, 30))
        .endDate(LocalDate.of(2026, 1, 1))
        .endTime(LocalTime.of(20, 30))
        .build();

    // Regra de recorrência: mensal, na segunda terça-feira
    RecorrenciaDTO regra = RecorrenciaDTO.builder()
        .frequency(Frequencia.MONTHLY)
        .intervalValue(1)
        .byDay(Set.of(DayOfWeek.TUESDAY))
        .bySetPosition(2)  // Segunda terça do mês
        .build();

    PeriodicidadeDTO periodicidade = PeriodicidadeDTO.builder()
        .intervaloBase(intervalo)
        .regra(regra)
        .zoneId(ZoneId.of("America/Recife").getId())
        .build();

    // Define o período: de 01/01/2026 até 31/12/2026
    ZonedDateTime start = ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0,
        ZoneId.of("America/Recife"));
    ZonedDateTime end = ZonedDateTime.of(2026, 12, 31, 23, 59, 59, 0,
        ZoneId.of("America/Recife"));

    // Gera ocorrências no período
    List<IntervaloTemporalDTO> occurrences = calculator.generateOccurrences(
        periodicidade, start, end);

    System.out.println("Ocorrências de 01/01/2026 até 31/12/2026 (" + occurrences.size() + " ocorrências):");
    for (IntervaloTemporalDTO occ : occurrences) {
      String endStr = (occ.getEndDate() != null ? occ.getEndDate() : occ.getStartDate()) 
          + " " + (occ.getEndTime() != null ? occ.getEndTime() : occ.getStartTime());
      System.out.println("  - " + occ.getStartDate() + " " + occ.getStartTime() 
          + " até " + endStr);
    }

    // Exemplo de saída:
    // Ocorrências de 01/01/2026 até 31/12/2026 (12 ocorrências):
    //   - 2026-01-14 18:30 até 2026-01-14 20:30
    //   - 2026-02-11 18:30 até 2026-02-11 20:30
    //   - 2026-03-11 18:30 até 2026-03-11 20:30
    //   - 2026-04-08 18:30 até 2026-04-08 20:30
    //   - 2026-05-13 18:30 até 2026-05-13 20:30
    //   - 2026-06-10 18:30 até 2026-06-10 20:30
    //   - 2026-07-08 18:30 até 2026-07-08 20:30
    //   - 2026-08-12 18:30 até 2026-08-12 20:30
    //   - 2026-09-09 18:30 até 2026-09-09 20:30
    //   - 2026-10-14 18:30 até 2026-10-14 20:30
    //   - 2026-11-11 18:30 até 2026-11-11 20:30
    //   - 2026-12-09 18:30 até 2026-12-09 20:30
  }

  /**
   * Método principal para executar todos os exemplos.
   */
  public static void main(String[] args) {
    OccurrenceCalculatorExamples examples = new OccurrenceCalculatorExamples();

    examples.exemploEventoDiario();
    examples.exemploEventoSemanalDiasEspecificos();
    examples.exemploEventoMensalDiasEspecificos();
    examples.exemploComDataTermino();
    examples.exemploComLimiteOccorrências();
    examples.exemploEventoAnual();
    examples.exemploComExcecoes();
    examples.exemploComDatasInclusao();
    examples.exemploVerificarInterseccao();
    examples.exemploIntervaloMaiorQueUm();
    examples.exemploSegundaSemanaMes();
    examples.exemploSegundaSemanaMesComPeriodo();

    System.out.println("\n=== Todos os exemplos executados com sucesso! ===");
  }
}
