package com.ia.core.quartz.model.periodicidade;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

/**
 * Representa a regra de exclusão de recorrência (EXRULE) de uma Periodicidade.
 * <p>
 * Modela conceitualmente a especificação EXRULE definida na RFC 5545
 * (iCalendar), permitindo a definição de exceções a uma regra de recorrência.
 * <p>
 * Esta classe é um Value Object no contexto DDD:
 * <ul>
 * <li>Não possui identidade própria</li>
 * <li>Depende de uma entidade agregadora (ex: Periodicidade)</li>
 * <li>É imutável do ponto de vista conceitual</li>
 * </ul>
 * <p>
 * A EXRULE funciona de forma similar à RRULE, mas em vez de definir quando
 * o evento ocorre, define datas específicas a serem excluídas da recorrência.
 * <p>
 * Exemplo de uso:
 * <pre>
 * RRULE: FREQ=WEEKLY;BYDAY=MO,WE,FR
 * EXRULE: FREQ=YEARLY;BYMONTH=12;BYMONTHDAY=25
 * → Todo dia de semana, exceto Natal
 * </pre>
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExclusaoRecorrencia
  implements Serializable {
  /** Serial UID */
  private static final long serialVersionUID = 1L;
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = QuartzModel.SCHEMA;

  /**
   * Frequência base da exclusão.
   * <p>
   * Equivalente ao parâmetro FREQ da RFC 5545.
   */
  @Column(name = "ex_frequency")
  private Frequencia frequency;

  /**
   * Intervalo multiplicador da frequência.
   * <p>
   * Equivalente ao parâmetro INTERVAL da RFC 5545.
   */
  @Column(name = "ex_interval_value")
  private Integer intervalValue;

  /**
   * Conjunto de dias da semana aplicados como filtro de exclusão.
   * <p>
   * Equivalente ao parâmetro BYDAY da RFC 5545.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "EXC_RECORRENCIA_DIA_SEMANA", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = DayOfWeek.class)
  @Column(name = "ex_by_day")
  private Set<DayOfWeek> byDay = new HashSet<>();

  /**
   * Conjunto de dias numéricos do mês para exclusão.
   * <p>
   * Equivalente ao parâmetro BYMONTHDAY da RFC 5545.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "EXC_RECORRENCIA_DIA_MES", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "ex_by_month_day")
  private Set<Integer> byMonthDay = new HashSet<>();

  /**
   * Conjunto de meses válidos para a exclusão.
   * <p>
   * Equivalente ao parâmetro BYMONTH da RFC 5545.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "EXC_RECORRENCIA_MES", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Month.class)
  @Column(name = "ex_by_month")
  private Set<Month> byMonth = new HashSet<>();

  /**
   * Posição específica dentro do conjunto de ocorrências para exclusão.
   * <p>
   * Equivalente ao parâmetro BYSETPOS da RFC 5545.
   */
  @Column(name = "ex_by_set_position")
  private Integer bySetPosition;

  /**
   * Data limite da exclusão.
   * <p>
   * Equivalente ao parâmetro UNTIL da RFC 5545.
   */
  @Column(name = "ex_until_date")
  private LocalDate untilDate;

  /**
   * Número máximo de exclusões permitidas.
   * <p>
   * Equivalente ao parâmetro COUNT da RFC 5545.
   */
  @Column(name = "ex_count_limit")
  private Integer countLimit;

  /**
   * Dia da semana que inicia a contagem da frequência semanal.
   * <p>
   * Equivalente ao parâmetro WKST da RFC 5545.
   */
  @Column(name = "ex_week_start_day")
  private DayOfWeek weekStartDay;

  /**
   * Conjunto de dias do ano (1-366) para exclusão.
   * <p>
   * Equivalente ao parâmetro BYYEARDAY da RFC 5545.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "EXC_RECORRENCIA_DIA_ANO", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "ex_by_year_day")
  private Set<Integer> byYearDay = new HashSet<>();

  /**
   * Conjunto de números de semana (1-53) para exclusão.
   * <p>
   * Equivalente ao parâmetro BYWEEKNO da RFC 5545.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "EXC_RECORRENCIA_SEMANA_ANO", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "ex_by_week_no")
  private Set<Integer> byWeekNo = new HashSet<>();

  /**
   * Conjunto de horas (0-23) para filtrar exclusões.
   * <p>
   * Equivalente ao parâmetro BYHOUR da RFC 5545.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "EXC_RECORRENCIA_HORA", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "ex_by_hour")
  private Set<Integer> byHour = new HashSet<>();

  /**
   * Conjunto de minutos (0-59) para filtrar exclusões.
   * <p>
   * Equivalente ao parâmetro BYMINUTE da RFC 5545.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "EXC_RECORRENCIA_MINUTO", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "ex_by_minute")
  private Set<Integer> byMinute = new HashSet<>();

  /**
   * Conjunto de segundos (0-59) para filtrar exclusões.
   * <p>
   * Equivalente ao parâmetro BYSECOND da RFC 5545.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "EXC_RECORRENCIA_SEGUNDO", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "ex_by_second")
  private Set<Integer> bySecond = new HashSet<>();
}
