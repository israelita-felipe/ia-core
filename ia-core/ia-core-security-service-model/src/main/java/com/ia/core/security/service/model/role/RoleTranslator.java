package com.ia.core.security.service.model.role;

/**
 * Translator constants for Role DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Role DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.role.RoleDTO
 */
public final class RoleTranslator {

    private RoleTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String ROLE = "role.help";
        public static final String NOME = "role.help.name";
        public static final String USERS = "role.help.users";
        public static final String PRIVILEGES = "role.help.privileges";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String NAME_REQUIRED = "validation.role.name.required";
        public static final String NAME_SIZE = "validation.role.name.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String NOME_OBRIGATORIO = "role.rule.nome.obrigatorio";
        public static final String USUARIO_SEM_PERMISSAO = "role.rule.usuario.sem.permissao";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "role.message.criado.sucesso";
        public static final String ATUALIZADO_SUCESSO = "role.message.atualizado.sucesso";
        public static final String DELETADO_SUCESSO = "role.message.deletado.sucesso";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String ROLE_CRIADO = "role.event.criado";
        public static final String ROLE_ATUALIZADO = "role.event.atualizado";
        public static final String PERMISSAO_ALTERADA = "role.event.permissao.alterada";
    }

    /**
     * DTO class canonical name
     */
    public static final String ROLE_CLASS = RoleDTO.class.getCanonicalName();

    /**
     * Field name constants
     */
    public static final String ROLE = "role";
    public static final String NOME = "role.name";
    public static final String USERS = "role.users";
    public static final String PRIVILEGES = "role.privileges";

    /**
     * Menu i18n keys
     */
    public static final class MENU {
        public static final String SECURITY = "role.menu.security";
    }

    /**
     * Role i18n keys
     */
    public static final class ROLE {
        public static final String ROLE = "role.role";
    }
}
