package com.ia.core.security.model.exception;

/**
 * Exceção para acesso negado (autenticado mas sem permissão).
 * <p>
 * Lançada quando um usuário está autenticado mas não possui
 * permissão para acessar o recurso solicitado.
 * </p>
 * <p>
 * HTTP Status: 403 Forbidden
 * </p>
 * <p>
 * Exemplos de uso:
 * <pre>{@code
 * throw new AccessDeniedException("Usuário não tem permissão para excluir este recurso");
 * throw new AccessDeniedException("Função ADMIN necessária para esta operação");
 * }</pre>
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public class AccessDeniedException extends SecurityException {

    /**
     * Código de erro padrão.
     */
    public static final String DEFAULT_ERROR_CODE = "ACCESS_DENIED";

    /**
     * Recurso que foi negado.
     */
    private final String resource;

    /**
     * Permissão necessária que está faltando.
     */
    private final String requiredPermission;

    /**
     * Cria uma nova exceção com a mensagem especificada.
     *
     * @param message mensagem descritiva do erro
     */
    public AccessDeniedException(String message) {
        super(message, determineErrorCode(AccessDeniedException.class.getSimpleName()));
        this.resource = null;
        this.requiredPermission = null;
    }

    /**
     * Cria uma exceção com recurso e permissão.
     *
     * @param message mensagem descritiva
     * @param resource recurso acessado
     * @param requiredPermission permissão necessária
     */
    public AccessDeniedException(String message, String resource, String requiredPermission) {
        super(message, determineErrorCode(AccessDeniedException.class.getSimpleName()));
        this.resource = resource;
        this.requiredPermission = requiredPermission;
    }

    /**
     * Factory method para permissão negada.
     *
     * @param resource recurso acessado
     * @param permission permissão necessária
     * @return nova exceção
     */
    public static AccessDeniedException permissionDenied(String resource, String permission) {
        return new AccessDeniedException(
            "Permissão '" + permission + "' necessária para acessar: " + resource,
            resource,
            permission
        );
    }

    /**
     * Factory method para função necessária.
     *
     * @param resource recurso acessado
     * @param role função necessária
     * @return nova exceção
     */
    public static AccessDeniedException roleRequired(String resource, String role) {
        return new AccessDeniedException(
            "Função '" + role + "' necessária para acessar: " + resource,
            resource,
            role
        );
    }

    /**
     * Factory method para proprietário necessário.
     *
     * @param resource recurso acessado
     * @return nova exceção
     */
    public static AccessDeniedException ownerRequired(String resource) {
        return new AccessDeniedException(
            "Você deve ser o proprietário para acessar: " + resource,
            resource,
            "OWNER"
        );
    }

    /**
     * Retorna o recurso que foi negado.
     *
     * @return recurso ou null
     */
    public String getResource() {
        return resource;
    }

    /**
     * Retorna a permissão necessária.
     *
     * @return permissão ou null
     */
    public String getRequiredPermission() {
        return requiredPermission;
    }
}
