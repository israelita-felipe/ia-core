package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.config.ResilienceProperties;
import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.exception.ResilienceExecutionException;
import com.ia.core.resilience4j.fallback.FallbackStrategyRegistry;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.resilience4j.registry.ResilienceRegistry;
import com.ia.core.resilience4j.template.ResilienceTemplate;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AOP Aspect that intercepts methods annotated with {@link Resilient}
 * and applies the resilience4j patterns.
 *
 * <p>Applied patterns:</p>
 * <ul>
 *   <li>Aspect (AOP) - transverse application of resilience</li>
 *   <li>Decorator - wraps the method with protection layers</li>
 *   <li>Template Method - defines the skeleton of resilient execution</li>
 *   <li>Strategy - selects configurations by profile</li>
 * </ul>
 *
 * <p>Execution flow:</p>
 * <ol>
 *   <li>Resolve the resilience profile</li>
 *   <li>Create/obtain the Resilience4j decorators (CircuitBreaker, Retry, etc.)</li>
 *   <li>Execute the method within the resilient template</li>
 *   <li>In case of failure, apply the configured fallback</li>
 * </ol>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ResilienceAspect {

    private final ResilienceProperties properties;
    private final ResilienceRegistry resilienceRegistry;
    private final FallbackStrategyRegistry fallbackRegistry;
    private final ResilienceTemplate resilienceTemplate;
    private final ResilienceAspectContext contextPropagator;

    /**
     * Thread pool for asynchronous execution with timeout.
     */
    private final ExecutorService timeoutExecutor = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r);
        t.setName("resilience-timeout-%d".formatted(t.getId()));
        t.setDaemon(true);
        return t;
    });

    /**
     * Intercepts all methods annotated with @Resilient.
     */
    @Around("@annotation(com.ia.core.resilience4j.annotation.Resilient)")
    public Object applyResilience(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Resilient annotation = method.getAnnotation(Resilient.class);

        // 1. Resolve the profile
        ResilienceProfile profile = resolveProfile(annotation);

        // 2. Create the resilience context
        ResilienceContext context = ResilienceContext.builder()
                .profile(profile)
                .method(method)
                .joinPoint(joinPoint)
                .annotation(annotation)
                .build();

        // 3. Resolve/create the decorators
        CircuitBreaker circuitBreaker = resolveCircuitBreaker(context);
        Retry retry = resolveRetry(context);
        Bulkhead bulkhead = resolveBulkhead(context);
        RateLimiter rateLimiter = resolveRateLimiter(context);
        TimeLimiter timeLimiter = resolveTimeLimiter(context);

        // Capture context state from the current thread.
        // This allows the context propagator to restore state in a different thread.
        Object contextSnapshot = contextPropagator.captureContext();

        // 4. Execute within the resilient template
        try {
            return resilienceTemplate.execute(context, () -> {
                try {
                    // Rate Limiter → Bulkhead → Circuit Breaker → Retry → Timeout → Execution
                    return rateLimiter.executeSupplier(() ->
                            bulkhead.executeSupplier(() ->
                                    circuitBreaker.executeSupplier(() ->
                                            retry.executeSupplier(() ->
                                                    {
                                                        try {
                                                            return timeLimiter.executeFutureSupplier(() ->
                                                                    CompletableFuture.supplyAsync(() -> {
                                                                        try {
                                                                            return contextPropagator.executeWithContext(
                                                                                    contextSnapshot,
                                                                                    () -> {
                                                                                        try {
                                                                                            return joinPoint.proceed();
                                                                                        } catch (RuntimeException e) {
                                                                                            throw e;
                                                                                        } catch (Throwable e) {
                                                                                            throw new ResilienceExecutionException(e);
                                                                                        }
                                                                                    });
                                                                        } catch (RuntimeException e) {
                                                                            throw e;
                                                                        } catch (Throwable e) {
                                                                            throw new ResilienceExecutionException(e);
                                                                        }
                                                                    },
                                                                    timeoutExecutor)
                                                            );
                                                        } catch (RuntimeException e) {
                                                            throw e;
                                                        } catch (Throwable e) {
                                                            throw new ResilienceExecutionException(e);
                                                        }
                                                    }
                                            )
                                    )
                            )
                    );
                } catch (CompletionException e) {
                    throw e.getCause() != null ? new ResilienceExecutionException(e.getCause()): new ResilienceExecutionException(e);
                }
            });
        } catch (ResilienceExecutionException e) {
            throw e.getCause();
        } catch (Exception e) {
            // If fallback is enabled, execute it
            if (annotation.fallbackEnabled()) {
                log.debug("Executing fallback for {}#{}",
                        method.getDeclaringClass().getSimpleName(), method.getName());
                return fallbackRegistry.executeFallback(
                        annotation.value(), method, joinPoint.getArgs(), e);
            }
            throw e;
        }
    }

    /**
     * Resolves the resilience profile (annotation > properties > DEFAULT).
     */
    private ResilienceProfile resolveProfile(Resilient annotation) {
        return annotation.value();
    }

    /**
     * Resolves or creates the Circuit Breaker for the context.
     */
    private CircuitBreaker resolveCircuitBreaker(ResilienceContext context) {
        ResilienceProfile profile = context.getProfile();
        Resilient annotation = context.getAnnotation();

        String name = context.getCircuitBreakerName();

        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(
                        annotation.circuitBreakerFailureRate() >= 0
                                ? annotation.circuitBreakerFailureRate()
                                : profile.getCircuitBreakerFailureRateThreshold())
                .waitDurationInOpenState(Duration.ofMillis(
                        annotation.circuitBreakerWaitDuration() >= 0
                                ? annotation.circuitBreakerWaitDuration()
                                : profile.getCircuitBreakerWaitDurationInOpenStateMs()))
                .slidingWindowSize(
                        annotation.circuitBreakerSlidingWindow() >= 0
                                ? annotation.circuitBreakerSlidingWindow()
                                : profile.getCircuitBreakerSlidingWindowSize())
                .minimumNumberOfCalls(
                        annotation.circuitBreakerMinCalls() >= 0
                                ? annotation.circuitBreakerMinCalls()
                                : profile.getCircuitBreakerMinimumNumberOfCalls())
                .permittedNumberOfCallsInHalfOpenState(profile.getCircuitBreakerPermittedCallsInHalfOpen())
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .recordExceptions(IOException.class, java.util.concurrent.TimeoutException.class)
                .build();

        return resilienceRegistry.circuitBreaker(name, config);
    }

    /**
     * Resolves or creates the Retry for the context.
     */
    private Retry resolveRetry(ResilienceContext context) {
        ResilienceProfile profile = context.getProfile();
        Resilient annotation = context.getAnnotation();

        String name = context.getRetryName();

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(
                        annotation.maxRetryAttempts() >= 0
                                ? annotation.maxRetryAttempts()
                                : profile.getMaxRetryAttempts())
                .waitDuration(java.time.Duration.ofMillis(
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
                .retryExceptions(IOException.class, java.util.concurrent.TimeoutException.class)
                .build();

        return resilienceRegistry.retry(name, config);
    }

    /**
     * Resolves or creates the Bulkhead for the context.
     */
    private Bulkhead resolveBulkhead(ResilienceContext context) {
        ResilienceProfile profile = context.getProfile();
        Resilient annotation = context.getAnnotation();

        String name = context.getBulkheadName();

        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(
                        annotation.bulkheadMaxConcurrent() >= 0
                                ? annotation.bulkheadMaxConcurrent()
                                : profile.getBulkheadMaxConcurrentCalls())
                .maxWaitDuration(java.time.Duration.ofMillis(
                        annotation.bulkheadMaxWait() >= 0
                                ? annotation.bulkheadMaxWait()
                                : 0))
                .build();

        return resilienceRegistry.bulkhead(name, config);
    }

    /**
     * Resolves or creates the Rate Limiter for the context.
     */
    private RateLimiter resolveRateLimiter(ResilienceContext context) {
        ResilienceProfile profile = context.getProfile();
        Resilient annotation = context.getAnnotation();

        String name = context.getRateLimiterName();

        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(
                        annotation.rateLimiterLimit() >= 0
                                ? annotation.rateLimiterLimit()
                                : profile.getRateLimiterLimitForPeriod())
                .limitRefreshPeriod(java.time.Duration.ofSeconds(1))
                .timeoutDuration(java.time.Duration.ofMillis(
                        annotation.rateLimiterPeriod() >= 0
                                ? annotation.rateLimiterPeriod()
                                : profile.getRateLimiterTimeoutDurationMs()))
                .build();

        return resilienceRegistry.rateLimiter(name, config);
    }

    /**
     * Resolves or creates the Time Limiter for the context.
     */
    private TimeLimiter resolveTimeLimiter(ResilienceContext context) {
        ResilienceProfile profile = context.getProfile();
        Resilient annotation = context.getAnnotation();

        String name = context.getTimeLimiterName();

        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(java.time.Duration.ofMillis(
                        annotation.timeoutMs() >= 0
                                ? annotation.timeoutMs()
                                : profile.getRateLimiterTimeoutDurationMs()))
                .cancelRunningFuture(true)
                .build();

        return resilienceRegistry.timeLimiter(name, config);
    }

    /**
     * Encapsulates context propagation for thread environments (e.g., SecurityContext, MDC, etc).
     *
     * <p>This interface allows decoupling the resilience aspect from specific context implementations.
     * Implementations can capture and restore any ThreadLocal-based context (Spring Security,
     * SLF4J MDC, custom contexts, etc.) without coupling the aspect to those frameworks.</p>
     *
     * <p>Implementations should be Spring beans and will be automatically injected into
     * {@link ResilienceAspect}.</p>
     *
     * @author Israel Araújo
     * @since 1.0.0
     */
    public interface ResilienceAspectContext {

        /**
         * Captures the current context state from the calling thread.
         *
         * <p>This method is invoked in the original thread (where the method annotated
         * with @Resilient is being called) and should capture any ThreadLocal state
         * that needs to be propagated to the executor thread.</p>
         *
         * @return A snapshot of the context state. The returned object should be
         *         serializable (or at least transferable) to another thread.
         */
        Object captureContext();

        /**
         * Executes a supplier with the captured context restored.
         *
         * <p>This method is invoked in a worker thread (from the executor pool) and
         * should restore the captured context state before executing the supplier,
         * then clean up after execution.</p>
         *
         * @param contextSnapshot The context snapshot captured by {@link #captureContext()}.
         * @param supplier        The code to execute with the restored context.
         * @param <T>             The return type of the supplier.
         * @return The result returned by the supplier.
         * @throws Exception If the supplier throws an exception, it should be propagated.
         */
        <T> T executeWithContext(Object contextSnapshot, ContextSupplier<T> supplier) throws Exception;

        /**
         * A supplier that can execute code and throw checked exceptions.
         *
         * @param <T> The return type.
         */
        interface ContextSupplier<T> {
            T get() throws Throwable;
        }
    }
}
