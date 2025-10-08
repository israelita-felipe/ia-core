package com.ia.core.quartz.model.periodicidade;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Israel Araújo
 */
public enum OcorrenciaSemanal {
  PRIMEIRA(1), SEGUNDA(2), TERCEIRA(3), QUARTA(4), QUINTA(5);

  public static OcorrenciaSemanal of(int codigo) {
    return Stream.of(values())
        .filter(tipo -> Objects.equals(codigo, tipo.codigo)).findFirst()
        .orElse(null);
  }

  @jakarta.persistence.Transient
  private final int codigo;

  /**
   * @param codigo
   */
  private OcorrenciaSemanal(int codigo) {
    this.codigo = codigo;
  }

  /**
   * @return {@link #codigo}
   */
  public int getCodigo() {
    return codigo;
  }
}
