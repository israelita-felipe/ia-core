package com.ia.core.llm.service.model.validacao;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO para explicação de inconsistência em linguagem natural.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
public class ExplicacaoInconsistencia {

  private String mensagemTecnica;
  private String explicacaoNatural;
  private List<String> axiomasCausadores;
  private String tipoInconsistencia;
  private List<String> sugestoesCorrecao;
  private String gravidade;
}
