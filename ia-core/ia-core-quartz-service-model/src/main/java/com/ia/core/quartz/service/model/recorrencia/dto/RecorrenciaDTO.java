package com.ia.core.quartz.service.model.recorrencia.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.model.periodicidade.Recorrencia;
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
 * @see com.ia.core.quartz.model.periodicidade.Recorrencia
 * @see Frequencia
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RecorrenciaDTO
  implements DTO<Recorrencia> {

  /** Serial UID */
  private static final long serialVersionUID = 1L;

  @NotNull(message = "{validation.periodicidade.regra.frequency.required}")
  private Frequencia frequency;

  @Positive(message = "{validation.periodicidade.regra.intervalValue.positive}")
  @Builder.Default
  private Integer intervalValue = 1;

  @Default
  private Set<DayOfWeek> byDay = new HashSet<>();

  @Default
  private Set<Integer> byMonthDay = new HashSet<>();

  @Default
  private Set<Month> byMonth = new HashSet<>();

  @Default
  private Set<Integer> bySetPosition = new HashSet<>();

  private LocalDate untilDate;

  @Min(value = 1,
       message = "{validation.periodicidade.regra.countLimit.positive}")
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
