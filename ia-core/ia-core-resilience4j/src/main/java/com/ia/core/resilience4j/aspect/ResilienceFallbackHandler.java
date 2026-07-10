package com.ia.core.resilience4j.aspect;

import com.ia.core.resilience4j.dto.ResilienceContext;
import com.ia.core.resilience4j.exception.BulkheadFullException;
import com.ia.core.resilience4j.exception.CircuitBreakerOpenException;
import com.ia.core.resilience4j.exception.RateLimitExceededException;
import com.ia.core.resilience4j.exception.ResilienceException;
import com.ia.core.resilience4j.fallback.FallbackResponse;
import com.ia.core.resilience4j.fallback.FallbackStrategyRegistry;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
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
            throw new CircuitBreakerOpenException(profileName.getName() + "-" + method.getName() + "-circuitbreaker");
        }

        if (isBulkheadFull(throwable)) {
            log.warn("Bulkhead full for {}#{} - rejecting call",
                method.getDeclaringClass().getSimpleName(), method.getName());
            throw new BulkheadFullException(profileName.getName() + "-" + method.getName() + "-bulkhead");
        }

        if (isRateLimitExceeded(throwable)) {
            log.warn("Rate limit exceeded for {}#{} - rejecting call",
                method.getDeclaringClass().getSimpleName(), method.getName());
            throw new RateLimitExceededException(profileName.getName() + "-" + method.getName() + "-ratelimiter");
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

    /**
      * Checks if the exception is caused by a CircuitBreaker being open.
      *
      * <p>Usa verificação de tipo em vez de análise de mensagem para maior robustez
      * e evitar falsos positivos após as mudanças no tratamento de mensagens.</p>
      */
    private boolean isCircuitBreakerOpen(Throwable t) {
        if (t == null) {
            return false;
        }
        // Unwrap CompletionException to check the actual cause
        t = unwrapCompletionException(t);
        // Check for Resilience4j's CallNotPermittedException (thrown when CB is OPEN)
        if (t instanceof CallNotPermittedException) {
            return true;
        }
        // Also check in cause chain for wrapped exceptions
        Throwable cause = t.getCause();
        while (cause != null && cause != t) {
            if (cause instanceof CallNotPermittedException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
      * Checks if the exception is caused by a Bulkhead being full.
      *
      * <p>Usa verificação de tipo em vez de análise de mensagem para maior robustez.</p>
      * <p>Note: Uses fully qualified class name to avoid conflict with custom BulkheadFullException.</p>
      */
    private boolean isBulkheadFull(Throwable t) {
        if (t == null) {
            return false;
        }
        // Unwrap CompletionException to check the actual cause
        t = unwrapCompletionException(t);
        // Check for Resilience4j's BulkheadFullException
        if (t instanceof io.github.resilience4j.bulkhead.BulkheadFullException) {
            return true;
        }
        // Also check in cause chain for wrapped exceptions
        Throwable cause = t.getCause();
        while (cause != null && cause != t) {
            if (cause instanceof io.github.resilience4j.bulkhead.BulkheadFullException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
      * Checks if the exception is caused by RateLimiter rejecting the call.
      *
      * <p>Usa verificação de tipo em vez de análise de mensagem para maior robustez.</p>
      */
    private boolean isRateLimitExceeded(Throwable t) {
        if (t == null) {
            return false;
        }
        // Unwrap CompletionException to check the actual cause
        t = unwrapCompletionException(t);
        // Check for Resilience4j's RequestNotPermitted
        if (t instanceof RequestNotPermitted) {
            return true;
        }
        // Also check in cause chain for wrapped exceptions
        Throwable cause = t.getCause();
        while (cause != null && cause != t) {
            if (cause instanceof RequestNotPermitted) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
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