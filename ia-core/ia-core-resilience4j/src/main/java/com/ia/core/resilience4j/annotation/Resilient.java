package com.ia.core.resilience4j.annotation;

import com.ia.core.resilience4j.profile.ResilienceProfile;

import java.lang.annotation.*;

/**
 * Anotação que combina múltiplos padrões de resiliência do Resilience4j.
 *
 * <p>Permite aplicar Circuit Breaker, Retry, Bulkhead e Rate Limiter de forma
 * declarativa em métodos, usando perfis predefinidos ou configurações
 * individuais.</p>
 *
 * @see ResilienceProfile
 * @author Israel Araújo
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Resilient {

    ResilienceProfile value() default ResilienceProfile.DEFAULT;

    String registryName() default "";

    int circuitBreakerFailureRate() default -1;

    long circuitBreakerWaitDuration() default -1;

    int circuitBreakerSlidingWindow() default -1;

    int circuitBreakerMinCalls() default -1;

    int maxRetryAttempts() default -1;

    long retryInitialWait() default -1;

    double retryBackoffMultiplier() default -1;

    long retryMaxWait() default -1;

    int bulkheadMaxConcurrent() default -1;

    long bulkheadMaxWait() default -1;

    int rateLimiterLimit() default -1;

    long rateLimiterPeriod() default -1;

    long timeoutMs() default -1;

    String fallbackMethod() default "";

    String fallbackBean() default "";

    Class<? extends Throwable>[] fallbackOn() default {};

    Class<? extends Throwable>[] noFallbackOn() default {};

    boolean fallbackEnabled() default false;
}
