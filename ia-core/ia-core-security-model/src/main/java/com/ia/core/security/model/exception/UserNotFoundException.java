package com.ia.core.security.model.exception;

/**
 * Exceção para usuário não encontrado durante autenticação.
 * <p>
 * Lançada quando o processo de autenticação não encontra
 * o usuário no sistema.
 * </p>
 * <p>
 * HTTP Status: 401 Unauthorized
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public class UserNotFoundException extends SecurityException {

    /**
     * Código de erro padrão.
     */
    public static final String DEFAULT_ERROR_CODE = "USER_NOT_FOUND";

    /**
     * Identificador do usuário que não foi encontrado.
     */
    private final String userIdentifier;

    /**
     * Cria uma nova exceção com o identificador do usuário.
     *
     * @param userIdentifier identificador do usuário
     */
    public UserNotFoundException(String userIdentifier) {
        super(
            "Usuário não encontrado: " + userIdentifier,
            determineErrorCode(UserNotFoundException.class.getSimpleName())
        );
        this.userIdentifier = userIdentifier;
    }

    /**
     * Cria uma exceção com identificador customizado.
     *
     * @param userIdentifier identificador do usuário
     * @param message mensagem adicional
     */
    public UserNotFoundException(String userIdentifier, String message) {
        super(
            message + " (usuário: " + userIdentifier + ")",
            determineErrorCode(UserNotFoundException.class.getSimpleName())
        );
        this.userIdentifier = userIdentifier;
    }

    /**
     * Factory method para usuário não encontrado por código.
     *
     * @param userCode código do usuário
     * @return nova exceção
     */
    public static UserNotFoundException byCode(String userCode) {
        return new UserNotFoundException(userCode);
    }

    /**
     * Factory method para usuário não encontrado por email.
     *
     * @param email email do usuário
     * @return nova exceção
     */
    public static UserNotFoundException byEmail(String email) {
        return new UserNotFoundException("email:" + email);
    }

    /**
     * Factory method para usuário inativo.
     *
     * @param userIdentifier identificador do usuário
     * @return nova exceção
     */
    public static UserNotFoundException inactive(String userIdentifier) {
        return new UserNotFoundException(
            userIdentifier,
            "Usuário encontrado, mas está inativo"
        );
    }

    /**
     * Retorna o identificador do usuário.
     *
     * @return identificador
     */
    public String getUserIdentifier() {
        return userIdentifier;
    }
}
