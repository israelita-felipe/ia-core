package com.ia.core.resilience4j.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
@Configuration
@ConfigurationProperties(prefix = "resilience4j")
public class ResilienceProperties {

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
        private int maxAttempts = 3;
        private long initialWaitMs = 1000;
        private double backoffMultiplier = 2.0;
        private long maxWaitMs = 10000;
        private boolean exponentialBackoff = true;
        private Class<? extends Throwable>[] retryExceptions = new Class[]{
                java.io.IOException.class,
                java.util.concurrent.TimeoutException.class
        };
    }

    @Getter
    @Setter
    public static class CircuitBreaker {
        private int failureRateThreshold = 50;
        private long waitDurationInOpenStateMs = 30000;
        private int slidingWindowSize = 10;
        private int minimumNumberOfCalls = 5;
        private int permittedCallsInHalfOpen = 3;
        private long slowCallDurationThresholdMs = 5000;
        private int slowCallRateThreshold = 100;
        private boolean automaticTransitionFromOpenToHalfOpen = true;
        private Class<? extends Throwable>[] recordExceptions = new Class[]{
                java.io.IOException.class,
                java.util.concurrent.TimeoutException.class
        };
    }

    @Getter
    @Setter
    public static class Bulkhead {
        private int maxConcurrentCalls = 10;
        private long maxWaitDurationMs = 0;
    }

    @Getter
    @Setter
    public static class RateLimiter {
        private int limitForPeriod = 50;
        private long limitRefreshPeriodMs = 1000;
        private long timeoutDurationMs = 2000;
    }

    @Getter
    @Setter
    public static class Timeout {
        private long durationMs = 10000;
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
