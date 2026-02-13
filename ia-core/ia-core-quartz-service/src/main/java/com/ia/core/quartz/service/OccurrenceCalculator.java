package com.ia.core.quartz.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ia.core.quartz.service.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.periodicidade.dto.RecorrenciaDTO;

public class OccurrenceCalculator {

  public Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p,
                                                       LocalDateTime after) {

    Optional<IntervaloTemporalDTO> rdate = nextIncludeDate(p, after);
    if (rdate.isPresent())
      return rdate;

    LocalTime baseTime = p.getIntervaloBase().getStartTime();

    if (!p.hasRecurrence()) {
      if (after.toLocalDate().atTime(baseTime).isAfter(after))
        return Optional.of(p.getIntervaloBase());
      return Optional.empty();
    }

    RecorrenciaDTO r = p.getRegra();

    LocalDateTime base = after.toLocalDate().atTime(baseTime);
    LocalDateTime periodCursor = fastForward(base, after, r);

    int generatedPeriods = estimateOccurrenceIndex(base, periodCursor, r);

    while (true) {

      if (r.getCountLimit() != null
          && generatedPeriods >= r.getCountLimit())
        return Optional.empty();

      if (r.getUntilDate() != null
          && periodCursor.isAfter(r.getUntilDate().atStartOfDay()))
        return Optional.empty();

      List<LocalDateTime> candidates = generateCandidatesForPeriod(periodCursor,
                                                                   r);

      candidates = applyBySetPos(candidates, r);

      for (LocalDateTime candidate : candidates) {

        if (candidate.isAfter(after)
            && !p.getExceptionDates().contains(candidate.toLocalDate())) {

          LocalTime startTime = p.getIntervaloBase().getStartTime();
          LocalTime endTime = p.getIntervaloBase().getEndTime();
          return Optional.of(new IntervaloTemporalDTO(startTime, endTime));
        }
      }

      periodCursor = increment(periodCursor, r);
      generatedPeriods++;
    }
  }

  private List<LocalDateTime> generateCandidatesForPeriod(LocalDateTime periodStart,
                                                          RecorrenciaDTO r) {

    List<LocalDateTime> list = new ArrayList<>();

    switch (r.getFrequency()) {

    case DIARIAMENTE -> {
      list.add(periodStart);
    }

    case SEMANALMENTE -> {
      LocalDate weekStart = periodStart.toLocalDate()
          .with(DayOfWeek.MONDAY);

      for (DayOfWeek dow : resolveByDayOrDefault(r)) {
        list.add(weekStart.with(dow).atTime(periodStart.toLocalTime()));
      }
    }

    case MENSALMENTE -> {
      YearMonth ym = YearMonth.from(periodStart);

      if (!r.getByMonthDay().isEmpty()) {
        for (Integer day : r.getByMonthDay()) {
          if (day <= ym.lengthOfMonth()) {
            list.add(ym.atDay(day).atTime(periodStart.toLocalTime()));
          }
        }
      }

      if (!r.getByDay().isEmpty()) {
        for (DayOfWeek dow : r.getByDay()) {
          for (int i = 1; i <= 5; i++) {
            try {
              LocalDate d = ym.atDay(1)
                  .with(TemporalAdjusters.dayOfWeekInMonth(i, dow));
              if (d.getMonth() == ym.getMonth())
                list.add(d.atTime(periodStart.toLocalTime()));
            } catch (Exception ignored) {
            }
          }
        }
      }
    }

    case ANUALMENTE -> {
      int year = periodStart.getYear();

      for (Month m : resolveByMonthOrDefault(r)) {
        YearMonth ym = YearMonth.of(year, m);

        if (!r.getByMonthDay().isEmpty()) {
          for (Integer day : r.getByMonthDay()) {
            if (day <= ym.lengthOfMonth()) {
              list.add(ym.atDay(day).atTime(periodStart.toLocalTime()));
            }
          }
        }
      }
    }
    }

    list.sort(LocalDateTime::compareTo);
    return list;
  }

  private List<LocalDateTime> applyBySetPos(List<LocalDateTime> candidates,
                                            RecorrenciaDTO r) {

    if (r.getBySetPosition() == null)
      return candidates;

    int pos = r.getBySetPosition();

    if (candidates.isEmpty())
      return List.of();

    if (pos > 0) {
      if (pos <= candidates.size())
        return List.of(candidates.get(pos - 1));
    } else {
      int index = candidates.size() + pos;
      if (index >= 0)
        return List.of(candidates.get(index));
    }

    return List.of();
  }

  private Set<DayOfWeek> resolveByDayOrDefault(RecorrenciaDTO r) {
    return r.getByDay().isEmpty() ? Set.of(DayOfWeek.of(1)) : r.getByDay();
  }

  private Set<Month> resolveByMonthOrDefault(RecorrenciaDTO r) {
    return r.getByMonth().isEmpty() ? Set.of(Month.of(1)) : r.getByMonth();
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

  private Optional<IntervaloTemporalDTO> nextIncludeDate(PeriodicidadeDTO p,
                                                         LocalDateTime after) {

    LocalTime startTime = p.getIntervaloBase().getStartTime();
    LocalTime endTime = p.getIntervaloBase().getEndTime();

    return p.getIncludeDates().stream().filter(d -> d
        .atStartOfDay(p.getZoneIdValue()).toLocalDateTime().isAfter(after))
        .min(LocalDate::compareTo).map(d -> {
          return new IntervaloTemporalDTO(startTime, endTime);
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

      LocalTime startA = iA.getStartTime();
      LocalTime startB = iB.getStartTime();

      if (startA.isBefore(startB)) {
        cursorA = cursorA.plusDays(1).with(iA.getEndTime());
      } else {
        cursorB = cursorB.plusDays(1).with(iB.getEndTime());
      }

      if (cursorA.isAfter(windowEnd) || cursorB.isAfter(windowEnd))
        return false;
    }
  }
}
