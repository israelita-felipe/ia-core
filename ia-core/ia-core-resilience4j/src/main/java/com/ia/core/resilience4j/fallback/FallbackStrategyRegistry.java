package com.ia.core.resilience4j.fallback;

import com.ia.core.resilience4j.profile.ResilienceProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registro de estratégias de fallback por perfil de resiliência.
 *
 * <p>Permite registrar e recuperar estratégias de fallback para diferentes
 * perfis de resiliência, com estratégias padrão para cenários comuns.</p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Slf4j
@Component
public class FallbackStrategyRegistry {

    private final Map<ResilienceProfile, FallbackStrategy> strategies = new ConcurrentHashMap<>();

    /**
     * Construtor que registra as estratégias de fallback padrão.
     */
    public FallbackStrategyRegistry() {
        registerDefaultStrategies();
    }

    /**
     * Registra as estratégias de fallback padrão para perfis comuns de resiliência.
     * Estas podem ser sobrescritas por registros personalizados.
     */
    private void registerDefaultStrategies() {
        register(ResilienceProfile.EXTERNAL_API, (method, args, exception) -> {
            log.warn("Fallback EXTERNAL_API: {} - {}", method.getName(), exception.getMessage());
            return FallbackResponse.builder()
                    .success(false)
                    .errorCode("EXTERNAL_SERVICE_UNAVAILABLE")
                    .message("External service temporarily unavailable")
                    .retryable(true).originalException(exception)
                    .build();
        });
        register(ResilienceProfile.LLM_SERVICE, (method, args, exception) -> {
            log.warn("Fallback LLM_SERVICE: {} - {}", method.getName(), exception.getMessage());
            return FallbackResponse.builder()
                    .success(false)
                    .errorCode("LLM_SERVICE_UNAVAILABLE")
                    .message("AI service temporarily unavailable")
                    .retryable(false).originalException(exception)
                    .build();
        });
        register(ResilienceProfile.WEB_SCRAPING, (method, args, exception) -> {
            log.warn("Fallback WEB_SCRAPING: {} - {}", method.getName(), exception.getMessage());
            return FallbackResponse.builder()
                    .success(false)
                    .errorCode("SCRAPING_UNAVAILABLE")
                    .message("Scraping service temporarily unavailable")
                    .retryable(true).originalException(exception)
                    .build();
        });
        register(ResilienceProfile.INTERNAL_SERVICE, (method, args, exception) -> {
            log.warn("Fallback INTERNAL_SERVICE: {} - {}", method.getName(), exception.getMessage());
            return FallbackResponse.builder()
                    .success(false)
                    .errorCode("INTERNAL_SERVICE_ERROR")
                    .message("Internal service error")
                    .retryable(true).originalException(exception)
                    .build();
        });
        register(ResilienceProfile.DATABASE, (method, args, exception) -> {
            log.error("Fallback DATABASE: {} - {}", method.getName(), exception.getMessage());
            return FallbackResponse.builder()
                    .success(false)
                    .errorCode("DATABASE_ERROR")
                    .message("Data access error. Please try again.")
                    .retryable(false).originalException(exception)
                    .build();
        });
        register(ResilienceProfile.DEFAULT, (method, args, exception) -> {
            log.warn("Fallback DEFAULT: {} - {}", method.getName(), exception.getMessage());
            return FallbackResponse.builder()
                    .success(false)
                    .errorCode("SERVICE_UNAVAILABLE")
                    .message("Service temporarily unavailable")
                    .retryable(true).originalException(exception)
                    .build();
        });
    }

    /**
     * Registra uma estratégia de fallback para um perfil específico.
     *
     * @param profileName o perfil de resiliência
     * @param strategy a estratégia de fallback a ser registrada
     */
    public void register(ResilienceProfile profileName, FallbackStrategy strategy) {
        strategies.put(profileName, strategy);
        log.info("Fallback strategy registered for profile: {}", profileName);
    }

    /**
     * Obtém a estratégia de fallback para o perfil informado.
     *
     * @param profileName o perfil de resiliência
     * @return a estratégia de fallback, ou a padrão se não encontrada
     */
    public FallbackStrategy getStrategy(ResilienceProfile profileName) {
        if (profileName == null) {
            return strategies.get(ResilienceProfile.DEFAULT);
        }
        return strategies.getOrDefault(profileName, strategies.get(ResilienceProfile.DEFAULT));
    }

    /**
     * Executa a estratégia de fallback para o perfil e contexto informados.
     *
     * @param profileName o perfil de resiliência
     * @param method o método que falhou
     * @param args os argumentos do método
     * @param exception a exceção que acionou o fallback
     * @return a resposta do fallback
     */
    public FallbackResponse executeFallback(ResilienceProfile profileName, Method method, Object[] args, Throwable exception) {
        return getStrategy(profileName).handle(method, args, exception);
    }
}
