package com.ia.core.quartz.model.periodicidade;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.quartz.model.QuartzModel;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Entity
@Table(name = Periodicidade.TABLE_NAME, schema = Periodicidade.SCHEMA_NAME)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Periodicidade
  extends BaseEntity {
  /** Serial UID */
  private static final long serialVersionUID = 8959576762018688241L;
  /** NOME DA TABELA */
  public static final String TABLE_NAME = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = QuartzModel.SCHEMA;

  @Default
  @Column(name = "ativo", nullable = false,
          columnDefinition = "BOOLEAN DEFAULT TRUE")
  private boolean ativo = true;
  @Default
  @Column(name = "dia_todo", nullable = false,
          columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean diaTodo = false;
  @Default
  @Column(name = "periodico", nullable = false,
          columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean periodico = false;
  @Default
  @Column(name = "intervalo_tempo", nullable = false,
          columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean intervaloTempo = false;

  @Column(name = "data_inicio")
  private LocalDate dataInicio;
  @Column(name = "hora_inicio")
  private LocalTime horaInicio;
  @Column(name = "data_fim")
  private LocalDate dataFim;
  @Column(name = "hora_fim")
  private LocalTime horaFim;
  @Column(name = "tempo_intervalo")
  private LocalTime tempoIntervalo;

  @Default
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE_DIA", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = DayOfWeek.class)
  @Column(name = "dia")
  private Set<DayOfWeek> dias = new HashSet<>();

  @Default
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE_MES", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Month.class)
  @Column(name = "mes")
  private Set<Month> meses = new HashSet<>();

  @Default
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE_OCORRENCIA_SEMANAL", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY,
                     targetClass = OcorrenciaSemanal.class)
  @Column(name = "semana")
  private Set<OcorrenciaSemanal> ocorrenciaSemanal = new HashSet<>();

  @Default
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE_OCORRENCIA_DIARIA", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "dia")
  private Set<Integer> ocorrenciaDiaria = new HashSet<>();
}
