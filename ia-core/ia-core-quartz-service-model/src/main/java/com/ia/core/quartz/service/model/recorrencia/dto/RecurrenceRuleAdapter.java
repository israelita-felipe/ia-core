package com.ia.core.quartz.service.model.recorrencia.dto;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;

public class RecurrenceRuleAdapter {

  private static final DateTimeFormatter UNTIL_FORMATTER = DateTimeFormatter
      .ofPattern("yyyyMMdd");

  public static RecurrenceRule toRecurrenceRule(RecorrenciaDTO regra) {
    if (regra == null || regra.getFrequency() == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder();

    // FREQ (obrigatório) – converte o enum do seu domínio para o padrão RFC
    String freq;
    switch (regra.getFrequency()) {
    case DIARIAMENTE:
      freq = "DAILY";
      break;
    case SEMANALMENTE:
      freq = "WEEKLY";
      break;
    case MENSALMENTE:
      freq = "MONTHLY";
      break;
    case ANUALMENTE:
      freq = "YEARLY";
      break;
    default:
      throw new IllegalArgumentException("Frequência desconhecida: "
          + regra.getFrequency());
    }
    sb.append("FREQ=").append(freq);

    // INTERVAL
    if (regra.getIntervalValue() != null && regra.getIntervalValue() > 1) {
      sb.append(";INTERVAL=").append(regra.getIntervalValue());
    }

    // COUNT
    if (regra.getCountLimit() != null && regra.getCountLimit() > 0) {
      sb.append(";COUNT=").append(regra.getCountLimit());
    }

    // UNTIL – usamos UTC, fim do dia
    if (regra.getUntilDate() != null) {
      sb.append(";UNTIL=")
          .append(regra.getUntilDate().format(UNTIL_FORMATTER))
          .append("T235959Z"); // 23:59:59 UTC
    }

    // BYDAY
    if (!regra.getByDay().isEmpty()) {
      sb.append(";BYDAY=");
      sb.append(regra.getByDay().stream()
          .map(RecurrenceRuleAdapter::toWeekdayStr)
          .collect(Collectors.joining(",")));
    }

    // BYMONTHDAY
    if (!regra.getByMonthDay().isEmpty()) {
      sb.append(";BYMONTHDAY=");
      sb.append(regra.getByMonthDay().stream().map(String::valueOf)
          .collect(Collectors.joining(",")));
    }

    // BYMONTH
    if (!regra.getByMonth().isEmpty()) {
      sb.append(";BYMONTH=");
      sb.append(regra.getByMonth().stream()
          .map(month -> String.valueOf(month.getValue()))
          .collect(Collectors.joining(",")));
    }

    // BYHOUR
    if (!regra.getByHour().isEmpty()) {
      sb.append(";BYHOUR=");
      sb.append(regra.getByHour().stream().map(String::valueOf)
          .collect(Collectors.joining(",")));
    }

    // BYMINUTE
    if (!regra.getByMinute().isEmpty()) {
      sb.append(";BYMINUTE=");
      sb.append(regra.getByMinute().stream().map(String::valueOf)
          .collect(Collectors.joining(",")));
    }

    // BYSECOND
    if (!regra.getBySecond().isEmpty()) {
      sb.append(";BYSECOND=");
      sb.append(regra.getBySecond().stream().map(String::valueOf)
          .collect(Collectors.joining(",")));
    }

    // WKST
    if (regra.getWeekStartDay() != null) {
      sb.append(";WKST=").append(toWeekdayStr(regra.getWeekStartDay()));
    }

    if (!regra.getBySetPosition().isEmpty()) {
      sb.append(";BYSETPOS=");
      sb.append(regra.getBySetPosition().stream().map(String::valueOf)
          .collect(Collectors.joining(",")));
    }

    try {
      return new RecurrenceRule(sb.toString());
    } catch (InvalidRecurrenceRuleException e) {
      throw new IllegalArgumentException("Regra de recorrência inválida: "
          + sb, e);
    }
  }

  private static String toWeekdayStr(DayOfWeek dow) {
    switch (dow) {
    case MONDAY:
      return "MO";
    case TUESDAY:
      return "TU";
    case WEDNESDAY:
      return "WE";
    case THURSDAY:
      return "TH";
    case FRIDAY:
      return "FR";
    case SATURDAY:
      return "SA";
    case SUNDAY:
      return "SU";
    default:
      throw new IllegalArgumentException("Dia inválido: " + dow);
    }
  }
}
