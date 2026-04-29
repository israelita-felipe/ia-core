package com.ia.core.nlp.model;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * Enumeração dos valores EIIP (Electron-Ion Interaction Pseudopotential).
 *
 * <p>Esta enumeração define os valores numéricos usados para mapeamento
 * de bases nitrogenadas (G, A, T, C) em processamento de sequências
 * biológicas e análise de texto usando o algoritmo EIIP.
 *
 * <p><b>Valores EIIP:</b></p>
 * <ul>
 *   <li><b>G (Guanina):</b> 0.0806</li>
 *   <li><b>A (Adenina):</b> 0.1260</li>
 *   <li><b>T (Timina):</b> 0.1335</li>
 *   <li><b>C (Citosina):</b> 0.1340</li>
 * </ul>
 *
 * <p><b>Por quê usar EIIPEnum?</b></p>
 * <ul>
 *   <li>Fornece valores padronizados para mapeamento de caracteres</li>
 *   <li>Usado pelo {@link NodeCreator} para conversão de texto</li>
 *   <li>Base do algoritmo de processamento NLP do projeto</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see NodeCreator
 * @see Node
 * @since 1.0.0
 */
public enum EIIPEnum {

  /** Adenina - valor EIIP: 0.1260 */
  A(new BigDecimal("0.1260")),

  /** Citosina - valor EIIP: 0.1340 */
  C(new BigDecimal("0.1340")),

  /** Guanina - valor EIIP: 0.0806 */
  G(new BigDecimal("0.0806")),

  /** Timina - valor EIIP: 0.1335 */
  T(new BigDecimal("0.1335"));

  /** Valor numérico do EIIP para esta base nitrogenada. */
  @Getter
  private final BigDecimal value;

  /**
   * Construtor privado para definir o valor EIIP.
   *
   * @param value valor numérico do EIIP
   */
  EIIPEnum(BigDecimal value) {
    this.value = value;
  }
}
