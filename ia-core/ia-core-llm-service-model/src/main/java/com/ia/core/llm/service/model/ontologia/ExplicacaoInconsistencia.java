package com.ia.core.llm.service.model.ontologia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO para explicação de inconsistência em linguagem natural.
 * <p>
 * Converte mensagens técnicas do reasoner em explicações compreensíveis para usuários.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExplicacaoInconsistencia {

  /**
   * Mensagem técnica original do reasoner.
   */
  private String mensagemTecnica;

  /**
   * Explicação em linguagem natural (português).
   */
  private String explicacaoNatural;

  /**
   * Lista de axiomas que causam a inconsistência.
   */
  @Builder.Default
  private List<String> axiomasCausadores = new ArrayList<>();

  /**
   * Tipo de inconsistência (ex: classe insatisfatível, cardinalidade conflitante).
   */
  private String tipoInconsistencia;

  /**
   * Sugestões de correção.
   */
  @Builder.Default
  private List<String> sugestoesCorrecao = new ArrayList<>();

  /**
   * Gravidade da inconsistência (ERROR, WARNING, INFO).
   */
  private String gravidade;
}
