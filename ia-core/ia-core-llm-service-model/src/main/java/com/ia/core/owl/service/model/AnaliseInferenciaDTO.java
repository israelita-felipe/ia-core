package com.ia.core.owl.service.model;

import com.ia.core.owl.service.model.axioma.AxiomaDTO;

import java.util.List;

/**
 * Resultado da análise de consistência e inferências
 *
 * @author Israel Araújo
 * @version 1.0.0
 * @param consistente
 * @param inconsistencias
 * @param axiomasInferidos
 * @param totalInferencias
 */
public record AnaliseInferenciaDTO(boolean consistente,
                               List<String> inconsistencias,
                               List<AxiomaDTO> axiomasInferidos,
                               int totalInferencias) {

  /**
   * Constantes dos campos do DTO.
   */
  @SuppressWarnings("javadoc")
  public static class CAMPOS {
    public static final String CONSISTENTE = "consistente";
    public static final String INCONSISTENCIAS = "inconsistencias";
    public static final String AXIOMAS_INFERIDOS = "axiomasInferidos";
    public static final String TOTAL_INFERENCIAS = "totalInferencias";

    public static java.util.Set<String> values() {
      return java.util.Set.of(CONSISTENTE, INCONSISTENCIAS, AXIOMAS_INFERIDOS, TOTAL_INFERENCIAS);
    }
  }
}
