package com.ia.core.owl.service.tool.base;

import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para resultado de execução de uma tool OWL.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolResult {

  /**
   * Axiomas gerados pela tool.
   */
  @Builder.Default
  private List<AxiomaDTO> axioms = new ArrayList<>();

  /**
   * Indica se o resultado é válido.
   */
  private boolean valid;

  /**
   * Mensagem de erro (se houver).
   */
  private String errorMessage;

  /**
   * Número de iterações usadas.
   */
  private int iterationsUsed;

  /**
   * Tempo de processamento em milissegundos.
   */
  private long processingTimeMs;
}
