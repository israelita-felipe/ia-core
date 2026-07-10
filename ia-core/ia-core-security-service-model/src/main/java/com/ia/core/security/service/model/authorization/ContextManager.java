package com.ia.core.security.service.model.authorization;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Gerenciador de contextos para autorização.
 * <p>
 * Singleton responsável por gerenciar contextos e definições de contexto
 * no sistema de autorização. Fornece operações para armazenar, recuperar
 * e remover contextos associados a chaves específicas.
 *
 * @author Israel Araújo
 * @see ContextDefinition
 * @since 1.0.0
 */

public class ContextManager {

  /**
   * Mapa de contextos armazenados.
   */
  private Map<String, Set<String>> context = new HashMap<>();

  /**
   * Mapa de definições de contexto.
   */
  private Map<String, Supplier<Collection<ContextDefinition>>> contextDefinition = new HashMap<>();

  /**
   * Instância singleton com inicialização thread-safe.
   */
  private static final ContextManager INSTANCE = new ContextManager();

  private ContextManager() {
  }

  private static ContextManager get() {
    return INSTANCE;
  }

  /**
   * Adiciona um valor ao contexto para uma chave específica.
   *
   * @param key a chave do contexto (não pode ser null)
   * @param value o valor a ser adicionado (não pode ser null)
   * @throws NullPointerException se key ou value forem null
   */
  public static void put(String key, String value) {
    Objects.requireNonNull(key, "Context key cannot be null");
    Objects.requireNonNull(value, "Context value cannot be null");
    Set<String> current = get(key);
    current.add(value);
  }

  /**
   * Recupera os valores de contexto para uma chave específica.
   *
   * @param key a chave do contexto (não pode ser null)
   * @return conjunto de valores do contexto, nunca null
   * @throws NullPointerException se key for null
   */
  public static Set<String> get(String key) {
    Objects.requireNonNull(key, "Context key cannot be null");
    Set<String> current = get().context.get(key);
    if (current == null) {
      current = new HashSet<>();
      get().context.put(key, current);
    }
    return current;
  }

  /**
   * Remove um contexto pelo nome da chave.
   *
   * @param key a chave do contexto (não pode ser null)
   * @throws NullPointerException se key for null
   */
  public static void delete(String key) {
    Objects.requireNonNull(key, "Context key cannot be null");
    get().context.remove(key);
  }

  /**
   * Adiciona uma definição de contexto.
   *
   * @param key a chave da definição (não pode ser null)
   * @param value fornecedor de definições (não pode ser null)
   * @throws NullPointerException se key ou value forem null
   */
  public static void putDefinition(String key,
                                   Supplier<Collection<ContextDefinition>> value) {
    Objects.requireNonNull(key, "Context definition key cannot be null");
    Objects.requireNonNull(value, "Context definition value supplier cannot be null");
    get().contextDefinition.put(key, value);
  }

  /**
   * Recupera a definição de contexto para uma chave específica.
   *
   * @param key a chave da definição (não pode ser null)
   * @return fornecedor de definições, nunca null
   * @throws NullPointerException se key for null
   */
  public static Supplier<Collection<ContextDefinition>> getDefinition(String key) {
    Objects.requireNonNull(key, "Context definition key cannot be null");
    Supplier<Collection<ContextDefinition>> current = get().contextDefinition
        .get(key);
    if (current == null) {
      current = () -> new HashSet<>();
      get().contextDefinition.put(key, current);
    }
    return current;
  }

  /**
   * Remove uma definição de contexto.
   *
   * @param key a chave da definição (não pode ser null)
   * @throws NullPointerException se key for null
   */
  public static void deleteDefinition(String key) {
    Objects.requireNonNull(key, "Context definition key cannot be null");
    get().contextDefinition.remove(key);
  }

  /**
   * Definição de contexto.
   *
   * @param key chave do contexto
   * @param label rótulo descritivo
   */
  public static record ContextDefinition(String key, String label) {

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      ContextDefinition other = (ContextDefinition) obj;
      return Objects.equals(key, other.key);
    }

    @Override
    public int hashCode() {
      return Objects.hash(key);
    }

    @Override
    public String toString() {
      return String.format("%s - %s", key, label);
    }
  };
}
