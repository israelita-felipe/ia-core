package com.ia.core.model.filter;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Classe que representa uma requisição de filtro.
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest
  implements Serializable {

  /**
   * Serial UID.
   */
  private static final long serialVersionUID = 6293344849078612450L;

  /**
   * Nome do campo
   */
  private String key;

  /**
   * Operador.
   */
  private Operator operator;

  /**
   * Tipo do campo.
   */
  private FieldType fieldType;

  /**
   * Valor a ser comparado.
   */
  private transient Object value;
  /**
   * Negação
   */
  @Default
  private boolean negate = false;

}
