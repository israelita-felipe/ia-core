package com.ia.core.resilience4j.exception;

/**
 * Exceção lançada quando o circuit breaker está aberto.
 *
 * <p>Indica que o circuit breaker rejeitou a chamada devido a um número
 * excessivo de falhas consecutivas. O chamador deve aguardar o tempo de
 * reset antes de tentar novamente.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class CircuitBreakerOpenException extends ResilienceException {
    private final String circuitBreakerName;

    public CircuitBreakerOpenException(String circuitBreakerName) {
        super("CIRCUIT_BREAKER_OPEN",
                "Circuit breaker '" + circuitBreakerName + "' is open. Call rejected.",
                false);
        this.circuitBreakerName = circuitBreakerName;
    }

    public String getCircuitBreakerName() {
        return circuitBreakerName;
    }
}
