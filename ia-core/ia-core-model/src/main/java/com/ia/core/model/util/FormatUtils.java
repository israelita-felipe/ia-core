package com.ia.core.model.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitário para formatação
 *
 * @author Israel Araújo
 */
public class FormatUtils {
  /** Padrão data */
  public static final String DATE = "dd/MM/yyyy";
  /** Padrão tempo */
  public static final String TIME = "HH:mm:ss";
  /** Padrão data e tempo */
  public static final String DATE_TIME_FULL = DATE + " " + TIME + ".SSS";

  /**
   * Formata um {@link LocalDateTime} baseado em um padrão
   *
   * @param pattern  padrão a ser utilizado
   * @param dateTime {@link LocalDateTime}
   * @return {@link String} contendo o valor formatado
   */
  public static String formatDateTime(String pattern,
                                      LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String formatCurrency(BigDecimal value) {
    return NumberFormat.getCurrencyInstance().format(value);
  }
}
