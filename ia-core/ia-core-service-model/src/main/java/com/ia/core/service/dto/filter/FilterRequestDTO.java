package com.ia.core.service.dto.filter;

import com.ia.core.model.filter.FieldType;
import com.ia.core.model.filter.FilterRequest;
import com.ia.core.service.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO para requisição de filtro.
 * <p>
 * Define os critérios de filtro para consultas, especificando o campo,
 * operador, tipo de campo e valor a ser comparado.
 * </p>
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequestDTO
  implements DTO<FilterRequest> {
  /**
   * Serial UID para serialização
   */
  private static final long serialVersionUID = -6843840428361841324L;

  /**
   * Constantes para nomes de campos utilizados em serialização/mapping.
   */
  @SuppressWarnings("javadoc")
  public static final class CAMPOS {
    public static final String PROPRIEDADE = "key";
    public static final String OPERACAO = "operator";
    public static final String TIPO = "fieldType";
    public static final String VALOR = "value";
  }

  /**
   * Nome do campo a ser filtrado
   */
  private String key;

  /**
   * Operador de comparação (igual, diferente, contém, etc.)
   */
  private OperatorDTO operator;

  /**
   * Tipo de dado do campo
   */
  private FieldType fieldType;

  /**
   * Valor a ser comparado no filtro
   */
  private transient Object value;

  /**
   * Indica se o filtro deve ser negado (inverter o resultado)
   */
  @Default
  private boolean negate = false;

  /**
   * Cria uma cópia deste DTO.
   *
   * @return Nova instância de FilterRequestDTO com os mesmos valores
   */
  @Override
  public FilterRequestDTO cloneObject() {
    return toBuilder().build();
  }
}
