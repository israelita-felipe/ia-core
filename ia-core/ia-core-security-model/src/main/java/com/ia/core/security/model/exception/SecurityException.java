package com.ia.core.security.model.exception;

/**
 * Exceção base para erros de segurança.
 * <p>
 * Todas as exceções relacionadas à autenticação e autorização
 * devem estender esta classe abstrata.
 * </p>
 * <p>
 * A hierarquia de exceções segue o padrão:
 * <pre>{@code
 * SecurityException (abstract)
 *     ├── AuthenticationException (401)
 *     ├── AccessDeniedException (403)
 *     ├── InvalidTokenException (401)
 *     ├── TokenExpiredException (401)
 *     └── UserNotFoundException (401)
 * }</pre>
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SecurityException extends RuntimeException {

    /**
     * Código de erro associado à exceção.
     */
    private final String errorCode;

    /**
     * Construtor protegido para subclasses.
     *
     * @param message mensagem de erro
     * @param errorCode código de erro
     */
    protected SecurityException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Construtor protegido com causa.
     *
     * @param message mensagem de erro
     * @param errorCode código de erro
     * @param cause exceção causadora
     */
    protected SecurityException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Retorna o código de erro associado à exceção.
     *
     * @return código de erro
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Determina o código de erro baseado no nome da classe.
     * Converte CamelCase para SCREAMING_SNAKE_CASE.
     *
     * @param className nome da classe
     * @return código de erro formatado
     */
    protected static String determineErrorCode(String className) {
        return className.replaceAll("(?<!^)(?=[A-Z])", "_").toUpperCase();
    }
}
