package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.exception.BulkheadFullException;
import com.ia.core.resilience4j.exception.CircuitBreakerOpenException;
import com.ia.core.resilience4j.exception.RateLimitExceededException;
import com.ia.core.resilience4j.exception.ResilienceException;
import com.ia.core.resilience4j.fallback.FallbackResponse;
import com.ia.core.resilience4j.fallback.FallbackStrategyRegistry;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Handles fallback execution when resilience patterns prevent method execution.
 *
 * <p>This class determines when to apply fallback based on exception types
 * and delegates to the fallback strategy registry.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResilienceFallbackHandler {

    private final FallbackStrategyRegistry fallbackRegistry;

    /**
     * Handles fallback logic for a given context and throwable.
     *
     * @param context   the resilience context
     * @param throwable the exception that triggered the fallback
     * @return the fallback result or throws an exception
     */
    public Object handleFallback(ResilienceContext context, Throwable throwable) {
        Method method = context.getMethod();
        ResilienceProfile profileName = context.getProfile();

        if (isCircuitBreakerOpen(throwable)) {
            log.warn("Circuit breaker open for {}#{} - rejecting call",
                    method.getDeclaringClass().getSimpleName(), method.getName());
            throw new CircuitBreakerOpenException(context.getCircuitBreakerName());
        }

        if (isBulkheadFull(throwable)) {
            log.warn("Bulkhead full for {}#{} - rejecting call",
                    method.getDeclaringClass().getSimpleName(), method.getName());
            throw new BulkheadFullException(context.getBulkheadName());
        }

        if (isRateLimitExceeded(throwable)) {
            log.warn("Rate limit exceeded for {}#{} - rejecting call",
                    method.getDeclaringClass().getSimpleName(), method.getName());
            throw new RateLimitExceededException(context.getRateLimiterName());
        }

        FallbackResponse response = fallbackRegistry.executeFallback(
                profileName, method, context.getArgs(), throwable);

        if (response != null && response.isSuccess()) {
            log.info("Fallback executed successfully for {}#{}",
                    method.getDeclaringClass().getSimpleName(), method.getName());
            return response.getData();
        }

        if (throwable instanceof ResilienceException) {
            throw (ResilienceException) throwable;
        }

        throw new ResilienceException(
                "FAILURE_NOT_RECOVERABLE",
                "Non-recoverable error in " + method.getName(),
                throwable,
                false);
    }

    private boolean isCircuitBreakerOpen(Throwable t) {
        return t != null && (t.getMessage() != null &&
                t.getMessage().contains("CircuitBreaker") &&
                t.getMessage().contains("OPEN"));
    }

    private boolean isBulkheadFull(Throwable t) {
        return t != null && (t.getMessage() != null &&
                t.getMessage().contains("Bulkhead") &&
                t.getMessage().contains("full"));
    }

    private boolean isRateLimitExceeded(Throwable t) {
        return t != null && (t.getMessage() != null &&
                t.getMessage().contains("RateLimiter") &&
                t.getMessage().contains("wait"));
    }
}
