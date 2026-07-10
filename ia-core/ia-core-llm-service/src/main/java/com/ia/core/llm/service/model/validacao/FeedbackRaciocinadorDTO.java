package com.ia.core.llm.service.model.validacao;

import lombok.Builder;
import lombok.Data;

/**
 * DTO para feedback do loop LLM-Reasoner.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
public class FeedbackRaciocinadorDTO {

  private boolean axiomaValido;
  private String explicacao;
  private String erroConsistencia;
  private String descricaoCorrigida;
  private int iteracaoAtual;
  private int maxIteracoes;
}
