package com.ia.core.llm.service.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Métricas para monitoramento de interações com LLM.
 * <p>
 * Fornece métricas para monitorar requisições LLM, tempo de resposta e outros indicadores.
 * Baseado no ADR-013 (Logging e Monitoramento).
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Component
public class LlmMetrics {

  private final Counter llmRequestCounter;
  private final Timer llmResponseTimer;

  public LlmMetrics(MeterRegistry registry) {
    this.llmRequestCounter = Counter.builder("llm_requests_total")
        .description("Total de requisições LLM")
        .tag("type", "agent")
        .register(registry);

    this.llmResponseTimer = Timer.builder("llm_response_time")
        .description("Tempo de resposta LLM")
        .publishPercentiles(0.5, 0.95, 0.99)
        .register(registry);
  }

  /**
   * Registra uma requisição LLM.
   *
   * @param type Tipo de requisição (ex: agent, chat, tool)
   */
  public void recordRequest(String type) {
    llmRequestCounter.increment();
  }

  /**
   * Registra o tempo de resposta de uma requisição LLM.
   *
   * @param duration Duração em milissegundos
   */
  public void recordResponseTime(long duration) {
    llmResponseTimer.record(duration, TimeUnit.MILLISECONDS);
  }
}
