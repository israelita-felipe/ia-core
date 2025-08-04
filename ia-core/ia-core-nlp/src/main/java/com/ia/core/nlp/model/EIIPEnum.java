package com.ia.core.nlp.model;

import java.math.BigDecimal;

import lombok.Getter;

/**
 * @author Israel Araújo
 * @see <a href="https://doi.org/10.1371/journal.pone.0173288">doi</a>
 */
public enum EIIPEnum {
  /** adenina */
  A(new BigDecimal("0.1260")),
  /** citosina */
  C(new BigDecimal("0.1340")),
  /** guanina */
  G(new BigDecimal("0.0806")),
  /** tinina */
  T(new BigDecimal("0.1335"));

  /** valor do enum */
  @Getter
  private final BigDecimal value;

  /**
   * Construtor Privado
   *
   * @param value valor padrão
   */
  private EIIPEnum(BigDecimal value) {
    this.value = value;
  }
}
