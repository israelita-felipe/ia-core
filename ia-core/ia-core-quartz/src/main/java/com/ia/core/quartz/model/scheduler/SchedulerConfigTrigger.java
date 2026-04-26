package com.ia.core.quartz.model.scheduler;

import com.ia.core.quartz.model.QuartzModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade que representa um trigger do Quartz.
 * <p>
 * Armazena as informações de execução de um job agendado, incluindo
 * nome do trigger, scheduler, grupo, tempos de execução anterior e próximo,
 * estado e prioridade.
 *
 * @author Israel Araújo
 */
@Entity
@Table(name = SchedulerConfigTrigger.TABLE_NAME,
       schema = SchedulerConfigTrigger.SCHEMA_NAME)
@Getter
@Setter
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
  /**
   * Nome único do trigger.
   */
  @Id
  @Column(name = "TRIGGER_NAME")
  private UUID triggerName;

  /**
   * Nome do scheduler ao qual este trigger pertence.
   */
  @Column(name = "SCHED_NAME")
  private String schedulerName;

  /**
   * Grupo do trigger para organização.
   */
  @Column(name = "TRIGGER_GROUP")
  private String triggerGroup;

  /**
   * Nome do job associado a este trigger.
   */
  @Column(name = "JOB_NAME")
  private String jobName;

  /**
   * Grupo do job associado.
   */
  @Column(name = "JOB_GROUP")
  private String jobGroup;

  /**
   * Descrição opcional do trigger.
   */
  @Column(name = "DESCRIPTION")
  private String description;

  /**
   * Próxima data/hora de execução em formato string.
   */
  @Column(name = "NEXT_FIRE_TIME")
  private String nextFireTime;

  /**
   * Data/hora da execução anterior em formato string.
   */
  @Column(name = "PREV_FIRE_TIME")
  private String prevFireTime;

  /**
   * Prioridade do trigger.
   */
  @Column(name = "PRIORITY")
  private String priority;

  /**
   * Estado atual do trigger (ex: PAUSED, NORMAL, COMPLETE).
   */
  @Column(name = "TRIGGER_STATE")
  private String triggerState;

  /**
   * Tipo do trigger (ex: CRON, SIMPLE).
   */
  @Column(name = "TRIGGER_TYPE")
  private String triggerType;

  /**
   * Data/hora de início do trigger.
   */
  @Column(name = "START_TIME")
  private String triggerStartTime;

  /**
   * Data/hora de fim do trigger.
   */
  @Column(name = "END_TIME")
  private String endTime;

  /**
   * Nome do calendário associado ao trigger.
   */
  @Column(name = "CALENDAR_NAME")
  private String calendarName;

  /**
   * Instrução de tratamento de missed firings (misfire).
   */
  @Column(name = "MISFIRE_INSTR")
  private String misFireInstr;

  /**
   * Dados serializados do job.
   */
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
