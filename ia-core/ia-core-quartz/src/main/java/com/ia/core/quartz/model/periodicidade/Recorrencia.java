package com.ia.core.quartz.model.periodicidade;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import com.ia.core.quartz.model.QuartzModel;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recorrencia
  implements Serializable {
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = QuartzModel.SCHEMA;

  @Column(name = "frequency")
  private Frequencia frequency;

  @Column(name = "interval_value")
  private Integer intervalValue;

  @CollectionTable(name = QuartzModel.TABLE_PREFIX + "RECORRENCIA_DIA",
                   schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = DayOfWeek.class)
  @Column(name = "by_day")
  private Set<DayOfWeek> byDay = new HashSet<>();

  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "RECORRENCIA_OCORRENCIA_DIARIA", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "by_month_day")
  private Set<Integer> byMonthDay = new HashSet<>();

  @CollectionTable(name = QuartzModel.TABLE_PREFIX + "RECORRENCIA_MES",
                   schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Month.class)
  @Column(name = "by_month")
  private Set<Month> byMonth = new HashSet<>();

  @Column(name = "by_set_position")
  private Integer bySetPosition;

  @Column(name = "until_date")
  private LocalDateTime untilDate;

  @Column(name = "count_limit")
  private Integer countLimit;

}
