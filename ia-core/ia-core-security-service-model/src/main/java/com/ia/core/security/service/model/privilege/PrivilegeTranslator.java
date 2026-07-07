package com.ia.core.security.service.model.privilege;

/**
 * Translator constants for Privilege DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Privilege DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.privilege.PrivilegeDTO
 */
public final class PrivilegeTranslator {

    private PrivilegeTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String PRIVILEGE = "privilege.help";
        public static final String NOME = "privilege.help.name";
        public static final String TYPE = "privilege.help.type";
        public static final String ROLES = "privilege.help.roles";
        public static final String VALUES = "privilege.help.values";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String NAME_REQUIRED = "validation.privilege.name.required";
        public static final String NAME_SIZE = "validation.privilege.name.size";
        public static final String TYPE_REQUIRED = "validation.privilege.type.required";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String NOT_FOUND = "privilege.error.notfound";
        public static final String DUPLICATE = "privilege.error.duplicate";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String CREATED = "privilege.message.created";
        public static final String UPDATED = "privilege.message.updated";
        public static final String DELETED = "privilege.message.deleted";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String NOME_OBRIGATORIO = "privilege.rule.nome.obrigatorio";
        public static final String DUPLICADO = "privilege.rule.duplicado";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String PRIVILEGE_CRIADO = "privilege.event.criado";
        public static final String PRIVILEGE_ATUALIZADO = "privilege.event.atualizado";
    }

    /**
     * DTO class canonical name
     */
    public static final String PRIVILEGE_CLASS = PrivilegeDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String PRIVILEGE = "privilege";
    public static final String NOME = "privilege.name";
    public static final String TYPE = "privilege.type";
    public static final String ROLES = "privilege.roles";
    public static final String VALUES = "privilege.values";

    /**
     * Menu i18n keys
     */
    public static final class MENU {
        public static final String SECURITY = "privilege.menu.security";
    }

    /**
     * Privilege i18n keys
     */
    public static final class PRIVILEGE {
        public static final String PRIVILEGE = "privilege.privilege";
    }
}
