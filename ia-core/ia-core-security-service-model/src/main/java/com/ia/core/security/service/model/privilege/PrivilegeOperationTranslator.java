package com.ia.core.security.service.model.privilege;

/**
 * Translator constants for PrivilegeOperation DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the PrivilegeOperation DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.privilege.PrivilegeOperationDTO
 */
public final class PrivilegeOperationTranslator {

    private PrivilegeOperationTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String PRIVILEGE_OPERATION = "privilege.operation.help";
        public static final String OPERATION = "privilege.operation.help.operation";
        public static final String CONTEXT = "privilege.operation.help.context";
    }

    /**
     * DTO class canonical name
     */
    public static final String PRIVILEGE_OPERATION_CLASS = PrivilegeOperationDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String PRIVILEGE_OPERATION = "privilege.operation";
    public static final String OPERATION = "privilege.operation.operation";
    public static final String CONTEXT = "privilege.operation.context";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String OPERATION_REQUIRED = "validation.privilege.operation.operation.required";
        public static final String CONTEXT_REQUIRED = "validation.privilege.operation.context.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String OPERACAO_JA_EXISTE = "privilege.operation.rule.operacao.ja.existe";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "privilege.operation.error.notfound";
        public static final String DUPLICATE = "privilege.operation.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "privilege.operation.message.created";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String OPERACAO_CRIADA = "privilege.operation.event.criada";
    }
}
