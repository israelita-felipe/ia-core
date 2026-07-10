package com.ia.core.quartz.service.model.recorrencia.dto;

import com.ia.core.quartz.model.periodicidade.ExclusaoRecorrencia;
import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
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
 * DTO para regra de exclusão de recorrência.
 * <p>
 * Representa uma regra de exclusão (EXRULE) conforme especificação RFC 5545
 * (iCalendar). Define quais ocorrências devem ser excluídas de uma regra
 * de recorrência existente.
 *
 * @author Israel Araújo
 * @see ExclusaoRecorrencia
 * @see Frequencia
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExclusaoRecorrenciaDTO
  implements DTO<ExclusaoRecorrencia> {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  /**
   * Frequência da regra de exclusão.
   */
  private Frequencia frequency;

  /**
   * Intervalo multiplicador da exclusão.
   */
  private Integer intervalValue;

  /**
   * Dias da semana para exclusão.
   */
  @Default
  private Set<DayOfWeek> byDay = new HashSet<>();

  /**
   * Dias do mês para exclusão (1-31).
   */
  @Default
  private Set<Integer> byMonthDay = new HashSet<>();

  /**
   * Meses para exclusão.
   */
  @Default
  private Set<Month> byMonth = new HashSet<>();

  /**
   * Posição no conjunto para exclusão.
   */
  private Integer bySetPosition;

  /**
   * Data limite da exclusão.
   */
  private LocalDate untilDate;

  /**
   * Limite máximo de exclusões.
   */
  private Integer countLimit;

  /**
   * Dia de início da semana.
   */
  private DayOfWeek weekStartDay;

  /**
   * Dias do ano para exclusão (1-366).
   */
  @Default
  private Set<Integer> byYearDay = new HashSet<>();

  /**
   * Números da semana para exclusão.
   */
  @Default
  private Set<Integer> byWeekNo = new HashSet<>();

  /**
   * Horas do dia para exclusão.
   */
  @Default
  private Set<Integer> byHour = new HashSet<>();

  /**
   * Minutos da hora para exclusão.
   */
  @Default
  private Set<Integer> byMinute = new HashSet<>();

  /**
   * Segundos do minuto para exclusão.
   */
  @Default
  private Set<Integer> bySecond = new HashSet<>();

  /**
   * Compara este objeto com outro para ordenação.
   *
   * @param other o objeto a ser comparado
   * @return valor negativo, zero ou positivo se este objeto for menos, igual ou maior
   */
  public int compareTo(ExclusaoRecorrenciaDTO other) {
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
    result = Objects.compare(bySetPosition, other.bySetPosition,
                             Integer::compareTo);
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
        result = Boolean.TRUE.compareTo(Objects.equals(segundo, segundoObj));
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
  public ExclusaoRecorrenciaDTO cloneObject() {
    return toBuilder().byDay(new HashSet<>(byDay))
        .byMonth(new HashSet<>(byMonth))
        .byMonthDay(new HashSet<>(byMonthDay))
        .byYearDay(new HashSet<>(byYearDay))
        .byWeekNo(new HashSet<>(byWeekNo))
        .byHour(new HashSet<>(byHour))
        .byMinute(new HashSet<>(byMinute))
        .bySecond(new HashSet<>(bySecond)).build();
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
