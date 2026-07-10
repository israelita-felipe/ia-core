package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Handles bulkhead strategy configuration and execution.
 *
 * <p>This class encapsulates the logic for creating and configuring Bulkhead instances
 * based on resilience profiles and annotations.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Apenas lida com Bulkhead</li>
 *   <li><b>Open/Closed</b>: Extensível via herança da classe abstrata</li>
 *   <li><b>Liskov Substitution</b>: Estende AbstractResilienceStrategyHandler</li>
 *   <li><b>Interface Segregation</b>: Interface mínima necessária</li>
 *   <li><b>Dependency Inversion</b>: Depende de ResilienceContext (abstração)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class BulkheadStrategyHandler extends AbstractResilienceStrategyHandler<Bulkhead> {

    /**
      * Creates or resolves a Bulkhead instance for the given context.
      *
      * <p>Configures the Bulkhead with:</p>
      * <ul>
      *   <li>Max concurrent calls from annotation or profile</li>
      *   <li>Max wait duration for acquiring permission</li>
      * </ul>
      *
      * @param context the resilience context containing profile and annotation info
      * @return configured Bulkhead instance
      */
    @Override
    public Bulkhead resolve(ResilienceContext context) {
        ProfileAnnotation data = extractProfileAndAnnotation(context);
        ResilienceProfile profile = data.profile();
        Resilient annotation = data.annotation();

        String name = profile.getName() + "-" + context.getMethod().getName() + "-bulkhead";

        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(getIntValue(annotation.bulkheadMaxConcurrent(),
                        profile.getBulkheadMaxConcurrentCalls()))
                .maxWaitDuration(Duration.ofMillis(
                        getLongValue(annotation.bulkheadMaxWait(),
                                profile.getBulkheadMaxWaitMs())))
                .build();

        log.debug("Resolved Bulkhead '{}' with maxConcurrentCalls={}",
                name, config.getMaxConcurrentCalls());
        return context.getResilienceRegistry().bulkhead(name, config);
    }

    /**
      * Executes the next step with the resolved Bulkhead.
      *
      * <p>Retrieves the Bulkhead from the context and applies it to the next supplier.
      * The Bulkhead is expected to be stored in the context during the resolve phase.</p>
      *
      * <p>Tratamento de exceções:</p>
      * <ul>
      *   <li>BulkheadFullException: wrapped with Bulkhead context message</li>
      *   <li>Other exceptions: rethrown as-is to prevent message accumulation</li>
      * </ul>
      *
      * @param context o contexto de resiliência já resolvido
      * @param next o próximo passo na cadeia de execução
      * @return o resultado da execução
      */
    @Override
    public Object execute(ResilienceContext context, Supplier<Object> next) {
        Bulkhead bulkhead = resolve(context);
        try {
            return bulkhead.executeSupplier(() ->
                CompletableFuture.supplyAsync(next::get)
            ).join();
        } catch (BulkheadFullException e) {
            // Only wrap BulkheadFullException - these are truly caused by Bulkhead
            throw new RuntimeException("Bulkhead full: " + bulkhead.getName() + 
                    ". Max concurrent calls: " + bulkhead.getBulkheadConfig().getMaxConcurrentCalls(), e);
        } catch (Exception e) {
            // Unwrap CompletionException to check the actual cause
            e = (Exception) unwrapCompletionException(e);
            // For other exceptions, check if they're caused by Bulkhead
            // If not, rethrow as-is to prevent message accumulation
            if (isCausedBy(e, BulkheadFullException.class)) {
                throw new RuntimeException("Bulkhead full: " + bulkhead.getName() + 
                        ". Max concurrent calls: " + bulkhead.getBulkheadConfig().getMaxConcurrentCalls(), e);
            }
            // Rethrow without wrapping to prevent nested message accumulation
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }
    }

    /**
      * Unwraps CompletionException to get the actual cause.
      *
      * <p>This method safely unwraps CompletionException without causing issues
      * if the cause is null.</p>
      */
    private Throwable unwrapCompletionException(Throwable t) {
        if (t instanceof java.util.concurrent.CompletionException) {
            Throwable cause = t.getCause();
            if (cause != null) {
                return cause;
            }
        }
        return t;
    }
}