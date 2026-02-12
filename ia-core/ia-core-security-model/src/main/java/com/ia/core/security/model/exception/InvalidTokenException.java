package com.ia.core.security.model.exception;

/**
 * Exceção para token JWT inválido, expirado ou malformado.
 * <p>
 * Lançada quando um token não passa nas validações de segurança,
 * incluindo estrutura, assinatura ou expiração.
 * </p>
 * <p>
 * HTTP Status: 401 Unauthorized
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public class InvalidTokenException extends SecurityException {

    /**
     * Código de erro padrão.
     */
    public static final String DEFAULT_ERROR_CODE = "INVALID_TOKEN";

    /**
     * Cria uma nova exceção com a mensagem especificada.
     *
     * @param message mensagem descritiva do erro
     */
    public InvalidTokenException(String message) {
        super(message, determineErrorCode(InvalidTokenException.class.getSimpleName()));
    }

    /**
     * Cria uma nova exceção com mensagem e causa.
     *
     * @param message mensagem descritiva do erro
     * @param cause exceção causadora
     */
    public InvalidTokenException(String message, Throwable cause) {
        super(message, determineErrorCode(InvalidTokenException.class.getSimpleName()), cause);
    }

    /**
     * Cria uma exceção para token com estrutura inválida.
     *
     * @param reason motivo específico da invalidade
     * @return nova exceção
     */
    public static InvalidTokenException malformed(String reason) {
        return new InvalidTokenException("Token malformado: " + reason);
    }

    /**
     * Cria uma exceção para assinatura inválida.
     *
     * @return nova exceção
     */
    public static InvalidTokenException invalidSignature() {
        return new InvalidTokenException("Assinatura do token inválida");
    }

    /**
     * Cria uma exceção para issuer inválido.
     *
     * @param expected issuer esperado
     * @param actual issuer encontrado
     * @return nova exceção
     */
    public static InvalidTokenException invalidIssuer(String expected, String actual) {
        return new InvalidTokenException("Issuer inválido. Esperado: " + expected + ", Encontrado: " + actual);
    }
}
