package com.ia.core.quartz.model.scheduler;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.ia.core.quartz.model.QuartzModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = SchedulerConfigTrigger.TABLE_NAME,
       schema = SchedulerConfigTrigger.SCHEMA_NAME)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SchedulerConfigTrigger
  implements Serializable, Comparable<SchedulerConfigTrigger> {

  /** NOME DA TABELA */
  public static final String TABLE_NAME = QuartzModel.TABLE_PREFIX
      + "TRIGGERS";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = QuartzModel.SCHEMA;

  /**
   * Id da classe de entidade.
   */
  @Id
  @Column(name = "TRIGGER_NAME")
  private UUID triggerName;

  @Column(name = "SCHED_NAME")
  private String schedulerName;

  @Column(name = "TRIGGER_GROUP")
  private String triggerGroup;
  @Column(name = "JOB_NAME")
  private String jobName;
  @Column(name = "JOB_GROUP")
  private String jobGroup;
  @Column(name = "DESCRIPTION")
  private String description;
  @Column(name = "NEXT_FIRE_TIME")
  private String nextFireTime;
  @Column(name = "PREV_FIRE_TIME")
  private String prevFireTime;
  @Column(name = "PRIORITY")
  private String priority;
  @Column(name = "TRIGGER_STATE")
  private String triggerState;
  @Column(name = "TRIGGER_TYPE")
  private String triggerType;
  @Column(name = "START_TIME")
  private String triggerStartTime;
  @Column(name = "END_TIME")
  private String endTime;
  @Column(name = "CALENDAR_NAME")
  private String calendarName;
  @Column(name = "MISFIRE_INSTR")
  private String misFireInstr;
  @Column(name = "JOB_DATA")
  private String jobData;

  @Override
  public int compareTo(SchedulerConfigTrigger o) {
    if (this.triggerName == null) {
      if (o.triggerName == null) {
        return 0;
      }
      return -1;
    }
    if (o.triggerName == null) {
      return 1;
    }
    return this.triggerName.compareTo(o.triggerName);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (triggerName == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    SchedulerConfigTrigger other = (SchedulerConfigTrigger) obj;
    return Objects.equals(triggerName, other.triggerName);
  }

  @Override
  public int hashCode() {
    if (triggerName != null) {
      return Objects.hash(triggerName);
    }
    return super.hashCode();
  }

}
