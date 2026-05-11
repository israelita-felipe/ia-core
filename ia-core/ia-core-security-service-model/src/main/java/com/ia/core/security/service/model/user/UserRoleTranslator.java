package com.ia.core.security.service.model.user;

/**
 * Translator constants for UserRole DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the UserRole DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.user.UserRoleDTO
 */
public final class UserRoleTranslator {

    private UserRoleTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String USER_ROLE = "user.role.help";
        public static final String NAME = "user.role.help.name";
        public static final String PRIVILEGES = "user.role.help.privileges";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String NAME_REQUIRED = "validation.user.role.name.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String PAPEL_OBRIGATORIO = "user.role.rule.papel.obrigatorio";
        public static final String USUARIO_SEM_ACESSO = "user.role.rule.usuario.sem.acesso";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "user.role.message.criado.sucesso";
        public static final String DELETADO_SUCESSO = "user.role.message.deletado.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String PAPEL_ATRIBUIDO = "user.role.event.papel.atribuido";
        public static final String PAPEL_REMOVIDO = "user.role.event.papel.removido";
    }

    /**
     * DTO class canonical name
     */
    public static final String USER_ROLE_CLASS = UserRoleDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String USER_ROLE = "user.role";
    public static final String NAME = "user.role.name";
    public static final String PRIVILEGES = "user.role.privileges";
}
