package com.ia.core.owl;

import com.ia.core.owl.OWLModel.HasName;
import com.ia.core.owl.OWLModel.HasType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@RequiredArgsConstructor
public class OWLModel<T extends HasName & HasType> {
  @Getter
  private final T object;

  String getType() {
    if (object == null) {
      return null;
    }
    return object.getType();
  }

  String toIndividual() {
    if (object == null) {
      return null;
    }
    return object.getName();
  }

  @FunctionalInterface
  public static interface HasName {
    String getName();
  }

  @FunctionalInterface
  public static interface HasType {
    String getType();
  }
}
