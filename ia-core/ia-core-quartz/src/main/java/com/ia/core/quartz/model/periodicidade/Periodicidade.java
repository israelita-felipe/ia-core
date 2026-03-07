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
 * Entidade que representa a periodicidade de um evento.
 * <p>
 * Armazena as informações de recorrência baseadas na RFC 5545 (iCalendar),
 * incluindo intervalo base, regra de recorrência, exclusões e datas de exceção.
 *
 * @author Israel Araújo
 * @see IntervaloTemporal
 * @see Recorrencia
 * @see ExclusaoRecorrencia
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

  /**
   * Intervalo temporal base para a periodicidade.
   * <p>
   * Define o período inicial (data/hora de início e fim) a partir do qual
   * as ocorrências serão calculadas. Este intervalo serve como referência
   * para o cálculo de todas as recorrências subseqüentes.
   *
   * @since 1.0.0
   * @see IntervaloTemporal
   */
  @Default
  @Embedded
  private IntervaloTemporal intervaloBase = new IntervaloTemporal();

  /**
   * Regra de recorrência principal (RRULE).
   * <p>
   * Define o padrão de recorrência do evento conforme especificação RFC 5545.
   * Esta regra determina quando e com que frequência o evento deve ocorrer.
   *
   * @since 1.0.0
   * @see Recorrencia
   */
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

  /**
   * Fuso horário utilizado para cálculo das ocorrências.
   * <p>
   * O fuso horário é essencial para o correto cálculo de recorrências,
   * especialmente em eventos que envolvem transições de horário de verão.
   * O valor padrão é o fuso horário do sistema onde a aplicação está rodando.
   *
   * @since 1.0.0
   * @see java.time.ZoneId
   */
  @Default
  @Column(name = "zone_id")
  private String zoneId = ZoneId.systemDefault().getId();

  /**
   * Datas de exceção - datas específicas a serem excluídas da recorrência.
   * <p>
   * Diferentemente da {@link ExclusaoRecorrencia} (que define padrões de exclusão),
   * este conjunto contém datas específicas que não devem gerar ocorrências.
   * Este campo é equivalente ao parâmetro EXDATE da RFC 5545.
   * <p>
   * Exemplo de uso:
   * <pre>
   * periodicidade.setExceptionDates(Set.of(
   *     LocalDate.of(2024, 12, 25),  // Natal
   *     LocalDate.of(2024, 1, 1)      // Ano Novo
   * ));
   * </pre>
   *
   * @since 1.0.0
   * @see <a href="https://tools.ietf.org/html/rfc5545#section-3.2.3">RFC 5545 Section 3.2.3</a>
   */
  @Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE_EXCEPTION_DATE", schema = SCHEMA_NAME)
  private Set<LocalDate> exceptionDates = new HashSet<>();

  /**
   * Datas de inclusão forçada.
   * <p>
   * Mesmo que uma data seja excluída por uma regra de recorrência ou exceção,
   * se estiver presente neste conjunto, será incluída nas ocorrências.
   * Este campo é equivalente ao parâmetro RDATE da RFC 5545.
   * <p>
   * Útil para adicionar ocorrências extras que não seguem o padrão de recorrência.
   *
   * @since 1.0.0
   * @see <a href="https://tools.ietf.org/html/rfc5545#section-3.2.3">RFC 5545 Section 3.2.3</a>
   */
  @Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "PERIODICIDADE_INCLUDE_DATE", schema = SCHEMA_NAME)
  private Set<LocalDate> includeDates = new HashSet<>();

  /**
   * Indica se a periodicidade está ativa.
   * <p>
   * Uma periodicidade inativa não será considerada no cálculo de ocorrências.
   * Este campo permite "desligar" uma periodicidade temporariamente sem
   * excluí-la do banco de dados.
   *
   * @since 1.0.0
   */
  @Default
  private Boolean ativo = Boolean.TRUE;

}
