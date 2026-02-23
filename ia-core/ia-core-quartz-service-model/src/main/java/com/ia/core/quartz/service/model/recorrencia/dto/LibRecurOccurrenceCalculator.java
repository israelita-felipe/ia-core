package com.ia.core.quartz.service.model.recorrencia.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;

public class LibRecurOccurrenceCalculator
  implements OccurrenceCalculator {

  private static final Logger log = LoggerFactory
      .getLogger(LibRecurOccurrenceCalculator.class);
  private static LibRecurOccurrenceCalculator INSTANCE = null;

  /**
   * @return {@link #INSTANCE}
   */
  protected static LibRecurOccurrenceCalculator get() {
    if (INSTANCE == null) {
      INSTANCE = new LibRecurOccurrenceCalculator();
    }
    return INSTANCE;
  }

  /**
   *
   */
  private LibRecurOccurrenceCalculator() {
  }

  @Override
  public Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                       ZonedDateTime after) {
    List<IntervaloTemporalDTO> list = generateOccurrences(p, after, 1);
    return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
  }

  @Override
  public Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                       ZonedDateTime after,
                                                       int maxResults) {
    return nextOccurrence(p, after);
  }

  @Override
  public List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                        ZonedDateTime after,
                                                        int maxCount) {
    if (!periodicidade.getAtivo() || maxCount <= 0) {
      return Collections.emptyList();
    }

    // Constrói o conjunto ordenado de todas as ocorrências (RRULE + RDATE) e
    // aplica EXDATE
    SortedSet<DateTime> allInstances = buildAllInstances(periodicidade);

    // Filtra após 'after' e limita
    DateTime afterUtc = DateTimeAdapter.toUtcDateTime(after);
    SortedSet<DateTime> filtered = allInstances.tailSet(afterUtc);

    List<IntervaloTemporalDTO> results = new ArrayList<>();
    Duration duration = periodicidade.duration();
    ZoneId zone = periodicidade.getZoneIdValue();

    for (DateTime dt : filtered) {
      if (results.size() >= maxCount)
        break;
      results
          .add(DateTimeAdapter.toIntervaloTemporalDTO(dt, duration, zone));
    }
    return results;
  }

  @Override
  public List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                        LocalDate startDate,
                                                        int maxCount) {
    ZonedDateTime start = startDate
        .atStartOfDay(periodicidade.getZoneIdValue());
    return generateOccurrences(periodicidade, start, maxCount);
  }

  @Override
  public List<IntervaloTemporalDTO> generateOccurrences(PeriodicidadeDTO periodicidade,
                                                        ZonedDateTime start,
                                                        ZonedDateTime end) {
    if (!periodicidade.getAtivo() || start.isAfter(end)) {
      return Collections.emptyList();
    }

    SortedSet<DateTime> allInstances = buildAllInstances(periodicidade);

    DateTime startUtc = DateTimeAdapter.toUtcDateTime(start);
    DateTime endUtc = DateTimeAdapter.toUtcDateTime(end);

    // Instâncias no intervalo [startUtc, endUtc]
    SortedSet<DateTime> inRange = allInstances.subSet(startUtc, endUtc);

    List<IntervaloTemporalDTO> results = new ArrayList<>();
    Duration duration = periodicidade.duration();
    ZoneId zone = periodicidade.getZoneIdValue();

    for (DateTime dt : inRange) {
      results
          .add(DateTimeAdapter.toIntervaloTemporalDTO(dt, duration, zone));
    }
    return results;
  }

  @Override
  public boolean intersects(PeriodicidadeDTO a, PeriodicidadeDTO b,
                            LocalDateTime windowStart,
                            LocalDateTime windowEnd, ZoneId zoneId) {
    ZonedDateTime start = windowStart.atZone(zoneId);
    ZonedDateTime end = windowEnd.atZone(zoneId);

    List<IntervaloTemporalDTO> occA = generateOccurrences(a, start, end);
    List<IntervaloTemporalDTO> occB = generateOccurrences(b, start, end);

    for (IntervaloTemporalDTO ia : occA) {
      ZonedDateTime startA = ZonedDateTime.of(ia.getStartDate(),
                                              ia.getStartTime(), zoneId);
      ZonedDateTime endA = ZonedDateTime.of(ia.getEndDate(),
                                            ia.getEndTime(), zoneId);
      for (IntervaloTemporalDTO ib : occB) {
        ZonedDateTime startB = ZonedDateTime.of(ib.getStartDate(),
                                                ib.getStartTime(), zoneId);
        ZonedDateTime endB = ZonedDateTime.of(ib.getEndDate(),
                                              ib.getEndTime(), zoneId);
        if (intervalsIntersect(startA, endA, startB, endB)) {
          return true;
        }
      }
    }
    return false;
  }

  // ---------- Métodos auxiliares ----------

  private SortedSet<DateTime> buildAllInstances(PeriodicidadeDTO p) {
    TreeSet<DateTime> instances = new TreeSet<>(createDateTimeComparator(p));

    // 1. Adiciona as ocorrências da RRULE (se houver)
    if (p.hasRecurrence()) {
      RecurrenceRuleIterator it = createRecurrenceIterator(p);
      if (it != null) {
        // Para evitar loop infinito, limitamos a um número razoável (ex: 1000)
        int maxIter = 1000;
        while (it.hasNext() && maxIter-- > 0) {
          instances.add(it.nextDateTime());
        }
      }
    } else {
      // Sem recorrência: adiciona apenas o evento base
      DateTime start = DateTimeAdapter
          .toUtcDateTime(p.getIntervaloBase().getStartDate(),
                         p.getIntervaloBase().getStartTime(),
                         p.getZoneIdValue());
      instances.add(start);
    }

    // 2. Adiciona RDATEs (includeDates) – tratamos como all-day
    for (LocalDate include : p.getIncludeDates()) {
      DateTime includeDt = DateTimeAdapter.toAllDayUtcDateTime(include);
      instances.add(includeDt);
    }

    // 3. Remove EXDATEs (exceptionDates)
    for (LocalDate exclude : p.getExceptionDates()) {
      DateTime excludeDt = DateTimeAdapter.toAllDayUtcDateTime(exclude);
      // Remove todas as instâncias que caem neste dia (all-day)
      instances.removeIf(dt -> DateTimeAdapter.isSameAllDay(dt, excludeDt));
    }

    return instances;
  }

  /**
   * @param p
   * @return
   */
  public Comparator<? super DateTime> createDateTimeComparator(PeriodicidadeDTO p) {
    return (a, b) -> DateTimeAdapter
        .fromUtcDateTime(a, p.getZoneIdValue())
        .compareTo(DateTimeAdapter.fromUtcDateTime(b, p.getZoneIdValue()));
  }

  private RecurrenceRuleIterator createRecurrenceIterator(PeriodicidadeDTO p) {
    if (!p.hasRecurrence() || p.getRegra() == null)
      return null;
    RecurrenceRule rrule = RecurrenceRuleAdapter
        .toRecurrenceRule(p.getRegra());
    if (rrule == null)
      return null;
    DateTime start = DateTimeAdapter
        .toUtcDateTime(p.getIntervaloBase().getStartDate(),
                       p.getIntervaloBase().getStartTime(),
                       p.getZoneIdValue());
    return rrule.iterator(start);
  }

  private boolean intervalsIntersect(ZonedDateTime startA,
                                     ZonedDateTime endA,
                                     ZonedDateTime startB,
                                     ZonedDateTime endB) {
    return !(endA.isBefore(startB) || endB.isBefore(startA));
  }
}
