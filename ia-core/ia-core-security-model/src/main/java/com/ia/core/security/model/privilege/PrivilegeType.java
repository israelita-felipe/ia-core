package com.ia.core.security.model.privilege;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import com.ia.core.security.model.functionality.Operation;
import com.ia.core.security.model.functionality.OperationEnum;

/**
 * @author Israel Araújo
 */
public enum PrivilegeType {
  SYSTEM(1) {
    @Override
    public Set<Operation> getOperations() {
      return Set.of(OperationEnum.values());
    }
  },
  USER(2) {
    @Override
    public Set<Operation> getOperations() {
      return Set.of();
    }
  };

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

  public abstract Set<Operation> getOperations();
}
