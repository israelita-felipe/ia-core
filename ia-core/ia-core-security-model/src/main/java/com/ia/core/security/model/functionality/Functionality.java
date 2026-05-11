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
    // Contrato do Comparable: lança NPE se comparado com null (padrão do Java)
    // Mas aqui optamos por retornar 1 (this > null) para consistência com implementações anteriores
    if (o == null) {
      return 1;
    }
    if (o == this) {
      return 0;
    }

    // @bugfix SECURITY: Null safety para getName()
    // Evita NullPointerException se getName() retornar null
    String thisName = this.getName();
    String otherName = o.getName();

    if (thisName == null && otherName == null) {
      return 0;
    }
    if (thisName == null) {
      return -1;  // null vem antes de não-null
    }
    if (otherName == null) {
      return 1;   // não-null vem depois de null
    }

    return thisName.compareTo(otherName);
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
