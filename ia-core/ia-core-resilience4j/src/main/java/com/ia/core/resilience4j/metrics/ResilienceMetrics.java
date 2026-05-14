package com.ia.core.resilience4j.metrics;

import io.micrometer.core.instrument.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Métricas de resiliência usando Micrometer.
 *
 * <p>Registra métricas para chamadas bem-sucedidas, falhas,
 * rejeições de circuit breaker, bulkhead e rate limiter.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class ResilienceMetrics {

    private static final String METRIC_PREFIX = "resilience4j";
    private final MeterRegistry meterRegistry;
    private final Map<String, Counter> counterCache = new ConcurrentHashMap<>();
    private final Map<String, Timer> timerCache = new ConcurrentHashMap<>();

    public ResilienceMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        log.info("ResilienceMetrics initialized with MeterRegistry: {}",
                meterRegistry != null ? meterRegistry.getClass().getSimpleName() : "null");
    }

    /**
     * Registra o sucesso de uma operação.
     *
     * @param profile nome do perfil de resiliência
     * @param methodName nome do método executado
     * @param durationMs duração da operação em milissegundos
     */
    public void recordSuccess(String profile, String methodName, long durationMs) {
        counter("result", "success", profile, methodName).increment();
        timer("success", profile, methodName).record(durationMs, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    /**
     * Registra um erro em uma operação.
     *
     * @param profile nome do perfil de resiliência
     * @param methodName nome do método executado
     * @param errorType tipo de erro (classe da exceção)
     * @param durationMs duração da operação em milissegundos
     */
    public void recordError(String profile, String methodName, String errorType, long durationMs) {
        counter("result", "error", profile, methodName).increment();
        counter("error", errorType, profile, methodName).increment();
        timer("error", profile, methodName).record(durationMs, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    /**
     * Registra rejeição por circuit breaker.
     *
     * @param profile nome do perfil de resiliência
     * @param methodName nome do método rejeitado
     */
    public void recordCircuitBreakerRejection(String profile, String methodName) {
        counter("rejection", "circuit_breaker", profile, methodName).increment();
    }

    /**
     * Registra rejeição por bulkhead.
     *
     * @param profile nome do perfil de resiliência
     * @param methodName nome do método rejeitado
     */
    public void recordBulkheadRejection(String profile, String methodName) {
        counter("rejection", "bulkhead", profile, methodName).increment();
    }

    /**
     * Registra rejeição por rate limiter.
     *
     * @param profile nome do perfil de resiliência
     * @param methodName nome do método rejeitado
     */
    public void recordRateLimiterRejection(String profile, String methodName) {
        counter("rejection", "rate_limiter", profile, methodName).increment();
    }

    /**
     * Registra rejeição por timeout.
     *
     * @param profile nome do perfil de resiliência
     * @param methodName nome do método rejeitado
     */
    public void recordTimeoutRejection(String profile, String methodName) {
        counter("rejection", "timeout", profile, methodName).increment();
    }

    private Counter counter(String... tags) {
        String key = String.join(".", tags);
        return counterCache.computeIfAbsent(key, k ->
                Counter.builder(METRIC_PREFIX + ".calls").tags(tags).register(meterRegistry));
    }

    private Timer timer(String... tags) {
        String key = String.join(".", tags);
        return timerCache.computeIfAbsent(key, k ->
                Timer.builder(METRIC_PREFIX + ".timers").tags(tags)
                        .publishPercentileHistogram().register(meterRegistry));
    }
}
