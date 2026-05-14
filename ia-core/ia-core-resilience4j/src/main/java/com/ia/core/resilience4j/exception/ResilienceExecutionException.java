package com.ia.core.resilience4j.exception;

/**
 * Wrapper para exceções lançadas durante a execução de operações resilientes.
 *
 * <p>Utilizada para encapsular a causa raiz da falha durante a execução
 * de uma operação com padrões de resiliência, preservando o stack trace
 * original e permitindo tratamento adequado no fallback.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ResilienceExecutionException extends RuntimeException {

    public ResilienceExecutionException(Throwable cause) {
        super(cause);
    }
}
