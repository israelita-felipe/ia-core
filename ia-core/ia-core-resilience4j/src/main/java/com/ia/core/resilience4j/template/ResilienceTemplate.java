package com.ia.core.resilience4j.template;

import com.ia.core.resilience4j.aspect.ResilienceFallbackHandler;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.metrics.ResilienceMetrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Template method para execução de operações resilientes.
 *
 * <p>Define o esqueleto da execução com medição de tempo e métricas,
 * delegando a lógica específica para o Supplier fornecido.</p>
 *
 * <p>Padrões aplicados:</p>
 * <ul>
 *   <li>Template Method - define o esqueleto da operação</li>
 *   <li>Strategy - delega a lógica de execução para o Supplier</li>
 *   <li>Observer - registra métricas de execução</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResilienceTemplate {

    private final ResilienceMetrics metrics;
    private final ResilienceFallbackHandler fallbackHandler;

    /**
     * Executa uma operação com timing e métricas.
     *
     * @param context   o contexto de resiliência
     * @param operation a operação a ser executada
     * @param <T>       o tipo de retorno
     * @return o resultado da operação
     */
    public <T> T execute(ResilienceContext context, Supplier<T> operation) {
        long startTime = System.currentTimeMillis();
        String profileName = context.getProfile().getName();
        String methodName = context.getMethod().getName();

        try {
            T result = operation.get();
            long duration = System.currentTimeMillis() - startTime;
            metrics.recordSuccess(profileName, methodName, duration);
            log.debug("Operation {}#{} executed successfully in {}ms",
                context.getMethod().getDeclaringClass().getSimpleName(),
                methodName, duration);
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            metrics.recordError(profileName, methodName, e.getClass().getSimpleName(), duration);
            log.error("Error in operation {}#{} after {}ms: {}",
                context.getMethod().getDeclaringClass().getSimpleName(),
                methodName, duration, e.getMessage());
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
