package com.ia.core.quartz.model.periodicidade;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.quartz.model.QuartzModel;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Entity
@Table(name = Periodicidade.TABLE_NAME, schema = Periodicidade.SCHEMA_NAME)
@Getter
@Setter
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
  @Embedded
  private IntervaloTemporal intervaloBase = new IntervaloTemporal();

  @Default
  @Embedded
  private Recorrencia regra = new Recorrencia();

  /**
   * Regra de exclusão de recorrência (EXRULE).
   * <p>
   * Equivalente ao parâmetro EXRULE da RFC 5545 (iCalendar).
   * Define datas específicas a serem excluídas da recorrência principal.
   * <p>
   * Diferentemente de exceptionDates (que são datas específicas), a EXRULE
   * define regras de exclusão baseadas em padrões temporais.
   * <p>
   * Exemplo:
   * <pre>
   * RRULE: FREQ=WEEKLY;BYDAY=MO,WE,FR
   * EXRULE: FREQ=MONTHLY;BYMONTHDAY=1
   * → Toda segunda, quarta e sexta, exceto o primeiro dia de cada mês
   * </pre>
   */
  @Default
  @Embedded
  private ExclusaoRecorrencia exclusaoRecorrencia = new ExclusaoRecorrencia();

  @Default
  @Column(name = "zone_id")
  private String zoneId = ZoneId.systemDefault().getId();

  @Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE_EXCEPTION_DATE", schema = SCHEMA_NAME)
  private Set<LocalDate> exceptionDates = new HashSet<>();

  @Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE_INCLUDE_DATE", schema = SCHEMA_NAME)
  private Set<LocalDate> includeDates = new HashSet<>();

  @Default
  private Boolean ativo = Boolean.TRUE;

}
