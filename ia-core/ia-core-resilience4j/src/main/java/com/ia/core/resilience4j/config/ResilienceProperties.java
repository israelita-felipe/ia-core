package com.ia.core.resilience4j.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Propriedades de configuração centralizada para o Resilience4j.
 *
 * <p>Permite configuração via YAML (application.yml) e programática.
 * Cada perfil de serviço pode ter suas configurações sobrescritas
 * individualmente.</p>
 *
 * <p>Estrutura de configuração:</p>
 * <pre>
 * resilience4j:
 *   profiles:
 *     external-api:
 *       retry:
 *         max-attempts: 5
 *         initial-wait: 1000ms
 *     llm-service:
 *       bulkhead:
 *         max-concurrent-calls: 3
 * </pre>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "resilience4j")
public class ResilienceProperties {

    // Default values for Retry configuration
    private static final int DEFAULT_RETRY_MAX_ATTEMPTS = 3;
    private static final long DEFAULT_RETRY_INITIAL_WAIT_MS = 1000;
    private static final double DEFAULT_RETRY_BACKOFF_MULTIPLIER = 2.0;
    private static final long DEFAULT_RETRY_MAX_WAIT_MS = 10000;

    // Default values for CircuitBreaker configuration
    private static final int DEFAULT_CIRCUIT_BREAKER_FAILURE_RATE_THRESHOLD = 50;
    private static final long DEFAULT_CIRCUIT_BREAKER_WAIT_DURATION_MS = 30000;
    private static final int DEFAULT_CIRCUIT_BREAKER_SLIDING_WINDOW_SIZE = 10;
    private static final int DEFAULT_CIRCUIT_BREAKER_MINIMUM_CALLS = 5;
    private static final int DEFAULT_CIRCUIT_BREAKER_PERMITTED_CALLS_HALF_OPEN = 3;
    private static final long DEFAULT_CIRCUIT_BREAKER_SLOW_CALL_DURATION_MS = 5000;
    private static final int DEFAULT_CIRCUIT_BREAKER_SLOW_CALL_RATE_THRESHOLD = 100;

    // Default values for Bulkhead configuration
    private static final int DEFAULT_BULKHEAD_MAX_CONCURRENT_CALLS = 10;
    private static final long DEFAULT_BULKHEAD_MAX_WAIT_DURATION_MS = 0;

    // Default values for RateLimiter configuration
    private static final int DEFAULT_RATE_LIMITER_LIMIT_FOR_PERIOD = 50;
    private static final long DEFAULT_RATE_LIMITER_REFRESH_PERIOD_MS = 1000;
    private static final long DEFAULT_RATE_LIMITER_TIMEOUT_MS = 2000;

    // Default values for Timeout configuration
    private static final long DEFAULT_TIMEOUT_DURATION_MS = 10000;

    /** Default global configurations */
    private Global global = new Global();

    /** Map of configurations by service profile */
    private Map<String, ProfileConfig> profiles = new HashMap<>();

    /** Metrics/observability configurations */
    private Metrics metrics = new Metrics();

    @Getter
    @Setter
    public static class Global {
        private Retry retry = new Retry();
        private CircuitBreaker circuitBreaker = new CircuitBreaker();
        private Bulkhead bulkhead = new Bulkhead();
        private RateLimiter rateLimiter = new RateLimiter();
        private Timeout timeout = new Timeout();
    }

    @Getter
    @Setter
    public static class ProfileConfig {
        private Retry retry = new Retry();
        private CircuitBreaker circuitBreaker = new CircuitBreaker();
        private Bulkhead bulkhead = new Bulkhead();
        private RateLimiter rateLimiter = new RateLimiter();
        private Timeout timeout = new Timeout();
        private Fallback fallback = new Fallback();
    }

    @Getter
    @Setter
    public static class Retry {
        private int maxAttempts = DEFAULT_RETRY_MAX_ATTEMPTS;
        private long initialWaitMs = DEFAULT_RETRY_INITIAL_WAIT_MS;
        private double backoffMultiplier = DEFAULT_RETRY_BACKOFF_MULTIPLIER;
        private long maxWaitMs = DEFAULT_RETRY_MAX_WAIT_MS;
        private boolean exponentialBackoff = true;
        private Class<? extends Throwable>[] retryExceptions = new Class[]{
                java.io.IOException.class,
                java.util.concurrent.TimeoutException.class
        };
    }

    @Getter
    @Setter
    public static class CircuitBreaker {
        private int failureRateThreshold = DEFAULT_CIRCUIT_BREAKER_FAILURE_RATE_THRESHOLD;
        private long waitDurationInOpenStateMs = DEFAULT_CIRCUIT_BREAKER_WAIT_DURATION_MS;
        private int slidingWindowSize = DEFAULT_CIRCUIT_BREAKER_SLIDING_WINDOW_SIZE;
        private int minimumNumberOfCalls = DEFAULT_CIRCUIT_BREAKER_MINIMUM_CALLS;
        private int permittedCallsInHalfOpen = DEFAULT_CIRCUIT_BREAKER_PERMITTED_CALLS_HALF_OPEN;
        private long slowCallDurationThresholdMs = DEFAULT_CIRCUIT_BREAKER_SLOW_CALL_DURATION_MS;
        private int slowCallRateThreshold = DEFAULT_CIRCUIT_BREAKER_SLOW_CALL_RATE_THRESHOLD;
        private boolean automaticTransitionFromOpenToHalfOpen = true;
        private Class<? extends Throwable>[] recordExceptions = new Class[]{
                java.io.IOException.class,
                java.util.concurrent.TimeoutException.class
        };
    }

    @Getter
    @Setter
    public static class Bulkhead {
        private int maxConcurrentCalls = DEFAULT_BULKHEAD_MAX_CONCURRENT_CALLS;
        private long maxWaitDurationMs = DEFAULT_BULKHEAD_MAX_WAIT_DURATION_MS;
    }

    @Getter
    @Setter
    public static class RateLimiter {
        private int limitForPeriod = DEFAULT_RATE_LIMITER_LIMIT_FOR_PERIOD;
        private long limitRefreshPeriodMs = DEFAULT_RATE_LIMITER_REFRESH_PERIOD_MS;
        private long timeoutDurationMs = DEFAULT_RATE_LIMITER_TIMEOUT_MS;
    }

    @Getter
    @Setter
    public static class Timeout {
        private long durationMs = DEFAULT_TIMEOUT_DURATION_MS;
        private boolean cancelRunningFuture = true;
    }

    @Getter
    @Setter
    public static class Fallback {
        private String fallbackMethod;
        private String fallbackBean;
        private boolean enabled = true;
    }

    @Getter
    @Setter
    public static class Metrics {
        private boolean enabled = true;
        private boolean exportToMicrometer = true;
        private String[] metricNames = {
                "resilience4j.circuitbreaker.calls",
                "resilience4j.circuitbreaker.state",
                "resilience4j.retry.calls",
                "resilience4j.bulkhead.calls",
                "resilience4j.ratelimiter.calls",
                "resilience4j.timed.calls"
        };
    }
}
