package com.ia.core.resilience4j.exception;

/**
 * Exceção lançada quando o bulkhead está cheio.
 *
 * <p>Indica que o número máximo de chamadas concorrentes foi atingido
 * e a nova chamada deve ser rejeitada ou aguardar.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class BulkheadFullException extends ResilienceException {

    private final String bulkheadName;

    public BulkheadFullException(String bulkheadName) {
        super("BULKHEAD_FULL",
                "Bulkhead '" + bulkheadName + "' is full. Maximum concurrent calls reached.",
                false);
        this.bulkheadName = bulkheadName;
    }

    public BulkheadFullException(String bulkheadName, String message) {
        super("BULKHEAD_FULL", message, false);
        this.bulkheadName = bulkheadName;
    }

    public String getBulkheadName() {
        return bulkheadName;
    }
}
