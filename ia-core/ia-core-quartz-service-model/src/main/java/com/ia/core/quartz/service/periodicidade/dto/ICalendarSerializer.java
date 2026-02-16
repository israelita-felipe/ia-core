package com.ia.core.quartz.service.periodicidade.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.quartz.model.periodicidade.ExclusaoRecorrencia;
import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.model.periodicidade.Recorrencia;

/**
 * Serializador para formato iCalendar (RFC 5545).
 * <p>
 * Responsável por converter objetos {@link Recorrencia} e
 * {@link ExclusaoRecorrencia} para os formatos RRULE e EXRULE,
 * respectivamente, conforme especificação RFC 5545 (iCalendar).
 * <p>
 * Formato de data UTC: YYYYMMDD'T'HHMMSS'Z'
 * Formato de data local: YYYYMMDD'T'HHMMSS
 */
public final class ICalendarSerializer {

  /** Construtor privado para classe utilitária */
  private ICalendarSerializer() {
  }

  /**
   * Formatter para data UTC (com 'Z')
   */
  private static final DateTimeFormatter UTC_FORMAT = DateTimeFormatter
      .ofPattern("yyyyMMdd'T'HHmmss'Z'");

  /**
   * Formatter para data local (sem 'Z')
   */
  private static final DateTimeFormatter LOCAL_FORMAT = DateTimeFormatter
      .ofPattern("yyyyMMdd'T'HHmmss");

