package com.ia.core.quartz.service.model.scheduler.dto.triggers;

/**
 * Translator constants for SchedulerConfigTrigger DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the SchedulerConfigTrigger DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see SchedulerConfigTriggerDTO
 */
public final class SchedulerConfigTriggerTranslator {

    private SchedulerConfigTriggerTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        // Campos básicos do trigger
        public static final String TRIGGER_NAME = "scheduler.config.trigger.help.triggerName";
        public static final String SCHEDULER_NAME = "scheduler.config.trigger.help.schedulerName";
        public static final String TRIGGER_GROUP = "scheduler.config.trigger.help.triggerGroup";
        public static final String JOB_NAME = "scheduler.config.trigger.help.jobName";
        public static final String JOB_GROUP = "scheduler.config.trigger.help.jobGroup";
        public static final String DESCRIPTION = "scheduler.config.trigger.help.description";

        // Campos de tempo de execução
        public static final String NEXT_FIRE_TIME = "scheduler.config.trigger.help.nextFireTime";
        public static final String PREV_FIRE_TIME = "scheduler.config.trigger.help.prevFireTime";
        public static final String START_TIME = "scheduler.config.trigger.help.startTime";
        public static final String END_TIME = "scheduler.config.trigger.help.endTime";

        // Campos de configuração e estado
        public static final String PRIORITY = "scheduler.config.trigger.help.priority";
        public static final String TRIGGER_STATE = "scheduler.config.trigger.help.triggerState";
        public static final String TRIGGER_TYPE = "scheduler.config.trigger.help.triggerType";
        public static final String CALENDAR_NAME = "scheduler.config.trigger.help.calendarName";
        public static final String MISFIRE_INSTR = "scheduler.config.trigger.help.misFireInstr";
        public static final String JOB_DATA = "scheduler.config.trigger.help.jobData";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String TRIGGER_NAME_NOT_BLANK = "scheduler.validation.trigger.name.not.blank";
        public static final String JOB_NAME_NOT_BLANK = "scheduler.validation.job.name.not.blank";
        public static final String START_TIME_NOT_NULL = "scheduler.validation.start.time.not.null";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String TRIGGER_JA_EXISTENTE = "scheduler.rule.trigger.ja.existente";
        public static final String CONFLITO_HORARIO = "scheduler.rule.conflito.horario";
        public static final String JOB_NAO_ENCONTRADO = "scheduler.rule.job.nao.encontrado";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "scheduler.trigger.error.notfound";
        public static final String DUPLICATE = "scheduler.trigger.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "scheduler.trigger.message.created";
        public static final String UPDATED = "scheduler.trigger.message.updated";
        public static final String DELETED = "scheduler.trigger.message.deleted";
        public static final String TRIGGERED = "scheduler.trigger.message.triggered";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String TRIGGER_CRIADO = "scheduler.event.trigger.criado";
        public static final String TRIGGER_DISPARADO = "scheduler.event.trigger.disparado";
        public static final String TRIGGER_ERRO = "scheduler.event.trigger.erro";
    }

    /**
     * DTO class canonical name
     */
    public static final String SCHEDULER_CONFIG_CLASS = SchedulerConfigTriggerDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String TRIGGER_NAME = "scheduler.config.trigger.triggerName";
    public static final String SCHEDULER_NAME = "scheduler.config.trigger.schedulerName";
    public static final String TRIGGER_GROUP = "scheduler.config.trigger.triggerGroup";
    public static final String JOB_NAME = "scheduler.config.trigger.jobName";
    public static final String JOB_GROUP = "scheduler.config.trigger.jobGroup";
    public static final String DESCRIPTION = "scheduler.config.trigger.description";
    public static final String NEXT_FIRE_TIME = "scheduler.config.trigger.nextFireTime";
    public static final String PREV_FIRE_TIME = "scheduler.config.trigger.prevFireTime";
    public static final String START_TIME = "scheduler.config.trigger.startTime";
    public static final String END_TIME = "scheduler.config.trigger.endTime";
    public static final String PRIORITY = "scheduler.config.trigger.priority";
    public static final String TRIGGER_STATE = "scheduler.config.trigger.triggerState";
    public static final String TRIGGER_TYPE = "scheduler.config.trigger.triggerType";
    public static final String CALENDAR_NAME = "scheduler.config.trigger.calendarName";
    public static final String MISFIRE_INSTR = "scheduler.config.trigger.misFireInstr";
    public static final String JOB_DATA = "scheduler.config.trigger.jobData";
}
