package com.ia.core.llm.service.model.agente;

import com.ia.core.llm.service.model.ontologia.OntologiaDTO;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoConstrucaoOntologia implements DTO<String> {

  @Override
  public DTO<String> cloneObject() {
    return toBuilder().build();
  }

  /**
   * ID do job de construção.
   */
  private String jobId;

  /**
   * Status do job (QUEUED, RUNNING, COMPLETED, FAILED).
   */
  private String status;

  /**
   * Ontologia gerada.
   */
  private OntologiaDTO ontology;

  /**
   * Estatísticas da construção.
   */
  private EstatisticasConstrucao statistics;

  /**
   * Mensagem de erro (se falhou).
   */
  private String errorMessage;

  /**
   * DTO interno para estatísticas de construção.
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EstatisticasConstrucao {
    /**
     * Número de classes criadas.
     */
    private int classCount;

    /**
     * Número de propriedades criadas.
     */
    private int propertyCount;

    /**
     * Número de axiomas criados.
     */
    private int axiomCount;

    /**
     * Número de iterações usadas.
     */
    private int iterationsUsed;

    /**
     * Tempo total de processamento em milissegundos.
     */
    private long totalProcessingTimeMs;

    /**
     * Número de inconsistências corrigidas.
     */
    private int inconsistenciesCorrected;

    /**
     * Construtores OWL utilizados.
     */
    private java.util.Set<String> constructorsUsed;
  }
}
