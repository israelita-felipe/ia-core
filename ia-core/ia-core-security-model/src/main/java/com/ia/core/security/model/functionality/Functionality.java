package com.ia.core.security.model.functionality;

import com.ia.core.security.model.privilege.PrivilegeType;

import java.util.Set;
/**
 * Entidade de domínio que representa functionality.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a Functionality
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
