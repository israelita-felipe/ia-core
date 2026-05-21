package com.ia.core.resilience4j.config;

import com.ia.core.resilience4j.aspect.ResilienceAspect;
import com.ia.core.resilience4j.aspect.ResilienceExecutionChainBuilder;
import com.ia.core.resilience4j.aspect.ResilienceFallbackHandler;
import com.ia.core.resilience4j.fallback.FallbackStrategyRegistry;
import com.ia.core.resilience4j.metrics.ResilienceMetrics;
import com.ia.core.resilience4j.registry.ResilienceRegistry;
import com.ia.core.resilience4j.template.ResilienceTemplate;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Auto-configuration of the Resilience4j skill.
 *
 * <p>Registers all necessary beans for the resilience functionality,
 * including registries, aspect, template and metrics.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(ResilienceProperties.class)
public class ResilienceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ResilienceRegistry resilienceRegistry(ResilienceProperties properties) {
        log.info("Creating ResilienceRegistry");
        return new ResilienceRegistry(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public FallbackStrategyRegistry fallbackStrategyRegistry() {
        log.info("Creating FallbackStrategyRegistry");
        return new FallbackStrategyRegistry();
    }

    @Bean
    @ConditionalOnMissingBean
    public ResilienceMetrics resilienceMetrics(MeterRegistry meterRegistry) {
        log.info("Creating ResilienceMetrics with MeterRegistry: {}",
                meterRegistry != null ? meterRegistry.getClass().getSimpleName() : "null");
        return new ResilienceMetrics(meterRegistry);
    }

    @Bean
    @ConditionalOnMissingBean
    public ResilienceTemplate resilienceTemplate(ResilienceMetrics metrics, ResilienceFallbackHandler fallbackHandler) {
        log.info("Creating ResilienceTemplate with ResilienceMetrics: {} and ResilienceFallbackHandler: {}",
                metrics != null ? metrics.getClass().getSimpleName() : "null",
                fallbackHandler != null ? fallbackHandler.getClass().getSimpleName() : "null");
        return new ResilienceTemplate(metrics, fallbackHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public ResilienceAspect resilienceAspect(
            ResilienceRegistry registry,
            ResilienceTemplate template,
            ResilienceExecutionChainBuilder executionChainBuilder) {
        log.info("Creating ResilienceAspect");
        return new ResilienceAspect(registry, template,
                 executionChainBuilder);
    }

    @Bean
    @ConditionalOnMissingBean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        return CircuitBreakerRegistry.ofDefaults();
    }

    @Bean
    @ConditionalOnMissingBean
    public RetryRegistry retryRegistry() {
        return RetryRegistry.ofDefaults();
    }

    @Bean
    @ConditionalOnMissingBean
    public BulkheadRegistry bulkheadRegistry() {
        return BulkheadRegistry.ofDefaults();
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterRegistry rateLimiterRegistry() {
        return RateLimiterRegistry.ofDefaults();
    }

    @Bean
    @ConditionalOnMissingBean
    public TimeLimiterRegistry timeLimiterRegistry() {
        return TimeLimiterRegistry.ofDefaults();
    }
}
