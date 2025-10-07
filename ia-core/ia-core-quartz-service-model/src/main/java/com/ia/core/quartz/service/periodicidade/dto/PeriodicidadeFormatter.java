package com.ia.core.quartz.service.periodicidade.dto;

import static com.cronutils.model.field.expression.FieldExpressionFactory.always;
import static com.cronutils.model.field.expression.FieldExpressionFactory.on;
import static com.cronutils.model.field.expression.FieldExpressionFactory.questionMark;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
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
 *
 */
public class PeriodicidadeFormatter {

  public static String format(PeriodicidadeDTO periodicidade,
                              Translator translator) {
    Cron cron = createCron(periodicidade);

    CronDescriptor descriptor = CronDescriptor
        .instance(translator.getLocale());
    return descriptor.describe(cron);
  }

  public static Cron createCron(PeriodicidadeDTO periodicidade) {
    // Usa definição do Quartz que suporta segundos e é mais flexível :cite[1]
    CronBuilder cronBuilder = CronBuilder
        .cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));

    // configura o tempo
    configureTimeFields(cronBuilder, periodicidade);

    // Configura dia do mês e dia da semana com base nos campos disponíveis
    configureDayFields(cronBuilder, periodicidade);

    // Configura meses
    configureMonthFields(cronBuilder, periodicidade);

    // Configura anos
    configureYearsFields(cronBuilder, periodicidade);

    return cronBuilder.instance();
  }

  /**
   * @param cronBuilder
   * @param periodicidade
   */
  protected static void configureTimeFields(CronBuilder cronBuilder,
                                            PeriodicidadeDTO periodicidade) {
    // Configura minutos e horas
    if (periodicidade.getHoraInicio() != null) {
      int secondInicio = periodicidade.getHoraInicio().getSecond();
      int minuteInicio = periodicidade.getHoraInicio().getMinute();
      int hourInicio = periodicidade.getHoraInicio().getHour();
      cronBuilder.withSecond(on(secondInicio));
      cronBuilder.withMinute(on(minuteInicio));
      cronBuilder.withHour(on(hourInicio));
    } else {
      // Valores padrão se horaInicio for nulo
      cronBuilder.withSecond(on(0));
      cronBuilder.withMinute(on(0));
      cronBuilder.withHour(on(0));
    }
  }

  /**
   * @param cronBuilder
   * @param periodicidade
   */
  protected static void configureYearsFields(CronBuilder cronBuilder,
                                             PeriodicidadeDTO periodicidade) {
    LocalDate dataFim = periodicidade.getDataFim();
    LocalDate dataInicio = periodicidade.getDataInicio();
    if (dataInicio != null) {
      Integer anoInicio = dataInicio.getYear();
      if (dataFim != null) {
        Integer anoFim = dataFim.getYear();
        cronBuilder
            .withYear(FieldExpressionFactory.between(anoInicio, anoFim));
      } else {
        cronBuilder.withYear(on(anoInicio));
      }
    } else {
      cronBuilder.withYear(always());
    }
  }

  private static void configureDayFields(CronBuilder cronBuilder,
                                         PeriodicidadeDTO periodicidade) {
    Set<Integer> ocorrenciaDiaria = periodicidade.getOcorrenciaDiaria();
    Set<DayOfWeek> dias = periodicidade.getDias();
    Set<OcorrenciaSemanal> ocorrenciaSemanal = periodicidade
        .getOcorrenciaSemanal();

    // Prioridade: ocorrenciaDiaria > dias + ocorrenciaSemanal
    if (!ocorrenciaDiaria.isEmpty()) {
      // Usa dias específicos do mês (ex: 1,15 para dias 1 e 15) :cite[1]
      FieldExpression dayOfMonth = buildFieldExpressionFromSet(ocorrenciaDiaria);
      cronBuilder.withDoM(dayOfMonth);
      cronBuilder.withDoW(questionMark()); // Dia da semana deve ser ? quando
                                           // dia do mês é especificado
    } else if (!dias.isEmpty()) {
      // Usa dias da semana com possíveis ocorrências (ex: 2#1 para primeira
      // segunda-feira) :cite[3]
      FieldExpression dayOfWeek = buildDayOfWeekExpression(dias,
                                                           ocorrenciaSemanal);
      cronBuilder.withDoM(questionMark()); // Dia do mês deve ser ? quando dia
                                           // da semana é especificado
      cronBuilder.withDoW(dayOfWeek);
    } else {
      // Se nenhum dia for especificado, assume todos os dias do mês
      cronBuilder.withDoM(always());
      cronBuilder.withDoW(questionMark());
    }
  }

  private static void configureMonthFields(CronBuilder cronBuilder,
                                           PeriodicidadeDTO periodicidade) {
    Set<Month> meses = periodicidade.getMeses();
    LocalDate startDate = periodicidade.getDataInicio();
    LocalDate endDate = periodicidade.getDataFim();
    FieldExpression monthExpression = always();
    if (!meses.isEmpty()) {
      monthExpression = buildMonthExpression(meses);
    }
    if (startDate != null) {
      if (endDate != null) {
        monthExpression = monthExpression.and(FieldExpressionFactory
            .between(startDate.getMonthValue(), endDate.getMonthValue()));
      } else {
        monthExpression = monthExpression
            .and(FieldExpressionFactory.on(startDate.getMonthValue()));
      }
    }
    cronBuilder.withMonth(monthExpression);
  }

  private static FieldExpression buildFieldExpressionFromSet(Set<Integer> values) {
    FieldExpression expression = null;
    for (Integer value : values) {
      if (expression == null) {
        expression = on(value);
      } else {
        expression = expression.and(on(value)); // Usa AND para criar lista com
                                                // vírgulas :cite[7]
      }
    }
    return expression;
  }

  private static FieldExpression buildDayOfWeekExpression(Set<DayOfWeek> dias,
                                                          Set<OcorrenciaSemanal> ocorrenciaSemanal) {
    FieldExpression expression = null;

    for (DayOfWeek dia : dias) {
      FieldExpression diaExpression = buildSingleDayOfWeekExpression(dia,
                                                                     ocorrenciaSemanal);

      if (expression == null) {
        expression = diaExpression;
      } else {
        expression = expression.and(diaExpression); // Combina múltiplos dias
                                                    // com vírgulas :cite[7]
      }
    }

    return expression;
  }

  private static FieldExpression buildSingleDayOfWeekExpression(DayOfWeek dia,
                                                                Set<OcorrenciaSemanal> ocorrenciaSemanal) {
    int quartzDayOfWeek = convertDayOfWeekToQuartz(dia);

    if (ocorrenciaSemanal.isEmpty()) {
      return on(quartzDayOfWeek); // Returns a simple day-of-week expression
                                  // (e.g., "2")
    }

    // For expressions with occurrences (e.g., "2#1")
    FieldExpression expression = null;
    for (OcorrenciaSemanal ocorrencia : ocorrenciaSemanal) {
      // Create a new FieldExpression for each "day#week" combination
      FieldExpression occExpression = FieldExpressionFactory
          .on(quartzDayOfWeek).and(on(SpecialChar.HASH))
          .and(on(ocorrencia.getCodigo()));

      // Use 'or' to combine multiple occurrences if needed (e.g., "2#1,2#3")
      if (expression == null) {
        expression = occExpression;
      } else {
        expression = expression.and(occExpression);
      }
    }
    return expression;
  }

  private static FieldExpression buildMonthExpression(Set<Month> meses) {
    FieldExpression expression = null;
    for (Month month : meses) {
      if (expression == null) {
        expression = on(month.getValue()); // Month enum já retorna 1-12
      } else {
        expression = expression.and(on(month.getValue()));
      }
    }
    return expression;
  }

  /**
   * Converte DayOfWeek para formato Quartz (Domingo=1, Segunda=2, ...,
   * Sábado=7) DayOfWeek: Segunda=1, Domingo=7 → Quartz: Domingo=1, Segunda=2,
   * ..., Sábado=7
   */
  private static int convertDayOfWeekToQuartz(DayOfWeek dia) {
    return dia.getValue() == 7 ? 1 : dia.getValue() + 1;
  }

  public static String asCronExpression(PeriodicidadeDTO periodicidade) {
    Cron cron = createCron(periodicidade);
    return cron.asString();
  }
}
