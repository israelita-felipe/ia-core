package com.ia.core.quartz.service.model.periodicidade.dto;

/**
 * Translator constants for Periodicidade (Periodicity) DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Periodicidade DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see PeriodicidadeDTO
 */
public final class PeriodicidadeTranslator {

    private PeriodicidadeTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String PERIODICIDADE = "periodicidade.help";
        public static final String ATIVO = "periodicidade.help.ativo";
        public static final String INTERVALO_BASE = "periodicidade.help.intervaloBase";
        public static final String START_DATE = "periodicidade.help.intervaloBase.startDate";
        public static final String START_TIME = "periodicidade.help.intervaloBase.startTime";
        public static final String END_DATE = "periodicidade.help.intervaloBase.endDate";
        public static final String END_TIME = "periodicidade.help.intervaloBase.endTime";
        public static final String REGRA = "periodicidade.help.regra";
        public static final String FREQUENCY = "periodicidade.help.regra.frequency";
        public static final String INTERVAL_VALUE = "periodicidade.help.regra.intervalValue";
        public static final String BY_DAY = "periodicidade.help.regra.byDay";
        public static final String BY_MONTH_DAY = "periodicidade.help.regra.byMonthDay";
        public static final String BY_MONTH = "periodicidade.help.regra.byMonth";
        public static final String BY_SET_POSITION = "periodicidade.help.regra.bySetPosition";
        public static final String UNTIL_DATE = "periodicidade.help.regra.untilDate";
        public static final String COUNT_LIMIT = "periodicidade.help.regra.countLimit";
        public static final String WEEK_START_DAY = "periodicidade.help.regra.weekStartDay";
        public static final String BY_YEAR_DAY = "periodicidade.help.regra.byYearDay";
        public static final String BY_WEEK_NO = "periodicidade.help.regra.byWeekNo";
        public static final String BY_HOUR = "periodicidade.help.regra.byHour";
        public static final String BY_MINUTE = "periodicidade.help.regra.byMinute";
        public static final String BY_SECOND = "periodicidade.help.regra.bySecond";
        public static final String ZONE_ID = "periodicidade.help.zoneId";
        public static final String EXCEPTION_DATES = "periodicidade.help.exceptionDates";
        public static final String INCLUDE_DATES = "periodicidade.help.includeDates";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String INTERVALO_BASE_REQUIRED = "periodicidade.validation.intervaloBase.required";
        public static final String START_DATE_REQUIRED = "periodicidade.validation.intervaloBase.startDate.required";
        public static final String START_TIME_REQUIRED = "periodicidade.validation.intervaloBase.startTime.required";
        public static final String END_TIME_REQUIRED = "periodicidade.validation.intervaloBase.endTime.required";
        public static final String FREQUENCY_REQUIRED = "periodicidade.validation.regra.frequency.required";
        public static final String COUNT_LIMIT = "periodicidade.validation.regra.countLimit.positive";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "periodicidade.error.notfound";
        public static final String DUPLICATE = "periodicidade.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "periodicidade.message.created";
        public static final String UPDATED = "periodicidade.message.updated";
        public static final String DELETED = "periodicidade.message.deleted";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String DATA_INICIO_MAIOR_DATA_FIM = "periodicidade.rule.dataInicio.maior.dataFim";
        public static final String INTERVALO_INVALIDO = "periodicidade.rule.intervalo.invalido";
        public static final String REGRA_INVALIDA = "periodicidade.rule.regra.invalida";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String PERIODICIDADE_CRIADA = "periodicidade.event.criada";
        public static final String PERIODICIDADE_ATUALIZADA = "periodicidade.event.atualizada";
    }

    /**
   * DTO class canonical name
   */
  public static final String PERIODICIDADE_CLASS = PeriodicidadeDTO.class.getCanonicalName();

  /**
   * Field name constants
   */
  public static final String PERIODICIDADE = "periodicidade";
  public static final String ATIVO = "periodicidade.ativo";
  public static final String DATA_INICIO = "periodicidade.dataInicio";
  public static final String DATA_FIM = "periodicidade.dataFim";
  public static final String HORA_INICIO = "periodicidade.horaInicio";
  public static final String HORA_FIM = "periodicidade.horaFim";
  public static final String INTERVALO_BASE = "periodicidade.intervaloBase";
  public static final String REGRA = "periodicidade.regra";
  public static final String ZONE_ID = "periodicidade.zoneId";
  public static final String EXCEPTION_DATES = "periodicidade.exceptionDates";
  public static final String INCLUDE_DATES = "periodicidade.includeDates";

  /**
   * Nested path constants for search filters.
   */
  public static final String INTERVALO_BASE_START_TIME = "periodicidade.intervaloBase.startTime";
  public static final String INTERVALO_BASE_END_TIME = "periodicidade.intervaloBase.endTime";
  public static final String REGRA_FREQUENCY = "periodicidade.regra.frequency";
  public static final String REGRA_INTERVAL_VALUE = "periodicidade.regra.intervalValue";

  /**
   * Recorrência field constant.
   */
  public static final String RECORRENCIA_FIELD = "periodicidade.recorrencia";

  /**
   * Recorrência nested constants.
   */
  public static final class RECORRENCIA {
    public static final String FREQUENCY = "frequencia";
    public static final String INTERVAL_VALUE = "intervalValue";
    public static final String BY_DAY = "byDay";
    public static final String BY_MONTH_DAY = "byMonthDay";
    public static final String BY_MONTH = "byMonth";
    public static final String BY_SET_POSITION = "bySetPosition";
    public static final String WEEK_START_DAY = "weekStartDay";
    public static final String BY_YEAR_DAY = "byYearDay";
    public static final String BY_WEEK_NO = "byWeekNo";
    public static final String BY_HOUR = "byHour";
    public static final String BY_MINUTE = "byMinute";
    public static final String BY_SECOND = "bySecond";
    public static final String UNTIL_DATE = "untilDate";
    public static final String COUNT_LIMIT = "countLimit";

    public static final class HELP {
      public static final String FREQUENCY = "periodicidade.recorrencia.help.frequencia";
      public static final String INTERVAL_VALUE = "periodicidade.recorrencia.help.intervalValue";
      public static final String BY_DAY = "periodicidade.recorrencia.help.byDay";
      public static final String BY_MONTH_DAY = "periodicidade.recorrencia.help.byMonthDay";
      public static final String BY_MONTH = "periodicidade.recorrencia.help.byMonth";
      public static final String BY_SET_POSITION = "periodicidade.recorrencia.help.bySetPosition";
      public static final String WEEK_START_DAY = "periodicidade.recorrencia.help.weekStartDay";
      public static final String BY_YEAR_DAY = "periodicidade.recorrencia.help.byYearDay";
      public static final String BY_WEEK_NO = "periodicidade.recorrencia.help.byWeekNo";
      public static final String BY_HOUR = "periodicidade.recorrencia.help.byHour";
      public static final String BY_MINUTE = "periodicidade.recorrencia.help.byMinute";
      public static final String BY_SECOND = "periodicidade.recorrencia.help.bySecond";
      public static final String UNTIL_DATE = "periodicidade.recorrencia.help.untilDate";
      public static final String COUNT_LIMIT = "periodicidade.recorrencia.help.countLimit";
    }

    public static final class TAB {
      public static final String MONTHLY = "periodicidade.recorrencia.tab.mensal";
      public static final String ADVANCED = "periodicidade.recorrencia.tab.avancado";
      public static final String TIME = "periodicidade.recorrencia.tab.horario";
      public static final String LIMITS = "periodicidade.recorrencia.tab.limites";
    }
  }

  /**
   * Tab constants for UI.
   */
  public static final String TAB_BASIC = "periodicidade.tab.basic";
  public static final String TAB_INTERVAL = "periodicidade.tab.interval";
  public static final String TAB_RECURRENCE = "periodicidade.tab.recurrence";
  public static final String TAB_EXCEPTIONS = "periodicidade.tab.exceptions";
}
