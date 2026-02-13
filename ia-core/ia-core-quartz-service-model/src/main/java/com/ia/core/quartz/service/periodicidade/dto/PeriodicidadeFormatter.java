package com.ia.core.quartz.service.periodicidade.dto;

import java.time.DayOfWeek;
import java.time.Month;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.service.translator.Translator;

public final class PeriodicidadeFormatter {

  private PeriodicidadeFormatter() {
  }

  public static String format(PeriodicidadeDTO p, Translator translator) {

    if (p == null || p.getRegra() == null) {
      return "";
    }

    var r = p.getRegra();
    var base = p.getIntervaloBase();

    StringBuilder sb = new StringBuilder();

    // =============================
    // Frequência base
    // =============================

    switch (r.getFrequency()) {

    case DIARIAMENTE -> sb.append("Todos os dias");

    case SEMANALMENTE -> sb.append("Toda semana");

    case MENSALMENTE -> sb.append("Todo mês");

    case ANUALMENTE -> sb.append("Todo ano");
    }

    // =============================
    // Intervalo
    // =============================

    if (r.getIntervalValue() != null && r.getIntervalValue() > 1) {
      sb.append(" a cada ").append(r.getIntervalValue()).append(" ")
          .append(unidadeFrequencia(r.getFrequency()));
    }

    // =============================
    // BYDAY
    // =============================

    if (!r.getByDay().isEmpty()) {

      sb.append(" na ");

      if (r.getBySetPosition() != null) {
        sb.append(ordem(r.getBySetPosition())).append(" ");
      }

      sb.append(r.getByDay().stream()
          .map(PeriodicidadeFormatter::diaSemanaPT).findFirst().orElse(""));
    }

    // =============================
    // BYMONTH
    // =============================

    if (!r.getByMonth().isEmpty()) {

      sb.append(" de ");

      sb.append(r.getByMonth().stream().map(PeriodicidadeFormatter::mesPT)
          .reduce((a, b) -> a + ", " + b).orElse(""));
    }

    // =============================
    // Horário
    // =============================

    if (base != null) {

      sb.append(" às ").append(base.getStartTime()).append(" até ")
          .append(base.getEndTime());
    }

    // =============================
    // UNTIL
    // =============================

    if (r.getUntilDate() != null) {
      sb.append(", até ").append(r.getUntilDate());
    }

    // =============================
    // COUNT
    // =============================

    if (r.getCountLimit() != null) {
      sb.append(", por ").append(r.getCountLimit())
          .append(" ocorrência(s)");
    }

    return sb.toString();
  }

  private static String unidadeFrequencia(Frequencia f) {
    return switch (f) {
    case DIARIAMENTE -> "dias";
    case SEMANALMENTE -> "semanas";
    case MENSALMENTE -> "meses";
    case ANUALMENTE -> "anos";
    };
  }

  private static String ordem(int pos) {
    return switch (pos) {
    case 1 -> "primeira";
    case 2 -> "segunda";
    case 3 -> "terceira";
    case 4 -> "quarta";
    case -1 -> "última";
    default -> pos + "ª";
    };
  }

  private static String diaSemanaPT(DayOfWeek d) {
    return switch (d) {
    case MONDAY -> "segunda-feira";
    case TUESDAY -> "terça-feira";
    case WEDNESDAY -> "quarta-feira";
    case THURSDAY -> "quinta-feira";
    case FRIDAY -> "sexta-feira";
    case SATURDAY -> "sábado";
    case SUNDAY -> "domingo";
    };
  }

  private static String mesPT(Month m) {
    return switch (m) {
    case JANUARY -> "janeiro";
    case FEBRUARY -> "fevereiro";
    case MARCH -> "março";
    case APRIL -> "abril";
    case MAY -> "maio";
    case JUNE -> "junho";
    case JULY -> "julho";
    case AUGUST -> "agosto";
    case SEPTEMBER -> "setembro";
    case OCTOBER -> "outubro";
    case NOVEMBER -> "novembro";
    case DECEMBER -> "dezembro";
    };
  }

  public static String formatCompact(PeriodicidadeDTO p,
                                     Translator translator) {

    if (p == null || p.getRegra() == null) {
      return "";
    }

    var r = p.getRegra();
    var base = p.getIntervaloBase();

    StringBuilder sb = new StringBuilder();

    sb.append(r.getFrequency().name());

    if (r.getBySetPosition() != null && !r.getByDay().isEmpty()) {

      sb.append(" • ").append(r.getBySetPosition()).append("ª ")
          .append(r.getByDay().iterator().next().name());
    }

    if (base != null) {
      sb.append(" • ").append(base.getStartTime()).append("–")
          .append(base.getEndTime());
    }

    if (r.getUntilDate() != null) {
      sb.append(" • até ").append(r.getUntilDate().getMonthValue())
          .append("/").append(r.getUntilDate().getYear());
    }

    return sb.toString();
  }
}
