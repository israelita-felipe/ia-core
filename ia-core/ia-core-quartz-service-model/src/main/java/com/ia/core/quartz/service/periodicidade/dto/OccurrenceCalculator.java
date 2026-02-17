package com.ia.core.quartz.service.periodicidade.dto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ia.core.quartz.model.periodicidade.Frequencia;

/**
 * Calculadora de ocorrências para eventos periódicos.
 * <p>
 * Responsável por calcular as próximas ocorrências de um evento
 * com base em sua regra de recorrência RFC 5545.
 *
 * @author Israel Araújo
 */
public class OccurrenceCalculator {

  public Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p, ZonedDateTime after) {
    return nextOccurrence(p, after, Integer.MAX_VALUE);
  }

  public Optional<IntervaloTemporalDTO> nextOccurrence(PeriodicidadeDTO p, ZonedDateTime after, int maxResults) {
    if (p == null || p.getIntervaloBase() == null) {
      return Optional.empty();
    }

    ZoneId zone = p.getZoneIdValue();
    IntervaloTemporalDTO baseInterval = p.getIntervaloBase();
    ZonedDateTime baseStart = ZonedDateTime.of(
        baseInterval.getStartDate(),
        baseInterval.getStartTime(),
        zone);

    // Verifica exception dates
    if (p.getExceptionDates() != null && p.getExceptionDates().contains(baseStart.toLocalDate())) {
      ZonedDateTime newBase = baseStart.plusDays(1);
      PeriodicidadeDTO adjustedP = p.toBuilder()
          .intervaloBase(p.getIntervaloBase().toBuilder()
              .startDate(newBase.toLocalDate())
              .build())
          .build();
      return nextOccurrence(adjustedP, after, maxResults);
    }

    // Verifica include dates (RDATE)
    Optional<IntervaloTemporalDTO> rdate = nextRDate(p, after);
    if (rdate.isPresent()) {
      return rdate;
    }

    // Sem recorrência
    if (!p.hasRecurrence()) {
      if (!baseStart.isBefore(after)) {
        return Optional.of(p.getIntervaloBase());
      }
      return Optional.empty();
    }

    RecorrenciaDTO r = p.getRegra();
    if (r == null || r.getFrequency() == null) {
      if (!baseStart.isBefore(after)) {
        return Optional.of(p.getIntervaloBase());
      }
      return Optional.empty();
    }

    int interval = r.getIntervalValue() != null ? r.getIntervalValue() : 1;

    // Limite de ocorrências
    final int effectiveLimit;
    if (r.getCountLimit() != null) {
      effectiveLimit = Math.min(r.getCountLimit(), maxResults);
    } else {
      effectiveLimit = maxResults;
    }

    // Começa a buscar a partir de 'after'
    ZonedDateTime searchFrom = after;
    
    // Se after é antes da base, começa da base
    if (searchFrom.isBefore(baseStart)) {
      searchFrom = baseStart;
    }

    // Calcula a duração do evento base para aplicar a cada ocorrência
    Duration duration = calculateDuration(baseInterval);

    // Para cada tipo de frequência, encontra a próxima ocorrência
    return findNextOccurrence(p, r, searchFrom, baseInterval, duration, interval, effectiveLimit);
  }

  /**
   * Encontra a próxima ocorrência baseada na frequência.
   */
  private Optional<IntervaloTemporalDTO> findNextOccurrence(PeriodicidadeDTO p, RecorrenciaDTO r,
      ZonedDateTime searchFrom, IntervaloTemporalDTO baseInterval, Duration duration,
      int interval, int maxIterations) {

    Frequencia freq = r.getFrequency();
    ZoneId zone = p.getZoneIdValue();

    // Loop para encontrar a próxima ocorrência válida
    ZonedDateTime candidate = searchFrom;
    int iterations = 0;

    while (iterations < maxIterations) {
      // Avança candidate para o próximo possível momento de ocorrência
      candidate = getNextCandidate(candidate, freq, r, baseInterval, interval);

      if (candidate == null) {
        return Optional.empty();
      }

      // Se candidate é antes de searchFrom, continua avançando
      while (candidate.isBefore(searchFrom)) {
        candidate = getNextCandidate(candidate, freq, r, baseInterval, interval);
        if (candidate == null) {
          return Optional.empty();
        }
      }

      // Verifica se candidate passou do limite de iterações
      iterations++;
      if (iterations > 10000) {
        return Optional.empty();
      }

      // Verifica exception dates
      if (p.getExceptionDates() != null && p.getExceptionDates().contains(candidate.toLocalDate())) {
        continue;
      }

      // Verifica untilDate - inclui a data até (até o final do dia)
      if (r.getUntilDate() != null) {
        LocalDate untilDate = r.getUntilDate();
        // Se candidate é depois do último dia válido, para
        if (candidate.toLocalDate().isAfter(untilDate)) {
          return Optional.empty();
        }
      }

      // Verifica se candidate corresponde aos filtros BY
      if (!matchesByFilters(candidate, r, baseInterval, zone)) {
        continue;
      }

      // Encontrou uma ocorrência válida!
      ZonedDateTime endDateTime = candidate.plus(duration);

      return Optional.of(new IntervaloTemporalDTO(
          candidate.toLocalDate(),
          candidate.toLocalTime(),
          endDateTime.toLocalDate(),
          endDateTime.toLocalTime()));
    }

    return Optional.empty();
  }

  /**
   * Obtém o próximo candidato para ocorrência baseado na frequência.
   */
  private ZonedDateTime getNextCandidate(ZonedDateTime current, Frequencia freq,
      RecorrenciaDTO r, IntervaloTemporalDTO baseInterval, int interval) {

    LocalTime eventTime = baseInterval.getStartTime() != null
        ? baseInterval.getStartTime()
        : LocalTime.of(0, 0);

    switch (freq) {
    case DIARIAMENTE:
    case DAILY:
      // Para diário: verificar se podemos usar o dia atual
      ZonedDateTime todayAtEventTime = current.toLocalDate().atTime(eventTime).atZone(current.getZone());
      // Se hoje é antes do horário do evento, usa hoje
      // Se hoje é depois do horário do evento, vai para o próximo dia
      if (todayAtEventTime.isBefore(current)) {
        // Já passou do horário de hoje, avanza para o próximo dia
        return current.toLocalDate().plusDays(1).atTime(eventTime).atZone(current.getZone());
      }
      return todayAtEventTime;

    case SEMANALMENTE:
    case WEEKLY:
      // Para semanal com byDay
      if (r.getByDay() != null && !r.getByDay().isEmpty()) {
        return getNextWeeklyWithByDay(current, r.getByDay(), eventTime, interval);
      }
      // Sem byDay: avançar semanas
      return current.plusWeeks(interval);

    case MENSALMENTE:
    case MONTHLY:
      // Para mensal com byDay e bySetPosition
      if (r.getByDay() != null && !r.getByDay().isEmpty() && r.getBySetPosition() != null) {
        return getNextMonthlyWithByDayAndSetPos(current, r.getByDay(), r.getBySetPosition(),
            eventTime, interval);
      }
      // Para mensal com byDay sem bySetPosition
      if (r.getByDay() != null && !r.getByDay().isEmpty()) {
        return getNextMonthlyWithByDay(current, r.getByDay(), eventTime, interval);
      }
      // Para mensal simples
      return current.plusMonths(interval);

    case ANUALMENTE:
    case YEARLY:
      return current.plusYears(interval);

    default:
      return current.plusDays(1);
    }
  }

  /**
   * Encontra o próximo candidato para frequência semanal com byDay.
   */
  private ZonedDateTime getNextWeeklyWithByDay(ZonedDateTime current, Set<DayOfWeek> byDays,
      LocalTime eventTime, int interval) {

    // Começa do dia atual com a hora do evento
    ZonedDateTime candidate = current.toLocalDate().atTime(eventTime).atZone(current.getZone());
    
    // Se candidate é antes de current, avanza para o próximo dia
    if (candidate.isBefore(current)) {
      candidate = candidate.plusDays(1);
    }
    
    // Avança até encontrar um dia que corresponda
    while (!byDays.contains(candidate.getDayOfWeek())) {
      candidate = candidate.plusDays(1);
    }
    
    return candidate;
  }

  /**
   * Encontra o próximo candidato para frequência mensal com byDay (sem bySetPosition).
   */
  private ZonedDateTime getNextMonthlyWithByDay(ZonedDateTime current, Set<DayOfWeek> byDays,
      LocalTime eventTime, int interval) {

    ZonedDateTime candidate = current.with(eventTime);

    // Se o dia atual corresponde, verifica se já passou
    if (byDays.contains(candidate.getDayOfWeek())) {
      if (candidate.isBefore(current) || candidate.isEqual(current)) {
        candidate = candidate.plusMonths(interval);
      }
    }

    // Avança até encontrar um dia que corresponda no mês
    while (!byDays.contains(candidate.getDayOfWeek())) {
      candidate = candidate.plusDays(1);
      // Se passou para o próximo mês, ajusta
      if (candidate.getDayOfMonth() < 7 && candidate.minusDays(7).getMonth() != candidate.getMonth()) {
        candidate = candidate.plusMonths(interval).withDayOfMonth(1);
      }
    }

    return candidate.with(eventTime);
  }

  /**
   * Encontra o próximo candidato para frequência mensal com byDay e bySetPosition.
   * Ex: "2ª terça-feira do mês" = byDay=TUESDAY, bySetPosition=2
   */
  private ZonedDateTime getNextMonthlyWithByDayAndSetPos(ZonedDateTime current,
      Set<DayOfWeek> byDays, Integer bySetPosition, LocalTime eventTime, int interval) {

    // Começa do primeiro dia do mês atual
    ZonedDateTime candidate = current.toLocalDate().atTime(eventTime).atZone(current.getZone());
    
    // Se candidate é antes de current, usa o primeiro dia do mês
    // caso contrário, ajusta para o primeiro dia do mês se estamos no início do mês
    if (candidate.isBefore(current) && candidate.getDayOfMonth() > 7) {
      // Já passamos dos primeiros dias do mês, vai para o próximo mês
      candidate = candidate.plusMonths(1).withDayOfMonth(1);
    } else if (candidate.getDayOfMonth() > 7) {
      // Estamos no meio do mês, ajusta para o primeiro dia
      candidate = candidate.withDayOfMonth(1);
    }

    // Encontra a enésima ocorrência do dia no mês
    int occurrence = 0;
    int maxDays = candidate.toLocalDate().lengthOfMonth();

    for (int day = 1; day <= maxDays; day++) {
      ZonedDateTime checkDate = candidate.withDayOfMonth(day);

      if (byDays.contains(checkDate.getDayOfWeek())) {
        occurrence++;
        if (occurrence == bySetPosition) {
          // Encontrou a posição correta
          return checkDate;
        }
      }
    }

    // Não encontrou no mês atual, tenta o próximo mês
    return getNextMonthlyWithByDayAndSetPos(
        candidate.plusMonths(1).withDayOfMonth(1),
        byDays, bySetPosition, eventTime, interval);
  }

  /**
   * Verifica se o candidato corresponde aos filtros BY.
   */
  private boolean matchesByFilters(ZonedDateTime candidate, RecorrenciaDTO r,
      IntervaloTemporalDTO baseInterval, ZoneId zone) {

    // Verifica byMonth
    if (r.getByMonth() != null && !r.getByMonth().isEmpty()) {
      if (!r.getByMonth().contains(candidate.getMonth())) {
        return false;
      }
    }

    // Verifica byMonthDay
    if (r.getByMonthDay() != null && !r.getByMonthDay().isEmpty()) {
      if (!r.getByMonthDay().contains(candidate.getDayOfMonth())) {
        return false;
      }
    }

    // Verifica byDay para frequências não tratadas especialmente
    if (r.getByDay() != null && !r.getByDay().isEmpty()) {
      Frequencia freq = r.getFrequency();
      // Para WEEKLY e MONTHLY com byDay, a verificação já foi feita nos métodos específicos
      boolean alreadyChecked = (freq == Frequencia.SEMANALMENTE || freq == Frequencia.WEEKLY
          || freq == Frequencia.MENSALMENTE || freq == Frequencia.MONTHLY);
      if (!alreadyChecked) {
        if (!r.getByDay().contains(candidate.getDayOfWeek())) {
          return false;
        }
      }
    }

    // Verifica byHour
    if (r.getByHour() != null && !r.getByHour().isEmpty()) {
      if (!r.getByHour().contains(candidate.getHour())) {
        return false;
      }
    }

    // Verifica byMinute
    if (r.getByMinute() != null && !r.getByMinute().isEmpty()) {
      if (!r.getByMinute().contains(candidate.getMinute())) {
        return false;
      }
    }

    return true;
  }

  /**
   * Calcula a duração do evento base.
   */
  private Duration calculateDuration(IntervaloTemporalDTO baseInterval) {
    LocalDateTime start = LocalDateTime.of(
        baseInterval.getStartDate(),
        baseInterval.getStartTime() != null ? baseInterval.getStartTime() : LocalTime.of(0, 0));

    LocalDateTime end;
    if (baseInterval.getEndDate() != null && baseInterval.getEndTime() != null) {
      end = LocalDateTime.of(baseInterval.getEndDate(), baseInterval.getEndTime());
    } else if (baseInterval.getEndTime() != null) {
      end = LocalDateTime.of(baseInterval.getStartDate(), baseInterval.getEndTime());
    } else {
      // Assume duração de 1 hora
      end = start.plusHours(1);
    }

    return Duration.between(start, end);
  }

  /**
   * Verifica a próxima data de inclusão (RDATE).
   */
  private Optional<IntervaloTemporalDTO> nextRDate(PeriodicidadeDTO p, ZonedDateTime after) {
    if (p.getIncludeDates() == null || p.getIncludeDates().isEmpty()) {
      return Optional.empty();
    }

    return p.getIncludeDates().stream()
        .filter(d -> {
          ZonedDateTime dateTime = d.atStartOfDay(p.getZoneIdValue());
          return dateTime.isAfter(after) || dateTime.isEqual(after);
        })
        .min(LocalDate::compareTo)
        .map(d -> {
          ZonedDateTime start = d.atStartOfDay(p.getZoneIdValue());
          Duration duration = p.duration();
          if (duration == null || duration.isZero()) {
            duration = Duration.ofHours(1);
          }
          ZonedDateTime end = start.plus(duration);
          return new IntervaloTemporalDTO(start.toLocalDate(), start.toLocalTime(),
              end.toLocalDate(), end.toLocalTime());
        });
  }

  /**
   * Gera uma lista de ocorrências futuras.
   */
  public List<IntervaloTemporalDTO> generateOccurrences(
      PeriodicidadeDTO periodicidade, ZonedDateTime after, int maxCount) {
    List<IntervaloTemporalDTO> occurrences = new ArrayList<>();

    if (periodicidade == null) {
      return occurrences;
    }

    // Limite efetivo
    int effectiveLimit = maxCount;
    if (periodicidade.getRegra() != null && periodicidade.getRegra().getCountLimit() != null) {
      effectiveLimit = Math.min(maxCount, periodicidade.getRegra().getCountLimit());
    }

    ZonedDateTime cursor = after;
    int count = 0;

    while (count < effectiveLimit) {
      Optional<IntervaloTemporalDTO> next = nextOccurrence(periodicidade, cursor);

      if (next.isEmpty()) {
        break;
      }

      IntervaloTemporalDTO occurrence = next.get();
      occurrences.add(occurrence);
      count++;

      // Move o cursor para após esta ocorrência
      ZonedDateTime endTime;
      if (occurrence.getEndDate() != null && occurrence.getEndTime() != null) {
        endTime = ZonedDateTime.of(occurrence.getEndDate(), occurrence.getEndTime(),
            periodicidade.getZoneIdValue());
      } else if (occurrence.getStartTime() != null) {
        endTime = ZonedDateTime.of(occurrence.getStartDate(), occurrence.getStartTime(),
            periodicidade.getZoneIdValue()).plusHours(1);
      } else {
        endTime = ZonedDateTime.of(occurrence.getStartDate(),
            LocalTime.MIN, periodicidade.getZoneIdValue()).plusDays(1);
      }

      cursor = endTime.plusMinutes(1);
    }

    return occurrences;
  }

  /**
   * Gera uma lista de ocorrências a partir de uma data.
   */
  public List<IntervaloTemporalDTO> generateOccurrences(
      PeriodicidadeDTO periodicidade, LocalDate startDate, int maxCount) {
    return generateOccurrences(periodicidade,
        startDate.atStartOfDay(periodicidade.getZoneIdValue()), maxCount);
  }

  /**
   * Gera uma lista de ocorrências dentro de um período.
   */
  public List<IntervaloTemporalDTO> generateOccurrences(
      PeriodicidadeDTO periodicidade, ZonedDateTime start, ZonedDateTime end) {
    List<IntervaloTemporalDTO> occurrences = new ArrayList<>();

    if (periodicidade == null || start == null || end == null) {
      return occurrences;
    }

    if (start.isAfter(end)) {
      return occurrences;
    }

    ZonedDateTime cursor = start;

    while (true) {
      Optional<IntervaloTemporalDTO> next = nextOccurrence(periodicidade, cursor);

      if (next.isEmpty()) {
        break;
      }

      IntervaloTemporalDTO occurrence = next.get();

      // Verifica se a ocorrência está dentro do período
      ZonedDateTime occStart = ZonedDateTime.of(
          occurrence.getStartDate(),
          occurrence.getStartTime(),
          periodicidade.getZoneIdValue());

      if (occStart.isAfter(end)) {
        break;
      }

      occurrences.add(occurrence);

      // Move o cursor para após esta ocorrência
      ZonedDateTime occEnd;
      if (occurrence.getEndDate() != null && occurrence.getEndTime() != null) {
        occEnd = ZonedDateTime.of(occurrence.getEndDate(), occurrence.getEndTime(),
            periodicidade.getZoneIdValue());
      } else if (occurrence.getStartTime() != null) {
        occEnd = ZonedDateTime.of(occurrence.getStartDate(),
            occurrence.getStartTime(), periodicidade.getZoneIdValue()).plusHours(1);
      } else {
        occEnd = occStart.plusHours(1);
      }

      cursor = occEnd.plusMinutes(1);
    }

    return occurrences;
  }

  /**
   * Verifica interseção entre dois eventos periódicos.
   */
  public boolean intersects(PeriodicidadeDTO a, PeriodicidadeDTO b,
      LocalDateTime windowStart, LocalDateTime windowEnd, ZoneId zoneId) {

    ZonedDateTime cursorA = windowStart.atZone(zoneId);
    ZonedDateTime cursorB = windowStart.atZone(zoneId);

    while (true) {
      Optional<IntervaloTemporalDTO> nextA = nextOccurrence(a, cursorA);
      Optional<IntervaloTemporalDTO> nextB = nextOccurrence(b, cursorB);

      if (nextA.isEmpty() || nextB.isEmpty()) {
        return false;
      }

      IntervaloTemporalDTO iA = nextA.get();
      IntervaloTemporalDTO iB = nextB.get();

      if (iA.intersects(iB)) {
        return true;
      }

      // Combina data e hora para LocalDateTime
      LocalDateTime startA = LocalDateTime.of(iA.getStartDate(), iA.getStartTime());
      LocalDateTime endA = iA.getEndDate() != null && iA.getEndTime() != null
          ? LocalDateTime.of(iA.getEndDate(), iA.getEndTime())
          : (iA.getStartTime() != null
              ? LocalDateTime.of(iA.getStartDate(), iA.getStartTime()).plusHours(1)
              : null);
      LocalDateTime startB = LocalDateTime.of(iB.getStartDate(), iB.getStartTime());
      LocalDateTime endB = iB.getEndDate() != null && iB.getEndTime() != null
          ? LocalDateTime.of(iB.getEndDate(), iB.getEndTime())
          : (iB.getStartTime() != null
              ? LocalDateTime.of(iB.getStartDate(), iB.getStartTime()).plusHours(1)
              : null);

      if (startA.isBefore(startB)) {
        cursorA = endA != null ? endA.atZone(zoneId) : startA.plusHours(1).atZone(zoneId);
      } else {
        cursorB = endB != null ? endB.atZone(zoneId) : startB.plusHours(1).atZone(zoneId);
      }

      if (cursorA.isAfter(windowEnd.atZone(zoneId))
          || cursorB.isAfter(windowEnd.atZone(zoneId))) {
        return false;
      }
    }
  }
}
