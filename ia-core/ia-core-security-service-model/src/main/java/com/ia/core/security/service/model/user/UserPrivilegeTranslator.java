package com.ia.core.security.service.model.user;

/**
 * Translator constants for UserPrivilege DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the UserPrivilege DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.user.UserPrivilegeDTO
 */
public final class UserPrivilegeTranslator {

    private UserPrivilegeTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String USER_PRIVILEGE = "user.privilege.help";
        public static final String PRIVILEGE = "user.privilege.help.privilege";
        public static final String OPERATIONS = "user.privilege.help.operations";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String PRIVILEGE_REQUIRED = "validation.user.privilege.privilege.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String PERMISSAO_NEGADA = "user.privilege.rule.permissao.negada";
        public static final String OPERACAO_INVALIDA = "user.privilege.rule.operacao.invalida";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "user.privilege.error.notfound";
        public static final String DUPLICATE = "user.privilege.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "user.privilege.message.created";
        public static final String DELETED = "user.privilege.message.deleted";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String PERMISSAO_ATRIBUIDA = "user.privilege.event.permissao.atribuida";
        public static final String PERMISSAO_REMOVIDA = "user.privilege.event.permissao.removida";
    }

    /**
     * DTO class canonical name
     */
    public static final String USER_PRIVILEGE_CLASS = UserPrivilegeDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String USER_PRIVILEGE = "user.privilege";
    public static final String PRIVILEGE = "user.privilege.privilege";
    public static final String OPERATIONS = "user.privilege.operations";
}
