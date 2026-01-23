package com.ia.core.security.view.privilege.operation.context.converter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.ia.core.security.service.model.authorization.ContextManager.ContextDefinition;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

/**
 *
 */
public class ContextDefinitionConverter
  implements Converter<Set<ContextDefinition>, Set<String>> {

  private Map<String, ContextDefinition> contextMap = new HashMap<>();

  @Override
  public Result<Set<String>> convertToModel(Set<ContextDefinition> value,
                                            ValueContext context) {
    if (value == null) {
      Result.ok(null);
    }
    contextMap.clear();
    value.forEach(cd -> contextMap.put(cd.key(), cd));
    return Result.ok(new HashSet<>(value.stream()
        .map(ContextDefinition::key).collect(Collectors.toSet())));
  }

  @Override
  public Set<ContextDefinition> convertToPresentation(Set<String> value,
                                                      ValueContext context) {
    if (value == null) {
      return null;
    }
    if (contextMap.isEmpty()) {
      return new HashSet<>(value.stream()
          .map(key -> new ContextDefinition(key, null)).toList());
    }
    return new HashSet<>(contextMap.entrySet().stream()
        .filter(entry -> value.contains(entry.getKey()))
        .map(Entry::getValue).toList());
  }
}
