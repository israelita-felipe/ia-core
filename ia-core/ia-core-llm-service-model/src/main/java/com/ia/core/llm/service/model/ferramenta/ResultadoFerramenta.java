package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.owl.service.model.axioma.AxiomaDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para resultado de execução de ferramenta OWL 2 DL.
 * <p>
 * Contém os axiomas gerados, status de consistência e informações sobre o processo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoFerramenta {

  /**
   * Axiomas gerados pela ferramenta.
   */
  @Builder.Default
  private List<AxiomaDTO> axiomas = new ArrayList<>();

  /**
   * Indica se os axiomas são consistentes com a ontologia.
   */
  private boolean consistente;

  /**
   * Explicação do resultado em linguagem natural.
   */
  private String explicacao;

  /**
   * Número de iterações usadas (se houve loop LLM-Reasoner).
   */
  private int iteracoesUsadas;

  /**
   * Tempo de processamento em milissegundos.
   */
  private long tempoProcessamentoMs;

  /**
   * Construtor OWL utilizado.
   */
  private String construtorUtilizado;

  /**
   * Erro ocorrido (se houve falha).
   */
  private String erro;
}
