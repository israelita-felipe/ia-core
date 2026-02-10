package com.ia.core.monitoring.config;

import org.springframework.boot.actuate.metrics.annotation.Timed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Configuração de métricas do ia-core-apps.
 * 
 * Este arquivo define as métricas customizadas que serão expostas
 * via endpoint /actuator/prometheus para integração com sistemas
 * de monitoramento como Prometheus e Grafana.
 * 
 * @author Israel Araújo
 */
@Configuration
public class MetricsConfig {

    /**
     * Timer para tempo de resposta da API.
     */
    @Bean
    public Timer apiResponseTimer(MeterRegistry registry) {
        return Timer.builder("ia_core_api_response_time")
                .description("Tempo de resposta da API")
                .publishPercentiles(0.5, 0.9, 0.95, 0.99)
                .publishPercentileHistogram()
                .register(registry);
    }

    /**
     * Contador de requisições à API.
     */
    @Bean
    public Counter apiRequestsCounter(MeterRegistry registry) {
        return Counter.builder("ia_core_api_requests_total")
                .description("Total de requisições à API")
                .tag("application", "ia-core-apps")
                .register(registry);
    }

    /**
     * Contador de entidades processadas.
     */
    @Bean
    public Counter entitiesProcessedCounter(MeterRegistry registry) {
        return Counter.builder("ia_core_entities_processed_total")
                .description("Total de entidades processadas")
                .register(registry);
    }

    /**
     * Timer para operações de negócio.
     */
    @Bean
    public Timer businessOperationTimer(MeterRegistry registry) {
        return Timer.builder("ia_core_business_operation_time")
                .description("Tempo de operações de negócio")
                .publishPercentiles(0.5, 0.9, 0.95)
                .register(registry);
    }

    /**
     * Exemplo de gauge para sessões ativas (a ser implementado pelo serviço).
     */
    @Bean
    public Gauge activeSessionsGauge(MeterRegistry registry) {
        return Gauge.builder("ia_core_active_sessions", registry, 
                r -> r.get("ia_core_active_sessions").gauge().value())
                .description("Número de sessões ativas")
                .tag("application", "ia-core-apps")
                .register(registry);
    }

    /**
     * Métrica de erros por tipo.
     */
    @Bean
    public Counter errorCounter(MeterRegistry registry) {
        return Counter.builder("ia_core_errors_total")
                .description("Total de erros por tipo")
                .tag("type", "generic")
                .register(registry);
    }
}
