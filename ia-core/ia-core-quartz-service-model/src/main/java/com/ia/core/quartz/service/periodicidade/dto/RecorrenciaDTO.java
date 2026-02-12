package com.ia.core.quartz.service.periodicidade.dto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.model.periodicidade.Recorrencia;
import com.ia.core.service.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RecorrenciaDTO
  implements DTO<Recorrencia> {

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

  private LocalDateTime untilDate;

  private Integer countLimit;

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
    result = Objects.compare(bySetPosition, other.bySetPosition,
                             Integer::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(untilDate, other.untilDate,
                             LocalDateTime::compareTo);
    if (result != 0) {
      return result;
    }
    result = Objects.compare(countLimit, other.countLimit,
                             Integer::compareTo);
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
    return 0;
  }

  @Override
  public RecorrenciaDTO cloneObject() {
    return toBuilder().byDay(new HashSet<>(byDay))
        .byMonth(new HashSet<>(byMonth))
        .byMonthDay(new HashSet<>(byMonthDay)).build();
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
  }

}
