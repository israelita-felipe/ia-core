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

}
