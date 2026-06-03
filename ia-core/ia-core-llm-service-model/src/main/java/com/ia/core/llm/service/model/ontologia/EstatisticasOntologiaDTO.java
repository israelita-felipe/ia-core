package com.ia.core.llm.service.model.ontologia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para estatísticas de uma ontologia.
 * <p>
 * Contém métricas sobre classes, propriedades, axiomas e outros elementos da ontologia.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticasOntologiaDTO {

  /**
   * Número total de classes na ontologia.
   */
  private int classeCount;

  /**
   * Número total de propriedades de objeto.
   */
  private int objectPropertyCount;

  /**
   * Número total de propriedades de dado.
   */
  private int dataPropertyCount;

  /**
   * Número total de axiomas.
   */
  private int axiomCount;

  /**
   * Número de axiomas SubClassOf.
   */
  private int subClassOfCount;

  /**
   * Número de axiomas EquivalentClasses.
   */
  private int equivalentClassesCount;

  /**
   * Número de axiomas DisjointClasses.
   */
  private int disjointClassesCount;

  /**
   * Número de indivíduos.
   */
  private int individualCount;

  /**
   * Número de axiomas de restrição (SomeValuesFrom, AllValuesFrom, etc).
   */
  private int restrictionCount;

  /**
   * Profundidade máxima da hierarquia de classes.
   */
  private int maxHierarchyDepth;

  /**
   * Indica se há classes insatisfatíveis.
   */
  private boolean hasUnsatisfiableClasses;

  /**
   * Número de classes insatisfatíveis.
   */
  private int unsatisfiableClassCount;
}
