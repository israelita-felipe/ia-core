package com.ia.core.security.service.model.role;

/**
 * Translator constants for RolePrivilege DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the RolePrivilege DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.role.RolePrivilegeDTO
 */
public final class RolePrivilegeTranslator {

    private RolePrivilegeTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String ROLE_PRIVILEGE = "role.privilege.help";
        public static final String PRIVILEGE = "role.privilege.help.privilege";
        public static final String OPERATIONS = "role.privilege.help.operations";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String PRIVILEGE_REQUIRED = "validation.role.privilege.privilege.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String PERMISSAO_DUPLICADA = "role.privilege.rule.permissao.duplicada";
        public static final String OPERACAO_NAO_PERMITIDA = "role.privilege.rule.operacao.nao.permitida";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "role.privilege.message.criado.sucesso";
        public static final String DELETADO_SUCESSO = "role.privilege.message.deletado.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String PERMISSAO_ATRIBUIDA_AO_PAPEL = "role.privilege.event.permissao.atribuida";
        public static final String PERMISSAO_REMOVIDA_DO_PAPEL = "role.privilege.event.permissao.removida";
    }

    /**
     * DTO class canonical name
     */
    public static final String ROLE_PRIVILEGE_CLASS = RolePrivilegeDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String ROLE_PRIVILEGE = "role.privilege";
    public static final String PRIVILEGE = "role.privilege.privilege";
    public static final String OPERATIONS = "role.privilege.operations";
}
