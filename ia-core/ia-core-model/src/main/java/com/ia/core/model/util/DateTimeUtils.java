package com.ia.core.model.util;

import java.time.format.DateTimeFormatter;

/**
 * Utilitários para manipulação de datas e horas.
 *
 * <p>Fornece formatadores pré-definidos para datas, horas e dateTimes
 * no padrão brasileiro (dd/MM/yyyy).
 *
 * @author Israel Araújo
 * @see DateTimeFormatter
 * @see FormatUtils
 * @since 1.0.0
 */
public class DateTimeUtils {
  /**
   * Formatador de data e hora completo.
   * Formato: {@code "dd/MM/yyyy HH:mm:ss.SSS"}
   */
  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern(FormatUtils.DATE_TIME_FULL);
  /**
   * Formatador de data.
   * Formato: {@code "dd/MM/yyyy"}
   */
  public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
      .ofPattern(FormatUtils.DATE);
  /**
   * Formatador de tempo.
   * Formato: {@code "HH:mm:ss"}
   */
  public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter
      .ofPattern(FormatUtils.TIME);
}
