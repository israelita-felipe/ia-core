package com.ia.core.llm.service.model.agente;

import com.ia.core.llm.service.model.ontologia.EstatisticasOntologiaDTO;
import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

/**
 * DTO para resultado da construção de ontologia pelo agente construtor.
 * <p>
 * Contém a ontologia gerada, estatísticas e informações sobre o processo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoConstrucaoOntologiaDTO implements DTO<String> {

    /** Serial UID */
    private static final long serialVersionUID = -474920123456789123L;

    private String jobId;

    private String status;

    private OntologiaDTO ontology;

    private EstatisticasOntologiaDTO statistics;

    private String errorMessage;

    @Override
    public ResultadoConstrucaoOntologiaDTO cloneObject() {
        return toBuilder().build();
    }

    @Override
    public String toString() {
        return String.format("ResultadoConstrucaoOntologiaDTO{status=%s}", status);
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String JOB_ID = "jobId";
        public static final String STATUS = "status";
        public static final String ONTOLOGY = "ontology";
        public static final String STATISTICS = "statistics";
        public static final String ERROR_MESSAGE = "errorMessage";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(JOB_ID, STATUS, ONTOLOGY, STATISTICS, ERROR_MESSAGE));
        }
    }
}