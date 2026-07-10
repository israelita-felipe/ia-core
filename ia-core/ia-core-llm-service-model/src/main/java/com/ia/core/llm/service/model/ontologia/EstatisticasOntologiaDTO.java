package com.ia.core.llm.service.model.ontologia;

import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
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
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticasOntologiaDTO implements DTO<Long> {

    private int classeCount;
    private int objectPropertyCount;
    private int dataPropertyCount;
    private int axiomCount;
    private int subClassOfCount;
    private int equivalentClassesCount;
    private int disjointClassesCount;
    private int individualCount;
    private int restrictionCount;
    private int maxHierarchyDepth;
    private boolean hasUnsatisfiableClasses;
    private int unsatisfiableClassCount;
    private int iterationsUsed;
    private long totalProcessingTimeMs;
    private int inconsistenciesCorrected;
    private Set<String> constructorsUsed;

    @Override
    public EstatisticasOntologiaDTO cloneObject() {
        return toBuilder().build();
    }

    @Override
    public String toString() {
        return String.format("EstatisticasOntologiaDTO{classeCount=%d, axiomCount=%d}", classeCount, axiomCount);
    }

    @SuppressWarnings("javadoc")
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
        public static final String ITERATIONS_USED = "iterationsUsed";
        public static final String TOTAL_PROCESSING_TIME_MS = "totalProcessingTimeMs";
        public static final String INCONSISTENCIES_CORRECTED = "inconsistenciesCorrected";
        public static final String CONSTRUCTORS_USED = "constructorsUsed";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(CLASSE_COUNT, OBJECT_PROPERTY_COUNT, DATA_PROPERTY_COUNT, AXIOM_COUNT,
                SUB_CLASS_OF_COUNT, EQUIVALENT_CLASSES_COUNT, DISJOINT_CLASSES_COUNT, INDIVIDUAL_COUNT,
                RESTRICTION_COUNT, MAX_HIERARCHY_DEPTH, HAS_UNSATISFIABLE_CLASSES, UNSATISFIABLE_CLASS_COUNT,
                ITERATIONS_USED, TOTAL_PROCESSING_TIME_MS, INCONSISTENCIES_CORRECTED, CONSTRUCTORS_USED));
        }
    }
}