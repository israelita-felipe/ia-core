package com.ia.core.quartz.service.model.recorrencia.dto;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.model.periodicidade.Recorrencia;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.service.dto.DTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DTO para regra de recorrência de eventos.
 * <p>
 * Representa uma regra de recorrência conforme especificação RFC 5545
 * (iCalendar). Suporta os seguintes parâmetros:
 * <ul>
 * <li>FREQ: Frequência base (diária, semanal, mensal, anual)</li>
 * <li>INTERVAL: Intervalo multiplicador</li>
 * <li>UNTIL: Data limite da recurrência</li>
 * <li>COUNT: Número máximo de ocorrências</li>
 * <li>BYMONTH: Filtro por mês</li>
 * <li>BYMONTHDAY: Filtro por dia do mês</li>
 * <li>BYDAY: Filtro por dia da semana</li>
 * <li>BYSETPOS: Posição no conjunto</li>
 * <li>WKST: Dia de início da semana</li>
 * <li>BYYEARDAY: Dia do ano</li>
 * <li>BYWEEKNO: Número da semana</li>
 * <li>BYHOUR: Hora do dia</li>
 * <li>BYMINUTE: Minuto da hora</li>
 * <li>BYSECOND: Segundo do minuto</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see Recorrencia
 * @see Frequencia
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RecorrenciaDTO
  implements DTO<Recorrencia> {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Frequência base da regra de recorrência.
   * <p>
   * Deve corresponder a um dos valores definidos em {@link Frequencia}.
   */
  @NotNull(message = PeriodicidadeTranslator.VALIDATION.FREQUENCY_REQUIRED)
  private Frequencia frequency;

  /**
   * Intervalo multiplicador da recorrência.
   * <p>
   * Valor padrão é 1. Deve ser positivo.
   */
  @Positive(message = PeriodicidadeTranslator.HELP.INTERVAL_VALUE)
  @Builder.Default
  private Integer intervalValue = 1;

  /**
   * Dias da semana para recorrência.
   */
  @Default
  private Set<DayOfWeek> byDay = new HashSet<>();

  /**
   * Dias do mês para recorrência (1-31).
   */
  @Default
  private Set<Integer> byMonthDay = new HashSet<>();

  /**
   * Meses para recorrência.
   */
  @Default
  private Set<Month> byMonth = new HashSet<>();

  /**
   * Posições no conjunto para recorrência.
   */
  @Default
  private Set<Integer> bySetPosition = new HashSet<>();

  /**
   * Data limite da recorrência.
   */
  private LocalDate untilDate;

  /**
   * Limite máximo de ocorrências.
   */
  @Min(value = 1,
       message = PeriodicidadeTranslator.VALIDATION.COUNT_LIMIT)
  private Integer countLimit;

  /**
   * Dia de início da semana.
   */
  private DayOfWeek weekStartDay;

  /**
   * Dias do ano para recorrência (1-366).
   */
  @Default
  private Set<Integer> byYearDay = new HashSet<>();

  /**
   * Números da semana para recorrência.
   */
  @Default
  private Set<Integer> byWeekNo = new HashSet<>();

  /**
   * Horas do dia para recorrência.
   */
  @Default
  private Set<Integer> byHour = new HashSet<>();

  /**
   * Minutos da hora para recorrência.
   */
  @Default
  private Set<Integer> byMinute = new HashSet<>();

  /**
   * Segundos do minuto para recorrência.
   */
  @Default
  private Set<Integer> bySecond = new HashSet<>();

  /**
   * Compara este objeto com outro para ordenação.
   *
   * @param other o objeto a ser comparado
   * @return valor negativo, zero ou positivo se este objeto for menos, igual ou maior
   */
  public int compareTo(RecorrenciaDTO other) {
    int result = Objects
        .compare(frequency, other.frequency,
                 (f1, f2) -> Integer
                     .compare(f1 != null ? f1.getCodigo() : 0,
                              f2 != null ? f2.getCodigo() : 0));
    if (result != 0) {
      return result;
    }
    result = Integer.compare(intervalValue, other.intervalValue);
    if (result != 0) {
      return result;
    }
    for (Integer position : bySetPosition.stream()
        .sorted(Integer::compareTo).collect(Collectors.toList())) {
      for (Integer positionObj : other.bySetPosition.stream()
          .sorted(Integer::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE
            .compareTo(Objects.equals(position, positionObj));
        if (result != 0) {
          return result;
        }
      }
    }
    if (result != 0) {
      return result;
    }
    result = Objects.compare(untilDate, other.untilDate,
                             LocalDate::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(countLimit, other.countLimit,
                             Integer::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(weekStartDay, other.weekStartDay,
                             DayOfWeek::compareTo);
    if (result != 0) {
      return result;
    }

    for (DayOfWeek dia : byDay.stream().sorted(DayOfWeek::compareTo)
        .collect(Collectors.toList())) {
      for (DayOfWeek diaObj : other.byDay.stream()
          .sorted(DayOfWeek::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(dia, diaObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Integer dia : byMonthDay.stream().sorted(Integer::compareTo)
        .collect(Collectors.toList())) {
      for (Integer diaObj : other.byMonthDay.stream()
          .sorted(Integer::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(dia, diaObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Month mes : byMonth.stream().sorted(Month::compareTo)
        .collect(Collectors.toList())) {
      for (Month mesObj : other.byMonth.stream().sorted(Month::compareTo)
          .collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(mes, mesObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Integer dia : byYearDay.stream().sorted(Integer::compareTo)
        .collect(Collectors.toList())) {
      for (Integer diaObj : other.byYearDay.stream()
          .sorted(Integer::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(dia, diaObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Integer semana : byWeekNo.stream().sorted(Integer::compareTo)
        .collect(Collectors.toList())) {
      for (Integer semanaObj : other.byWeekNo.stream()
          .sorted(Integer::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(semana, semanaObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Integer hora : byHour.stream().sorted(Integer::compareTo)
        .collect(Collectors.toList())) {
      for (Integer horaObj : other.byHour.stream()
          .sorted(Integer::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(hora, horaObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Integer minuto : byMinute.stream().sorted(Integer::compareTo)
        .collect(Collectors.toList())) {
      for (Integer minutoObj : other.byMinute.stream()
          .sorted(Integer::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE.compareTo(Objects.equals(minuto, minutoObj));
        if (result != 0) {
          return result;
        }
      }
    }
    for (Integer segundo : bySecond.stream().sorted(Integer::compareTo)
        .collect(Collectors.toList())) {
      for (Integer segundoObj : other.bySecond.stream()
          .sorted(Integer::compareTo).collect(Collectors.toList())) {
        result = Boolean.TRUE
            .compareTo(Objects.equals(segundo, segundoObj));
        if (result != 0) {
          return result;
        }
      }
    }
    return 0;
  }

  /**
   * Cria uma cópia superficial (clone) deste objeto DTO.
   *
   * @return novo objeto com os mesmos valores
   */
  @Override
  public RecorrenciaDTO cloneObject() {
    return toBuilder().byDay(new HashSet<>(byDay))
        .byMonth(new HashSet<>(byMonth))
        .byMonthDay(new HashSet<>(byMonthDay))
        .byYearDay(new HashSet<>(byYearDay))
        .byWeekNo(new HashSet<>(byWeekNo)).byHour(new HashSet<>(byHour))
        .byMinute(new HashSet<>(byMinute)).bySecond(new HashSet<>(bySecond))
        .bySetPosition(new HashSet<>(bySetPosition)).build();
  }

  /**
   * Constantes para nomes dos campos deste DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {

    /** Frequência */
    public static final String FREQUENCY = "frequency";

    /** Valor do intervalo */
    public static final String INTERVAL_VALUE = "intervalValue";

    /** Dias da semana */
    public static final String BY_DAY = "byDay";

    /** Dias do mês */
    public static final String BY_MONTH_DAY = "byMonthDay";

    /** Meses */
    public static final String BY_MONTH = "byMonth";

    /** Posição no conjunto */
    public static final String BY_SET_POSITION = "bySetPosition";

    /** Data limite */
    public static final String UNTIL_DATE = "untilDate";

    /** Limite de ocorrências */
    public static final String COUNT_LIMIT = "countLimit";

    /** Dia de início da semana */
    public static final String WEEK_START_DAY = "weekStartDay";

    /** Dias do ano */
    public static final String BY_YEAR_DAY = "byYearDay";

    /** Números da semana */
    public static final String BY_WEEK_NO = "byWeekNo";

    /** Horas */
    public static final String BY_HOUR = "byHour";

    /** Minutos */
    public static final String BY_MINUTE = "byMinute";

    /** Segundos */
    public static final String BY_SECOND = "bySecond";

    /**
     * Retorna todos os nomes de campos deste DTO.
     *
     * @return conjunto de strings com os nomes dos campos
     */
    public static Set<String> values() {
        return Set.of(FREQUENCY, INTERVAL_VALUE, BY_DAY, BY_MONTH_DAY, BY_MONTH,
                     BY_SET_POSITION, UNTIL_DATE, COUNT_LIMIT, WEEK_START_DAY,
                     BY_YEAR_DAY, BY_WEEK_NO, BY_HOUR, BY_MINUTE, BY_SECOND);
    }
  }

}
