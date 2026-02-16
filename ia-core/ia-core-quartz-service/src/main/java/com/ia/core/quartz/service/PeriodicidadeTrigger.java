package com.ia.core.quartz.service;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import org.quartz.ScheduleBuilder;
import org.quartz.impl.triggers.AbstractTrigger;

import com.ia.core.quartz.service.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.periodicidade.dto.OccurrenceCalculator;
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
    
    // Valida se o intervalo base existe
    if (periodicidade.getIntervaloBase() == null 
        || periodicidade.getIntervaloBase().getStartDateTime() == null) {
      throw new IllegalArgumentException(
          "Periodicidade deve ter um intervalo base com data/hora de início");
    }
    
    setStartTime(Date
        .from(periodicidade.getIntervaloBase().getStartDateTime()
            .atZone(periodicidade.getZoneIdValue()).toInstant()));
  }

  private OccurrenceCalculator calculator() {
    if (calculator == null) {
      calculator = new OccurrenceCalculator();
    }
    return calculator;
  }

  @Override
  public Date computeFirstFireTime(org.quartz.Calendar calendar) {

    ZoneId zone = periodicidade.getZoneIdValue();
    
    // Valida o intervalo base
    if (periodicidade.getIntervaloBase() == null 
        || periodicidade.getIntervaloBase().getStartDateTime() == null) {
      return null;
    }
    
    // Começa 1 segundo antes do início para incluir a primeira ocorrência
    ZonedDateTime startDateTime = periodicidade.getIntervaloBase()
        .getStartDateTime().atZone(zone);
    ZonedDateTime after = startDateTime.minusSeconds(1);

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty())
      return null;

    Date nextDate = Date
        .from(next.get().getStartDateTime().atZone(zone).toInstant());

    if (calendar != null && !calendar.isTimeIncluded(nextDate.getTime())) {

      next = calculator()
          .nextOccurrence(periodicidade,
                          next.get().getStartDateTime().atZone(zone));

      if (next.isEmpty())
        return null;

      nextDate = Date
          .from(next.get().getStartDateTime().atZone(zone).toInstant());
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
        .ofInstant(previousFireTime.toInstant(), zone)
        .plusSeconds(1);

    Optional<IntervaloTemporalDTO> next = calculator()
        .nextOccurrence(periodicidade, after);

    if (next.isEmpty()) {
      this.nextFireTime = null;
      return;
    }

    Date nextDate = Date
        .from(next.get().getStartDateTime().atZone(zone).toInstant());

    if (calendar != null && !calendar.isTimeIncluded(nextDate.getTime())) {

      next = calculator()
          .nextOccurrence(periodicidade,
                          next.get().getStartDateTime().atZone(zone));

      nextDate = next
          .map(i -> Date
              .from(i.getStartDateTime().atZone(zone).toInstant()))
          .orElse(null);
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

    this.nextFireTime = next
        .map(i -> Date.from(i.getStartDateTime().atZone(zone).toInstant()))
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

    if (periodicidade.getRegra() != null) {
      // Se tem UntilDate, usa ele
      if (periodicidade.getRegra().getUntilDate() != null) {
        // Usa o horário do intervalo base se disponível
        if (periodicidade.getIntervaloBase() != null 
            && periodicidade.getIntervaloBase().getStartDateTime() != null) {
          return Date.from(periodicidade.getRegra().getUntilDate()
              .atTime(periodicidade.getIntervaloBase().getStartDateTime()
                  .toLocalTime())
              .atZone(periodicidade.getZoneIdValue()).toInstant());
        }
        return Date.from(periodicidade.getRegra().getUntilDate()
            .atStartOfDay(periodicidade.getZoneIdValue()).toInstant());
      }
      
      // Se tem CountLimit, calcula a última ocorrência
      if (periodicidade.getRegra().getCountLimit() != null) {
        // Gera ocorrências para encontrar a última
        if (periodicidade.getIntervaloBase() != null) {
          ZonedDateTime start = periodicidade.getIntervaloBase()
              .getStartDateTime()
              .atZone(periodicidade.getZoneIdValue());
          
          var occurrences = calculator()
              .generateOccurrences(periodicidade, start, 
                  periodicidade.getRegra().getCountLimit() + 1);
          
          if (!occurrences.isEmpty()) {
            var lastOccurrence = occurrences.get(occurrences.size() - 1);
            return Date.from(lastOccurrence.getStartDateTime()
                .atZone(periodicidade.getZoneIdValue()).toInstant());
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

    ZonedDateTime nextStart = next.get().getStartDateTime().atZone(zone);

    if (getEndTime() != null
        && nextStart.toInstant().isAfter(getEndTime().toInstant())) {
      return null;
    }

    return Date.from(nextStart.toInstant());
  }

}
