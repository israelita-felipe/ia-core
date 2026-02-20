package com.ia.core.quartz.service;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import org.quartz.ScheduleBuilder;
import org.quartz.impl.triggers.AbstractTrigger;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.model.recorrencia.dto.OccurrenceCalculator;

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

    // Valida se o intervalo base existe
    if (periodicidade.getIntervaloBase() == null
        || periodicidade.getIntervaloBase().getStartDate() == null
        || periodicidade.getIntervaloBase().getStartTime() == null) {
      throw new IllegalArgumentException("Periodicidade deve ter um intervalo base com data/hora de início");
    }

    // Combina startDate e startTime para ZonedDateTime
    ZonedDateTime startDateTime = ZonedDateTime
        .of(periodicidade.getIntervaloBase().getStartDate(),
            periodicidade.getIntervaloBase().getStartTime(),
            periodicidade.getZoneIdValue());
    setStartTime(Date.from(startDateTime.toInstant()));
  }

  private OccurrenceCalculator calculator() {
    if (calculator == null) {
      calculator = OccurrenceCalculator.libRecurCalculator();
    }
    return calculator;
  }

  @Override
  public Date computeFirstFireTime(org.quartz.Calendar calendar) {

    ZoneId zone = periodicidade.getZoneIdValue();

    // Valida o intervalo base
    if (periodicidade.getIntervaloBase() == null
        || periodicidade.getIntervaloBase().getStartDate() == null
        || periodicidade.getIntervaloBase().getStartTime() == null) {
      return null;
    }

    // Começa 1 segundo antes do início para incluir a primeira ocorrência
    ZonedDateTime startDateTime = ZonedDateTime
        .of(periodicidade.getIntervaloBase().getStartDate(),
            periodicidade.getIntervaloBase().getStartTime(), zone);
    ZonedDateTime after = startDateTime.minusSeconds(1);

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty())
      return null;

    // Combina startDate e startTime para ZonedDateTime
    ZonedDateTime nextStart = ZonedDateTime
        .of(next.get().getStartDate(), next.get().getStartTime(), zone);
    Date nextDate = Date.from(nextStart.toInstant());

    if (calendar != null && !calendar.isTimeIncluded(nextDate.getTime())) {

      next = calculator().nextOccurrence(periodicidade, nextStart);

      if (next.isEmpty())
        return null;

      ZonedDateTime nextOccurrenceStart = ZonedDateTime
          .of(next.get().getStartDate(), next.get().getStartTime(), zone);
      nextDate = Date.from(nextOccurrenceStart.toInstant());
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

    ZoneId zone = periodicidade.getZoneIdValue();

    // Avança 1 segundo após o último fired time para obter a próxima ocorrência
    ZonedDateTime after = ZonedDateTime
        .ofInstant(previousFireTime.toInstant(), zone).plusSeconds(1);

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty()) {
      this.nextFireTime = null;
      return;
    }

    // Combina startDate e startTime para ZonedDateTime
    ZonedDateTime nextStart = ZonedDateTime
        .of(next.get().getStartDate(), next.get().getStartTime(), zone);
    Date nextDate = Date.from(nextStart.toInstant());

    if (calendar != null && !calendar.isTimeIncluded(nextDate.getTime())) {

      next = calculator().nextOccurrence(periodicidade, nextStart);

      ZonedDateTime nextOccurrenceStart = next.isPresent() ? ZonedDateTime
          .of(next.get().getStartDate(), next.get().getStartTime(), zone)
                                                           : null;
      nextDate = nextOccurrenceStart != null ? Date
          .from(nextOccurrenceStart.toInstant()) : null;
    }

    this.nextFireTime = nextDate;
  }

  @Override
  public void updateAfterMisfire(org.quartz.Calendar cal) {

    int instr = getMisfireInstruction();

    if (instr == MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY)
      return;

    ZoneId zone = periodicidade.getZoneIdValue();
    ZonedDateTime now = ZonedDateTime.now(zone);

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, now);

    ZonedDateTime nextStart = next.isPresent() ? ZonedDateTime
        .of(next.get().getStartDate(), next.get().getStartTime(), zone)
                                               : null;
    this.nextFireTime = nextStart != null ? Date.from(nextStart.toInstant())
                                          : null;
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

    if (periodicidade.getRegra() != null) {
      // Se tem UntilDate, usa ele
      if (periodicidade.getRegra().getUntilDate() != null) {
        // Usa o horário do intervalo base se disponível
        if (periodicidade.getIntervaloBase() != null
            && periodicidade.getIntervaloBase().getStartTime() != null) {
          return Date.from(periodicidade.getRegra().getUntilDate()
              .atTime(periodicidade.getIntervaloBase().getStartTime())
              .atZone(periodicidade.getZoneIdValue()).toInstant());
        }
        return Date.from(periodicidade.getRegra().getUntilDate()
            .atStartOfDay(periodicidade.getZoneIdValue()).toInstant());
      }

      // Se tem CountLimit, calcula a última ocorrência
      if (periodicidade.getRegra().getCountLimit() != null) {
        // Gera ocorrências para encontrar a última
        if (periodicidade.getIntervaloBase() != null) {
          ZonedDateTime start = ZonedDateTime
              .of(periodicidade.getIntervaloBase().getStartDate(),
                  periodicidade.getIntervaloBase().getStartTime(),
                  periodicidade.getZoneIdValue());

          var occurrences = calculator()
              .generateOccurrences(periodicidade, start,
                                   periodicidade.getRegra().getCountLimit()
                                       + 1);

          if (!occurrences.isEmpty()) {
            var lastOccurrence = occurrences.get(occurrences.size() - 1);
            ZonedDateTime lastStart = ZonedDateTime
                .of(lastOccurrence.getStartDate(),
                    lastOccurrence.getStartTime(),
                    periodicidade.getZoneIdValue());
            return Date.from(lastStart.toInstant());
          }
        }
      }
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

    ZoneId zone = periodicidade.getZoneIdValue();
    ZonedDateTime after = ZonedDateTime.ofInstant(afterTime.toInstant(),
                                                  zone);

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty()) {
      return null;
    }

    ZonedDateTime nextStart = next.isPresent() ? ZonedDateTime
        .of(next.get().getStartDate(), next.get().getStartTime(), zone)
                                               : null;
    if (nextStart == null) {
      return null;
    }
    if (getEndTime() != null
        && nextStart.toInstant().isAfter(getEndTime().toInstant())) {
      return null;
    }

    return Date.from(nextStart.toInstant());
  }

}
