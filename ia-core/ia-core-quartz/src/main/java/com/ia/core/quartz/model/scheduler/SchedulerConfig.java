package com.ia.core.quartz.model.scheduler;

import com.ia.core.model.BaseEntity;
import com.ia.core.quartz.model.QuartzModel;
import com.ia.core.quartz.model.periodicidade.Periodicidade;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

  /** NOME DA TABELA */
  public static final String TABLE_NAME = QuartzModel.TABLE_PREFIX
      + "SCHEDULER_CONFIG";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = QuartzModel.SCHEMA;

  @Column(name = "JOB_CLASS_NAME", nullable = false, unique = true)
  private String jobClassName;

  /**
   * Periodicidade
   */
  @Default
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            optional = false, orphanRemoval = true,
            targetEntity = Periodicidade.class)
  @JoinColumn(name = "periodicidade", referencedColumnName = "id")
  private Periodicidade periodicidade = new Periodicidade();

}
