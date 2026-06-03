package com.ia.core.llm.service.model.agente;

import com.ia.core.llm.service.model.ontologia.EstatisticasOntologiaDTO;
import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para resposta do agente conversacional.
 * <p>
 * Contém a resposta em linguagem natural, axiomas extraídos e status da ontologia.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespostaAgente {

  /**
   * Resposta do agente em linguagem natural.
   */
  private String agentResponse;

  /**
   * Axiomas extraídos da mensagem do usuário.
   */
  @Builder.Default
  private List<AxiomaDTO> extractedAxioms = new ArrayList<>();

  /**
   * Status da ontologia após este turno.
   */
  private OntologiaStatus ontologyStatus;

  /**
   * Número de iterações usadas na validação.
   */
  private int iteracoesUsadas;

  /**
   * Indica se houve correção de inconsistência.
   */
  private boolean inconsistenciaCorrigida;

  /**
   * Explicação de correção (se houve).
   */
  private String explicacaoCorrecao;

  /**
   * DTO interno para status da ontologia.
   */
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OntologiaStatus {
    /**
     * IRI da ontologia.
     */
    private String iri;

    /**
     * Indica se a ontologia está consistente.
     */
    private boolean consistent;

    /**
     * Número de classes.
     */
    private int classCount;

    /**
     * Número de axiomas.
     */
    private int axiomCount;

    /**
     * Número de avisos.
     */
    private int warningsCount;

    /**
     * Estatísticas detalhadas.
     */
    private EstatisticasOntologiaDTO estatisticas;
  }

  /**
   * Constantes dos campos do DTO para uso type-safe.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String AGENT_RESPONSE = "agentResponse";
    public static final String EXTRACTED_AXIOMS = "extractedAxioms";
    public static final String ONTOLOGY_STATUS = "ontologyStatus";
    public static final String ITERACOES_USADAS = "iteracoesUsadas";
    public static final String INCONSISTENCIA_CORRIGIDA = "inconsistenciaCorrigida";
    public static final String EXPLICACAO_CORRECAO = "explicacaoCorrecao";

    public static java.util.Set<String> values() {
      return java.util.Set.of(
          AGENT_RESPONSE, EXTRACTED_AXIOMS, ONTOLOGY_STATUS,
          ITERACOES_USADAS, INCONSISTENCIA_CORRIGIDA, EXPLICACAO_CORRECAO
      );
    }
  }
}
