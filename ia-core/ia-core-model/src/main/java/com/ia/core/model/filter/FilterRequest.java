package com.ia.core.model.filter;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Classe que representa uma requisição de filtro para buscas dinâmicas.
 *
 * <p>Encapsula a lógica de um único filtro a ser aplicado em uma consulta,
 * incluindo o campo, operador de comparação, tipo de dado e valor.
 *
 * <p><b>Por quê usar FilterRequest?</b></p>
 * <ul>
 *   <li>Permite criar consultas dinâmicas com múltiplos critérios</li>
 *   <li>Abstrai a complexidade do JPA Criteria API</li>
 *   <li>Fornece validação automática dos parâmetros</li>
 * </ul>
 *
 * <p><b>Exemplo de uso:</b></p>
 * {@code
 * FilterRequest filtro = FilterRequest.builder()
 *     .key("nome")
 *     .operator(Operator.LIKE)
 *     .fieldType(FieldType.STRING)
 *     .value("João")
 *     .negate(false)
 *     .build();
 * filtro.validate();
 * }
 *
 * @author Israel Araújo
 * @see Operator
 * @see FieldType
 * @see SearchRequest
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequest implements Serializable {

  private static final long serialVersionUID = 6293344849078612450L;

  /**
   * Nome do campo a ser filtrado na entidade.
   *
   * <p>Pode ser um atributo simples (ex: "nome", "email") ou um caminho
   * composto separado por ponto (ex: "endereco.cidade.nome").
   *
   * @return o nome do campo, nunca {@code null} ou vazio após validação
   */
  private String key;

  /**
   * Operador de comparação a ser aplicado.
   *
   * <p>Define como o valor será comparado com o campo do banco de dados.
   * Exemplos: {@link Operator#EQUAL}, {@link Operator#LIKE}, {@link Operator#GREATER_THAN}
   *
   * @return o operador de comparação, nunca {@code null}
   */
  private Operator operator;

  /**
   * Tipo do campo para conversão e validação do valor.
   *
   * <p>Utilizado para converter a string recebida na requisição para o
   * tipo correto antes de executar a consulta no banco de dados.
   *
   * @return o tipo do campo, nunca {@code null}
   * @see FieldType
   */
  private FieldType fieldType;

  /**
   * Valor a ser comparado com o campo do banco de dados.
   *
   * <p>Pode ser {@code null} em operadores específicos como {@link Operator#EQUAL}
   * quando usado com {@code fieldType} {@link FieldType#OBJECT} para verificações
   * de nulo.
   *
   * @return o valor a ser comparado (pode ser {@code null})
   */
  private transient Object value;

  /**
   * Flag de negação para inverter o resultado do operador.
   *
   * <p>Se {@code true}, o resultado da comparação será negado.
   * Exemplo: {@link Operator#EQUAL} negado resulta em {@link Operator#NOT_EQUAL}
   *
   * @return {@code true} para negar o resultado, {@code false} para manter normal
   */
  @Default
  private boolean negate = false;

  /**
   * Valida se o filtro está bem-formado e pronto para uso.
   *
   * <p>Verifica se os campos obrigatórios estão presentes e são válidos.
   * Deve ser chamado antes de usar o filtro em uma consulta.
   *
   * @throws IllegalArgumentException se {@code key} for nulo ou vazio
   * @throws NullPointerException se {@code operator} ou {@code fieldType} forem nulos
   */
  public void validate() {
    if (key == null || key.trim().isEmpty()) {
      throw new IllegalArgumentException("FilterRequest.key não pode ser nulo ou vazio");
    }
    Objects.requireNonNull(operator, "FilterRequest.operator não pode ser nulo");
    Objects.requireNonNull(fieldType, "FilterRequest.fieldType não pode ser nulo");
  }
}