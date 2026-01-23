package com.ia.core.security.model.functionality;

import java.util.Set;

import com.ia.core.security.model.privilege.PrivilegeType;

/**
 * @author Israel Araújo
 */
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

  /**
   * @return {@link PrivilegeType}
   */
  PrivilegeType getType();

  /**
   * @return Valores de contexto
   */
  Set<String> getValues();
}
