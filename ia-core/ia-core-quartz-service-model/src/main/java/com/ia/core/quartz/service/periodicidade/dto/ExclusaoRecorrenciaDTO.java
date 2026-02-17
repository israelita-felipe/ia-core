package com.ia.core.quartz.service.periodicidade.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.quartz.model.periodicidade.ExclusaoRecorrencia;
import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.service.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para ExclusaoRecorrencia.
 * <p>
 * Equivalente ao parâmetro EXRULE da RFC 5545 (iCalendar).
 * Define datas específicas a serem excluídas de uma recorrência.
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExclusaoRecorrenciaDTO
  implements DTO<ExclusaoRecorrencia> {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  private Frequencia frequency;

  private Integer intervalValue;

  @Default
  private Set<DayOfWeek> byDay = new HashSet<>();

  @Default
  private Set<Integer> byMonthDay = new HashSet<>();

  @Default
  private Set<Month> byMonth = new HashSet<>();

  private Integer bySetPosition;

  private LocalDate untilDate;

  private Integer countLimit;

  private DayOfWeek weekStartDay;

  @Default
  private Set<Integer> byYearDay = new HashSet<>();

  @Default
  private Set<Integer> byWeekNo = new HashSet<>();

  @Default
  private Set<Integer> byHour = new HashSet<>();

  @Default
  private Set<Integer> byMinute = new HashSet<>();

  @Default
  private Set<Integer> bySecond = new HashSet<>();

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
   * Campos para busca/filtro
   */
  public static class CAMPOS {
    public static final String FREQUENCY = "frequency";
    public static final String INTERVAL_VALUE = "intervalValue";
    public static final String BY_DAY = "byDay";
    public static final String BY_MONTH_DAY = "byMonthDay";
    public static final String BY_MONTH = "byMonth";
    public static final String BY_SET_POSITION = "bySetPosition";
    public static final String UNTIL_DATE = "untilDate";
    public static final String COUNT_LIMIT = "countLimit";
    public static final String WEEK_START_DAY = "weekStartDay";
    public static final String BY_YEAR_DAY = "byYearDay";
    public static final String BY_WEEK_NO = "byWeekNo";
    public static final String BY_HOUR = "byHour";
    public static final String BY_MINUTE = "byMinute";
    public static final String BY_SECOND = "bySecond";
  }
}
