package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
public class EstatisticasOntologiaDTO implements DTO<Long> {

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

  @Override
  public EstatisticasOntologiaDTO cloneObject() {
    return EstatisticasOntologiaDTO.builder()
        .classeCount(classeCount)
        .objectPropertyCount(objectPropertyCount)
        .dataPropertyCount(dataPropertyCount)
        .axiomCount(axiomCount)
        .subClassOfCount(subClassOfCount)
        .equivalentClassesCount(equivalentClassesCount)
        .disjointClassesCount(disjointClassesCount)
        .individualCount(individualCount)
        .restrictionCount(restrictionCount)
        .maxHierarchyDepth(maxHierarchyDepth)
        .hasUnsatisfiableClasses(hasUnsatisfiableClasses)
        .unsatisfiableClassCount(unsatisfiableClassCount)
        .build();
  }

  public static class CAMPOS {
    public static final String CLASSE_COUNT = "classeCount";
    public static final String OBJECT_PROPERTY_COUNT = "objectPropertyCount";
    public static final String DATA_PROPERTY_COUNT = "dataPropertyCount";
    public static final String AXIOM_COUNT = "axiomCount";
    public static final String SUB_CLASS_OF_COUNT = "subClassOfCount";
    public static final String EQUIVALENT_CLASSES_COUNT = "equivalentClassesCount";
    public static final String DISJOINT_CLASSES_COUNT = "disjointClassesCount";
    public static final String INDIVIDUAL_COUNT = "individualCount";
    public static final String RESTRICTION_COUNT = "restrictionCount";
    public static final String MAX_HIERARCHY_DEPTH = "maxHierarchyDepth";
    public static final String HAS_UNSATISFIABLE_CLASSES = "hasUnsatisfiableClasses";
    public static final String UNSATISFIABLE_CLASS_COUNT = "unsatisfiableClassCount";
    public static final String PROPERTY_CHANGE_SUPPORT = "propertyChangeSupport";

    public static Set<String> values() {
      return Set.of(CLASSE_COUNT, OBJECT_PROPERTY_COUNT, DATA_PROPERTY_COUNT, AXIOM_COUNT,
          SUB_CLASS_OF_COUNT, EQUIVALENT_CLASSES_COUNT, DISJOINT_CLASSES_COUNT, INDIVIDUAL_COUNT,
          RESTRICTION_COUNT, MAX_HIERARCHY_DEPTH, HAS_UNSATISFIABLE_CLASSES, UNSATISFIABLE_CLASS_COUNT,
          PROPERTY_CHANGE_SUPPORT);
    }
  }
}
