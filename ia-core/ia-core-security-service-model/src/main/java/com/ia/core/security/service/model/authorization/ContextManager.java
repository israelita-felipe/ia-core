package com.ia.core.security.service.model.authorization;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 *
 */
public class ContextManager {
  /** Coleção de contexto */
  private Map<String, Set<String>> context = new HashMap<>();
  /** Coleção de itens de definição de contexto */
  private Map<String, Supplier<Collection<ContextDefinition>>> contextDefinition = new HashMap<>();
  /** Instância singleton */
  private static ContextManager INSTANCE = null;

  private static ContextManager get() {
    if (INSTANCE == null) {
      INSTANCE = new ContextManager();
    }
    return INSTANCE;
  }

  /**
   * Construtor privado
   */
  private ContextManager() {
  }

  public static void put(String key, String value) {
    Set<String> current = get(key);
    current.add(value);
  }

  /**
   * @param key
   * @return
   */
  public static Set<String> get(String key) {
    Set<String> current = get().context.get(key);
    if (current == null) {
      current = new HashSet<>();
      get().context.put(key, current);
    }
    return current;
  }

  public static void delete(String key) {
    get().context.remove(key);
  }

  public static void putDefinition(String key,
                                   Supplier<Collection<ContextDefinition>> value) {
    get().contextDefinition.put(key, value);
  }

  /**
   * @param key
   * @return
   */
  public static Supplier<Collection<ContextDefinition>> getDefinition(String key) {
    Supplier<Collection<ContextDefinition>> current = get().contextDefinition
        .get(key);
    if (current == null) {
      current = () -> new HashSet<>();
      get().contextDefinition.put(key, current);
    }
    return current;
  }

  public static void deleteDefinition(String key) {
    get().contextDefinition.remove(key);
  }

  public static record ContextDefinition(String key, String label) {

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (key == null) {
        return this == obj;
      }
      if (!(getClass().isInstance(obj))) {
        return false;
      }
      ContextDefinition other = (ContextDefinition) obj;
      return Objects.equals(key, other.key);
    }

    @Override
    public int hashCode() {
      if (key != null) {
        return Objects.hash(key);
      }
      return Objects.hashCode(this);
    }

    @Override
    public String toString() {
      return String.format("%s - %s", key, label);
    }
  };
}
