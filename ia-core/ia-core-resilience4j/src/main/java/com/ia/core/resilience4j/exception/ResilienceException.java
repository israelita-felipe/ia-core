package com.ia.core.resilience4j.exception;

/**
 * Exceção base para todos os erros relacionados a resiliência.
 *
 * <p>Esta exceção inclui um código de erro e uma flag de repetição
 * para auxiliar no tratamento de erros e decisões de fallback.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ResilienceException extends RuntimeException {
    private final String errorCode;
    private final boolean retryable;

    public ResilienceException(String message) {
        super(message);
        this.errorCode = "RESILIENCE_ERROR";
        this.retryable = true;
    }

    public ResilienceException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "RESILIENCE_ERROR";
        this.retryable = true;
    }

    public ResilienceException(String errorCode, String message, boolean retryable) {
        super(message);
        this.errorCode = errorCode;
        this.retryable = retryable;
    }

    public ResilienceException(String errorCode, String message, Throwable cause, boolean retryable) {
        super(message, cause);
        this.errorCode = errorCode;
        this.retryable = retryable;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean isRetryable() {
        return retryable;
    }
}
