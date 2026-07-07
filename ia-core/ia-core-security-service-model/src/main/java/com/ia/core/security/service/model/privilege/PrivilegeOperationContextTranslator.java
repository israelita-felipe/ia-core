package com.ia.core.security.service.model.privilege;

/**
 * Translator constants for PrivilegeOperationContext DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the PrivilegeOperationContext DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO
 */
public final class PrivilegeOperationContextTranslator {

    private PrivilegeOperationContextTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String PRIVILEGE_OPERATION_CONTEXT = "privilege.operation.context.help";
        public static final String CONTEXT_KEY = "privilege.operation.context.help.contextKey";
        public static final String VALUES = "privilege.operation.context.help.values";
    }

    /**
     * DTO class canonical name
     */
    public static final String PRIVILEGE_OPERATION_CONTEXT_CLASS = PrivilegeOperationContextDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String PRIVILEGE_OPERATION_CONTEXT = "privilege.operation.context";
    public static final String CONTEXT_KEY = "privilege.operation.context.contextKey";
    public static final String VALUES = "privilege.operation.context.values";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String CONTEXT_KEY_REQUIRED = "validation.privilege.operation.context.key.required";
        public static final String VALUES_REQUIRED = "validation.privilege.operation.context.values.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CONTEXTO_INVALIDO = "privilege.operation.context.rule.contexto.invalido";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "privilege.operation.context.error.notfound";
        public static final String DUPLICATE = "privilege.operation.context.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "privilege.operation.context.message.created";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String CONTEXTO_CRIADO = "privilege.operation.context.event.criado";
    }
}
