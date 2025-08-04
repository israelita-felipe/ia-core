package com.ia.core.security.model.functionality;

/**
 * @author Israel Araújo
 */
@FunctionalInterface
public interface Functionality
  extends Comparable<Functionality> {
  @Override
  default int compareTo(Functionality o) {
    if (o == null) {
      return 1;
    }
    if (o == this) {
      return 0;
    }
    return getName().compareTo(o.getName());
  }

  /**
   * @return O nome da funcionalidade
   */
  String getName();
}
