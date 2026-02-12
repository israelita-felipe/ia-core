package com.ia.core.quartz.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import com.ia.core.quartz.service.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.periodicidade.dto.RecorrenciaDTO;

public class OccurrenceCalculator {

  public Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                       LocalDateTime after) {

    LocalDateTime base = p.getIntervaloBase().getStartTime();

    Optional<IntervaloTemporalDTO> rdate = nextRDate(p, after);

    if (rdate.isPresent())
      return rdate;

    if (!p.hasRecurrence()) {
      if (base.isAfter(after))
        return Optional.of(p.getIntervaloBase());
      return Optional.empty();
    }

    RecorrenciaDTO r = p.getRegra();

    LocalDateTime candidate = fastForward(base, after, r);

    int generated = estimateOccurrenceIndex(base, candidate, r);

    while (true) {

      if (r.getCountLimit() != null && generated >= r.getCountLimit())
        return Optional.empty();

      if (r.getUntilDate() != null && candidate.isAfter(r.getUntilDate()))
        return Optional.empty();

      if (!p.getExDates().contains(candidate)
          && matchesRule(candidate, r)) {

        LocalDateTime end = candidate.plus(p.duration());

        return Optional.of(new IntervaloTemporalDTO(candidate, end));
      }

      candidate = increment(candidate, r);
      generated++;
    }
  }

  private LocalDateTime fastForward(LocalDateTime base, LocalDateTime after,
                                    RecorrenciaDTO r) {

    long diff;

    switch (r.getFrequency()) {

    case DIARIAMENTE -> {
      diff = ChronoUnit.DAYS.between(base, after);
      long cycles = diff / r.getIntervalValue();
      return base.plusDays(cycles * r.getIntervalValue());
    }

    case SEMANALMENTE -> {
      diff = ChronoUnit.WEEKS.between(base, after);
      long cycles = diff / r.getIntervalValue();
      return base.plusWeeks(cycles * r.getIntervalValue());
    }

    case MENSALMENTE -> {
      diff = ChronoUnit.MONTHS.between(base, after);
      long cycles = diff / r.getIntervalValue();
      return base.plusMonths(cycles * r.getIntervalValue());
    }

    case ANUALMENTE -> {
      diff = ChronoUnit.YEARS.between(base, after);
      long cycles = diff / r.getIntervalValue();
      return base.plusYears(cycles * r.getIntervalValue());
    }
    }

    return base;
  }

  private LocalDateTime increment(LocalDateTime t, RecorrenciaDTO r) {

    return switch (r.getFrequency()) {
    case DIARIAMENTE -> t.plusDays(r.getIntervalValue());
    case SEMANALMENTE -> t.plusWeeks(r.getIntervalValue());
    case MENSALMENTE -> t.plusMonths(r.getIntervalValue());
    case ANUALMENTE -> t.plusYears(r.getIntervalValue());
    };
  }

  private boolean matchesRule(LocalDateTime date, RecorrenciaDTO r) {

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
                                                   LocalDateTime after) {

    return p
        .getRDates().stream().filter(d -> d.atZone(p.getZoneIdValue())
            .toLocalDateTime().isAfter(after))
        .min(LocalDateTime::compareTo).map(d -> {
          LocalDateTime start = d.atZone(p.getZoneIdValue())
              .toLocalDateTime();
          return new IntervaloTemporalDTO(start, start.plus(p.duration()));
        });
  }

  private int estimateOccurrenceIndex(LocalDateTime base,
                                      LocalDateTime candidate,
                                      RecorrenciaDTO r) {

    switch (r.getFrequency()) {
    case DIARIAMENTE:
      return (int) ChronoUnit.DAYS.between(base, candidate)
          / r.getIntervalValue();
    case SEMANALMENTE:
      return (int) ChronoUnit.WEEKS.between(base, candidate)
          / r.getIntervalValue();
    case MENSALMENTE:
      return (int) ChronoUnit.MONTHS.between(base, candidate)
          / r.getIntervalValue();
    case ANUALMENTE:
      return (int) ChronoUnit.YEARS.between(base, candidate)
          / r.getIntervalValue();
    }
    return 0;
  }

  public boolean intersects(PeriodicidadeDTO a, PeriodicidadeDTO b,
                            LocalDateTime windowStart,
                            LocalDateTime windowEnd) {

    LocalDateTime cursorA = windowStart;
    LocalDateTime cursorB = windowStart;

    while (true) {

      Optional<IntervaloTemporalDTO> nextA = nextOccurrence(a, cursorA);

      Optional<IntervaloTemporalDTO> nextB = nextOccurrence(b, cursorB);

      if (nextA.isEmpty() || nextB.isEmpty())
        return false;

      IntervaloTemporalDTO iA = nextA.get();
      IntervaloTemporalDTO iB = nextB.get();

      if (iA.intersects(iB))
        return true;

      if (iA.getStartTime().isBefore(iB.getStartTime())) {
        cursorA = iA.getEndTime();
      } else {
        cursorB = iB.getEndTime();
      }

      if (cursorA.isAfter(windowEnd) || cursorB.isAfter(windowEnd))
        return false;
    }
  }
}
