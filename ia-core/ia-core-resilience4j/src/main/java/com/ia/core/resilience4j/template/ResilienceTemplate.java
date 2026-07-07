package com.ia.core.resilience4j.template;

import com.ia.core.resilience4j.aspect.ResilienceFallbackHandler;
import com.ia.core.resilience4j.dto.ResilienceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Template method para execução de operações resilientes.
 *
 * <p>Define o esqueleto da execução com tratamento de fallback,
 * delegando a lógica de métricas para ResilienceMetricsCollector.</p>
 *
 * <p>Padrões aplicados:</p>
 * <ul>
 *   <li>Template Method - define o esqueleto da operação</li>
 *   <li>Strategy - deleta a lógica de execução para o Supplier</li>
 * </ul>
 *
 * <p><b>Nota:</b> A coleta de métricas é feita pelo ResilienceMetricsCollector
 * encapsulado em ResilienceExecutionChainBuilder, evitando duplicação.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResilienceTemplate {

    private final ResilienceFallbackHandler fallbackHandler;

    /**
     * Executa uma operação com tratamento de fallback.
     *
     * <p>As métricas são coletadas pelo ResilienceMetricsCollector encapsulado
     * na cadeia de execução, garantindo uma única coleta por execução.</p>
     *
     * @param context   o contexto de resiliência
     * @param operation a operação a ser executada
     * @param <T>       o tipo de retorno
     * @return o resultado da operação
     */
    public <T> T execute(ResilienceContext context, Supplier<T> operation) {
        Objects.requireNonNull(context, "context must not be null");
        Objects.requireNonNull(operation, "operation must not be null");

        String profileName = context.getProfile().getName();
        String methodName = context.getMethod().getName();

        try {
            T result = operation.get();
            log.debug("Operation {}#{} executed successfully",
                context.getMethod().getDeclaringClass().getSimpleName(),
                methodName);
            return result;
        } catch (Exception e) {
            log.error("Error in operation {}#{}: {}",
                context.getMethod().getDeclaringClass().getSimpleName(),
                methodName, e.getMessage());
            if (context.getAnnotation().fallbackEnabled()) {
                log.debug("Executing fallback for {}#{}",
                    context.getMethod().getDeclaringClass().getSimpleName(), context.getMethod().getName());
                return (T) fallbackHandler.handleFallback(context, e);
            }
            throw e;
        }
    }

    /**
     * Executa uma operação sem valor de retorno.
     *
     * @param context   o contexto de resiliência
     * @param operation a operação a ser executada
     */
    public void executeVoid(ResilienceContext context, Runnable operation) {
        execute(context, () -> {
            operation.run();
            return null;
        });
    }
}
