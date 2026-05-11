package com.ia.core.security.service.model.user;

/**
 * Translator constants for User DTO.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the User DTO processing pipeline.
 *
 * @author Israel Araújo
 * @since 1.0.0
 * @see com.ia.core.security.service.model.user.UserDTO
 */
public final class UserTranslator {

    private UserTranslator() {
        // Utility class
    }

    /**
     * Help text i18n keys
     */
    public static final class HELP {
        public static final String USER = "user.help";
        public static final String NOME = "user.help.userName";
        public static final String CODIGO = "user.help.userCode";
        public static final String SENHA = "user.help.password";
        public static final String HABILITADO = "user.help.enabled";
        public static final String CONTA_NAO_EXPIRADA = "user.help.accountNotExpired";
        public static final String CONTA_NAO_BLOQUEADA = "user.help.accountNotLocked";
        public static final String CREDENCIAIS_NAO_EXPIRADAS = "user.help.credentialsNotExpired";
    }

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String USER_NAME_REQUIRED = "validation.user.userName.required";
        public static final String USER_NAME_SIZE = "validation.user.userName.size";
        public static final String USER_CODE_REQUIRED = "validation.user.userCode.required";
        public static final String USER_CODE_SIZE = "validation.user.userCode.size";
        public static final String PASSWORD_SIZE = "validation.user.password.size";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CODIGO_UNICO = "user.rule.codigo.unico";
        public static final String SENHA_ATUAL_INVALIDA = "user.rule.senha.atual.invalida";
        public static final String USUARIO_ATIVO = "user.rule.usuario.ativo";
    }

    /**
     * Success/error message keys
     */
    public static final class MESSAGE {
        public static final String CRIADO_SUCESSO = "user.message.criado.sucesso";
        public static final String ATUALIZADO_SUCESSO = "user.message.atualizado.sucesso";
        public static final String DELETADO_SUCESSO = "user.message.deletado.sucesso";
        public static final String SENHA_ALTERADA_SUCESSO = "user.message.senha.alterada.sucesso";
        public static final String CONFIRMAR_ALTERACAO_SENHA = "user.message.confirmar.alteracao.senha";
        public static final String PASSWORD_CHANGED_SUCCESS = "user.message.senha.alterada.sucesso";
        public static final String CONFIRM_CHANGE_PASSWORD = "user.message.confirmar.alteracao.senha";
        public static final String PASSWORD_RESET_SUCCESS = "user.message.senha.resetada.sucesso";
        public static final String CONFIRM_RESET_PASSWORD = "user.message.confirmar.reset.senha";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String USUARIO_CRIADO = "user.event.criado";
        public static final String USUARIO_ATUALIZADO = "user.event.atualizado";
        public static final String SENHA_ALTERADA = "user.event.senha.alterada";
    }

    /**
     * DTO class canonical name
     */
    public static final String USER_CLASS = UserDTO.class.getCanonicalName();

    /**
     * Password field i18n keys
     */
    public static final class PASSWORD {
        public static final String CURRENT = "user.password.current";
        public static final String NEW = "user.password.new";
        public static final String CONFIRM_NEW = "user.password.confirm.new";
    }

    /**
     * Field name constants
     */
    public static final String USER = "user";
    public static final String NOME = "user.userName";
    public static final String CODIGO = "user.userCode";
    public static final String SENHA = "user.password";
    public static final String HABILITADO = "user.enabled";
    public static final String CONTA_NAO_EXPIRADA = "user.accountNotExpired";
    public static final String CONTA_NAO_BLOQUEADA = "user.accountNotLocked";
    public static final String CREDENCIAIS_NAO_EXPIRADAS = "user.credentialsNotExpired";

    /**
     * Menu i18n keys
     */
    public static final class MENU {
        public static final String SECURITY = "user.menu.security";
    }

    /**
     * Action i18n keys
     */
    public static final class ACTION {
        public static final String CHANGE_PASSWORD = "user.action.changePassword";
        public static final String CONFIRM = "user.action.confirm";
        public static final String LOGOUT = "user.action.logout";
        public static final String RESET_PASSWORD = "user.action.resetPassword";
    }
}
