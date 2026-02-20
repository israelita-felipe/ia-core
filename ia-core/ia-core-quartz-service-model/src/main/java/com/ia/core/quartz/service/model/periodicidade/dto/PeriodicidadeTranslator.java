package com.ia.core.quartz.service.model.periodicidade.dto;

/**
 * @author Israel Araújo
 */
@SuppressWarnings("javadoc")
public class PeriodicidadeTranslator {

  public static final class INTERVALO_TEMPORAL {
    public static final String INTERVALO = "periodicidade.intervaloBase";
    public static final String START_DATE = "periodicidade.intervaloBase.startDate";
    public static final String START_TIME = "periodicidade.intervaloBase.startTime";
    public static final String END_DATE = "periodicidade.intervaloBase.endDate";
    public static final String END_TIME = "periodicidade.intervaloBase.endTime";

    public static final class HELP {
      public static final String INTERVALO = "periodicidade.help.intervaloBase";
      public static final String START_DATE = "periodicidade.help.intervaloBase.startDate";
      public static final String START_TIME = "periodicidade.help.intervaloBase.startTime";
      public static final String END_DATE = "periodicidade.help.intervaloBase.endDate";
      public static final String END_TIME = "periodicidade.help.intervaloBase.endTime";
    }
  }

  public static final class RECORRENCIA {
    public static final String REGRA = "periodicidade.regra";
    public static final String FREQUENCY = "periodicidade.regra.frequency";
    public static final String INTERVAL_VALUE = "periodicidade.regra.intervalValue";
    public static final String BY_DAY = "periodicidade.regra.byDay";
    public static final String BY_MONTH_DAY = "periodicidade.regra.byMonthDay";
    public static final String BY_MONTH = "periodicidade.regra.byMonth";
    public static final String BY_SET_POSITION = "periodicidade.regra.bySetPosition";
    public static final String UNTIL_DATE = "periodicidade.regra.untilDate";
    public static final String COUNT_LIMIT = "periodicidade.regra.countLimit";
    // Novos campos RFC 5545
    public static final String WEEK_START_DAY = "periodicidade.regra.weekStartDay";
    public static final String BY_YEAR_DAY = "periodicidade.regra.byYearDay";
    public static final String BY_WEEK_NO = "periodicidade.regra.byWeekNo";
    public static final String BY_HOUR = "periodicidade.regra.byHour";
    public static final String BY_MINUTE = "periodicidade.regra.byMinute";
    public static final String BY_SECOND = "periodicidade.regra.bySecond";

    // Abas
    public static final String TAB_BASIC = "periodicidade.regra.tab.basic";
    public static final String TAB_MONTHLY = "periodicidade.regra.tab.monthly";
    public static final String TAB_ADVANCED = "periodicidade.regra.tab.advanced";
    public static final String TAB_TIME = "periodicidade.regra.tab.time";
    public static final String TAB_LIMITS = "periodicidade.regra.tab.limits";

    public static final class HELP {
      public static final String REGRA = "periodicidade.help.regra";
      public static final String FREQUENCY = "periodicidade.help.regra.frequency";
      public static final String INTERVAL_VALUE = "periodicidade.help.regra.intervalValue";
      public static final String BY_DAY = "periodicidade.help.regra.byDay";
      public static final String BY_MONTH_DAY = "periodicidade.help.regra.byMonthDay";
      public static final String BY_MONTH = "periodicidade.help.regra.byMonth";
      public static final String BY_SET_POSITION = "periodicidade.help.regra.bySetPosition";
      public static final String UNTIL_DATE = "periodicidade.help.regra.untilDate";
      public static final String COUNT_LIMIT = "periodicidade.help.regra.countLimit";
      // Novos campos RFC 5545
      public static final String WEEK_START_DAY = "periodicidade.help.regra.weekStartDay";
      public static final String BY_YEAR_DAY = "periodicidade.help.regra.byYearDay";
      public static final String BY_WEEK_NO = "periodicidade.help.regra.byWeekNo";
      public static final String BY_HOUR = "periodicidade.help.regra.byHour";
      public static final String BY_MINUTE = "periodicidade.help.regra.byMinute";
      public static final String BY_SECOND = "periodicidade.help.regra.bySecond";
    }
  }

  public static final class HELP {
    public static final String PERIODICIDADE = "periodicidade";
    public static final String periodicidade = "periodicidade.help";
    public static final String ATIVO = "periodicidade.help.ativo";
    public static final String INTERVALO_BASE = "periodicidade.help.intervaloBase";
    public static final String REGRA = "periodicidade.help.regra";
    public static final String ZONE_ID = "periodicidade.help.zoneId";
    public static final String EXCEPTION_DATES = "periodicidade.help.exceptionDates";
    public static final String INCLUDE_DATES = "periodicidade.help.includeDates";
  }

  public static final String periodicidade_CLASS = PeriodicidadeDTO.class
      .getCanonicalName();
  // Campos do Periodicidade original para compatibilidade
  public static final String PERIODICIDADE = "periodicidade";
  public static final String ATIVO = "periodicidade.ativo";
  public static final String DATA_INICIO = "periodicidade.dataInicio";
  public static final String DATA_FIM = "periodicidade.dataFim";
  public static final String HORA_INICIO = "periodicidade.horaInicio";
  public static final String HORA_FIM = "periodicidade.horaFim";
  // Novos campos
  public static final String periodicidade = "periodicidade";
  public static final String INTERVALO_BASE = "periodicidade.intervaloBase";
  public static final String REGRA = "periodicidade.regra";
  public static final String ZONE_ID = "periodicidade.zoneId";
  public static final String EXCEPTION_DATES = "periodicidade.exceptionDates";
  public static final String INCLUDE_DATES = "periodicidade.includeDates";

  // Abas
  public static final String TAB_BASIC = "periodicidade.tab.basic";
  public static final String TAB_INTERVAL = "periodicidade.tab.interval";
  public static final String TAB_RECURRENCE = "periodicidade.tab.recurrence";
  public static final String TAB_EXCEPTIONS = "periodicidade.tab.exceptions";

  public static final class VALIDATION {
    public static final String INTERVALO_BASE_REQUIRED = "validation.periodicidade.intervaloBase.required";
    public static final String START_TIME_REQUIRED = "validation.periodicidade.intervaloBase.startTime.required";
    public static final String END_TIME_REQUIRED = "validation.periodicidade.intervaloBase.endTime.required";
    public static final String FREQUENCY_REQUIRED = "validation.periodicidade.regra.frequency.required";
  }
}
