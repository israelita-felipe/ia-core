package com.ia.core.model.filter;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Classe que representa uma requisição de filtro com validação e Javadoc completo.
 *
 * Um FilterRequest encapsula a lógica de um único filtro a ser aplicado em uma busca:
 * - A chave do campo (ex: "name", "email")
 * - O operador (ex: EQUAL, LIKE, GREATER_THAN)
 * - O tipo do campo (ex: STRING, LONG, DATE)
 * - O valor a comparar
 * - Um flag de negação (NOT)
 *
 * @author Israel Araújo
 * @see Operator
 * @see FieldType
 * @see SearchRequest
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest implements Serializable {

  private static final long serialVersionUID = 6293344849078612450L;

  /**
   * Nome do campo a filtrar (ex: "id", "nome", "email").
   * Não pode ser nulo ou vazio.
   */
  private String key;

  /**
   * Operador de comparação (ex: EQUAL, LIKE, GREATER_THAN).
   * Define como o valor será comparado com o campo.
   */
  private Operator operator;

  /**
   * Tipo do campo (ex: STRING, LONG, DATE).
   * Utilizado para conversão e validação do valor.
   */
  private FieldType fieldType;

  /**
   * Valor a ser comparado com o campo.
   * Pode ser nulo em operadores específicos (ex: IS_NULL).
   */
  private transient Object value;

  /**
   * Flag de negação: se true, nega o resultado do operador.
   * Ex: EQUAL negado = NOT_EQUAL.
   */
  @Default
  private boolean negate = false;

  /**
   * Valida se o filtro está bem-formado.
   * Lança IllegalArgumentException se houver problema.
   *
   * @throws IllegalArgumentException se key ou operator forem nulos
   */
  public void validate() {
    if (key == null || key.trim().isEmpty()) {
      throw new IllegalArgumentException("FilterRequest.key não pode ser nulo ou vazio");
    }
    Objects.requireNonNull(operator, "FilterRequest.operator não pode ser nulo");
    Objects.requireNonNull(fieldType, "FilterRequest.fieldType não pode ser nulo");
  }
}