package com.ia.core.llm.service.model.agente;

import com.ia.core.llm.service.model.ontologia.EstatisticasOntologiaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * DTO para resposta do agente conversacional.
 * <p>
 * Contém a resposta em linguagem natural, axiomas extraídos e status da ontologia.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RespostaAgenteDTO implements DTO<String> {

    private static final long serialVersionUID = -674920123456789123L;

    private String agentResponse;

    @Builder.Default
    private List<AxiomaDTO> extractedAxioms = new ArrayList<>();

    private EstatisticasOntologiaDTO ontologyStatus;

    private int iteracoesUsadas;

    private boolean inconsistenciaCorrigida;

    private String explicacaoCorrecao;

    @Override
    public RespostaAgenteDTO cloneObject() {
        return toBuilder()
            .extractedAxioms(new ArrayList<>(extractedAxioms))
            .build();
    }

    @Override
    public String toString() {
        return String.format("RespostaAgenteDTO{agentResponse=%s}", agentResponse);
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS {
        public static final String AGENT_RESPONSE = "agentResponse";
        public static final String EXTRACTED_AXIOMS = "extractedAxioms";
        public static final String ONTOLOGY_STATUS = "ontologyStatus";
        public static final String ITERACOES_USADAS = "iteracoesUsadas";
        public static final String INCONSISTENCIA_CORRIGIDA = "inconsistenciaCorrigida";
        public static final String EXPLICACAO_CORRECAO = "explicacaoCorrecao";

        public static Set<String> values() {
            return Collections.unmodifiableSet(Set.of(
                AGENT_RESPONSE, EXTRACTED_AXIOMS, ONTOLOGY_STATUS,
                ITERACOES_USADAS, INCONSISTENCIA_CORRIGIDA, EXPLICACAO_CORRECAO
            ));
        }
    }
}