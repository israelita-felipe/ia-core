package com.ia.core.security.service.model.login;

/**
 * Translator constants for Login.
 * <p>
 * Contains constants for i18n keys, validation messages, and field names
 * used throughout the Login processing.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public final class LoginTranslator {

    private LoginTranslator() {
        // Utility class
    }

    /**
     * DTO class canonical name (if applicable)
     */
    public static final String LOGIN_CLASS = "com.ia.core.security.service.model.login.LoginDTO";

    /**
     * Field name constants
     */
    public static final String LOGIN = "login";
    public static final String ADDITIONAL_INFORMATION = "login.additional.information";
    public static final String FIRST_LOGIN_ADDITIONAL_INFORMATION = "login.first.login.additional.information";
    public static final String ERROR_MESSAGE = "login.error.message";
    public static final String ERROR_WRONG_PASSWORD = "login.error.wrong.password";
    public static final String ERROR_WRONG_USERNAME = "login.error.wrong.username";
    public static final String ERROR_TITLE = "login.error.title";
    public static final String HEADER_TITLE = "login.header.title";
    public static final String HEADER_DESCRIPTION = "login.header.description";
    public static final String FORM_TITLE = "login.form.title";
    public static final String FORM_FORGOT_PASSWORD = "login.form.forgot.password";
    public static final String FORM_SUBMIT = "login.form.submit";
    public static final String FORM_PASSWORD = "login.form.password";
    public static final String FORM_USERNAME = "login.form.username";

    /**
     * Validation message keys
     */
    public static final class VALIDATION {
        public static final String USERNAME_REQUIRED = "validation.login.username.required";
        public static final String PASSWORD_REQUIRED = "validation.login.password.required";
    }

    /**
     * Business rule message keys
     */
    public static final class RULE {
        public static final String CONTA_BLOQUEADA = "login.rule.conta.bloqueada";
        public static final String SENHA_EXPIRADA = "login.rule.senha.expirada";
        public static final String PRIMEIRO_LOGIN = "login.rule.primeiro.login";
    }

    /**
     * Error message keys
     */
    public static final class ERROR {
        public static final String FAILED = "login.error.failed";
        public static final String WRONG_PASSWORD = "login.error.wrong.password";
        public static final String WRONG_USERNAME = "login.error.wrong.username";
    }

    /**
     * Success message keys
     */
    public static final class MESSAGE {
        public static final String SUCCESS = "login.message.success";
        public static final String FAILURE = "login.message.failure";
        public static final String LOGOUT_SUCCESS = "login.message.logout.success";
    }

    /**
     * Domain event message keys
     */
    public static final class EVENT {
        public static final String USUARIO_AUTENTICADO = "login.event.usuario.autenticado";
        public static final String TENTATIVA_LOGIN = "login.event.tentativa.login";
    }
}
