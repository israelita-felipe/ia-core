package com.ia.core.quartz.service.model.scheduler.dto;

/**
 * Translator constants for SchedulerConfig DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the SchedulerConfig DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO
 */
public final class SchedulerConfigTranslator {

    private SchedulerConfigTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String SCHEDULER_CONFIG = "scheduler.config.help";
        public static final String PERIODICIDADE = "periodicidade.help";
        public static final String JOB_CLASS_NAME = "job.class.name.help";
    }

    /**
     * DTO class canonical name
     */
    public static final String SCHEDULER_CONFIG_CLASS = SchedulerConfigDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String SCHEDULER_CONFIG = "scheduler.config";
    public static final String PERIODICIDADE = "periodicidade";
    public static final String JOB_CLASS_NAME = "job.class.name";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String JOB_CLASS_NAME_REQUIRED = "validation.scheduler.jobClassName.required";
        public static final String JOB_CLASS_NAME_PATTERN = "validation.scheduler.jobClassName.pattern";
        public static final String PERIODICIDADE_REQUIRED = "validation.scheduler.periodicidade.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CLASSE_NAO_ENCONTRADA = "scheduler.rule.classe.nao.encontrada";
        public static final String PERIODICIDADE_INVALIDA = "scheduler.rule.periodicidade.invalida";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String AGENDADO_SUCESSO = "scheduler.message.agendado.sucesso";
        public static final String REMOVIDO_SUCESSO = "scheduler.message.removido.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String TAREFA_AGENDADA = "scheduler.event.tarefa.agendada";
        public static final String TAREFA_EXECUTADA = "scheduler.event.tarefa.executada";
    }
}
