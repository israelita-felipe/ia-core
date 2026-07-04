package com.ia.core.resilience4j.annotation;

import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.test.CoreBaseUnitTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para a anotação Resilient baseados nos casos de teste documentados.
 */
class ResilientTestCore extends CoreBaseUnitTest {

    @Test
    void deveTerValorPadraoDoProfile() {
        // Arrange
        Resilient resilient = getResilientAnnotation();

        // Act
        ResilienceProfile profile = resilient.value();

        // Assert
        assertThat(profile).isEqualTo(ResilienceProfile.DEFAULT);
    }

    @Test
    void deveTerValorPadraoDoRegistryName() {
        // Arrange
        Resilient resilient = getResilientAnnotation();

        // Act
        String registryName = resilient.registryName();

        // Assert
        assertThat(registryName).isEmpty();
    }

    @Test
    void deveTerValoresPadraoDoCircuitBreaker() {
        // Arrange
        Resilient resilient = getResilientAnnotation();

        // Act & Assert
        assertThat(resilient.circuitBreakerFailureRate()).isEqualTo(-1);
        assertThat(resilient.circuitBreakerWaitDuration()).isEqualTo(-1);
        assertThat(resilient.circuitBreakerSlidingWindow()).isEqualTo(-1);
        assertThat(resilient.circuitBreakerMinCalls()).isEqualTo(-1);
    }

    @Test
    void deveTerValoresPadraoDoRetry() {
        // Arrange
        Resilient resilient = getResilientAnnotation();

        // Act & Assert
        assertThat(resilient.maxRetryAttempts()).isEqualTo(-1);
        assertThat(resilient.retryInitialWait()).isEqualTo(-1);
        assertThat(resilient.retryBackoffMultiplier()).isEqualTo(-1);
        assertThat(resilient.retryMaxWait()).isEqualTo(-1);
    }

    @Test
    void deveTerValoresPadraoDoBulkhead() {
        // Arrange
        Resilient resilient = getResilientAnnotation();

        // Act & Assert
        assertThat(resilient.bulkheadMaxConcurrent()).isEqualTo(-1);
        assertThat(resilient.bulkheadMaxWait()).isEqualTo(-1);
    }

    @Test
    void deveTerValoresPadraoDoRateLimiter() {
        // Arrange
        Resilient resilient = getResilientAnnotation();

        // Act & Assert
        assertThat(resilient.rateLimiterLimit()).isEqualTo(-1);
        assertThat(resilient.rateLimiterPeriod()).isEqualTo(-1);
    }

    @Test
    void deveTerValorPadraoDoTimeout() {
        // Arrange
        Resilient resilient = getResilientAnnotation();

        // Act
        long timeoutMs = resilient.timeoutMs();

        // Assert
        assertThat(timeoutMs).isEqualTo(-1);
    }

    @Test
    void deveTerValoresPadraoDoFallback() {
        // Arrange
        Resilient resilient = getResilientAnnotation();

        // Act & Assert
        assertThat(resilient.fallbackMethod()).isEmpty();
        assertThat(resilient.fallbackBean()).isEmpty();
        assertThat(resilient.fallbackEnabled()).isFalse();
    }

    private Resilient getResilientAnnotation() {
        // Cria uma classe anônima para obter a anotação
        class TestClass {
            @Resilient
            public void testMethod() {}
        }
        try {
            return TestClass.class.getMethod("testMethod").getAnnotation(Resilient.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
