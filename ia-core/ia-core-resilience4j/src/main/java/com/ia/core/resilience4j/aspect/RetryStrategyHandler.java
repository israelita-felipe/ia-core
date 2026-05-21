package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.core.IntervalFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * Handles retry strategy configuration and execution.
 *
 * <p>This class encapsulates the logic for creating and configuring Retry instances
 * based on resilience profiles and annotations.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Apenas lida com Retry</li>
 *   <li><b>Open/Closed</b>: Extensível via interface ResilienceStrategyHandler</li>
 *   <li><b>Liskov Substitution</b>: Implementa ResilienceStrategyHandler<Retry></li>
 *   <li><b>Interface Segregation</b>: Interface mínima necessária</li>
 *   <li><b>Dependency Inversion</b>: Depende de ResilienceContext (abstração)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class RetryStrategyHandler implements ResilienceStrategyHandler<Retry> {

    /**
     * Creates or resolves a Retry instance for the given context.
     *
     * <p>Configures the Retry with:</p>
     * <ul>
     *   <li>Max attempts from annotation or profile</li>
     *   <li>Wait duration with exponential backoff</li>
     *   <li>Retryable exceptions (IOException, TimeoutException)</li>
     * </ul>
     *
     * @param context the resilience context containing profile and annotation info
     * @return configured Retry instance
     */
    @Override
    public Retry resolve(ResilienceContext context) {
        ResilienceProfile profile = context.getProfile();
        com.ia.core.resilience4j.annotation.Resilient annotation = context.getAnnotation();

        String name = profile.getName() + "-" + context.getMethod().getName() + "-retry";

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(
                        annotation.maxRetryAttempts() >= 0
                                ? annotation.maxRetryAttempts()
                                : profile.getMaxRetryAttempts())
                .waitDuration(Duration.ofMillis(
                        annotation.retryInitialWait() >= 0
                                ? annotation.retryInitialWait()
                                : profile.getRetryInitialWaitMs()))
                .intervalFunction(
                        annotation.retryBackoffMultiplier() >= 0
                                ? IntervalFunction.ofExponentialBackoff(
                                        profile.getRetryInitialWaitMs(),
                                        annotation.retryBackoffMultiplier())
                                : IntervalFunction.ofExponentialBackoff(
                                        profile.getRetryInitialWaitMs(),
                                        profile.getRetryBackoffMultiplier()))
                .retryExceptions(IOException.class, TimeoutException.class)
                .build();

        log.debug("Resolved Retry '{}' with maxAttempts={}", name, config.getMaxAttempts());
        return context.getResilienceRegistry().retry(name, config);
    }

    /**
     * Executes the next step with the resolved Retry.
     *
     * <p>Retrieves the Retry from the context and applies it to the next supplier.
     * The Retry is expected to be stored in the context during the resolve phase.</p>
     *
     * @param context o contexto de resiliência já resolvido
     * @param next o próximo passo na cadeia de execução
     * @return o resultado da execução
     */
    @Override
    public Object execute(ResilienceContext context, Supplier<Object> next){
        Retry retry = resolve(context);
        return retry.executeSupplier(next);
    }
}
