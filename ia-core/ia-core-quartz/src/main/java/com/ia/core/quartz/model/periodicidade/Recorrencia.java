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
 * Representa a regra de recorrência de uma Periodicidade.
 * <p>
 * Modela conceitualmente a especificação RRULE definida na RFC 5545
 * (iCalendar), permitindo a definição de eventos recorrentes com filtros
 * temporais.
 * <p>
 * Esta classe é um Value Object no contexto DDD:
 * <ul>
 * <li>Não possui identidade própria</li>
 * <li>Depende de uma entidade agregadora (ex: Periodicidade)</li>
 * <li>É imutável do ponto de vista conceitual (apesar de setters técnicos)</li>
 * </ul>
 * <p>
 * Ordem lógica de aplicação das regras:
 * <ol>
 * <li>FREQ + INTERVAL (expansão base)</li>
 * <li>BYMONTH</li>
 * <li>BYMONTHDAY</li>
 * <li>BYDAY</li>
 * <li>BYSETPOS</li>
 * <li>COUNT ou UNTIL (limitação)</li>
 * </ol>
 * <p>
 * Compatível com a maioria dos cenários corporativos de recorrência mensal,
 * semanal e anual.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recorrencia
  implements Serializable {
  /** Serial UID */
  private static final long serialVersionUID = 1L;
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = QuartzModel.SCHEMA;
  /**
   * Frequência base da recorrência.
   * <p>
   * Equivalente ao parâmetro FREQ da RFC 5545.
   * <p>
   * Define a unidade primária de repetição:
   * <ul>
   * <li>DIARIAMENTE</li>
   * <li>SEMANALMENTE</li>
   * <li>MENSALMENTE</li>
   * <li>ANUALMENTE</li>
   * </ul>
   * Toda a geração de ocorrências parte dessa definição.
   */
  @Column(name = "frequency")
  private Frequencia frequency;
  /**
   * Intervalo multiplicador da frequência.
   * <p>
   * Equivalente ao parâmetro INTERVAL da RFC 5545.
   * <p>
   * Define o espaçamento entre ocorrências base:
   * <ul>
   * <li>INTERVAL=2 com FREQ=SEMANALMENTE → a cada 2 semanas</li>
   * <li>INTERVAL=3 com FREQ=MENSALMENTE → a cada 3 meses</li>
   * </ul>
   * Valor padrão implícito: 1.
   */
  @Column(name = "interval_value")
  private Integer intervalValue;
  /**
   * Conjunto de dias da semana aplicados como filtro.
   * <p>
   * Equivalente ao parâmetro BYDAY da RFC 5545.
   * <p>
   * Interpretação depende da frequência:
   * <ul>
   * <li>FREQ=SEMANALMENTE → dias válidos na semana</li>
   * <li>FREQ=MENSALMENTE → dias da semana dentro do mês</li>
   * <li>FREQ=ANUALMENTE → dias da semana dentro do ano</li>
   * </ul>
   * Exemplo:
   *
   * <pre>
   * FREQ=MONTHLY;BYDAY=TU
   * → todas as terças-feiras do mês
   * </pre>
   *
   * Observação: a RFC permite ordinal (ex: 2TU), o que exigiria modelagem
   * adicional.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX
      + "RECORRENCIA_DIA_SEMANA", schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = DayOfWeek.class)
  @Column(name = "by_day")
  private Set<DayOfWeek> byDay = new HashSet<>();
  /**
   * Conjunto de dias numéricos do mês.
   * <p>
   * Equivalente ao parâmetro BYMONTHDAY da RFC 5545.
   * <p>
   * Define dias específicos do mês:
   * <ul>
   * <li>1 → dia 1</li>
   * <li>15 → dia 15</li>
   * <li>31 → último dia se existir</li>
   * </ul>
   * <p>
   * A RFC também permite valores negativos:
   * <ul>
   * <li>-1 → último dia do mês</li>
   * <li>-2 → penúltimo dia</li>
   * </ul>
   * Caso deseje compatibilidade total com RFC, suporte a negativos deve ser
   * implementado.
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX + "RECORRENCIA_DIA_MES",
                   schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Integer.class)
  @Column(name = "by_month_day")
  private Set<Integer> byMonthDay = new HashSet<>();

  /**
   * Conjunto de meses válidos para a recorrência.
   * <p>
   * Equivalente ao parâmetro BYMONTH da RFC 5545.
   * <p>
   * Restringe a geração de ocorrências a meses específicos do ano. Exemplo:
   *
   * <pre>
   * FREQ=YEARLY;BYMONTH=JANUARY,MARCH
   * </pre>
   */
  @CollectionTable(name = QuartzModel.TABLE_PREFIX + "RECORRENCIA_MES",
                   schema = SCHEMA_NAME)
  @ElementCollection(fetch = FetchType.LAZY, targetClass = Month.class)
  @Column(name = "by_month")
  private Set<Month> byMonth = new HashSet<>();

  /**
   * Posição específica dentro do conjunto de ocorrências geradas no período.
   * <p>
   * Equivalente ao parâmetro BYSETPOS da RFC 5545.
   * <p>
   * Aplicado após todos os filtros (BYDAY, BYMONTHDAY, etc).
   * <p>
   * Exemplos:
   * <ul>
   * <li>1 → primeira ocorrência do período</li>
   * <li>2 → segunda ocorrência</li>
   * <li>-1 → última ocorrência</li>
   * <li>-2 → penúltima ocorrência</li>
   * </ul>
   * Exemplo clássico:
   *
   * <pre>
   * FREQ=MONTHLY;BYDAY=TU;BYSETPOS=2
   * → segunda terça-feira do mês
   * </pre>
   */
  @Column(name = "by_set_position")
  private Integer bySetPosition;

  /**
   * Data limite da recorrência.
   * <p>
   * Equivalente ao parâmetro UNTIL da RFC 5545.
   * <p>
   * Nenhuma ocorrência pode ser gerada após essa data. Se coexistir com COUNT,
   * o primeiro limite atingido encerra a recorrência.
   */
  @Column(name = "until_date")
  private LocalDate untilDate;

  /**
   * Número máximo de ocorrências permitidas.
   * <p>
   * Equivalente ao parâmetro COUNT da RFC 5545.
   * <p>
   * Define quantas ocorrências válidas serão geradas, considerando todos os
   * filtros e exclusões aplicados. Exemplo:
   *
   * <pre>
   * FREQ=MONTHLY;COUNT=10
   * </pre>
   */
  @Column(name = "count_limit")
  private Integer countLimit;

}
