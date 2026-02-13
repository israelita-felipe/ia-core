package com.ia.core.quartz.model.periodicidade;

import java.time.Duration;
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
import jakarta.persistence.Transient;
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

  @Default
  @Column(name = "zone_id")
  private String zoneId = ZoneId.systemDefault().getId();

  @Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = QuartzModel.TABLE_PREFIX + "PERIODICIDADE_EXCEPTION_DATE",
                   schema = SCHEMA_NAME)
  private Set<LocalDate> exceptionDates = new HashSet<>();

  @Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = QuartzModel.TABLE_PREFIX + "PERIODICIDADE_INCLUDE_DATE",
                   schema = SCHEMA_NAME)
  private Set<LocalDate> includeDates = new HashSet<>();
  @Default
  private Boolean ativo = Boolean.TRUE;

  @Transient
  public ZoneId getZoneIdValue() {
    return ZoneId.of(zoneId);
  }

  @Transient
  public Duration duration() {
    return intervaloBase.duration();
  }

  @Transient
  public boolean hasRecurrence() {
    return regra != null;
  }
}
