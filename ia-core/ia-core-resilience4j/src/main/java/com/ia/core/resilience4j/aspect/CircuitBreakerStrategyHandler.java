package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * Handles circuit breaker strategy configuration and execution.
 *
 * <p>This class encapsulates the logic for creating and configuring CircuitBreaker instances
 * based on resilience profiles and annotations.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Apenas lida com CircuitBreaker</li>
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
public class CircuitBreakerStrategyHandler extends AbstractResilienceStrategyHandler<CircuitBreaker> {

    /**
     * Creates or resolves a CircuitBreaker instance for the given context.
     *
     * <p>Configures the CircuitBreaker with:</p>
     * <ul>
     *   <li>Failure rate threshold from annotation or profile</li>
     *   <li>Wait duration in open state</li>
     *   <li>Sliding window size and minimum number of calls</li>
     *   <li>Automatic transition from open to half-open</li>
     *   <li>Recorded exceptions (IOException, TimeoutException)</li>
     * </ul>
     *
     * @param context the resilience context containing profile and annotation info
     * @return configured CircuitBreaker instance
     */
    @Override
    public CircuitBreaker resolve(ResilienceContext context) {
        ProfileAnnotation data = extractProfileAndAnnotation(context);
        ResilienceProfile profile = data.profile();
        Resilient annotation = data.annotation();

        String name = profile.getName() + "-" + context.getMethod().getName() + "-cb";

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(getIntValue(annotation.circuitBreakerFailureRate(),
                        profile.getCircuitBreakerFailureRateThreshold()))
                .waitDurationInOpenState(Duration.ofMillis(
                        getLongValue(annotation.circuitBreakerWaitDuration(),
                                profile.getCircuitBreakerWaitDurationInOpenStateMs())))
                .slidingWindowSize(getIntValue(annotation.circuitBreakerSlidingWindow(),
                        profile.getCircuitBreakerSlidingWindowSize()))
                .minimumNumberOfCalls(getIntValue(annotation.circuitBreakerMinCalls(),
                        profile.getCircuitBreakerMinimumNumberOfCalls()))
                .permittedNumberOfCallsInHalfOpenState(profile.getCircuitBreakerPermittedCallsInHalfOpen())
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .recordExceptions(IOException.class, TimeoutException.class)
                .build();

        log.debug("Resolved CircuitBreaker '{}' with failureRateThreshold={}%",
                name, config.getFailureRateThreshold());
        return context.getResilienceRegistry().circuitBreaker(name, config);
    }

    /**
     * Executes the next step with the resolved CircuitBreaker.
     *
     * <p>Retrieves the CircuitBreaker from the context and applies it to the next supplier.
     * The CircuitBreaker is expected to be stored in the context during the resolve phase.</p>
     *
     * @param context o contexto de resiliência já resolvido
     * @param next o próximo passo na cadeia de execução
     * @return o resultado da execução
     */
    @Override
    public Object execute(ResilienceContext context, Supplier<Object> next) {
        CircuitBreaker circuitBreaker = resolve(context);
        try {
            return circuitBreaker.executeSupplier(() ->
                CompletableFuture.supplyAsync(next::get)
            ).join();
        } catch (Exception e) {
            throw new RuntimeException("CircuitBreaker execution failed: " + e.getLocalizedMessage(), e);
        }
    }
}
