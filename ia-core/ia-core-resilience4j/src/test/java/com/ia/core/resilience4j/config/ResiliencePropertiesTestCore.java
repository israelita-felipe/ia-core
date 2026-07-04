package com.ia.core.resilience4j.config;

import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para ResilienceProperties baseados nos casos de teste documentados.
 */
class ResiliencePropertiesTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerConfiguracaoGlobalPadrao() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();

        // Act
        ResilienceProperties.Global global = properties.getGlobal();

        // Assert
        assertThat(global).isNotNull();
        assertThat(global.getRetry()).isNotNull();
        assertThat(global.getCircuitBreaker()).isNotNull();
        assertThat(global.getBulkhead()).isNotNull();
        assertThat(global.getRateLimiter()).isNotNull();
        assertThat(global.getTimeout()).isNotNull();
    }

    @Test
    void deveTerValoresPadraoDeRetry() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();

        // Act
        ResilienceProperties.Retry retry = properties.getGlobal().getRetry();

        // Assert
        assertThat(retry.getMaxAttempts()).isEqualTo(3);
        assertThat(retry.getInitialWaitMs()).isEqualTo(1000);
        assertThat(retry.getBackoffMultiplier()).isEqualTo(2.0);
        assertThat(retry.getMaxWaitMs()).isEqualTo(10000);
        assertThat(retry.isExponentialBackoff()).isTrue();
    }

    @Test
    void deveTerValoresPadraoDeCircuitBreaker() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();

        // Act
        ResilienceProperties.CircuitBreaker circuitBreaker = properties.getGlobal().getCircuitBreaker();

        // Assert
        assertThat(circuitBreaker.getFailureRateThreshold()).isEqualTo(50);
        assertThat(circuitBreaker.getWaitDurationInOpenStateMs()).isEqualTo(30000);
        assertThat(circuitBreaker.getSlidingWindowSize()).isEqualTo(10);
        assertThat(circuitBreaker.getMinimumNumberOfCalls()).isEqualTo(5);
    }

    @Test
    void deveTerValoresPadraoDeBulkhead() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();

        // Act
        ResilienceProperties.Bulkhead bulkhead = properties.getGlobal().getBulkhead();

        // Assert
        assertThat(bulkhead.getMaxConcurrentCalls()).isEqualTo(10);
        assertThat(bulkhead.getMaxWaitDurationMs()).isEqualTo(0);
    }

    @Test
    void deveTerValoresPadraoDeRateLimiter() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();

        // Act
        ResilienceProperties.RateLimiter rateLimiter = properties.getGlobal().getRateLimiter();

        // Assert
        assertThat(rateLimiter.getLimitForPeriod()).isEqualTo(50);
        assertThat(rateLimiter.getLimitRefreshPeriodMs()).isEqualTo(1000);
        assertThat(rateLimiter.getTimeoutDurationMs()).isEqualTo(2000);
    }

    @Test
    void deveTerValoresPadraoDeTimeout() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();

        // Act
        ResilienceProperties.Timeout timeout = properties.getGlobal().getTimeout();

        // Assert
        assertThat(timeout.getDurationMs()).isEqualTo(10000);
        assertThat(timeout.isCancelRunningFuture()).isTrue();
    }

    @Test
    void deveTerValoresPadraoDeFallback() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();
        ResilienceProperties.ProfileConfig profileConfig = new ResilienceProperties.ProfileConfig();

        // Act
        ResilienceProperties.Fallback fallback = profileConfig.getFallback();

        // Assert
        assertThat(fallback.isEnabled()).isTrue();
        assertThat(fallback.getFallbackMethod()).isNull();
        assertThat(fallback.getFallbackBean()).isNull();
    }

    @Test
    void deveTerValoresPadraoDeMetrics() {
        // Arrange
        ResilienceProperties properties = new ResilienceProperties();

        // Act
        ResilienceProperties.Metrics metrics = properties.getMetrics();

        // Assert
        assertThat(metrics.isEnabled()).isTrue();
        assertThat(metrics.isExportToMicrometer()).isTrue();
    }
}
