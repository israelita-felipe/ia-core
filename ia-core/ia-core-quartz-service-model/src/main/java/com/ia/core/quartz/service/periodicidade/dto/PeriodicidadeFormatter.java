package com.ia.core.quartz.service.periodicidade.dto;

import static com.cronutils.model.field.expression.FieldExpressionFactory.always;
import static com.cronutils.model.field.expression.FieldExpressionFactory.between;
import static com.cronutils.model.field.expression.FieldExpressionFactory.every;
import static com.cronutils.model.field.expression.FieldExpressionFactory.on;
import static com.cronutils.model.field.expression.FieldExpressionFactory.questionMark;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import com.cronutils.builder.CronBuilder;
import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.model.field.expression.FieldExpressionFactory;
import com.cronutils.model.field.value.SpecialChar;
import com.ia.core.quartz.model.periodicidade.OcorrenciaSemanal;
import com.ia.core.service.translator.Translator;

/**
 * Classe utilitária para formatação e criação de expressões Cron a partir de
 * DTOs de periodicidade. Fornece métodos para gerar expressões Cron no formato
 * Quartz e descrições humanizadas em diferentes idiomas.
 * <p>
 * Esta classe suporta configurações complexas de agendamento incluindo:
 * <ul>
 * <li>Expressões de tempo com hora de início, fim e intervalo</li>
 * <li>Configurações de dias da semana, dias do mês e ocorrências semanais</li>
 * <li>Períodos mensais e anuais</li>
 * <li>Múltiplos padrões de repetição</li>
 * </ul>
 *
 * @see PeriodicidadeDTO
 * @see Cron
 * @see CronBuilder
 * @author Israel Araújo
 */
public class PeriodicidadeFormatter {

  /**
   * Construtor privado para prevenir instanciação da classe utilitária.
   */
  private PeriodicidadeFormatter() {
    throw new UnsupportedOperationException("Classe utilitária não pode ser instanciada");
  }

  /**
   * Formata a periodicidade em uma descrição humanizada no idioma do tradutor.
   * <p>
   * Exemplo de retorno: "Todos os dias às 10:30" ou "Toda segunda-feira às
   * 14:00"
   *
   * @param periodicidade DTO contendo as configurações de periodicidade
   * @param translator    Tradutor para internacionalização da descrição
   * @return Descrição humanizada da expressão Cron
   * @throws IllegalArgumentException se periodicidade for nula
   * @see CronDescriptor
   */
  public static String format(PeriodicidadeDTO periodicidade,
                              Translator translator) {
    if (periodicidade == null) {
      throw new IllegalArgumentException("PeriodicidadeDTO não pode ser nulo");
    }

    Cron cron = createCron(periodicidade);
    CronDescriptor descriptor = CronDescriptor
        .instance(translator.getLocale());
    return descriptor.describe(cron);
  }

