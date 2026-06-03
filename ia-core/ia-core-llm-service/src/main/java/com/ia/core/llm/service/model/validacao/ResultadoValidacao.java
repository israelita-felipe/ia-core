package com.ia.core.llm.service.model.validacao;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO para resultado de validação de ontologia.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
public class ResultadoValidacao {

  private boolean consistente;
  private String explicacao;
  private List<String> classesInsatisfativeis;
  private int iteracoesUsadas;
  private long tempoProcessamentoMs;
}
