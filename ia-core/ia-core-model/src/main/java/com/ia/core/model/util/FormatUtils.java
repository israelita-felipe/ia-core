package com.ia.core.model.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilitário para formatação de dados.
 *
 * <p>Fornece métodos para formatação de datas, horas e valores monetários
 * no padrão brasileiro.
 *
 * @author Israel Araújo
 * @see DateTimeUtils
 * @see NumberFormat
 * @since 1.0.0
 */
public class FormatUtils {
  /**
   * Padrão para formatação de data.
   * Formato: {@code "dd/MM/yyyy"}
   */
  public static final String DATE = "dd/MM/yyyy";

  /**
   * Padrão para formatação de tempo.
   * Formato: {@code "HH:mm:ss"}
   */
  public static final String TIME = "HH:mm:ss";

  /**
   * Padrão para formatação de data e hora completo.
   * Formato: {@code "dd/MM/yyyy HH:mm:ss.SSS"}
   */
  public static final String DATE_TIME_FULL = DATE + " " + TIME + ".SSS";

  /**
   * Formata um {@link LocalDateTime} baseado em um padrão.
   *
   * <p><b>Exemplo de uso:</b></p>
   * {@code
   * LocalDateTime agora = LocalDateTime.now();
   * String.formatado = FormatUtils.formatDateTime("dd/MM/yyyy HH:mm", agora);
   * }
   *
   * @param pattern  Padrão de formatação (ex: "dd/MM/yyyy HH:mm:ss")
   * @param dateTime {@link LocalDateTime} a ser formatado
   * @return {@link String} contendo o valor formatado
   * @throws NullPointerException se dateTime for {@code null}
   */
  public static String formatDateTime(String pattern,
                                      LocalDateTime dateTime) {
    return dateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  /**
   * Formata um valor monetário no padrão brasileiro (BRL).
   *
   * <p><b>Exemplo de uso:</b></p>
   * {@code
   * BigDecimal valor = new BigDecimal("1234.56");
   * String.formatado = FormatUtils.formatCurrency(valor);
   * // Resultado: "R$ 1.234,56"
   * }
   *
   * @param value Valor monetário a ser formatado
   * @return String formatada como moeda brasileira (BRL)
   * @throws NullPointerException se value for {@code null}
   */
  public static String formatCurrency(BigDecimal value) {
    return NumberFormat.getCurrencyInstance().format(value);
  }
}
