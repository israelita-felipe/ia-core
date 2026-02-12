package com.ia.core.quartz.service;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
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
      LocalDateTime startTime = intervaloBase.getStartTime();
      if (startTime != null) {
        setStartTime(toDate(startTime));
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

    LocalDateTime after = periodicidade.getIntervaloBase().getStartTime()
        .minusSeconds(1);

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty())
      return null;

    Date nextDate = toDate(next.get().getStartTime());

    if (calendar != null && !calendar.isTimeIncluded(nextDate.getTime())) {

      next = calculator().nextOccurrence(periodicidade,
                                         next.get().getStartTime());

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

      next = calculator().nextOccurrence(periodicidade,
                                         next.get().getStartTime());

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

    this.nextFireTime = next.map(i -> toDate(i.getStartTime()))
        .orElse(null);
  }

  @Override
  public void updateWithNewCalendar(org.quartz.Calendar cal,
                                    long misfireThreshold) {

    if (nextFireTime == null)
      return;

    if (!cal.isTimeIncluded(nextFireTime.getTime())) {
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

      return toDate(periodicidade.getRegra().getUntilDate());
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

    LocalDateTime nextStart = next.get().getStartTime();

    if (getEndTime() != null
        && toInstant(nextStart).isAfter(getEndTime().toInstant())) {
      return null;
    }

    return toDate(nextStart);
  }

  /**
   * Converte {@link LocalDateTime} para {@link Date} usando o fuso horário
   * padrão do sistema.
   *
   * @param localDateTime o objeto LocalDateTime a ser convertido (pode ser
   *                      {@code null})
   * @return o objeto Date correspondente, ou {@code null} se a entrada for
   *         {@code null}
   */
  public Date toDate(LocalDateTime localDateTime) {
    if (localDateTime == null) {
      return null;
    }
    return Date.from(localDateTime
        .atZone(this.periodicidade.getZoneIdValue()).toInstant());
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
