package com.ia.core.quartz.service;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

import org.quartz.ScheduleBuilder;
import org.quartz.impl.triggers.AbstractTrigger;

import com.ia.core.quartz.service.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;

import lombok.Getter;
import lombok.Setter;

public class PeriodicidadeTrigger
  extends AbstractTrigger<PeriodicidadeTrigger>
  implements Serializable {

  private transient OccurrenceCalculator calculator;

  @Getter
  private PeriodicidadeDTO periodicidade;

  @Getter
  @Setter
  private Date nextFireTime;

  @Getter
  @Setter
  private Date previousFireTime;

  @Getter
  @Setter
  private Date startTime;
  @Getter
  @Setter
  private Date endTime;

  public PeriodicidadeTrigger(PeriodicidadeDTO periodicidade) {
    this.periodicidade = periodicidade;
    this.calculator = new OccurrenceCalculator();
    IntervaloTemporalDTO intervaloBase = periodicidade.getIntervaloBase();
    if (intervaloBase != null) {
      LocalTime startTime = intervaloBase.getStartTime();
      if (startTime != null) {
        setStartTime(toDate(LocalDate.now(periodicidade.getZoneIdValue()),
                            startTime));
      }
    }
  }

  private OccurrenceCalculator calculator() {
    if (calculator == null) {
      calculator = new OccurrenceCalculator();
    }
    return calculator;
  }

  @Override
  public Date computeFirstFireTime(org.quartz.Calendar calendar) {

    LocalDateTime after = LocalDate.now(periodicidade.getZoneIdValue())
        .atTime(periodicidade.getIntervaloBase().getStartTime())
        .minusSeconds(1);

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty())
      return null;

    Date nextDate = toDate(next.get().getStartTime());

    if (calendar != null && !calendar.isTimeIncluded(nextDate.getTime())) {

      LocalDateTime nextCursor = next.get()
          .getStartTime() == null ? after.plusDays(1)
                                  : after.toLocalDate()
                                      .atTime(next.get().getStartTime());

      next = calculator().nextOccurrence(periodicidade, nextCursor);

      if (next.isEmpty())
        return null;

      nextDate = toDate(next.get().getStartTime());
    }

    this.nextFireTime = nextDate;
    return nextDate;
  }

  @Override
  public void triggered(org.quartz.Calendar calendar) {

    this.previousFireTime = this.nextFireTime;

    if (previousFireTime == null) {
      return;
    }

    LocalDateTime after = LocalDateTime
        .ofInstant(previousFireTime.toInstant(),
                   periodicidade.getZoneIdValue());

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty()) {
      this.nextFireTime = null;
      return;
    }

    Date nextDate = toDate(next.get().getStartTime());

    if (calendar != null && !calendar.isTimeIncluded(nextDate.getTime())) {

      LocalDateTime nextCursor = next.get()
          .getStartTime() == null ? after.plusDays(1)
                                  : after.toLocalDate()
                                      .atTime(next.get().getStartTime());

      next = calculator().nextOccurrence(periodicidade, nextCursor);

      nextDate = next.map(i -> toDate(i.getStartTime())).orElse(null);
    }

    this.nextFireTime = nextDate;
  }

  @Override
  public void updateAfterMisfire(org.quartz.Calendar cal) {

    int instr = getMisfireInstruction();

    if (instr == MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY)
      return;

    LocalDateTime now = LocalDateTime.now(periodicidade.getZoneIdValue());

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, now);

    LocalDate today = LocalDate.now(periodicidade.getZoneIdValue());
    this.nextFireTime = next.map(i -> toDate(today, i.getStartTime()))
        .orElse(null);
  }

  @Override
  public void updateWithNewCalendar(org.quartz.Calendar cal,
                                    long misfireThreshold) {

    if (nextFireTime == null)
      return;

    while (nextFireTime != null
        && !cal.isTimeIncluded(nextFireTime.getTime())) {
      triggered(cal);
    }
  }

  @Override
  public boolean mayFireAgain() {
    return nextFireTime != null;
  }

  @Override
  public Date getFinalFireTime() {

    if (periodicidade.getRegra() != null
        && periodicidade.getRegra().getUntilDate() != null) {

      return Date.from(periodicidade.getRegra().getUntilDate()
          .atStartOfDay(periodicidade.getZoneIdValue()).toInstant());
    }

    return null;
  }

  @Override
  protected boolean validateMisfireInstruction(int candidateMisfireInstruction) {

    return candidateMisfireInstruction == MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
        || candidateMisfireInstruction == MISFIRE_INSTRUCTION_SMART_POLICY;
  }

  @Override
  public ScheduleBuilder<PeriodicidadeTrigger> getScheduleBuilder() {

    return new PeriodicidadeScheduleBuilder(periodicidade);
  }

  @Override
  public Date getFireTimeAfter(Date afterTime) {

    if (afterTime == null) {
      return getNextFireTime();
    }

    if (getEndTime() != null && afterTime.after(getEndTime())) {
      return null;
    }

    LocalDateTime after = LocalDateTime
        .ofInstant(afterTime.toInstant(), periodicidade.getZoneIdValue());

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty()) {
      return null;
    }

    LocalDateTime nextStart = LocalDate.now(periodicidade.getZoneIdValue())
        .atTime(next.get().getStartTime());

    if (getEndTime() != null
        && toInstant(nextStart).isAfter(getEndTime().toInstant())) {
      return null;
    }

    return toDate(next.get().getStartTime());
  }

  /**
   * Converte {@link LocalTime} para {@link Date} usando o fuso horário padrão
   * do sistema.
   *
   * @param date a data
   * @param time o objeto LocalTime a ser convertido (pode ser {@code null})
   * @return o objeto Date correspondente, ou {@code null} se a entrada for
   *         {@code null}
   */
  public Date toDate(LocalDate date, LocalTime time) {
    if (time == null) {
      return null;
    }
    return Date.from(date.atTime(time)
        .atZone(this.periodicidade.getZoneIdValue()).toInstant());
  }

  /**
   * Converte {@link LocalTime} para {@link Date} usando a data atual.
   *
   * @param localTime o objeto LocalTime a ser convertido (pode ser
   *                  {@code null})
   * @return o objeto Date correspondente, ou {@code null} se a entrada for
   *         {@code null}
   */
  public Date toDate(LocalTime localTime) {
    if (localTime == null) {
      return null;
    }
    return Date.from(LocalDate.now(this.periodicidade.getZoneIdValue())
        .atTime(localTime).atZone(this.periodicidade.getZoneIdValue())
        .toInstant());
  }

  /**
   * Converte {@link LocalDateTime} para {@link Instant} usando um fuso horário
   * específico.
   *
   * @param localDateTime o objeto LocalDateTime a ser convertido (pode ser
   * @return o objeto Instant correspondente, ou {@code null} se a entrada for
   *         {@code null}
   * @throws NullPointerException se {@code zoneId} for nulo e
   *                              {@code localDateTime} não for nulo
   */
  public Instant toInstant(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return localDateTime.atZone(this.periodicidade.getZoneIdValue())
        .toInstant();
  }
}