  /**
   * Serializa uma {@link RecorrenciaDTO} para formato RRULE.
   *
   * @param recur a regra de recorrência DTO
   * @return string no formato RRULE (ex: FREQ=WEEKLY;BYDAY=MO;COUNT=10)
   */
  public static String toRRule(RecorrenciaDTO recur) {
    if (recur == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    // FREQ (obrigatório)
    appendParam(sb, "FREQ", toRfcName(recur.getFrequency()));

    // INTERVAL
    if (recur.getIntervalValue() != null && recur.getIntervalValue() != 1) {
      appendParam(sb, "INTERVAL", recur.getIntervalValue().toString());
    }

    // UNTIL
    if (recur.getUntilDate() != null) {
      appendParam(sb, "UNTIL",
                  recur.getUntilDate().atStartOfDay().format(UTC_FORMAT));
    }

    // COUNT
    if (recur.getCountLimit() != null) {
      appendParam(sb, "COUNT", recur.getCountLimit().toString());
    }

    // BYSECOND
    if (!recur.getBySecond().isEmpty()) {
      appendParam(sb, "BYSECOND",
                  recur.getBySecond().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYMINUTE
    if (!recur.getByMinute().isEmpty()) {
      appendParam(sb, "BYMINUTE",
                  recur.getByMinute().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYHOUR
    if (!recur.getByHour().isEmpty()) {
      appendParam(sb, "BYHOUR",
                  recur.getByHour().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYDAY
    if (!recur.getByDay().isEmpty()) {
      String byDayValue = recur.getByDay().stream()
          .sorted()
          .map(ICalendarSerializer::dayToRfc)
          .collect(Collectors.joining(","));
      appendParam(sb, "BYDAY", byDayValue);
    }

    // BYMONTHDAY
    if (!recur.getByMonthDay().isEmpty()) {
      appendParam(sb, "BYMONTHDAY",
                  recur.getByMonthDay().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYYEARDAY
    if (!recur.getByYearDay().isEmpty()) {
      appendParam(sb, "BYYEARDAY",
                  recur.getByYearDay().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYWEEKNO
    if (!recur.getByWeekNo().isEmpty()) {
      appendParam(sb, "BYWEEKNO",
                  recur.getByWeekNo().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYMONTH
    if (!recur.getByMonth().isEmpty()) {
      appendParam(sb, "BYMONTH",
                  recur.getByMonth().stream()
                      .sorted()
                      .map(ICalendarSerializer::monthToRfc)
                      .collect(Collectors.joining(",")));
    }

    // BYSETPOS
    if (recur.getBySetPosition() != null) {
      appendParam(sb, "BYSETPOS", recur.getBySetPosition().toString());
    }

    // WKST
    if (recur.getWeekStartDay() != null) {
      appendParam(sb, "WKST", dayToRfc(recur.getWeekStartDay()));
    }

    return sb.toString();
  }

  /**
   * Serializa uma {@link Recorrencia} para formato RRULE.
   *
   * @param recur a regra de recorrência
   * @return string no formato RRULE (ex: FREQ=WEEKLY;BYDAY=MO;COUNT=10)
   */
  public static String toRRule(Recorrencia recur) {
    if (recur == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    // FREQ (obrigatório)
    appendParam(sb, "FREQ", toRfcName(recur.getFrequency()));

    // INTERVAL
    if (recur.getIntervalValue() != null && recur.getIntervalValue() != 1) {
      appendParam(sb, "INTERVAL", recur.getIntervalValue().toString());
    }

    // UNTIL
    if (recur.getUntilDate() != null) {
      appendParam(sb, "UNTIL",
                  recur.getUntilDate().atStartOfDay().format(UTC_FORMAT));
    }

    // COUNT
    if (recur.getCountLimit() != null) {
      appendParam(sb, "COUNT", recur.getCountLimit().toString());
    }

    // BYSECOND
    if (!recur.getBySecond().isEmpty()) {
      appendParam(sb, "BYSECOND",
                  recur.getBySecond().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYMINUTE
    if (!recur.getByMinute().isEmpty()) {
      appendParam(sb, "BYMINUTE",
                  recur.getByMinute().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYHOUR
    if (!recur.getByHour().isEmpty()) {
      appendParam(sb, "BYHOUR",
                  recur.getByHour().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYDAY
    if (!recur.getByDay().isEmpty()) {
      String byDayValue = recur.getByDay().stream()
          .sorted()
          .map(ICalendarSerializer::dayToRfc)
          .collect(Collectors.joining(","));
      appendParam(sb, "BYDAY", byDayValue);
    }

    // BYMONTHDAY
    if (!recur.getByMonthDay().isEmpty()) {
      appendParam(sb, "BYMONTHDAY",
                  recur.getByMonthDay().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYYEARDAY
    if (!recur.getByYearDay().isEmpty()) {
      appendParam(sb, "BYYEARDAY",
                  recur.getByYearDay().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYWEEKNO
    if (!recur.getByWeekNo().isEmpty()) {
      appendParam(sb, "BYWEEKNO",
                  recur.getByWeekNo().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYMONTH
    if (!recur.getByMonth().isEmpty()) {
      appendParam(sb, "BYMONTH",
                  recur.getByMonth().stream()
                      .sorted()
                      .map(ICalendarSerializer::monthToRfc)
                      .collect(Collectors.joining(",")));
    }

    // BYSETPOS
    if (recur.getBySetPosition() != null) {
      appendParam(sb, "BYSETPOS", recur.getBySetPosition().toString());
    }

    // WKST
    if (recur.getWeekStartDay() != null) {
      appendParam(sb, "WKST", dayToRfc(recur.getWeekStartDay()));
    }

    return sb.toString();
  }

  /**
   * Serializa uma {@link ExclusaoRecorrenciaDTO} para formato EXRULE.
   *
   * @param ex a regra de exclusão de recorrência DTO
   * @return string no formato EXRULE (ex: EXRULE:FREQ=WEEKLY;BYDAY=FR)
   */
  public static String toExRule(ExclusaoRecorrenciaDTO ex) {
    if (ex == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    // FREQ (obrigatório)
    appendParam(sb, "FREQ", toRfcName(ex.getFrequency()));

    // INTERVAL
    if (ex.getIntervalValue() != null && ex.getIntervalValue() != 1) {
      appendParam(sb, "INTERVAL", ex.getIntervalValue().toString());
    }

    // UNTIL
    if (ex.getUntilDate() != null) {
      appendParam(sb, "UNTIL",
                  ex.getUntilDate().atStartOfDay().format(UTC_FORMAT));
    }

    // COUNT
    if (ex.getCountLimit() != null) {
      appendParam(sb, "COUNT", ex.getCountLimit().toString());
    }

    // BYSECOND
    if (!ex.getBySecond().isEmpty()) {
      appendParam(sb, "BYSECOND",
                  ex.getBySecond().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYMINUTE
    if (!ex.getByMinute().isEmpty()) {
      appendParam(sb, "BYMINUTE",
                  ex.getByMinute().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYHOUR
    if (!ex.getByHour().isEmpty()) {
      appendParam(sb, "BYHOUR",
                  ex.getByHour().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYDAY
    if (!ex.getByDay().isEmpty()) {
      String byDayValue = ex.getByDay().stream()
          .sorted()
          .map(ICalendarSerializer::dayToRfc)
          .collect(Collectors.joining(","));
      appendParam(sb, "BYDAY", byDayValue);
    }

    // BYMONTHDAY
    if (!ex.getByMonthDay().isEmpty()) {
      appendParam(sb, "BYMONTHDAY",
                  ex.getByMonthDay().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYYEARDAY
    if (!ex.getByYearDay().isEmpty()) {
      appendParam(sb, "BYYEARDAY",
                  ex.getByYearDay().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYWEEKNO
    if (!ex.getByWeekNo().isEmpty()) {
      appendParam(sb, "BYWEEKNO",
                  ex.getByWeekNo().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYMONTH
    if (!ex.getByMonth().isEmpty()) {
      appendParam(sb, "BYMONTH",
                  ex.getByMonth().stream()
                      .sorted()
                      .map(ICalendarSerializer::monthToRfc)
                      .collect(Collectors.joining(",")));
    }

    // BYSETPOS
    if (ex.getBySetPosition() != null) {
      appendParam(sb, "BYSETPOS", ex.getBySetPosition().toString());
    }

    // WKST
    if (ex.getWeekStartDay() != null) {
      appendParam(sb, "WKST", dayToRfc(ex.getWeekStartDay()));
    }

    return sb.toString();
  }

  /**
   * Serializa uma {@link ExclusaoRecorrencia} para formato EXRULE.
   *
   * @param ex a regra de exclusão de recorrência
   * @return string no formato EXRULE (ex: EXRULE:FREQ=WEEKLY;BYDAY=FR)
   */
  public static String toExRule(ExclusaoRecorrencia ex) {
    if (ex == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    // FREQ (obrigatório)
    appendParam(sb, "FREQ", toRfcName(ex.getFrequency()));

    // INTERVAL
    if (ex.getIntervalValue() != null && ex.getIntervalValue() != 1) {
      appendParam(sb, "INTERVAL", ex.getIntervalValue().toString());
    }

    // UNTIL
    if (ex.getUntilDate() != null) {
      appendParam(sb, "UNTIL",
                  ex.getUntilDate().atStartOfDay().format(UTC_FORMAT));
    }

    // COUNT
    if (ex.getCountLimit() != null) {
      appendParam(sb, "COUNT", ex.getCountLimit().toString());
    }

    // BYSECOND
    if (!ex.getBySecond().isEmpty()) {
      appendParam(sb, "BYSECOND",
                  ex.getBySecond().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYMINUTE
    if (!ex.getByMinute().isEmpty()) {
      appendParam(sb, "BYMINUTE",
                  ex.getByMinute().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYHOUR
    if (!ex.getByHour().isEmpty()) {
      appendParam(sb, "BYHOUR",
                  ex.getByHour().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYDAY
    if (!ex.getByDay().isEmpty()) {
      String byDayValue = ex.getByDay().stream()
          .sorted()
          .map(ICalendarSerializer::dayToRfc)
          .collect(Collectors.joining(","));
      appendParam(sb, "BYDAY", byDayValue);
    }

    // BYMONTHDAY
    if (!ex.getByMonthDay().isEmpty()) {
      appendParam(sb, "BYMONTHDAY",
                  ex.getByMonthDay().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYYEARDAY
    if (!ex.getByYearDay().isEmpty()) {
      appendParam(sb, "BYYEARDAY",
                  ex.getByYearDay().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYWEEKNO
    if (!ex.getByWeekNo().isEmpty()) {
      appendParam(sb, "BYWEEKNO",
                  ex.getByWeekNo().stream()
                      .sorted()
                      .map(String::valueOf)
                      .collect(Collectors.joining(",")));
    }

    // BYMONTH
    if (!ex.getByMonth().isEmpty()) {
      appendParam(sb, "BYMONTH",
                  ex.getByMonth().stream()
                      .sorted()
                      .map(ICalendarSerializer::monthToRfc)
                      .collect(Collectors.joining(",")));
    }

    // BYSETPOS
    if (ex.getBySetPosition() != null) {
      appendParam(sb, "BYSETPOS", ex.getBySetPosition().toString());
    }

    // WKST
    if (ex.getWeekStartDay() != null) {
      appendParam(sb, "WKST", dayToRfc(ex.getWeekStartDay()));
    }

    return sb.toString();
  }

  /**
   * Converte um {@link LocalDateTime} para formato iCalendar.
   *
   * @param dateTime o date time a converter
   * @param useUtc   true para formato UTC, false para formato local
   * @return string formatada
   */
  public static String formatDateTime(LocalDateTime dateTime, boolean useUtc) {
    if (dateTime == null) {
      return "";
    }
    return useUtc
        ? dateTime.atZone(java.time.ZoneOffset.UTC).format(UTC_FORMAT)
        : dateTime.format(LOCAL_FORMAT);
  }

  /**
   * Converte um {@link LocalDate} para formato iCalendar (data only).
   *
   * @param date a data a converter
   * @return string formatada (ex: 20231225)
   */
  public static String formatDate(LocalDate date) {
    if (date == null) {
      return "";
    }
    return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  }

  /**
   * Converte um {@link LocalTime} para formato iCalendar.
   *
   * @param time o tempo a converter
   * @return string formatada (ex: 143000)
   */
  public static String formatTime(LocalTime time) {
    if (time == null) {
      return "";
    }
    return time.format(DateTimeFormatter.ofPattern("HHmmss"));
  }

  /**
   * Converte nome do enum {@link Frequencia} para nome RFC 5545.
   *
   * @param freq a frequência
   * @return nome RFC (DAILY, WEEKLY, MONTHLY, YEARLY)
   */
  public static String toRfcName(Frequencia freq) {
    if (freq == null) {
      return null;
    }
    return freq.getRfcName();
  }

  /**
   * Converte {@link DayOfWeek} para formato RFC 5545.
   *
   * @param day o dia da semana
   * @return abreviação RFC (MO, TU, WE, TH, FR, SA, SU)
   */
  private static String dayToRfc(DayOfWeek day) {
    if (day == null) {
      return null;
    }
    return switch (day) {
      case MONDAY -> "MO";
      case TUESDAY -> "TU";
      case WEDNESDAY -> "WE";
      case THURSDAY -> "TH";
      case FRIDAY -> "FR";
      case SATURDAY -> "SA";
      case SUNDAY -> "SU";
    };
  }

  /**
   * Converte {@link java.time.Month} para formato RFC 5545.
   *
   * @param month o mês
   * @return abreviação RFC (JANUARY, FEBRUARY, etc.)
   */
  private static String monthToRfc(java.time.Month month) {
    if (month == null) {
      return null;
    }
    return month.name();
  }

  /**
   * Converte dia da semana RFC para {@link DayOfWeek}.
   *
   * @param rfcDay abreviação RFC (MO, TU, WE, TH, FR, SA, SU)
   * @return dia da semana
   */
  private static DayOfWeek fromRfcDay(String rfcDay) {
    if (rfcDay == null) {
      return null;
    }
    return switch (rfcDay.toUpperCase()) {
      case "MO" -> DayOfWeek.MONDAY;
      case "TU" -> DayOfWeek.TUESDAY;
      case "WE" -> DayOfWeek.WEDNESDAY;
      case "TH" -> DayOfWeek.THURSDAY;
      case "FR" -> DayOfWeek.FRIDAY;
      case "SA" -> DayOfWeek.SATURDAY;
      case "SU" -> DayOfWeek.SUNDAY;
      default -> null;
    };
  }

  /**
   * Converte mês RFC para {@link java.time.Month}.
   *
   * @param rfcMonth abreviação RFC (JANUARY, FEBRUARY, etc.)
   * @return mês
   */
  private static java.time.Month fromRfcMonth(String rfcMonth) {
    if (rfcMonth == null) {
      return null;
    }
    try {
      return java.time.Month.valueOf(rfcMonth.toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * Deserializa uma string RRULE para {@link RecorrenciaDTO}.
   *
   * @param rruleText string no formato RRULE (ex: FREQ=WEEKLY;BYDAY=MO;COUNT=10)
   * @return RecorrenciaDTO
   */
  public static RecorrenciaDTO fromRRule(String rruleText) {
    if (rruleText == null || rruleText.isBlank()) {
      return new RecorrenciaDTO();
    }

    RecorrenciaDTO dto = new RecorrenciaDTO();

    // Divide a string em parâmetros
    String[] params = rruleText.split(";");

    for (String param : params) {
      String[] keyValue = param.split("=", 2);
      if (keyValue.length != 2) {
        continue;
      }

      String key = keyValue[0].trim().toUpperCase();
      String value = keyValue[1].trim();

      switch (key) {
        case "FREQ" -> dto.setFrequency(Frequencia.fromRfcName(value));

        case "INTERVAL" -> {
          try {
            dto.setIntervalValue(Integer.parseInt(value));
          } catch (NumberFormatException e) {
            // Ignorar valor inválido
          }
        }

        case "UNTIL" -> {
          try {
            // Formato: YYYYMMDD'T'HHMMSS'Z' ou YYYYMMDD
            if (value.contains("T")) {
              LocalDateTime until = LocalDateTime.parse(
                  value.replace("Z", ""),
                  DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
              dto.setUntilDate(until.toLocalDate());
            } else {
              dto.setUntilDate(
                  LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyyMMdd")));
            }
          } catch (Exception e) {
            // Ignorar valor inválido
          }
        }

        case "COUNT" -> {
          try {
            dto.setCountLimit(Integer.parseInt(value));
          } catch (NumberFormatException e) {
            // Ignorar valor inválido
          }
        }

        case "BYSECOND" -> {
          Set<Integer> bySecond = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              bySecond.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setBySecond(bySecond);
        }

        case "BYMINUTE" -> {
          Set<Integer> byMinute = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byMinute.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByMinute(byMinute);
        }

        case "BYHOUR" -> {
          Set<Integer> byHour = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byHour.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByHour(byHour);
        }

        case "BYDAY" -> {
          Set<DayOfWeek> byDay = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            // Remove ordinal (ex: 2MO -> MO)
            String day = part.trim();
            if (day.length() > 2) {
              day = day.substring(day.length() - 2);
            }
            DayOfWeek dow = fromRfcDay(day);
            if (dow != null) {
              byDay.add(dow);
            }
          }
          dto.setByDay(byDay);
        }

        case "BYMONTHDAY" -> {
          Set<Integer> byMonthDay = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byMonthDay.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByMonthDay(byMonthDay);
        }

        case "BYYEARDAY" -> {
          Set<Integer> byYearDay = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byYearDay.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByYearDay(byYearDay);
        }

        case "BYWEEKNO" -> {
          Set<Integer> byWeekNo = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byWeekNo.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByWeekNo(byWeekNo);
        }

        case "BYMONTH" -> {
          Set<java.time.Month> byMonth = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            java.time.Month month = fromRfcMonth(part.trim());
            if (month != null) {
              byMonth.add(month);
            }
          }
          dto.setByMonth(byMonth);
        }

        case "BYSETPOS" -> {
          try {
            dto.setBySetPosition(Integer.parseInt(value));
          } catch (NumberFormatException ignored) {
          }
        }

        case "WKST" -> dto.setWeekStartDay(fromRfcDay(value));

        default -> {
          // Parâmetro desconhecido, ignorar
        }
      }
    }

    return dto;
  }

  /**
   * Deserializa uma string EXRULE para {@link ExclusaoRecorrenciaDTO}.
   *
   * @param exruleText string no formato EXRULE (ex: EXRULE:FREQ=WEEKLY;BYDAY=FR)
   * @return ExclusaoRecorrenciaDTO
   */
  public static ExclusaoRecorrenciaDTO fromExRule(String exruleText) {
    if (exruleText == null || exruleText.isBlank()) {
      return new ExclusaoRecorrenciaDTO();
    }

    // Remove prefixo EXRULE: se presente
    if (exruleText.toUpperCase().startsWith("EXRULE:")) {
      exruleText = exruleText.substring(7);
    }

    ExclusaoRecorrenciaDTO dto = new ExclusaoRecorrenciaDTO();

    // Divide a string em parâmetros
    String[] params = exruleText.split(";");

    for (String param : params) {
      String[] keyValue = param.split("=", 2);
      if (keyValue.length != 2) {
        continue;
      }

      String key = keyValue[0].trim().toUpperCase();
      String value = keyValue[1].trim();

      switch (key) {
        case "FREQ" -> dto.setFrequency(Frequencia.fromRfcName(value));

        case "INTERVAL" -> {
          try {
            dto.setIntervalValue(Integer.parseInt(value));
          } catch (NumberFormatException e) {
            // Ignorar valor inválido
          }
        }

        case "UNTIL" -> {
          try {
            if (value.contains("T")) {
              LocalDateTime until = LocalDateTime.parse(
                  value.replace("Z", ""),
                  DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
              dto.setUntilDate(until.toLocalDate());
            } else {
              dto.setUntilDate(
                  LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyyMMdd")));
            }
          } catch (Exception e) {
            // Ignorar valor inválido
          }
        }

        case "COUNT" -> {
          try {
            dto.setCountLimit(Integer.parseInt(value));
          } catch (NumberFormatException e) {
            // Ignorar valor inválido
          }
        }

        case "BYSECOND" -> {
          Set<Integer> bySecond = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              bySecond.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setBySecond(bySecond);
        }

        case "BYMINUTE" -> {
          Set<Integer> byMinute = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byMinute.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByMinute(byMinute);
        }

        case "BYHOUR" -> {
          Set<Integer> byHour = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byHour.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByHour(byHour);
        }

        case "BYDAY" -> {
          Set<DayOfWeek> byDay = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            String day = part.trim();
            if (day.length() > 2) {
              day = day.substring(day.length() - 2);
            }
            DayOfWeek dow = fromRfcDay(day);
            if (dow != null) {
              byDay.add(dow);
            }
          }
          dto.setByDay(byDay);
        }

        case "BYMONTHDAY" -> {
          Set<Integer> byMonthDay = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byMonthDay.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByMonthDay(byMonthDay);
        }

        case "BYYEARDAY" -> {
          Set<Integer> byYearDay = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byYearDay.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByYearDay(byYearDay);
        }

        case "BYWEEKNO" -> {
          Set<Integer> byWeekNo = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            try {
              byWeekNo.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException ignored) {
            }
          }
          dto.setByWeekNo(byWeekNo);
        }

        case "BYMONTH" -> {
          Set<java.time.Month> byMonth = new java.util.HashSet<>();
          for (String part : value.split(",")) {
            java.time.Month month = fromRfcMonth(part.trim());
            if (month != null) {
              byMonth.add(month);
            }
          }
          dto.setByMonth(byMonth);
        }

        case "BYSETPOS" -> {
          try {
            dto.setBySetPosition(Integer.parseInt(value));
          } catch (NumberFormatException ignored) {
          }
        }

        case "WKST" -> dto.setWeekStartDay(fromRfcDay(value));

        default -> {
          // Parâmetro desconhecido, ignorar
        }
      }
    }

    return dto;
  }

  /**
   * Adiciona um parâmetro ao formato iCalendar.
   *
   * @param sb    o StringBuilder
   * @param name  nome do parâmetro
   * @param value valor do parâmetro
   */
  private static void appendParam(StringBuilder sb, String name, String value) {
    if (value == null || value.isEmpty()) {
      return;
    }
    if (sb.length() > 0) {
      sb.append(";");
    }
    sb.append(name).append("=").append(value);
  }

  /**
   * Gera uma string iCalendar completa para um evento com periodicidade.
   *
   * @param periodicidade a periodicidade do evento
   * @return string no formato iCalendar
   */
  public static String toICalendar(PeriodicidadeDTO periodicidade) {
    if (periodicidade == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    sb.append("BEGIN:VCALENDAR\r\n");
    sb.append("VERSION:2.0\r\n");
    sb.append("PRODID:-//IA Core//Periodicidade//EN\r\n");

    // RRULE
    if (periodicidade.getRegra() != null) {
      String rrule = toRRule(periodicidade.getRegra());
      if (!rrule.isEmpty()) {
        sb.append("RRULE:").append(rrule).append("\r\n");
      }
    }

    // EXRULE
    if (periodicidade.getExclusaoRecorrencia() != null) {
      String exrule = toExRule(periodicidade.getExclusaoRecorrencia());
      if (!exrule.isEmpty()) {
        sb.append("EXRULE:").append(exrule).append("\r\n");
      }
    }

    // EXDATE (exception dates)
    if (periodicidade.getExceptionDates() != null
        && !periodicidade.getExceptionDates().isEmpty()) {
      for (LocalDate exDate : periodicidade.getExceptionDates()) {
        sb.append("EXDATE:").append(formatDate(exDate)).append("\r\n");
      }
    }

    // RDATE (include dates)
    if (periodicidade.getIncludeDates() != null
        && !periodicidade.getIncludeDates().isEmpty()) {
      for (LocalDate incDate : periodicidade.getIncludeDates()) {
        sb.append("RDATE:").append(formatDate(incDate)).append("\r\n");
      }
    }

    sb.append("END:VCALENDAR\r\n");
    return sb.toString();
  }

  /**
   * Exporta uma periodicidade para um arquivo ICS (iCalendar).
   * <p>
   * Este método cria um arquivo no formato iCalendar (RFC 5545)
   * que pode ser importado no Google Calendar, Outlook, Apple Calendar, etc.
   *
   * @param periodicidade a periodicidade do evento
   * @param nomeArquivo o nome do arquivo ICS a ser criado (ex: evento.ics)
   * @throws IOException se ocorrer erro ao escrever o arquivo
   */
  public static void exportToICSFile(PeriodicidadeDTO periodicidade,
                                     String nomeArquivo) throws IOException {
    String icsContent = toICS(periodicidade);
    try (FileWriter writer = new FileWriter(nomeArquivo)) {
      writer.write(icsContent);
    }
  }

  /**
   * Gera uma string iCalendar completa para um evento com periodicidade.
   * <p>
   * Inclui o evento (VEVENT) completo com DTSTART, DTEND, RRULE e EXRULE.
   *
   * @param periodicidade a periodicidade do evento
   * @return string no formato iCalendar completo
   */
  public static String toICS(PeriodicidadeDTO periodicidade) {
    if (periodicidade == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    // Cabeçalho do calendário
    sb.append("BEGIN:VCALENDAR\r\n");
    sb.append("VERSION:2.0\r\n");
    sb.append("PRODID:-//IA Core//Periodicidade//EN\r\n");
    sb.append("CALSCALE:GREGORIAN\r\n");
    sb.append("METHOD:PUBLISH\r\n");

    // Início do evento
    sb.append("BEGIN:VEVENT\r\n");

    // UID do evento (identificador único)
    if (periodicidade.getId() != null) {
      sb.append("UID:").append(periodicidade.getId())
          .append("@iacore.local\r\n");
    }

    // Data/hora de início (DTSTART)
    IntervaloTemporalDTO intervalo = periodicidade.getIntervaloBase();
    if (intervalo != null) {
      if (intervalo.getStartDateTime() != null) {
        sb.append("DTSTART:")
            .append(formatDateTime(intervalo.getStartDateTime(), false))
            .append("\r\n");
      } else if (intervalo.getStartDate() != null) {
        sb.append("DTSTART;VALUE=DATE:")
            .append(formatDate(intervalo.getStartDate()))
            .append("\r\n");
      }

      // Data/hora de fim (DTEND)
      if (intervalo.getEndDateTime() != null) {
        sb.append("DTEND:")
            .append(formatDateTime(intervalo.getEndDateTime(), false))
            .append("\r\n");
      } else if (intervalo.getEndDate() != null) {
        sb.append("DTEND;VALUE=DATE:")
            .append(formatDate(intervalo.getEndDate()))
            .append("\r\n");
      }
    }

    // Timezone
    if (periodicidade.getZoneId() != null
        && !periodicidade.getZoneId().isEmpty()) {
      sb.append("DTSTART;TZID=").append(periodicidade.getZoneId())
          .append(":")
          .append(formatDateTime(intervalo != null
              ? intervalo.getStartDateTime() : null, false))
          .append("\r\n");
      sb.append("DTEND;TZID=").append(periodicidade.getZoneId())
          .append(":")
          .append(formatDateTime(intervalo != null
              ? intervalo.getEndDateTime() : null, false))
          .append("\r\n");
    }

    // RRULE (regra de recorrência)
    if (periodicidade.getRegra() != null) {
      String rrule = toRRule(periodicidade.getRegra());
      if (!rrule.isEmpty()) {
        sb.append("RRULE:").append(rrule).append("\r\n");
      }
    }

    // EXRULE (regra de exclusão)
    if (periodicidade.getExclusaoRecorrencia() != null) {
      String exrule = toExRule(periodicidade.getExclusaoRecorrencia());
      if (!exrule.isEmpty()) {
        sb.append("EXRULE:").append(exrule).append("\r\n");
      }
    }

    // EXDATE (datas de exceção)
    if (periodicidade.getExceptionDates() != null
        && !periodicidade.getExceptionDates().isEmpty()) {
      for (LocalDate exDate : periodicidade.getExceptionDates()) {
        sb.append("EXDATE:").append(formatDate(exDate)).append("\r\n");
      }
    }

    // RDATE (datas inclusas)
    if (periodicidade.getIncludeDates() != null
        && !periodicidade.getIncludeDates().isEmpty()) {
      for (LocalDate incDate : periodicidade.getIncludeDates()) {
        sb.append("RDATE:").append(formatDate(incDate)).append("\r\n");
      }
    }

    // Timestamp de criação
    sb.append("DTSTAMP:")
        .append(java.time.ZonedDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")))
        .append("\r\n");

    sb.append("END:VEVENT\r\n");
    sb.append("END:VCALENDAR\r\n");

    return sb.toString();
  }
}
