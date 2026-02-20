package com.ia.core.quartz.service.model.periodicidade.dto;

import java.util.regex.Pattern;

/**
 * Validador para regras de recorrência no formato RFC 5545.
 * <p>
 * Valida strings RRULE e EXRULE conforme especificação RFC 5545.
 *
 * @author Israel Araújo
 */
public final class RRuleValidator {

  /** Construtor privado */
  private RRuleValidator() {
  }

  /**
   * Padrão para validar formato básico de RRULE.
   * Formato: FREQ=XXX;propriedades
   */
  private static final Pattern RRULE_PATTERN = Pattern.compile(
      "^FREQ=("
          + "DAILY|WEEKLY|MONTHLY|YEARLY"
          + ")"
          + "(;[^=]+=[^;]*)*$",
      Pattern.CASE_INSENSITIVE);

  /**
   * Valida uma string RRULE.
   *
   * @param rrule string RRULE a validar
   * @return true se o formato for válido
   */
  public static boolean isValidRRule(String rrule) {
    if (rrule == null || rrule.isBlank()) {
      return false;
    }

    // Remove prefixo RRULE: se presente
    String rule = rrule.startsWith("RRULE:")
        ? rrule.substring(6)
        : rrule;

    return RRULE_PATTERN.matcher(rule.trim()).matches();
  }

  /**
   * Valida uma string EXRULE.
   *
   * @param exrule string EXRULE a validar
   * @return true se o formato for válido
   */
  public static boolean isValidExRule(String exrule) {
    if (exrule == null || exrule.isBlank()) {
      return false;
    }

    // Remove prefixo EXRULE: se presente
    String rule = exrule.startsWith("EXRULE:")
        ? exrule.substring(7)
        : exrule;

    return RRULE_PATTERN.matcher(rule.trim()).matches();
  }

  /**
   * Valida um parâmetro específico de RRULE.
   *
   * @param paramName nome do parâmetro
   * @param value valor do parâmetro
   * @return true se o valor for válido para o parâmetro
   */
  public static boolean isValidParameter(String paramName, String value) {
    if (paramName == null || value == null) {
      return false;
    }

    return switch (paramName.toUpperCase()) {
      case "FREQ" -> isValidFrequency(value);
      case "INTERVAL" -> isValidPositiveInteger(value);
      case "COUNT" -> isValidPositiveInteger(value);
      case "BYDAY" -> isValidByDay(value);
      case "BYMONTHDAY" -> isValidByMonthDay(value);
      case "BYMONTH" -> isValidByMonth(value);
      case "BYSETPOS" -> isValidBySetPos(value);
      case "WKST" -> isValidDayOfWeek(value);
      case "BYYEARDAY" -> isValidByYearDay(value);
      case "BYWEEKNO" -> isValidByWeekNo(value);
      case "BYHOUR" -> isValidByHour(value);
      case "BYMINUTE" -> isValidByMinute(value);
      case "BYSECOND" -> isValidBySecond(value);
      case "UNTIL" -> isValidUntil(value);
      default -> true;
    };
  }

  private static boolean isValidFrequency(String value) {
    return value != null
        && value.matches("(?i)^(DAILY|WEEKLY|MONTHLY|YEARLY)$");
  }

  private static boolean isValidPositiveInteger(String value) {
    if (value == null) {
      return false;
    }
    try {
      int i = Integer.parseInt(value);
      return i > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private static boolean isValidByDay(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita MO, TU, WE, TH, FR, SA, SU ou+n/-n (ex: 2MO, -1FR)
    return value.matches("(?i)^(([+-]?\\d+)?(MO|TU|WE|TH|FR|SA|SU)(,[+-]?\\d+)?(MO|TU|WE|TH|FR|SA|SU))*$");
  }

  private static boolean isValidByMonthDay(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita números de -31 a 31 (exceto 0)
    return value.matches("^-?(?:[1-9]|[12]\\d|3[01])(,-?(?:[1-9]|[12]\\d|3[01]))*$");
  }

  private static boolean isValidByMonth(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita números de 1 a 12
    return value.matches("^(?:[1-9]|1[02])(,(?:[1-9]|1[02]))*$");
  }

  private static boolean isValidBySetPos(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita números de -366 a 366 (exceto 0)
    return value.matches("^-?(?:[1-9]|[1-9]\\d|[1-2]\\d{2}|3[0-6][0-6])$");
  }

  private static boolean isValidDayOfWeek(String value) {
    return value != null
        && value.matches("(?i)^(MO|TU|WE|TH|FR|SA|SU)$");
  }

  private static boolean isValidByYearDay(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita números de -366 a 366 (exceto 0)
    return value.matches("^-?(?:[1-9]|[1-9]\\d|[1-2]\\d{2}|3[0-6][0-6])$");
  }

  private static boolean isValidByWeekNo(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita números de -53 a 53 (exceto 0)
    return value.matches("^-?(?:[1-9]|[1-4]\\d|5[0-3])$");
  }

  private static boolean isValidByHour(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita números de 0 a 23
    return value.matches("^(?:[0-9]|1\\d|2[0-3])$");
  }

  private static boolean isValidByMinute(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita números de 0 a 59
    return value.matches("^(?:[0-9]|[1-5]\\d)$");
  }

  private static boolean isValidBySecond(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Aceita números de 0 a 59
    return value.matches("^(?:[0-9]|[1-5]\\d)$");
  }

  private static boolean isValidUntil(String value) {
    if (value == null || value.isBlank()) {
      return false;
    }
    // Formato UTC: YYYYMMDDTHHMMSSZ
    return value.matches("^\\d{8}T\\d{6}Z$")
        || value.matches("^\\d{8}$"); // Ou só data
  }

  /**
   * Retorna mensagem de erro para um RRULE inválido.
   *
   * @param rrule string RRULE
   * @return mensagem de erro ou null se válido
   */
  public static String getValidationMessage(String rrule) {
    if (rrule == null || rrule.isBlank()) {
      return "RRULE não pode ser vazio";
    }

    // Verifica se começa com FREQ=
    if (!rrule.toUpperCase().contains("FREQ=")) {
      return "RRULE deve conter parâmetro FREQ";
    }

    // Valida formato básico
    if (!isValidRRule(rrule)) {
      return "Formato de RRULE inválido";
    }

    return null;
  }
}
