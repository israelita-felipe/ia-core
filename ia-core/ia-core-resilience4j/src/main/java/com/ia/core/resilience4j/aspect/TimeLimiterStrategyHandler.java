package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Handles time limiter strategy configuration and execution.
 *
 * <p>This class encapsulates the logic for creating and configuring TimeLimiter instances
 * based on resilience profiles and annotations.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Apenas lida com TimeLimiter</li>
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
public class TimeLimiterStrategyHandler extends AbstractResilienceStrategyHandler<TimeLimiter> {

    /**
     * Creates or resolves a TimeLimiter instance for the given context.
     *
     * <p>Configures the TimeLimiter with:</p>
     * <ul>
     *   <li>Timeout duration from annotation or profile</li>
     *   <li>Cancel running future on timeout</li>
     * </ul>
     *
     * @param context the resilience context containing profile and annotation info
     * @return configured TimeLimiter instance
     */
    @Override
    public TimeLimiter resolve(ResilienceContext context) {
        ProfileAnnotation data = extractProfileAndAnnotation(context);
        ResilienceProfile profile = data.profile();
        Resilient annotation = data.annotation();

        String name = profile.getName() + "-" + context.getMethod().getName() + "-timelimiter";

        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(
                        getLongValue(annotation.timeoutMs(),
                                profile.getTimeoutMs())))
                .cancelRunningFuture(true)
                .build();

        log.debug("Resolved TimeLimiter '{}' with timeoutDuration={}",
                name, config.getTimeoutDuration());
        return context.getResilienceRegistry().timeLimiter(name, config);
    }

    /**
     * Executes the next step with the resolved TimeLimiter.
     *
     * <p>Retrieves the TimeLimiter from the context and applies it to the next supplier.
     * The TimeLimiter is expected to be stored in the context during the resolve phase.</p>
     *
     * @param context o contexto de resiliência já resolvido
     * @param next o próximo passo na cadeia de execução
     * @return o resultado da execução
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object execute(ResilienceContext context, Supplier<Object> next) {
        TimeLimiter timeLimiter = resolve(context);
        try {
            return timeLimiter.executeFutureSupplier(() ->
                    CompletableFuture.supplyAsync(next::get)
            );
        } catch (Exception e) {
            throw new RuntimeException("TimeLimiter execution failed: " + e.getLocalizedMessage(), e);
        }
    }
}
