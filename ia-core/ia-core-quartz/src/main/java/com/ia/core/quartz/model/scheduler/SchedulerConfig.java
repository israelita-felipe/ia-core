package com.ia.core.quartz.model.scheduler;

import com.ia.core.model.BaseEntity;
import com.ia.core.quartz.model.QuartzModel;
import com.ia.core.quartz.model.periodicidade.Periodicidade;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

/**
 * Entidade que armazena a configuração de agendamento de jobs.
 * <p>
 * Cada SchedulerConfig contém uma referência a uma classe de job e sua periodicidade
 * associada, permitindo o agendamento de tarefas recorrentes. Esta entidade é
 * utilizada para gerenciar jobs do Quartz de forma persistida.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see Periodicidade
 * @see com.ia.core.quartz.service.SchedulerConfigService
 */
@Entity
@Table(name = SchedulerConfig.TABLE_NAME,
       schema = SchedulerConfig.SCHEMA_NAME)
@NamedEntityGraph(name = "SchedulerConfig.withPeriodicidade",
                  attributeNodes = @NamedAttributeNode("periodicidade"))
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SchedulerConfig
  extends BaseEntity {

  /** Nome da tabela no banco de dados */
  public static final String TABLE_NAME = QuartzModel.TABLE_PREFIX
      + "SCHEDULER_CONFIG";
  /** Nome do schema no banco de dados */
  public static final String SCHEMA_NAME = QuartzModel.SCHEMA;

  /**
   * Nome completo da classe do job a ser executado.
   * <p>
   * Este valor deve ser o nome completamente qualificado da classe que
   * implementa a lógica do job. A classe deve implementar a interface
   * {@code org.quartz.Job} do framework Quartz.
   *
   * @since 1.0.0
   */
  @Column(name = "JOB_CLASS_NAME", nullable = false, unique = true)
  private String jobClassName;

  /**
   * Periodicidade associada ao job.
   * <p>
   * Define a frequência e padrão de recorrência para execução do job.
   * A periodicidade segue a especificação RFC 5545 (iCalendar) e controla
   * quando o job será disparado pelo scheduler.
   *
   * @since 1.0.0
   * @see Periodicidade
   */
  @Default
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            optional = false, orphanRemoval = true,
            targetEntity = Periodicidade.class)
  @JoinColumn(name = "periodicidade", referencedColumnName = "id")
  private Periodicidade periodicidade = new Periodicidade();

}
