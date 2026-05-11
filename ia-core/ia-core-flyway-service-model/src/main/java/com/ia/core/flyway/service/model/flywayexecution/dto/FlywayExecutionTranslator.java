package com.ia.core.flyway.service.model.flywayexecution.dto;

/**
 * Translator constants for FlywayExecution DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the FlywayExecution DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.flyway.service.model.flywayexecution.dto.FlywayExecutionDTO
 */
public final class FlywayExecutionTranslator {

    private FlywayExecutionTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String FLYWAY_EXECUTION = "flyway.execution.help";
        public static final String VERSION = "flyway.execution.help.migration.version";
        public static final String DESCRIPTION = "flyway.execution.help.description";
        public static final String TYPE = "flyway.execution.help.type";
        public static final String SCRIPT = "flyway.execution.help.script";
        public static final String CHECKSUM = "flyway.execution.help.checksum";
        public static final String INSTALLED_BY = "flyway.execution.help.installed.by";
        public static final String INSTALLED_ON = "flyway.execution.help.installed.on";
        public static final String EXECUTION_TIME = "flyway.execution.help.execution.time";
        public static final String SUCCESS = "flyway.execution.help.success";
    }

    /**
     * DTO class canonical name
     */
    public static final String FLYWAY_EXECUTION_CLASS = FlywayExecutionDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String FLYWAY_EXECUTION = "flyway.execution";
    public static final String VERSION = "flyway.execution.migration.version";
    public static final String DESCRIPTION = "flyway.execution.description";
    public static final String TYPE = "flyway.execution.type";
    public static final String SCRIPT = "flyway.execution.script";
    public static final String CHECKSUM = "flyway.execution.checksum";
    public static final String INSTALLED_BY = "flyway.execution.installed_by";
    public static final String INSTALLED_ON = "flyway.execution.installed_on";
    public static final String EXECUTION_TIME = "flyway.execution.execution_time";
    public static final String SUCCESS = "flyway.execution.success";
    public static final String RANK = "flyway.execution.installed_rank";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String VERSION_REQUIRED = "validation.flyway.execution.version.required";
        public static final String SCRIPT_REQUIRED = "validation.flyway.execution.script.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String MIGRACAO_JA_APLICADA = "flyway.execution.rule.migracao.ja.aplicada";
        public static final String VERSAO_INVALIDA = "flyway.execution.rule.versao.invalida";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String MIGRACAO_APLICADA = "flyway.execution.message.migracao.aplicada";
        public static final String MIGRACAO_FALHOU = "flyway.execution.message.migracao.falhou";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String MIGRACAO_EXECUTADA = "flyway.execution.event.migracao.executada";
    }
}
