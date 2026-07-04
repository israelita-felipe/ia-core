package com.ia.core.llm.service.model.ontologia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para feedback do raciocinador para o LLM.
 * <p>
 * Usado em loops iterativos LLM-Reasoner para auto-correção de axiomas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRaciocinador {

  /**
   * Indica se o axioma é válido.
   */
  private boolean axiomaValido;

  /**
   * Erro de sintaxe (se houver).
   */
  private String erroSintaxe;

  /**
   * Erro de consistência (se houver).
   */
  private String erroConsistencia;

  /**
   * Explicação detalhada do problema.
   */
  private String explicacao;

  /**
   * Sugestão de correção em linguagem natural.
   */
  private String sugestaoCorrecao;

  /**
   * Axioma corrigido (sugerido pelo reasoner ou LLM).
   */
  private String axiomaCorrigido;

  /**
   * Número da iteração atual.
   */
  private int iteracaoAtual;

  /**
   * Número máximo de iterações permitidas.
   */
  private int maxIteracoes;
}
