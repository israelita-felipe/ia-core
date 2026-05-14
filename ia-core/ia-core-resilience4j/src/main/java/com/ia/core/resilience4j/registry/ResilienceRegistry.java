package com.ia.core.resilience4j.registry;

import com.ia.core.resilience4j.config.ResilienceProperties;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registro centralizado de instâncias Resilience4j.
 *
 * <p>Gerencia o ciclo de vida de CircuitBreakers, Retries, Bulkheads,
 * RateLimiters e TimeLimiters, garantindo que cada perfil tenha
 * suas instâncias devidamente configuradas.</p>
 *
 * <p>Padrões aplicados:</p>
 * <ul>
 *   <li>Registry - catálogo centralizado de componentes</li>
 *   <li>Flyweight - reutiliza instâncias existentes</li>
 *   <li>Factory - cria instâncias com configurações específicas</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ResilienceRegistry {

    private final ResilienceProperties properties;

    /** Caches of instances by name */
    private final Map<String, CircuitBreaker> circuitBreakerCache = new ConcurrentHashMap<>();
    private final Map<String, Retry> retryCache = new ConcurrentHashMap<>();
    private final Map<String, Bulkhead> bulkheadCache = new ConcurrentHashMap<>();
    private final Map<String, ThreadPoolBulkhead> threadPoolBulkheadCache = new ConcurrentHashMap<>();
    private final Map<String, RateLimiter> rateLimiterCache = new ConcurrentHashMap<>();
    private final Map<String, TimeLimiter> timeLimiterCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        log.info("Initializing ResilienceRegistry with profiles: {}",
                properties.getProfiles().keySet());
        initializeDefaultInstances();
        initializeProfileInstances();
    }

    /**
     * Inicializa as instâncias com configuração global padrão.
     */
    private void initializeDefaultInstances() {
        var global = properties.getGlobal();

        circuitBreakerCache.put("default", createCircuitBreaker("default", global.getCircuitBreaker()));
        retryCache.put("default", createRetry("default", global.getRetry()));
        bulkheadCache.put("default", createBulkhead("default", global.getBulkhead()));
        rateLimiterCache.put("default", createRateLimiter("default", global.getRateLimiter()));
        timeLimiterCache.put("default", createTimeLimiter("default", global.getTimeout()));
    }

    /**
     * Inicializa as instâncias para cada perfil configurado.
     */
    private void initializeProfileInstances() {
        properties.getProfiles().forEach((name, profileConfig) -> {
            circuitBreakerCache.put(name, createCircuitBreaker(name, profileConfig.getCircuitBreaker()));
            retryCache.put(name, createRetry(name, profileConfig.getRetry()));
            bulkheadCache.put(name, createBulkhead(name, profileConfig.getBulkhead()));
            rateLimiterCache.put(name, createRateLimiter(name, profileConfig.getRateLimiter()));
            timeLimiterCache.put(name, createTimeLimiter(name, profileConfig.getTimeout()));
            log.info("Profile '{}' registered successfully", name);
        });
    }

    /**
     * Obtém ou cria um CircuitBreaker.
     *
     * @param name nome da instância
     * @param config configuração do circuit breaker
     * @return a instância de CircuitBreaker
     */
    public CircuitBreaker circuitBreaker(String name, CircuitBreakerConfig config) {
        return circuitBreakerCache.computeIfAbsent(name, n -> {
            log.info("Creating CircuitBreaker: {}", n);
            return CircuitBreaker.of(n, config);
        });
    }

    /**
     * Obtém ou cria um Retry.
     *
     * @param name nome da instância
     * @param config configuração do retry
     * @return a instância de Retry
     */
    public Retry retry(String name, RetryConfig config) {
        return retryCache.computeIfAbsent(name, n -> {
            log.info("Creating Retry: {}", n);
            return Retry.of(n, config);
        });
    }

    /**
     * Obtém ou cria um Bulkhead.
     *
     * @param name nome da instância
     * @param config configuração do bulkhead
     * @return a instância de Bulkhead
     */
    public Bulkhead bulkhead(String name, BulkheadConfig config) {
        return bulkheadCache.computeIfAbsent(name, n -> {
            log.info("Creating Bulkhead: {}", n);
            return Bulkhead.of(n, config);
        });
    }

    /**
     * Obtém ou cria um RateLimiter.
     *
     * @param name nome da instância
     * @param config configuração do rate limiter
     * @return a instância de RateLimiter
     */
    public RateLimiter rateLimiter(String name, RateLimiterConfig config) {
        return rateLimiterCache.computeIfAbsent(name, n -> {
            log.info("Creating RateLimiter: {}", n);
            return RateLimiter.of(n, config);
        });
    }

    /**
     * Obtém ou cria um TimeLimiter.
     *
     * @param name nome da instância
     * @param config configuração do time limiter
     * @return a instância de TimeLimiter
     */
    public TimeLimiter timeLimiter(String name, TimeLimiterConfig config) {
        return timeLimiterCache.computeIfAbsent(name, n -> {
            log.info("Creating TimeLimiter: {}", n);
            return TimeLimiter.of(n, config);
        });
    }

    /**
     * Obtém ou cria um ThreadPoolBulkhead.
     *
     * @param name nome da instância
     * @param config configuração do thread pool bulkhead
     * @return a instância de ThreadPoolBulkhead
     */
    public ThreadPoolBulkhead threadPoolBulkhead(String name, ThreadPoolBulkheadConfig config) {
        return threadPoolBulkheadCache.computeIfAbsent(name, n -> {
            log.info("Creating ThreadPoolBulkhead: {}", n);
            return ThreadPoolBulkhead.of(n, config);
        });
    }

    // === Internal factories ===

    private CircuitBreaker createCircuitBreaker(String name, ResilienceProperties.CircuitBreaker props) {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(props.getFailureRateThreshold())
                .waitDurationInOpenState(Duration.ofMillis(props.getWaitDurationInOpenStateMs()))
                .slidingWindowSize(props.getSlidingWindowSize())
                .minimumNumberOfCalls(props.getMinimumNumberOfCalls())
                .permittedNumberOfCallsInHalfOpenState(props.getPermittedCallsInHalfOpen())
                .automaticTransitionFromOpenToHalfOpenEnabled(props.isAutomaticTransitionFromOpenToHalfOpen())
                .slowCallDurationThreshold(Duration.ofMillis(props.getSlowCallDurationThresholdMs()))
                .slowCallRateThreshold(props.getSlowCallRateThreshold())
                .recordExceptions(props.getRecordExceptions())
                .build();
        return CircuitBreaker.of(name, config);
    }

    private Retry createRetry(String name, ResilienceProperties.Retry props) {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(props.getMaxAttempts())
                .waitDuration(Duration.ofMillis(props.getInitialWaitMs()))
                .retryExceptions(props.getRetryExceptions())
                .build();
        return Retry.of(name, config);
    }

    private Bulkhead createBulkhead(String name, ResilienceProperties.Bulkhead props) {
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(props.getMaxConcurrentCalls())
                .maxWaitDuration(Duration.ofMillis(props.getMaxWaitDurationMs()))
                .build();
        return Bulkhead.of(name, config);
    }

    private RateLimiter createRateLimiter(String name, ResilienceProperties.RateLimiter props) {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitForPeriod(props.getLimitForPeriod())
                .limitRefreshPeriod(Duration.ofMillis(props.getLimitRefreshPeriodMs()))
                .timeoutDuration(Duration.ofMillis(props.getTimeoutDurationMs()))
                .build();
        return RateLimiter.of(name, config);
    }

    private TimeLimiter createTimeLimiter(String name, ResilienceProperties.Timeout props) {
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(props.getDurationMs()))
                .cancelRunningFuture(props.isCancelRunningFuture())
                .build();
        return TimeLimiter.of(name, config);
    }

    /**
     * Obtém todas as instâncias registradas (para monitoramento/admin).
     *
     * @return mapa com todas as instâncias de resiliência registradas
     */
    public Map<String, Object> getAllInstances() {
        Map<String, Object> instances = new ConcurrentHashMap<>();
        instances.putAll(circuitBreakerCache);
        instances.putAll(retryCache);
        instances.putAll(bulkheadCache);
        instances.putAll(rateLimiterCache);
        instances.putAll(timeLimiterCache);
        instances.putAll(threadPoolBulkheadCache);
        return instances;
    }
}
