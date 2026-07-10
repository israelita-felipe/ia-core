package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * Handles rate limiter strategy configuration and execution.
 *
 * <p>This class encapsulates the logic for creating and configuring RateLimiter instances
 * based on resilience profiles and annotations.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>Single Responsibility</b>: Apenas lida com RateLimiter</li>
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
public class RateLimiterStrategyHandler extends AbstractResilienceStrategyHandler<RateLimiter> {

    /**
      * Creates or resolves a RateLimiter instance for the given context.
      *
      * <p>Configures the RateLimiter with:</p>
      * <ul>
      *   <li>Limit for period from annotation or profile</li>
      *   <li>Limit refresh period (1 second default)</li>
      *   <li>Timeout duration for acquiring permission</li>
      * </ul>
      *
      * @param context the resilience context containing profile and annotation info
      * @return configured RateLimiter instance
      */
    @Override
    public RateLimiter resolve(ResilienceContext context) {
        ProfileAnnotation data = extractProfileAndAnnotation(context);
        ResilienceProfile profile = data.profile();
        Resilient annotation = data.annotation();

        String name = profile.getName() + "-" + context.getMethod().getName() + "-ratelimiter";

        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(getIntValue(annotation.rateLimiterLimit(),
                        profile.getRateLimiterLimitForPeriod()))
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofMillis(
                        getLongValue(annotation.rateLimiterPeriod(),
                                profile.getRateLimiterTimeoutDurationMs())))
                .build();

        log.debug("Resolved RateLimiter '{}' with limitForPeriod={}",
                name, config.getLimitForPeriod());
        return context.getResilienceRegistry().rateLimiter(name, config);
    }

    /**
      * Executes the next step with the resolved RateLimiter.
      *
      * <p>Retrieves the RateLimiter from the context and applies it to the next supplier.
      * The RateLimiter is expected to be stored in the context during the resolve phase.</p>
      *
      * <p>Tratamento de exceções:</p>
      * <ul>
      *   <li>RequestNotPermitted: wrapped with RateLimiter context message</li>
      *   <li>Other exceptions: rethrown as-is to prevent message accumulation</li>
      * </ul>
      *
      * @param context o contexto de resiliência já resolvido
      * @param next o próximo passo na cadeia de execução
      * @return o resultado da execução
      */
    @Override
    public Object execute(ResilienceContext context, Supplier<Object> next) {
        RateLimiter rateLimiter = resolve(context);
        try {
            return rateLimiter.executeSupplier(() ->
                CompletableFuture.supplyAsync(next::get)
            ).join();
        } catch (RequestNotPermitted e) {
            // Only wrap RequestNotPermitted - these are truly caused by RateLimiter
            throw new RuntimeException("RateLimiter '" + rateLimiter.getName() + 
                    "' wait timeout exceeded. Too many requests.", e);
        } catch (Exception e) {
            // Unwrap CompletionException to check the actual cause
            e = (Exception) unwrapCompletionException(e);
            // For other exceptions, check if they're caused by RateLimiter
            // If not, rethrow as-is to prevent message accumulation
            if (isCausedBy(e, RequestNotPermitted.class)) {
                throw new RuntimeException("RateLimiter '" + rateLimiter.getName() + 
                        "' wait timeout exceeded. Too many requests.", e);
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