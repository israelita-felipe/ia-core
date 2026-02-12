package com.ia.core.quartz.model.periodicidade;

import java.util.Objects;
import java.util.stream.Stream;

public enum Frequencia {
  DIARIAMENTE(1), SEMANALMENTE(2), MENSALMENTE(3), ANUALMENTE(4);

  @jakarta.persistence.Transient
  private final int codigo;

  /**
   * @param codigo
   */
  private Frequencia(int codigo) {
    this.codigo = codigo;
  }

  /**
   * @return {@link #codigo}
   */
  public int getCodigo() {
    return codigo;
  }

  public static Frequencia of(int codigo) {
    return Stream.of(values())
        .filter(tipo -> Objects.equals(codigo, tipo.codigo)).findFirst()
        .orElse(null);
  }
}
