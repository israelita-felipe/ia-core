package com.ia.core.security.service.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Registry centralizado para estratégias de resolução de contexto.
 * 
 * Padrão: Registry Pattern (Service Locator)
 * Responsabilidade: Descobrir e registrar estratégias automaticamente.
 * 
 * Spring injeta todas as implementações de ContextResolveStrategy automaticamente,
 * permitindo extensão via plugin sem modificar esta classe.
 *
 * @author Israel Araújo
 */
@Component
@Slf4j
public class ContextResolveStrategyRegistry {

  private final Map<String, ContextResolveStrategy> strategies = new HashMap<>();

  /**
   * Construtor que recebe todas as estratégias via injeção de dependência.
   * Spring auto-descobre todas as implementações de ContextResolveStrategy.
   *
   * @param strategiesList Lista de estratégias disponíveis
   */
  public ContextResolveStrategyRegistry(List<ContextResolveStrategy> strategiesList) {
    Objects.requireNonNull(strategiesList, "Strategies list não pode ser nula");

    for (ContextResolveStrategy strategy : strategiesList) {
      String key = strategy.getContextKey();
      log.debug("Registrando estratégia de contexto: {}", key);
      strategies.put(key, strategy);
    }

    if (strategies.isEmpty()) {
      log.warn("Nenhuma estratégia de contexto registrada");
    } else {
      log.info("Total de estratégias registradas: {}", strategies.size());
    }
  }

  /**
   * Obtém a estratégia apropriada para uma chave de contexto.
   *
   * @param contextKey Chave do contexto
   * @return Estratégia apropriada ou null se não encontrada
   */
  public ContextResolveStrategy getStrategy(String contextKey) {
    Objects.requireNonNull(contextKey, "Context key não pode ser nula");
    ContextResolveStrategy strategy = strategies.get(contextKey);

    if (strategy == null) {
      log.warn("Nenhuma estratégia registrada para contexto: {}", contextKey);
    }

    return strategy;
  }

  /**
   * Verifica se uma estratégia está registrada.
   *
   * @param contextKey Chave do contexto
   * @return true se estratégia existe, false caso contrário
   */
  public boolean hasStrategy(String contextKey) {
    return strategies.containsKey(contextKey);
  }

  /**
   * Obtém todas as chaves de contexto disponíveis.
   *
   * @return Conjunto de chaves de estratégias registradas
   */
  public Map<String, ContextResolveStrategy> getAllStrategies() {
    return new HashMap<>(strategies);
  }
}
