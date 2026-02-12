package com.ia.core.quartz.service.periodicidade.dto;

import com.ia.core.service.dto.DTO;
import com.ia.core.service.translator.Translator;

/**
 * {@link DTO} Formatter for {@link PeriodicidadeDTO}
 *
 * @author Israel Araújo
 */
public class PeriodicidadeFormatter {

  private PeriodicidadeFormatter() {
    // Utility class
  }

  /**
   * Formats a {@link PeriodicidadeDTO} to a human-readable string
   *
   * @param periodicidade the periodicity to format
   * @param translator    the translator utils
   * @return formatted string
   */
  public static String format(PeriodicidadeDTO periodicidade, Translator translator) {
    if (periodicidade == null) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    if (periodicidade.getAtivo() != null && periodicidade.getAtivo()) {
      sb.append(translator.getTranslation(PeriodicidadeTranslator.ATIVO));
    }

    if (periodicidade.getIntervaloBase() != null) {
      if (sb.length() > 0) {
        sb.append(" - ");
      }
      sb.append(translator.getTranslation(PeriodicidadeTranslator.INTERVALO_BASE));
    }

    if (periodicidade.getRegra() != null) {
      if (sb.length() > 0) {
        sb.append(" - ");
      }
      sb.append(translator.getTranslation(PeriodicidadeTranslator.REGRA));
    }

    return sb.toString();
  }
}
