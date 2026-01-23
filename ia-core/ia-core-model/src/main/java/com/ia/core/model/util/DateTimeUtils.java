package com.ia.core.model.util;

import java.time.format.DateTimeFormatter;

/**
 *
 */
public class DateTimeUtils {
  /**
   * Formatador de data e hora
   */
  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern(FormatUtils.DATE_TIME_FULL);
  /**
   * Formatador de data
   */
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
      .ofPattern(FormatUtils.DATE);
  /**
   * Formatador de tempo
   */
  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
      .ofPattern(FormatUtils.TIME);
}
