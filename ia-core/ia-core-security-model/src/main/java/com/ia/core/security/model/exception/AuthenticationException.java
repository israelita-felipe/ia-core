package com.ia.core.security.model.exception;

/**
 * Exceção para erros de autenticação (credenciais inválidas).
 * <p>
 * Lançada quando o processo de autenticação falha devido a
 * credenciais incorretas ou outros problemas de autenticação.
 * </p>
 * <p>
 * HTTP Status: 401 Unauthorized
 * </p>
 * <p>
 * Exemplos de uso:
 * <pre>{@code
 * throw new AuthenticationException("Credenciais inválidas para usuário: " + username);
 * throw new AuthenticationException("Conta bloqueada após múltiplas tentativas");
 * }</pre>
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public class AuthenticationException extends SecurityException {

    /**
     * Código de erro padrão.
     */
    public static final String DEFAULT_ERROR_CODE = "AUTHENTICATION_ERROR";

    /**
     * Identificador do usuário (não deve conter informações sensíveis).
     */
    private final String userIdentifier;

    /**
     * Cria uma nova exceção com a mensagem especificada.
     *
     * @param message mensagem descritiva do erro
     */
    public AuthenticationException(String message) {
        super(message, determineErrorCode(AuthenticationException.class.getSimpleName()));
        this.userIdentifier = null;
    }

    /**
     * Cria uma exceção com identificador do usuário.
     *
     * @param message mensagem descritiva
     * @param userIdentifier identificador do usuário
     */
    public AuthenticationException(String message, String userIdentifier) {
        super(message, determineErrorCode(AuthenticationException.class.getSimpleName()));
        this.userIdentifier = userIdentifier;
    }

    /**
     * Cria uma exceção com mensagem e causa.
     *
     * @param message mensagem descritiva
     * @param cause exceção causadora
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, determineErrorCode(AuthenticationException.class.getSimpleName()), cause);
        this.userIdentifier = null;
    }

    /**
     * Factory method para credenciais inválidas.
     *
     * @param username nome de usuário
     * @return nova exceção
     */
    public static AuthenticationException invalidCredentials(String username) {
        return new AuthenticationException("Credenciais inválidas para usuário: " + username, username);
    }

    /**
     * Factory method para conta bloqueada.
     *
     * @param username nome de usuário
     * @param reason motivo do bloqueio
     * @return nova exceção
     */
    public static AuthenticationException accountLocked(String username, String reason) {
        return new AuthenticationException("Conta bloqueada: " + reason, username);
    }

    /**
     * Factory method para conta desativada.
     *
     * @param username nome de usuário
     * @return nova exceção
     */
    public static AuthenticationException accountDisabled(String username) {
        return new AuthenticationException("Conta desativada. Contate o administrador.", username);
    }

    /**
     * Retorna o identificador do usuário, se disponível.
     *
     * @return identificador ou null
     */
    public String getUserIdentifier() {
        return userIdentifier;
    }
}