  /**
   * Cria uma expressão Cron a partir das configurações de periodicidade.
   * <p>
   * Utiliza a definição Cron do Quartz que suporta segundos e é mais flexível
   * que a definição padrão UNIX.
   *
   * @param periodicidade DTO contendo as configurações de periodicidade
   * @return Expressão Cron configurada
   * @throws IllegalArgumentException se periodicidade for nula
   * @see CronType#QUARTZ
   */
  public static Cron createCron(PeriodicidadeDTO periodicidade) {
    if (periodicidade == null) {
      throw new IllegalArgumentException("PeriodicidadeDTO não pode ser nulo");
    }

    CronBuilder cronBuilder = CronBuilder
        .cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));

    configureTimeExpression(cronBuilder, periodicidade);
    configureDayExpression(cronBuilder, periodicidade);
    configureMonthExpression(cronBuilder, periodicidade);
    configureYearExpression(cronBuilder, periodicidade);

    return cronBuilder.instance();
  }

  /**
   * Converte a periodicidade em uma string de expressão Cron no formato Quartz.
   * <p>
   * Exemplo de retorno: "0 30 10 * * ? *" para execução diária às 10:30
   *
   * @param periodicidade DTO contendo as configurações de periodicidade
   * @return String da expressão Cron no formato Quartz
   * @throws IllegalArgumentException se periodicidade for nula
   */
  public static String asCronExpression(PeriodicidadeDTO periodicidade) {
    if (periodicidade == null) {
      throw new IllegalArgumentException("PeriodicidadeDTO não pode ser nulo");
    }

    return createCron(periodicidade).asString();
  }

  // ========== CONFIGURAÇÃO DE TEMPO (Hora, Minuto, Segundo) ==========

  /**
   * Configura as expressões de tempo (hora, minuto, segundo) no builder Cron.
   *
   * @param cronBuilder   Builder Cron para configuração
   * @param periodicidade DTO contendo as configurações de tempo
   */
  private static void configureTimeExpression(CronBuilder cronBuilder,
                                              PeriodicidadeDTO periodicidade) {
    TimeExpressionBuilder timeBuilder = new TimeExpressionBuilder(periodicidade);

    cronBuilder.withSecond(timeBuilder.buildSecondExpression());
    cronBuilder.withMinute(timeBuilder.buildMinuteExpression());
    cronBuilder.withHour(timeBuilder.buildHourExpression());
  }

  /**
   * Builder interno para construção de expressões de tempo (segundo, minuto,
   * hora).
   * <p>
   * Responsável por lidar com:
   * <ul>
   * <li>Horários de início e fim</li>
   * <li>Intervalos de repetição</li>
   * <li>Validação de valores dentro dos limites aceitáveis</li>
   * </ul>
   */
  private static class TimeExpressionBuilder {
    /** Periodicidade utilizada */
    private final PeriodicidadeDTO periodicidade;

    /**
     * Constrói um novo TimeExpressionBuilder para a periodicidade especificada.
     *
     * @param periodicidade DTO contendo as configurações de tempo
     */
    public TimeExpressionBuilder(PeriodicidadeDTO periodicidade) {
      this.periodicidade = periodicidade;
    }

    /**
     * Constrói a expressão para segundos.
     *
     * @return Expressão Cron para segundos
     */
    public FieldExpression buildSecondExpression() {
      return buildTimeFieldExpression(periodicidade.getHoraInicio(),
                                      periodicidade.getHoraFim(),
                                      periodicidade.getTempoIntervalo(),
                                      TimeField.SECOND);
    }

    /**
     * Constrói a expressão para minutos.
     *
     * @return Expressão Cron para minutos
     */
    public FieldExpression buildMinuteExpression() {
      return buildTimeFieldExpression(periodicidade.getHoraInicio(),
                                      periodicidade.getHoraFim(),
                                      periodicidade.getTempoIntervalo(),
                                      TimeField.MINUTE);
    }

    /**
     * Constrói a expressão para horas.
     *
     * @return Expressão Cron para horas
     */
    public FieldExpression buildHourExpression() {
      return buildTimeFieldExpression(periodicidade.getHoraInicio(),
                                      periodicidade.getHoraFim(),
                                      periodicidade.getTempoIntervalo(),
                                      TimeField.HOUR);
    }

    /**
     * Constrói uma expressão para um campo de tempo específico.
     *
     * @param startTime Hora de início, pode ser nulo
     * @param endTime   Hora de fim, pode ser nulo
     * @param interval  Intervalo de repetição, pode ser nulo
     * @param field     Tipo de campo de tempo (segundo, minuto, hora)
     * @return Expressão Cron configurada para o campo especificado
     */
    private FieldExpression buildTimeFieldExpression(LocalTime startTime,
                                                     LocalTime endTime,
                                                     LocalTime interval,
                                                     TimeField field) {
      if (interval == null) {
        // se não há intervalo, considera apenas o início
        endTime = null;
      }
      FieldExpression expression = on(0);

      if (startTime != null) {
        int startValue = field.getValue(startTime);

        if (endTime != null) {
          int endValue = field.getValue(endTime);
          if (endValue != 0) {
            expression = between(startValue, endValue);
          } else {
            expression = on(startValue);
          }
        } else {
          expression = on(startValue);
        }
      } else if (endTime != null) {
        int endValue = field.getValue(endTime);
        if (endValue != 0) {
          expression = between(0, endValue);
        }
      }

      return applyIntervalIfNeeded(expression, interval, field);
    }

    /**
     * Aplica intervalo de repetição à expressão se especificado e válido.
     *
     * @param expression Expressão base
     * @param interval   Intervalo a ser aplicado
     * @param field      Tipo de campo de tempo
     * @return Expressão com intervalo aplicado, ou a expressão original se
     *         intervalo for inválido
     */
    private FieldExpression applyIntervalIfNeeded(FieldExpression expression,
                                                  LocalTime interval,
                                                  TimeField field) {
      if (interval != null) {
        int intervalValue = field.getValue(interval);
        if (field.isValidInterval(intervalValue)) {
          expression = every(expression, intervalValue);
        }
      }
      return expression;
    }

    /**
     * Enumeração que representa os diferentes campos de tempo e suas
     * validações.
     */
    private enum TimeField {
      /** Campo de segundos (0-59) */
      SECOND {
        @Override
        public int getValue(LocalTime time) {
          return time.getSecond();
        }

        @Override
        public boolean isValidInterval(int interval) {
          return interval >= 1 && interval <= 59;
        }
      },

      /** Campo de minutos (0-59) */
      MINUTE {
        @Override
        public int getValue(LocalTime time) {
          return time.getMinute();
        }

        @Override
        public boolean isValidInterval(int interval) {
          return interval >= 1 && interval <= 59;
        }
      },

      /** Campo de horas (0-23) */
      HOUR {
        @Override
        public int getValue(LocalTime time) {
          return time.getHour();
        }

        @Override
        public boolean isValidInterval(int interval) {
          return interval >= 1 && interval <= 23;
        }
      };

      /**
       * Extrai o valor do campo de tempo do LocalTime.
       *
       * @param time Tempo do qual extrair o valor
       * @return Valor do campo específico
       */
      public abstract int getValue(LocalTime time);

      /**
       * Valida se o intervalo é aceitável para este campo de tempo.
       *
       * @param interval Intervalo a validar
       * @return true se o intervalo estiver dentro dos limites aceitáveis
       */
      public abstract boolean isValidInterval(int interval);
    }
  }

  // ========== CONFIGURAÇÃO DE DIAS ==========

  /**
   * Configura as expressões de dias (dia do mês e dia da semana) no builder
   * Cron.
   *
   * @param cronBuilder   Builder Cron para configuração
   * @param periodicidade DTO contendo as configurações de dias
   */
  private static void configureDayExpression(CronBuilder cronBuilder,
                                             PeriodicidadeDTO periodicidade) {
    DayExpressionBuilder dayBuilder = new DayExpressionBuilder(periodicidade);

    cronBuilder.withDoM(dayBuilder.buildDayOfMonthExpression());
    cronBuilder.withDoW(dayBuilder.buildDayOfWeekExpression());
  }

  /**
   * Builder interno para construção de expressões de dias (dia do mês e dia da
   * semana).
   * <p>
   * Prioridades na configuração:
   * <ol>
   * <li>Ocorrências diárias específicas (ex: dias 1, 15 do mês)</li>
   * <li>Dias da semana com ocorrências (ex: segunda#1 - primeira
   * segunda-feira)</li>
   * <li>Intervalo de datas</li>
   * </ol>
   */
  private static class DayExpressionBuilder {
    /** Periodicidade utilizada */
    private final PeriodicidadeDTO periodicidade;

    /**
     * Constrói um novo DayExpressionBuilder para a periodicidade especificada.
     *
     * @param periodicidade DTO contendo as configurações de dias
     */
    public DayExpressionBuilder(PeriodicidadeDTO periodicidade) {
      this.periodicidade = periodicidade;
    }

    /**
     * Constrói a expressão para dias do mês.
     * <p>
     * Quando ocorrências diárias são especificadas, o dia da semana deve ser
     * "?" conforme regras do Cron.
     *
     * @return Expressão Cron para dias do mês
     */
    public FieldExpression buildDayOfMonthExpression() {
      // Se há dias da semana especificados, força "?" no dia do mês
      if (!periodicidade.getDias().isEmpty()) {
        return questionMark();
      }

      FieldExpression expression = buildDateRangeExpression();

      if (!periodicidade.getOcorrenciaDiaria().isEmpty()) {
        expression = combineWithDailyOccurrences(expression);
      } else if (expression == null) {
        expression = always();
      }
      return expression;
    }

    /**
     * Constrói a expressão para dias da semana.
     * <p>
     * Quando ocorrências diárias são especificadas, retorna "?" pois os dois
     * campos (dia do mês e dia da semana) são mutualmente exclusivos no Cron.
     *
     * @return Expressão Cron para dias da semana
     */
    public FieldExpression buildDayOfWeekExpression() {
      boolean hasDayOfMonth = !periodicidade.getOcorrenciaDiaria().isEmpty()
          || buildDateRangeExpression() != null;

      if (hasDayOfMonth) {
        if (!periodicidade.getDias().isEmpty()) {
          return buildDaysOfWeekExpression();
        }
        return questionMark();
      }

      return questionMark();
    }

    /**
     * Constrói expressão de intervalo baseada nas datas de início e fim.
     *
     * @return Expressão de intervalo ou null se não houver datas
     */
    private FieldExpression buildDateRangeExpression() {
      LocalDate startDate = periodicidade.getDataInicio();
      LocalDate endDate = periodicidade.getDataFim();

      if (startDate != null) {
        if (endDate != null) {
          return between(startDate.getDayOfMonth(),
                         endDate.getDayOfMonth());
        } else {
          return on(startDate.getDayOfMonth());
        }
      }
      return null;
    }

    /**
     * Combina expressão existente com ocorrências diárias.
     *
     * @param existingExpression Expressão existente (pode ser null)
     * @return Expressão combinada ou apenas ocorrências diárias se expressão
     *         existente for null
     */
    private FieldExpression combineWithDailyOccurrences(FieldExpression existingExpression) {
      FieldExpression dailyExpression = buildIntegerExpressionFromSet(periodicidade
          .getOcorrenciaDiaria());

      return existingExpression != null ? existingExpression
          .and(dailyExpression) : dailyExpression;
    }

    /**
     * Constrói expressão para múltiplos dias da semana.
     *
     * @return Expressão combinada para todos os dias da semana especificados
     */
    private FieldExpression buildDaysOfWeekExpression() {
      return buildExpressionFromSet(periodicidade
          .getDias(), day -> buildSingleDayOfWeekExpression(day, periodicidade.getOcorrenciaSemanal()));
    }

    /**
     * Constrói expressão para um único dia da semana, possivelmente com
     * ocorrências.
     * <p>
     * Exemplos:
     * <ul>
     * <li>Segunda-feira: "2"</li>
     * <li>Primeira segunda-feira: "2#1"</li>
     * <li>Primeira e terceira segunda-feira: "2#1,2#3"</li>
     * </ul>
     *
     * @param day         Dia da semana
     * @param occurrences Ocorrências semanais (ex: primeira, segunda, etc.)
     * @return Expressão Cron para o dia da semana especificado
     */
    private FieldExpression buildSingleDayOfWeekExpression(DayOfWeek day,
                                                           Set<OcorrenciaSemanal> occurrences) {
      int quartzDay = convertToQuartzDayOfWeek(day);

      if (occurrences.isEmpty()) {
        return on(quartzDay);
      }

      return buildExpressionFromSet(occurrences,
                                    occurrence -> buildDayWithOccurrence(quartzDay,
                                                                         occurrence));
    }

    /**
     * Constrói expressão para dia da semana com ocorrência específica.
     * <p>
     * Formato: "DIA#OCORRENCIA" (ex: "2#1" para primeira segunda-feira)
     *
     * @param day        Dia no formato Quartz (1=Domingo, 2=Segunda, ...,
     *                   7=Sábado)
     * @param occurrence Ocorrência semanal
     * @return Expressão Cron formatada
     */
    private FieldExpression buildDayWithOccurrence(int day,
                                                   OcorrenciaSemanal occurrence) {
      return on(day, SpecialChar.HASH, occurrence.getCodigo());
    }

    /**
     * Converte DayOfWeek para formato Quartz.
     * <p>
     * Conversão:
     * <ul>
     * <li>DayOfWeek: Segunda=1, Terça=2, ..., Domingo=7</li>
     * <li>Quartz: Domingo=1, Segunda=2, ..., Sábado=7</li>
     * </ul>
     *
     * @param day Dia da semana no formato Java
     * @return Dia da semana no formato Quartz
     */
    private int convertToQuartzDayOfWeek(DayOfWeek day) {
      int javaValue = day.getValue(); // Segunda=1,...,Domingo=7
      // Converter Java para Quartz
      return javaValue == 7 ? 1 : javaValue + 1;
    }
  }

  // ========== CONFIGURAÇÃO DE MESES E ANOS ==========

  /**
   * Configura a expressão de meses no builder Cron.
   *
   * @param cronBuilder   Builder Cron para configuração
   * @param periodicidade DTO contendo as configurações de meses
   */
  private static void configureMonthExpression(CronBuilder cronBuilder,
                                               PeriodicidadeDTO periodicidade) {
    MonthExpressionBuilder monthBuilder = new MonthExpressionBuilder(periodicidade);
    cronBuilder.withMonth(monthBuilder.buildMonthExpression());
  }

  /**
   * Builder interno para construção de expressões de meses.
   */
  private static class MonthExpressionBuilder {
    /** Periodicidade utilizada */
    private final PeriodicidadeDTO periodicidade;

    /**
     * Construtor padrão
     *
     * @param periodicidade {@link PeriodicidadeDTO}
     */
    public MonthExpressionBuilder(PeriodicidadeDTO periodicidade) {
      this.periodicidade = periodicidade;
    }

    /**
     * Constrói a expressão para meses. Junta meses específicos e intervalo de
     * datas.
     *
     * @return {@link FieldExpression} resultante
     */
    public FieldExpression buildMonthExpression() {
      // Construir as expressões de meses específicos e intervalo
      FieldExpression specificMonths = buildSpecifiedMonthsExpression();
      FieldExpression intervalMonths = buildIntervalMonthsExpression();

      // Combinar as expressões conforme disponibilidade
      if (specificMonths != null && intervalMonths != null) {
        return specificMonths.and(intervalMonths);
      } else if (specificMonths != null) {
        return specificMonths;
      } else if (intervalMonths != null) {
        return intervalMonths;
      } else {
        return always();
      }
    }

    /**
     * @return {@link FieldExpression} com o dado intervalo
     */
    public FieldExpression buildIntervalMonthsExpression() {
      // Se não há meses específicos, verifica intervalo de datas
      LocalDate startDate = periodicidade.getDataInicio();
      LocalDate endDate = periodicidade.getDataFim();

      if (startDate != null && endDate != null) {
        int startMonth = startDate.getMonthValue();
        int endMonth = endDate.getMonthValue();

        // Para intervalo normal (não cruza o ano)
        if (startMonth <= endMonth) {
          return between(startMonth, endMonth);
        } else {
          // Intervalo que cruza o ano - divide em duas partes
          FieldExpression firstPart = between(startMonth, 12);
          FieldExpression secondPart = between(1, endMonth);
          return firstPart.and(secondPart);
        }
      } else if (startDate != null) {
        return on(startDate.getMonthValue());
      } else if (endDate != null) {
        return on(endDate.getMonthValue());
      }
      return null;
    }

    /**
     * Constrói expressão para meses específicos.
     *
     * @return {@link FieldExpression} resultante
     */
    private FieldExpression buildSpecifiedMonthsExpression() {
      return buildExpressionFromSet(periodicidade.getMeses(),
                                    month -> on(month.getValue()));
    }
  }

  /**
   * Configura a expressão de anos no builder Cron.
   * <p>
   * Utiliza intervalo entre datas de início e fim, ou ano específico se apenas
   * data de início for especificada.
   *
   * @param cronBuilder   Builder Cron para configuração
   * @param periodicidade DTO contendo as configurações de anos
   */
  private static void configureYearExpression(CronBuilder cronBuilder,
                                              PeriodicidadeDTO periodicidade) {
    LocalDate startDate = periodicidade.getDataInicio();
    LocalDate endDate = periodicidade.getDataFim();

    if (startDate != null) {
      int startYear = startDate.getYear();
      FieldExpression yearExpression = (endDate != null) ? FieldExpressionFactory
          .between(startYear, endDate.getYear()) : on(startYear);

      cronBuilder.withYear(yearExpression);
    } else {
      cronBuilder.withYear(always());
    }
  }

  // ========== MÉTODOS UTILITÁRIOS GENÉRICOS ==========

  /**
   * Constrói expressão Cron a partir de um conjunto de inteiros.
   * <p>
   * Exemplo: {1, 15} → "1,15"
   *
   * @param items Conjunto de inteiros
   * @return Expressão Cron combinando todos os inteiros
   */
  private static FieldExpression buildIntegerExpressionFromSet(Set<Integer> items) {
    FieldExpression expression = null;
    for (Integer item : items) {
      FieldExpression itemExpression = on(item);
      expression = (expression == null) ? itemExpression
                                        : expression.and(itemExpression);
    }
    return expression;
  }

  /**
   * Constrói expressão Cron a partir de um conjunto de objetos usando um
   * mapper.
   * <p>
   * Método genérico para construir expressões a partir de qualquer conjunto
   * convertendo cada item através de uma função mapper.
   *
   * @param <T>    Tipo dos itens no conjunto
   * @param items  Conjunto de itens
   * @param mapper Função para mapear cada item para uma expressão
   *               FieldExpression
   * @return Expressão Cron combinando todas as expressões mapeadas
   */
  private static <T> FieldExpression buildExpressionFromSet(Set<T> items,
                                                            FieldExpressionMapper<T> mapper) {
    FieldExpression expression = null;
    for (T item : items) {
      FieldExpression itemExpression = mapper.map(item);
      expression = (expression == null) ? itemExpression
                                        : expression.and(itemExpression);
    }
    return expression;
  }

  /**
   * Interface funcional para mapeamento de objetos para expressões
   * FieldExpression.
   *
   * @param <T> Tipo do objeto a ser mapeado
   */
  @FunctionalInterface
  private interface FieldExpressionMapper<T> {
    /**
     * Mapeia um objeto para uma expressão FieldExpression.
     *
     * @param item Objeto a ser mapeado
     * @return Expressão FieldExpression correspondente
     */
    FieldExpression map(T item);
  }
}
