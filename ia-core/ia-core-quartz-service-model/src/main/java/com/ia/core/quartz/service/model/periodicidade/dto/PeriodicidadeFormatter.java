package com.ia.core.quartz.service.model.periodicidade.dto;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.service.translator.Translator;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.Set;

/**
 * Utilitário para formatação de periodicidade em diferentes representações.
 * <p>
 * Esta classe fornece métodos para converter objetos {@link PeriodicidadeDTO}
 * em strings formatadas adequadas para exibição ao usuário, suportando múltiplos
 * níveis de detalhamento (compacto, completo e padrão).
 *
 * @author Equipe de Desenvolvimento
 * @since 1.0.0
 * @see PeriodicidadeDTO
 * @see Translator
 */
public final class PeriodicidadeFormatter {

  /**
   * Construtor privado para evitar instanciação da classe utilitária.
   */
  private PeriodicidadeFormatter() {
  }

  /**
   * Formata a periodicidade em versão padrão com descrição amigável.
   * <p>
   * Gera uma representação textual legível da periodicidade, incluindo
   * frequência, intervalo, dias da semana, meses e horários quando aplicável.
   *
   * @param p           objeto {@link PeriodicidadeDTO} a ser formatado (pode ser null)
   * @param translator  serviço de tradução para internacionalização (pode ser null)
   * @return string formatada descrevendo a periodicidade, ou string vazia se p for null
   */
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

