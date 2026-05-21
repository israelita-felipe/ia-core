package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
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
 *   <li><b>Open/Closed</b>: Extensível via interface ResilienceStrategyHandler</li>
 *   <li><b>Liskov Substitution</b>: Implementa ResilienceStrategyHandler<Bulkhead></li>
 *   <li><b>Interface Segregation</b>: Interface mínima necessária</li>
 *   <li><b>Dependency Inversion</b>: Depende de ResilienceContext (abstração)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class BulkheadStrategyHandler implements ResilienceStrategyHandler<Bulkhead> {

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
        ResilienceProfile profile = context.getProfile();
        com.ia.core.resilience4j.annotation.Resilient annotation = context.getAnnotation();

        String name = profile.getName() + "-" + context.getMethod().getName() + "-bulkhead";

        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(
                        annotation.bulkheadMaxConcurrent() >= 0
                                ? annotation.bulkheadMaxConcurrent()
                                : profile.getBulkheadMaxConcurrentCalls())
                .maxWaitDuration(Duration.ofMillis(
                        annotation.bulkheadMaxWait() >= 0
                                ? annotation.bulkheadMaxWait()
                                : 0))
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
     * @param context o contexto de resiliência já resolvido
     * @param next o próximo passo na cadeia de execução
     * @return o resultado da execução
     */
    @Override
    public Object execute(ResilienceContext context, Supplier<Object> next) {
        Bulkhead bulkhead = resolve(context);
        return bulkhead.executeSupplier(next);
    }
}
