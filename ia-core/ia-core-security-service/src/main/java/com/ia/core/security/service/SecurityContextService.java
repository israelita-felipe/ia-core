package com.ia.core.security.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ia.core.security.service.strategy.ContextResolveStrategy;
import com.ia.core.security.service.strategy.ContextResolveStrategyRegistry;
import com.ia.core.service.repository.BaseEntityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para gerenciamento de contextos de segurança. Princípio: SRP (Single
 * Responsibility Principle) Responsabilidade Única: Orquestrar resolução e
 * validação de contextos. Este serviço delega para estratégias específicas,
 * permitindo: - Novo tipo de contexto = nova estratégia (sem modificar este
 * serviço) - Testes isolados de cada estratégia - Extensibilidade via Service
 * Provider Interface (SPI)
 *
 * @author Israel Araújo
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityContextService {

  /**
   * Registry que contém todas as estratégias de resolução de contexto.
   */
  private final ContextResolveStrategyRegistry strategyRegistry;

  /**
   * Resolve valores de contexto para uma chave específica.
   *
   * @param contextKey Chave do contexto a resolver
   * @param values     Valores iniciais
   * @return Coleção de valores resolvidos
   */
  public Collection<Object> resolveContextValues(String contextKey,
                                                 Collection<String> values,
                                                 BaseEntityRepository<?> repository) {
    Objects.requireNonNull(contextKey, "Context key não pode ser nula");
    Objects.requireNonNull(values, "Values não pode ser nula");

    ContextResolveStrategy strategy = strategyRegistry
        .getStrategy(contextKey);

    if (strategy == null) {
      log.warn("Nenhuma estratégia disponível para contexto: {}",
               contextKey);
      return Collections.emptySet();
    }

    log.debug("Resolvendo contexto '{}' com estratégia: {}", contextKey,
              strategy.getClass().getSimpleName());

    return strategy.resolveContextValues(values, repository);
  }

  /**
   * Valida correspondência entre valor do serviço e valor do usuário.
   *
   * @param contextKey          Chave do contexto
   * @param serviceContextValue Valor do serviço
   * @param userContextValue    Valor do usuário
   * @return true se corresponde, false caso contrário
   */
  public boolean matches(String contextKey, String serviceContextValue,
                         String userContextValue) {
    Objects.requireNonNull(contextKey, "Context key não pode ser nula");
    Objects.requireNonNull(serviceContextValue,
                           "Service context value não pode ser nula");
    Objects.requireNonNull(userContextValue,
                           "User context value não pode ser nula");

    ContextResolveStrategy strategy = strategyRegistry
        .getStrategy(contextKey);

    if (strategy == null) {
      log.warn("Nenhuma estratégia disponível para validar contexto: {}",
               contextKey);
      return false;
    }

    log.debug("Validando contexto '{}' com estratégia: {}", contextKey,
              strategy.getClass().getSimpleName());

    return strategy.matches(serviceContextValue, userContextValue);
  }

  /**
   * Obtém todas as chaves de contexto disponíveis.
   *
   * @return Conjunto de chaves registradas
   */
  public Collection<String> getAvailableContextKeys() {
    return strategyRegistry.getAllStrategies().keySet();
  }

  /**
   * Verifica se uma chave de contexto tem estratégia registrada.
   *
   * @param contextKey Chave a validar
   * @return true se estratégia existe, false caso contrário
   */
  public boolean hasStrategy(String contextKey) {
    return strategyRegistry.hasStrategy(contextKey);
  }
}
