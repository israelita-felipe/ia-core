package com.ia.core.resilience4j.profile;

import lombok.Getter;

/**
 * Perfis de resiliência que definem o comportamento de circuit breaker,
 * retry, bulkhead e rate limiter para diferentes tipos de serviço.
 *
 * <p>Cada perfil representa uma estratégia de resiliência otimizada para
 * uma categoria específica de chamada:</p>
 * <ul>
 *   <li>EXTERNAL_API - APIs externas (IBGE, WhatsApp, Telegram)</li>
 *   <li>LLM_SERVICE - Serviços de LLM (OpenAI, Ollama)</li>
 *   <li>WEB_SCRAPING - Web scraping (MiracleScraper)</li>
 *   <li>INTERNAL_SERVICE - Serviços internos (Feign entre módulos)</li>
 *   <li>DATABASE - Operações de banco de dados (JPA/Repository)</li>
 * </ul>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Getter
public enum ResilienceProfile {

    /**
     * Perfil para APIs externas de terceiros.
     * <p>Endpoints de serviços externos como IBGE, WhatsApp e Telegram.
     * Tolerância maior a falhas e timeouts mais longos.</p>
     */
    EXTERNAL_API(
            "external-api",
            5, 1000L, 2.0, 30000L,
            40, 30000L, 20, 10, 5,
            10, 5000L, 100, 10000L, 30000L
    ),

    /**
     * Perfil para serviços de inteligência artificial (LLM).
     * <p>Endpoints de LLM como OpenAI e Ollama.
     * Menor taxa de retry e timeouts mais longos devido ao tempo de processamento.</p>
     */
    LLM_SERVICE(
            "llm-service",
            2, 2000L, 1.5, 5000L,
            30, 60000L, 10, 5, 3,
            3, 1000L, 10, 5000L, 60000L
    ),

    /**
     * Perfil para operações de web scraping.
     * <p>Scraping de páginas web via MiracleScraper.
     * Permite mais tentativas e janela de circuit breaker maior.</p>
     */
    WEB_SCRAPING(
            "web-scraping",
            3, 3000L, 2.0, 30000L,
            50, 60000L, 10, 5, 3,
            2, 500L, 2, 10000L, 60000L
    ),

    /**
     * Perfil para serviços internos da aplicação.
     * <p>Comunicação entre módulos via Feign.
     * Timeouts mais curtos e tolerância moderada a falhas.</p>
     */
    INTERNAL_SERVICE(
            "internal-service",
            3, 500L, 2.0, 5000L,
            50, 15000L, 20, 10, 5,
            20, 200, 10, 1000L, 5000L
    ),

    /**
     * Perfil para operações de banco de dados.
     * <p>Operações de leitura e escrita via JPA/Repository.
     * Timeouts curtos e alta tolerância a falhas.</p>
     */
    DATABASE(
            "database",
            3, 200L, 1.5, 2000L,
            60, 10000L, 10, 5, 3,
            15, 500, 5, 500L, 2000L
    ),

    /**
     * Perfil padrão de fallback.
     * <p>Utilizado quando nenhum perfil específico é encontrado
     * ou quando o nome informado é {@code null}.
     * Configurações equilibradas para uso geral.</p>
     */
    DEFAULT(
            "default",
            3, 1000L, 2.0, 10000L,
            50, 30000L, 10, 5, 3,
            10, 100, 50, 2000L, 10000L
    );

    /**
     * Nome identificador do perfil de resiliência.
     * Utilizado para correspondência no método {@link #fromName(String)}.
     */
    private final String name;

    /**
     * Número máximo de tentativas de retry antes de considerar a operação como falha.
     * Valor {@code 0} indica que o retry está desabilitado.
     */
    private final int maxRetryAttempts;

    /**
     * Tempo inicial de espera em milissegundos entre tentativas de retry.
     * Este valor é multiplicado pelo {@link #retryBackoffMultiplier} a cada tentativa subsequente.
     */
    private final long retryInitialWaitMs;

    /**
     * Multiplicador de backoff exponencial aplicado ao tempo de espera entre retries.
     * O tempo de espera é calculado como: {@code retryInitialWaitMs * retryBackoffMultiplier ^ (tentativa - 1)}.
     */
    private final double retryBackoffMultiplier;

    /**
     * Tempo máximo de espera em milissegundos entre tentativas de retry.
     * O backoff exponencial não excederá este valor.
     */
    private final long retryMaxWaitMs;

    /**
     * Percentual de falhas necessário para abrir o circuit breaker.
     * Quando a taxa de falhas atinge este percentual dentro da janela deslizante,
     * o circuit breaker entra no estado {@code OPEN}.
     */
    private final int circuitBreakerFailureRateThreshold;

    /**
     * Duração em milissegundos que o circuit breaker permanece no estado {@code OPEN}
     * antes de transicionar para o estado {@code HALF_OPEN}.
     */
    private final long circuitBreakerWaitDurationInOpenStateMs;

    /**
     * Tamanho da janela deslizante utilizada pelo circuit breaker.
     * Define o número de chamadas consideradas para calcular a taxa de falhas.
     */
    private final int circuitBreakerSlidingWindowSize;

    /**
     * Número mínimo de chamadas dentro da janela deslizante
     * antes que o circuit breaker possa ser aberto.
     * Se o número de chamadas for inferior a este valor, o circuit breaker não será aberto,
     * mesmo que a taxa de falhas exceda o limite configurado.
     */
    private final int circuitBreakerMinimumNumberOfCalls;

    /**
     * Número de chamadas permitidas no estado {@code HALF_OPEN}.
     * Se estas chamadas forem bem-sucedidas, o circuit breaker é fechado;
     * caso contrário, retorna ao estado {@code OPEN}.
     */
    private final int circuitBreakerPermittedCallsInHalfOpen;

    /**
     * Número máximo de chamadas concorrentes permitidas pelo bulkhead.
     * Chamadas adicionais além deste limite serão rejeitadas.
     */
    private final int bulkheadMaxConcurrentCalls;

    /**
     * Tempo máximo de espera em milissegundos para bulkhead adquirir permissão.
     * Chamadas que excederem este tempo serão rejeitadas.
     */
    private final long bulkheadMaxWaitMs;

    /**
     * Número máximo de chamadas permitidas por período no rate limiter.
     */
    private final int rateLimiterLimitForPeriod;

    /**
     * Duração do período em milissegundos para o rate limiter.
     * Define a janela de tempo em que o limite de chamadas é aplicado.
     */
    private final long rateLimiterTimeoutDurationMs;

    /**
     * Timeout em milissegundos para TimeLimiter.
     * Operações que excederem este tempo serão canceladas.
     */
    private final long timeoutMs;

    /**
     * Cria uma nova instância de {@link ResilienceProfile} com os parâmetros de resiliência especificados.
     *
     * @param name nome identificador do perfil (não pode ser {@code null})
     * @param maxRetryAttempts número máximo de tentativas de retry
     * @param retryInitialWaitMs tempo inicial de espera entre retries em milissegundos
     * @param retryBackoffMultiplier multiplicador de backoff exponencial
     * @param retryMaxWaitMs tempo máximo de espera entre retries em milissegundos
     * @param circuitBreakerFailureRateThreshold percentual de falhas para abrir o circuit breaker
     * @param circuitBreakerWaitDurationInOpenStateMs duração do estado aberto do circuit breaker em milissegundos
     * @param circuitBreakerSlidingWindowSize tamanho da janela deslizante do circuit breaker
     * @param circuitBreakerMinimumNumberOfCalls número mínimo de chamadas para ativar o circuit breaker
     * @param circuitBreakerPermittedCallsInHalfOpen número de chamadas permitidas no estado half-open
     * @param bulkheadMaxConcurrentCalls número máximo de chamadas concorrentes do bulkhead
     * @param bulkheadMaxWaitMs tempo máximo de espera do bulkhead em milissegundos
     * @param rateLimiterLimitForPeriod limite de chamadas por período do rate limiter
     * @param rateLimiterTimeoutDurationMs duração do período do rate limiter em milissegundos
     * @param timeoutMs timeout em milissegundos para TimeLimiter
     */
    ResilienceProfile(String name,
                      int maxRetryAttempts,
                      long retryInitialWaitMs,
                      double retryBackoffMultiplier,
                      long retryMaxWaitMs,
                      int circuitBreakerFailureRateThreshold,
                      long circuitBreakerWaitDurationInOpenStateMs,
                      int circuitBreakerSlidingWindowSize,
                      int circuitBreakerMinimumNumberOfCalls,
                      int circuitBreakerPermittedCallsInHalfOpen,
                      int bulkheadMaxConcurrentCalls,
                      long bulkheadMaxWaitMs,
                      int rateLimiterLimitForPeriod,
                      long rateLimiterTimeoutDurationMs,
                      long timeoutMs) {
        this.name = name;
        this.maxRetryAttempts = maxRetryAttempts;
        this.retryInitialWaitMs = retryInitialWaitMs;
        this.retryBackoffMultiplier = retryBackoffMultiplier;
        this.retryMaxWaitMs = retryMaxWaitMs;
        this.circuitBreakerFailureRateThreshold = circuitBreakerFailureRateThreshold;
        this.circuitBreakerWaitDurationInOpenStateMs = circuitBreakerWaitDurationInOpenStateMs;
        this.circuitBreakerSlidingWindowSize = circuitBreakerSlidingWindowSize;
        this.circuitBreakerMinimumNumberOfCalls = circuitBreakerMinimumNumberOfCalls;
        this.circuitBreakerPermittedCallsInHalfOpen = circuitBreakerPermittedCallsInHalfOpen;
        this.bulkheadMaxConcurrentCalls = bulkheadMaxConcurrentCalls;
        this.bulkheadMaxWaitMs = bulkheadMaxWaitMs;
        this.rateLimiterLimitForPeriod = rateLimiterLimitForPeriod;
        this.rateLimiterTimeoutDurationMs = rateLimiterTimeoutDurationMs;
        this.timeoutMs = timeoutMs;
    }

    /**
     * Busca um perfil de resiliência pelo nome.
     * <p>A busca é case-insensitive. Se o nome informado for {@code null}
     * ou não corresponder a nenhum perfil existente, o método retorna {@link #DEFAULT}.</p>
     *
     * @param name nome do perfil a ser buscado (pode ser {@code null})
     * @return o {@link ResilienceProfile} correspondente ao nome informado,
     *         ou {@link #DEFAULT} se não encontrado ou se {@code name} for {@code null}
     */
    public static ResilienceProfile fromName(String name) {
        if (name == null) return DEFAULT;
        for (ResilienceProfile profile : values()) {
            if (profile.name.equalsIgnoreCase(name)) {
                return profile;
            }
        }
        return DEFAULT;
    }
}
