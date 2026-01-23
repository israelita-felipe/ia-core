package com.ia.core.security.service.model.authorization;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.ia.core.security.service.model.authorization.ContextManager.ContextDefinition;

import io.jsonwebtoken.lang.Arrays;

/**
 *
 */
public interface HasContext {
  default Map<String, String> getContextValue(Object object) {
    return new HashMap<>();
  }

  public void createContext();

  public String getContextName();

  default Collection<Object> getContextDefinitionValue(String key,
                                                       Collection<String> values) {
    return Collections.emptyList();
  }

  default void addContext(String... contexts) {
    ContextManager.get(getContextName()).addAll(Arrays.asList(contexts));
  }

  default Collection<String> getContexts() {
    return ContextManager.get(getContextName());
  }

  default void addContextDefinition(String context,
                                    Supplier<Collection<ContextDefinition>> valueSupplier) {
    ContextManager.putDefinition(getContextKey(context), valueSupplier);
  }

  default Supplier<Collection<ContextDefinition>> getContextDefinition(String context) {
    return ContextManager.getDefinition(getContextKey(context));
  }

  /**
   * @param context
   * @return
   */
  default String getContextKey(String context) {
    return getContextName() + "." + context;
  }

  /**
   * @param contextKey
   * @param serviceContextValue
   * @param userContextValue
   * @return
   */
  default boolean matches(String contextKey, String serviceContextValue,
                          String userContextValue) {
    return String.valueOf(serviceContextValue).contains(userContextValue);
  }

}