    if (r.getByDay() != null && !r.getByDay().isEmpty()) {

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

    if (r.getByMonth() != null && !r.getByMonth().isEmpty()) {

      sb.append(" de ");

      sb.append(r.getByMonth().stream().map(PeriodicidadeFormatter::mesPT)
          .reduce((a, b) -> a + ", " + b).orElse(""));
    }

    // =============================
    // Horário
    // =============================

    if (base != null) {

      // Formata data e hora separadamente
      String startFormatted = base.getStartDate() != null ? base
          .getStartDate().toString() : "";
      if (base.getStartTime() != null) {
        startFormatted += " " + base.getStartTime();
      }

      String endFormatted = "";
      if (base.getEndDate() != null) {
        endFormatted = base.getEndDate().toString();
        if (base.getEndTime() != null) {
          endFormatted += " " + base.getEndTime();
        }
      } else if (base.getEndTime() != null) {
        endFormatted = base.getStartDate() != null
                                           ? base.getStartDate()
                                               .toString() + " "
                                               + base.getEndTime()
                                           : base.getEndTime()
                                               .toString();
      }

      sb.append(" às ").append(startFormatted).append(" até ")
          .append(endFormatted);
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

  /**
   * Obtém a unidade de medida correspondente à frequência.
   *
   * @param f a frequência a ser convertida (não pode ser null)
   * @return string representando a unidade de medida (dias, semanas, meses ou anos)
   */
  private static String unidadeFrequencia(Frequencia f) {
    return switch (f) {
    case DIARIAMENTE -> "dias";
    case SEMANALMENTE -> "semanas";
    case MENSALMENTE -> "meses";
    case ANUALMENTE -> "anos";
    };
  }

  /**
   * Formata uma posição ordinal em português.
   *
   * @param pos conjunto de posições ordinais (não pode ser null)
   * @return string formatada com as posições ordinais separadas por vírgula
   */
  private static String ordem(Set<Integer> pos) {
    String result = "";
    for (Integer i : pos) {
      result += ", " + ordem(i);
    }
    return result;
  }

  /**
   * Converte um número de posição ordinal para sua representação textual em português.
   *
   * @param pos a posição ordinal (1 = primeira, 2 = segunda, etc., -1 = última)
   * @return string representando a posição ordinal por extenso
   */
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

  /**
   * Converte um dia da semana para sua representação textual em português.
   *
   * @param d o dia da semana a ser convertido (não pode ser null)
   * @return string representando o dia da semana por extenso
   */
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

  /**
   * Converte um mês para sua representação textual em português.
   *
   * @param m o mês a ser convertido (não pode ser null)
   * @return string representando o mês por extenso
   */
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

  /**
   * Formata a periodicidade em versão compacta.
   * <p>
   * Gera uma representação resumida da periodicidade, adequada para
   * exibição em espaços limitados.
   *
   * @param p           objeto {@link PeriodicidadeDTO} a ser formatado (pode ser null)
   * @param translator  serviço de tradução para internacionalização (pode ser null)
   * @return string formatada em versão compacta, ou string vazia se p for null
   */
  public static String formatCompact(PeriodicidadeDTO p,
                                     Translator translator) {

    if (p == null || p.getRegra() == null) {
      return "";
    }

    var r = p.getRegra();
    var base = p.getIntervaloBase();

    StringBuilder sb = new StringBuilder();

    sb.append(r.getFrequency().name());

    if (r.getIntervalValue() != null && r.getIntervalValue() > 1) {
      sb.append(" (").append(r.getIntervalValue()).append(")");
    }

    if (r.getBySetPosition() != null && r.getByDay() != null && !r.getByDay().isEmpty()) {

      sb.append(" • ").append(r.getBySetPosition()).append("ª ")
          .append(r.getByDay().iterator().next().name());
    }

    if (base != null) {
      // Formata data e hora separadamente
      String startFormatted = base.getStartDate() != null ? base
          .getStartDate().toString() : "";
      if (base.getStartTime() != null) {
        startFormatted += " " + base.getStartTime();
      }

      String endFormatted = "";
      if (base.getEndDate() != null) {
        endFormatted = base.getEndDate().toString();
        if (base.getEndTime() != null) {
          endFormatted += " " + base.getEndTime();
        }
      } else if (base.getEndTime() != null) {
        endFormatted = base.getStartDate() != null
                                           ? base.getStartDate()
                                               .toString() + " "
                                               + base.getEndTime()
                                           : base.getEndTime()
                                               .toString();
      }

      sb.append(" • ").append(startFormatted).append("–")
          .append(endFormatted);
    }

    if (r.getUntilDate() != null) {
      sb.append(" • até ").append(r.getUntilDate().getMonthValue())
          .append("/").append(r.getUntilDate().getYear());
    }

    if (r.getCountLimit() != null) {
      sb.append(" • ").append(r.getCountLimit()).append("x");
    }

    return sb.toString();
  }

  /**
   * Formata a periodicidade em versão completa com todos os campos.
   * <p>
   * Inclui todos os parâmetros RFC 5545 suportados: FREQ, INTERVAL, UNTIL,
   * COUNT, BYMONTH, BYMONTHDAY, BYDAY, BYSETPOS, WKST, BYYEARDAY, BYWEEKNO,
   * BYHOUR, BYMINUTE, BYSECOND
   *
   * @param p           objeto {@link PeriodicidadeDTO} a ser formatado (pode ser null)
   * @param translator  serviço de tradução para internacionalização (pode ser null)
   * @return string com todos os campos da periodicidade formatados
   */
  public static String formatFull(PeriodicidadeDTO p,
                                   Translator translator) {

    if (p == null) {
      return "";
    }

    var r = p.getRegra();
    var base = p.getIntervaloBase();
    var exclusao = p.getExclusaoRecorrencia();

    StringBuilder sb = new StringBuilder();

    // Intervalo Base
    if (base != null) {
      sb.append("Intervalo: ");
      if (base.getStartDate() != null) {
        sb.append(base.getStartDate());
      }
      if (base.getStartTime() != null) {
        sb.append(" ").append(base.getStartTime());
      }
      if (base.getEndDate() != null || base.getEndTime() != null) {
        sb.append(" -> ");
        if (base.getEndDate() != null) {
          sb.append(base.getEndDate());
        }
        if (base.getEndTime() != null) {
          sb.append(" ").append(base.getEndTime());
        }
      }
      sb.append("\n");
    }

    // Sem recorrência
    if (r == null) {
      return sb.toString();
    }

    // FREQ + INTERVAL
    sb.append("Frequencia: ").append(nomeFrequencia(r.getFrequency()));
    if (r.getIntervalValue() != null && r.getIntervalValue() > 1) {
      sb.append(" (a cada ").append(r.getIntervalValue()).append(" ")
          .append(unidadeFrequencia(r.getFrequency())).append(")");
    }
    sb.append("\n");

    // BYMONTH
    if (r.getByMonth() != null && !r.getByMonth().isEmpty()) {
      sb.append("Meses: ");
      sb.append(r.getByMonth().stream().map(PeriodicidadeFormatter::mesPT)
          .reduce((a, b) -> a + ", " + b).orElse(""));
      sb.append("\n");
    }

    // BYMONTHDAY
    if (r.getByMonthDay() != null && !r.getByMonthDay().isEmpty()) {
      sb.append("Dias do mes: ");
      sb.append(r.getByMonthDay().stream().sorted().map(String::valueOf)
          .reduce((a, b) -> a + ", " + b).orElse(""));
      sb.append("\n");
    }

    // BYDAY
    if (r.getByDay() != null && !r.getByDay().isEmpty()) {
      sb.append("Dias da semana: ");
      if (r.getBySetPosition() != null) {
        sb.append(ordem(r.getBySetPosition())).append(" ");
      }
      sb.append(r.getByDay().stream()
          .map(PeriodicidadeFormatter::diaSemanaPT)
          .reduce((a, b) -> a + ", " + b).orElse(""));
      sb.append("\n");
    }

    // WKST
    if (r.getWeekStartDay() != null) {
      sb.append("Inicio semana: ").append(diaSemanaPT(r.getWeekStartDay()));
      sb.append("\n");
    }

    // BYYEARDAY
    if (r.getByYearDay() != null && !r.getByYearDay().isEmpty()) {
      sb.append("Dias do ano: ");
      sb.append(r.getByYearDay().stream().sorted().map(String::valueOf)
          .reduce((a, b) -> a + ", " + b).orElse(""));
      sb.append("\n");
    }

    // BYWEEKNO
    if (r.getByWeekNo() != null && !r.getByWeekNo().isEmpty()) {
      sb.append("Semanas: ");
      sb.append(r.getByWeekNo().stream().sorted().map(String::valueOf)
          .reduce((a, b) -> a + ", " + b).orElse(""));
      sb.append("\n");
    }

    // BYHOUR, BYMINUTE, BYSECOND
    if ((r.getByHour() != null && !r.getByHour().isEmpty())
        || (r.getByMinute() != null && !r.getByMinute().isEmpty())
        || (r.getBySecond() != null && !r.getBySecond().isEmpty())) {
      sb.append("Horario: ");
      if (r.getByHour() != null && !r.getByHour().isEmpty()) {
        sb.append("Horas: ")
            .append(r.getByHour().stream().sorted().map(String::valueOf)
                .reduce((a, b) -> a + ", " + b).orElse(""));
      }
      if (r.getByMinute() != null && !r.getByMinute().isEmpty()) {
        sb.append(" Minutos: ")
            .append(r.getByMinute().stream().sorted().map(String::valueOf)
                .reduce((a, b) -> a + ", " + b).orElse(""));
      }
      if (r.getBySecond() != null && !r.getBySecond().isEmpty()) {
        sb.append(" Segundos: ")
            .append(r.getBySecond().stream().sorted().map(String::valueOf)
                .reduce((a, b) -> a + ", " + b).orElse(""));
      }
      sb.append("\n");
    }

    // UNTIL / COUNT
    if (r.getUntilDate() != null) {
      sb.append("Até: ").append(r.getUntilDate()).append("\n");
    }
    if (r.getCountLimit() != null) {
      sb.append("Limite: ").append(r.getCountLimit())
          .append(" ocorrencia(s)\n");
    }

    // EXRULE (Exclusao)
    if (exclusao != null && exclusao.getFrequency() != null) {
      sb.append("\nExcecoes (EXRULE):\n");
      sb.append("Frequencia: ")
          .append(nomeFrequencia(exclusao.getFrequency()));
      if (exclusao.getIntervalValue() != null
          && exclusao.getIntervalValue() > 1) {
        sb.append(" (a cada ").append(exclusao.getIntervalValue())
            .append(")");
      }
      sb.append("\n");

      if (exclusao.getByDay() != null && !exclusao.getByDay().isEmpty()) {
        sb.append("Exceto: ")
            .append(exclusao.getByDay().stream()
                .map(PeriodicidadeFormatter::diaSemanaPT)
                .reduce((a, b) -> a + ", " + b).orElse(""));
        sb.append("\n");
      }

      if (exclusao.getUntilDate() != null) {
        sb.append("Excluir ate: ").append(exclusao.getUntilDate())
            .append("\n");
      }
    }

    // Exception Dates
    if (p.getExceptionDates() != null && !p.getExceptionDates().isEmpty()) {
      sb.append("\nDatas excluidas (EXDATE):\n");
      sb.append(p.getExceptionDates().stream().sorted().map(String::valueOf)
          .reduce((a, b) -> a + ", " + b).orElse(""));
      sb.append("\n");
    }

    // Include Dates
    if (p.getIncludeDates() != null && !p.getIncludeDates().isEmpty()) {
      sb.append("\nDatas incluidas (RDATE):\n");
      sb.append(p.getIncludeDates().stream().sorted().map(String::valueOf)
          .reduce((a, b) -> a + ", " + b).orElse(""));
      sb.append("\n");
    }

    return sb.toString();
  }

  /**
   * Obtém o nome da frequência por extenso em português.
   *
   * @param f a frequência a ser convertida (não pode ser null)
   * @return string representando a frequência por extenso
   */
  private static String nomeFrequencia(Frequencia f) {
    return switch (f) {
    case DIARIAMENTE -> "Diario";
    case SEMANALMENTE -> "Semanal";
    case MENSALMENTE -> "Mensal";
    case ANUALMENTE -> "Anual";
    };
  }
}
