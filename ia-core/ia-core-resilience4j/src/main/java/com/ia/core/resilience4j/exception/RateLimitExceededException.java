package com.ia.core.resilience4j.exception;

/**
 * Exceção lançada quando o rate limiter rejeita uma chamada.
 *
 * <p>Indica que o limite de requisições por período foi excedido
 * e a chamada deve ser rejeitada ou aguardar antes de tentar novamente.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class RateLimitExceededException extends ResilienceException {

    private final String rateLimiterName;

    public RateLimitExceededException(String rateLimiterName) {
        super("RATE_LIMIT_EXCEEDED",
                "Rate limit exceeded for '" + rateLimiterName + "'. Too many requests.",
                false);
        this.rateLimiterName = rateLimiterName;
    }

    public RateLimitExceededException(String rateLimiterName, String message) {
        super("RATE_LIMIT_EXCEEDED", message, false);
        this.rateLimiterName = rateLimiterName;
    }

    public String getRateLimiterName() {
        return rateLimiterName;
    }
}
