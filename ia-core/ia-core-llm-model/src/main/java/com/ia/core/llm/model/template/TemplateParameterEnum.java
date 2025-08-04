package com.ia.core.llm.model.template;

import java.util.Objects;
import java.util.stream.Stream;

import lombok.Getter;

/**
 * Enumerador que lista os parâmetros de um template que são padrão de sistema e
 * podem ser utilizados para inserção nos templates.
 *
 * @author Israel Araújo
 */
public enum TemplateParameterEnum {

  /** Documento */
  TEMPLATE_PARAM_DOCUMENTO(1, "documento"),
  /** Contexto */
  TEMPLATE_PARAM_CONTEXTO(2, "contexto");

  /**
   * Captura o {@link TemplateParameterEnum} de acordo o código
   *
   * @param codigo Código desejado
   * @return {@link TemplateParameterEnum} encontrado. Pode ser
   *         <code>null</code> caso o código seja <code>null</code> ou o código
   *         passado não possa ser encontrado.
   */
  public static TemplateParameterEnum valueOf(Integer codigo) {
    if (codigo == null) {
      return null;
    }
    return Stream.of(values())
        .filter(item -> Objects.equals(item.getCodigo(), codigo))
        .findFirst().orElse(null);
  }
  /** Código do parâmetro */
  @Getter
  private final Integer codigo;

  /** Nome do parâmetro */
  @Getter
  private final String nome;

  /**
   * @param codigo código do parâmetro
   * @param nome   Nome do parâmetro
   */
  private TemplateParameterEnum(Integer codigo, String nome) {
    this.codigo = codigo;
    this.nome = nome;
  }

  @Override
  public String toString() {
    return String.format("{%s}", nome);
  }
}
