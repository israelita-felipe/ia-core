package com.ia.core.quartz.service.periodicidade.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Calculadora de ocorrências para eventos periódicos.
 * <p>
 * Responsável por calcular as próximas ocorrências de um evento
 * com base em sua regra de recorrência RFC 5545.
 * <p>
 * Suporta:
 * <ul>
 * <li>Cálculo da próxima ocorrência após uma data</li>
 * <li>Geração de múltiplas ocorrências</li>
 * <li>Suporte a EXRULE (regras de exclusão)</li>
 * <li>Suporte a exception dates</li>
 * <li>Suporte a include dates</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see PeriodicidadeDTO
 * @see RecorrenciaDTO
 */
public class OccurrenceCalculator {

  public Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                       ZonedDateTime after) {

    ZoneId zone = p.getZoneIdValue();
    ZonedDateTime base = p.getIntervaloBase().getStartDateTime()
        .atZone(zone);

    Optional<IntervaloTemporalDTO> rdate = nextRDate(p, after);

    if (rdate.isPresent())
      return rdate;

    if (!p.hasRecurrence()) {
      if (base.isAfter(after))
        return Optional.of(p.getIntervaloBase());
      return Optional.empty();
    }

    RecorrenciaDTO r = p.getRegra();

    ZonedDateTime candidate = fastForward(base, after, r);

    int generated = estimateOccurrenceIndex(base, candidate, r);

    while (true) {

      if (r.getCountLimit() != null && generated >= r.getCountLimit())
        return Optional.empty();

      if (r.getUntilDate() != null
          && candidate.isAfter(r.getUntilDate().atStartOfDay(zone)))
        return Optional.empty();

      if (!p.getExceptionDates().contains(candidate.toLocalDate())
          && matchesRule(candidate, r)) {

        ZonedDateTime end = candidate.plus(p.duration());

        return Optional
            .of(new IntervaloTemporalDTO(candidate.toLocalDateTime(),
                                         end.toLocalDateTime()));
      }

      candidate = increment(candidate, r);
      generated++;
    }
  }

  private ZonedDateTime fastForward(ZonedDateTime base, ZonedDateTime after,
                                    RecorrenciaDTO r) {

    long diff;

    switch (r.getFrequency()) {

    case DIARIAMENTE, DAILY -> {
      diff = ChronoUnit.DAYS.between(base, after);
      long cycles = diff / r.getIntervalValue();
      return base.plusDays(cycles * r.getIntervalValue());
    }

    case SEMANALMENTE, WEEKLY -> {
      diff = ChronoUnit.WEEKS.between(base, after);
      long cycles = diff / r.getIntervalValue();
      return base.plusWeeks(cycles * r.getIntervalValue());
    }

    case MENSALMENTE, MONTHLY -> {
      diff = ChronoUnit.MONTHS.between(base, after);
      long cycles = diff / r.getIntervalValue();
      return base.plusMonths(cycles * r.getIntervalValue());
    }

    case ANUALMENTE, YEARLY -> {
      diff = ChronoUnit.YEARS.between(base, after);
      long cycles = diff / r.getIntervalValue();
      return base.plusYears(cycles * r.getIntervalValue());
    }
    }

    return base;
  }

  private ZonedDateTime increment(ZonedDateTime t, RecorrenciaDTO r) {

    return switch (r.getFrequency()) {
    case DIARIAMENTE, DAILY -> t.plusDays(r.getIntervalValue());
    case SEMANALMENTE, WEEKLY -> t.plusWeeks(r.getIntervalValue());
    case MENSALMENTE, MONTHLY -> t.plusMonths(r.getIntervalValue());
    case ANUALMENTE, YEARLY -> t.plusYears(r.getIntervalValue());
    };
  }

  private boolean matchesRule(ZonedDateTime date, RecorrenciaDTO r) {

    if (!r.getByMonth().isEmpty()
        && !r.getByMonth().contains(date.getMonth()))
      return false;

    if (!r.getByMonthDay().isEmpty()
        && !r.getByMonthDay().contains(date.getDayOfMonth()))
      return false;

    if (!r.getByDay().isEmpty()
        && !r.getByDay().contains(date.getDayOfWeek()))
      return false;

    return true;
  }

  private Optional<IntervaloTemporalDTO> nextRDate(PeriodicidadeDTO p,
                                                   ZonedDateTime after) {

    return p.getIncludeDates().stream()
        .filter(d -> d.atStartOfDay(p.getZoneIdValue()).isAfter(after))
        .min(LocalDate::compareTo).map(d -> {
          ZonedDateTime start = d.atStartOfDay(p.getZoneIdValue());
          return new IntervaloTemporalDTO(start.toLocalDateTime(), start
              .plus(p.duration()).toLocalDateTime());
        });
  }

  private int estimateOccurrenceIndex(ZonedDateTime base,
                                      ZonedDateTime candidate,
                                      RecorrenciaDTO r) {

    switch (r.getFrequency()) {
    case DIARIAMENTE, DAILY:
      return (int) ChronoUnit.DAYS.between(base, candidate)
          / r.getIntervalValue();
    case SEMANALMENTE, WEEKLY:
      return (int) ChronoUnit.WEEKS.between(base, candidate)
          / r.getIntervalValue();
    case MENSALMENTE, MONTHLY:
      return (int) ChronoUnit.MONTHS.between(base, candidate)
          / r.getIntervalValue();
    case ANUALMENTE, YEARLY:
      return (int) ChronoUnit.YEARS.between(base, candidate)
          / r.getIntervalValue();
    }
    return 0;
  }

  public boolean intersects(PeriodicidadeDTO a, PeriodicidadeDTO b,
                            LocalDateTime windowStart,
                            LocalDateTime windowEnd, ZoneId zoneId) {

    ZonedDateTime cursorA = windowStart.atZone(zoneId);
    ZonedDateTime cursorB = windowStart.atZone(zoneId);

    while (true) {

      Optional<IntervaloTemporalDTO> nextA = nextOccurrence(a, cursorA);

      Optional<IntervaloTemporalDTO> nextB = nextOccurrence(b, cursorB);

      if (nextA.isEmpty() || nextB.isEmpty())
        return false;

      IntervaloTemporalDTO iA = nextA.get();
      IntervaloTemporalDTO iB = nextB.get();

      if (iA.intersects(iB))
        return true;

      if (iA.getStartDateTime().isBefore(iB.getStartDateTime())) {
        cursorA = iA.getEndDateTime().atZone(zoneId);
      } else {
        cursorB = iB.getEndDateTime().atZone(zoneId);
      }

      if (cursorA.isAfter(windowEnd.atZone(zoneId))
          || cursorB.isAfter(windowEnd.atZone(zoneId)))
        return false;
    }
  }

  /**
   * Gera uma lista de ocorrências futuras de uma periodicidade.
   * <p>
   * Este método é útil para pré-visualização de eventos recorrentes
   * na interface do usuário.
   *
   * @param periodicidade a periodicidade
   * @param after data/hora inicial para buscar ocorrências
   * @param maxCount número máximo de ocorrências a retornar
   * @return lista de IntervaloTemporalDTO representando as ocorrências
   */
  public List<IntervaloTemporalDTO> generateOccurrences(
      PeriodicidadeDTO periodicidade, ZonedDateTime after, int maxCount) {
    List<IntervaloTemporalDTO> occurrences = new ArrayList<>();

    if (periodicidade == null) {
      return occurrences;
    }

    ZonedDateTime cursor = after;
    int count = 0;

    while (count < maxCount) {
      Optional<IntervaloTemporalDTO> next = nextOccurrence(periodicidade, cursor);

      if (next.isEmpty()) {
        break;
      }

      IntervaloTemporalDTO occurrence = next.get();
      occurrences.add(occurrence);

      // Move o cursor para após esta ocorrência
      ZonedDateTime endTime = occurrence.getEndDateTime() != null
          ? occurrence.getEndDateTime().atZone(periodicidade.getZoneIdValue())
          : occurrence.getStartDateTime()
              .atZone(periodicidade.getZoneIdValue()).plusHours(1);

      cursor = endTime.plusNanos(1);
      count++;
    }

    return occurrences;
  }

  /**
   * Gera uma lista de ocorrências a partir de uma data.
   *
   * @param periodicidade a periodicidade
   * @param startDate data inicial
   * @param maxCount número máximo de ocorrências
   * @return lista de IntervaloTemporalDTO
   */
  public List<IntervaloTemporalDTO> generateOccurrences(
      PeriodicidadeDTO periodicidade, LocalDate startDate, int maxCount) {
    return generateOccurrences(periodicidade,
        startDate.atStartOfDay(ZoneId.systemDefault()), maxCount);
  }
}
