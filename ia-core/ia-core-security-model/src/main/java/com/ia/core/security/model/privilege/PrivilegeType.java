package com.ia.core.security.model.privilege;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Israel Araújo
 */
public enum PrivilegeType {
  SYSTEM(1), USER(2);

  public static PrivilegeType of(int codigo) {
    return Stream.of(values())
        .filter(tipo -> Objects.equals(codigo, tipo.codigo)).findFirst()
        .orElse(null);
  }

  private final int codigo;

  /**
   * @param codigo
   */
  private PrivilegeType(int codigo) {
    this.codigo = codigo;
  }

  /**
   * @return {@link #codigo}
   */
  public int getCodigo() {
    return codigo;
  }
}
