package com.ia.core.security.model.exception;

import java.time.Instant;

/**
 * Exceção para token JWT expirado.
 * <p>
 * Lançada quando um token passou do seu tempo de validade.
 * Esta é uma subclasse de {@link InvalidTokenException} para permitir
 * tratamento específico de tokens expirados (ex: renovação automática).
 * </p>
 * <p>
 * HTTP Status: 401 Unauthorized
 * </p>
 * <p>
 * Exemplo de tratamento específico:
 * <pre>{@code
 * try {
 *     tokenValidator.validate(token);
 * } catch (TokenExpiredException e) {
 *     // Renovar token automaticamente
 *     refreshTokenService.refresh(userCode);
 * }
 * }</pre>
 * </p>
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @since 1.0.0
 */
public class TokenExpiredException extends InvalidTokenException {

    /**
     * Código de erro padrão.
     */
    public static final String DEFAULT_ERROR_CODE = "TOKEN_EXPIRED";

    /**
     * Momento de expiração do token.
     */
    private final Instant expirationTime;

    /**
     * Cria uma nova exceção com o momento de expiração.
     *
     * @param expirationTime momento em que o token expirou
     */
    public TokenExpiredException(Instant expirationTime) {
        super("Token expirado em: " + expirationTime);
        this.expirationTime = expirationTime;
    }

    /**
     * Cria uma exceção com momento de expiração customizado.
     *
     * @param message mensagem adicional
     * @param expirationTime momento de expiração
     */
    public TokenExpiredException(String message, Instant expirationTime) {
        super(message);
        this.expirationTime = expirationTime;
    }

    /**
     * Cria uma exceção factory method para expiração.
     *
     * @param expirationTime momento de expiração
     * @return nova exceção
     */
    public static TokenExpiredException at(Instant expirationTime) {
        return new TokenExpiredException(expirationTime);
    }

    /**
     * Retorna o momento de expiração do token.
     *
     * @return Instant de expiração
     */
    public Instant getExpirationTime() {
        return expirationTime;
    }

    /**
     * Verifica se o token está expirado há mais de tolerance segundos.
     *
     * @param toleranceSeconds tolerância em segundos
     * @return true se está expirado há mais tempo que a tolerância
     */
    public boolean isExpiredFor(long toleranceSeconds) {
        return Instant.now().isAfter(expirationTime.plusSeconds(toleranceSeconds));
    }

    /**
     * Retorna o tempo decorrido desde a expiração em segundos.
     *
     * @return segundos desde a expiração, ou 0 se ainda não expirou
     */
    public long getSecondsSinceExpiration() {
        Instant now = Instant.now();
        if (now.isBefore(expirationTime)) {
            return 0;
        }
        return now.getEpochSecond() - expirationTime.getEpochSecond();
    }

    @Override
    public String getErrorCode() {
        return DEFAULT_ERROR_CODE;
    }
}
