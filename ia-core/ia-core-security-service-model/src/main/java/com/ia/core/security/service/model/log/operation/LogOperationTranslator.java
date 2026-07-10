package com.ia.core.security.service.model.log.operation;

/**
 * Translator constants for LogOperation DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the LogOperation DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.log.operation.LogOperationDTO
 */
public final class LogOperationTranslator {

    private LogOperationTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String LOG_OPERATION = "log.operation.help";
        public static final String USER_NAME = "log.operation.help.user.name";
        public static final String USER_CODE = "log.operation.help.user.code";
        public static final String TYPE = "log.operation.help.type";
        public static final String OLD_VALUE = "log.operation.help.old.value";
        public static final String NEW_VALUE = "log.operation.help.new.value";
        public static final String DATE_TIME = "log.operation.help.date.time";
        public static final String OPERATION = "log.operation.help.operation";
        public static final String PROPERTY = "log.operation.help.property";
        public static final String VALUE_ID = "log.operation.help.valueId";
    }

    /**
     * DTO class canonical name
     */
    public static final String LOG_OPERATION_CLASS = LogOperationDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String LOG_OPERATION = "log.operation";
    public static final String USER_NAME = "log.operation.user.name";
    public static final String USER_CODE = "log.operation.user.code";
    public static final String TYPE = "log.operation.type";
    public static final String OLD_VALUE = "log.operation.old.value";
    public static final String NEW_VALUE = "log.operation.new.value";
    public static final String DATE_TIME = "log.operation.date.time";
    public static final String OPERATION = "log.operation.operation";
    public static final String PROPERTY = "log.operation.property";
    public static final String VALUE_ID = "log.operation.valueId";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String USER_NAME_REQUIRED = "validation.logoperation.username.required";
        public static final String USER_CODE_REQUIRED = "validation.logoperation.usercode.required";
        public static final String TYPE_REQUIRED = "validation.logoperation.type.required";
        public static final String VALUE_ID_REQUIRED = "validation.logoperation.valueid.required";
        public static final String DATE_TIME_REQUIRED = "validation.logoperation.datetime.required";
        public static final String OPERATION_REQUIRED = "validation.logoperation.operation.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String OPERACAO_NAO_PERMITIDA = "log.operation.rule.operacao.nao.permitida";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String LOG_CRIADO = "log.operation.message.criado";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String ALTERACAO_REGISTRADA = "log.operation.event.alteracao.registrada";
    }

    /**
     * Action i18n keys
     */
    public static final class ACTION {
        public static final String CLOSE = "log.operation.action.close";
    }

    /**
     * Title i18n keys
     */
    public static final class TITLE {
        public static final String AUDIT = "log.operation.title.audit";
    }
}
