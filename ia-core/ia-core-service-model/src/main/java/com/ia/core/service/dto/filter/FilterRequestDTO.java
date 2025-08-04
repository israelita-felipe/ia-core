package com.ia.core.service.dto.filter;

import com.ia.core.model.filter.FilterRequest;
import com.ia.core.service.dto.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Filtro de uma requisição
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
   * Serial UID
   */
  private static final long serialVersionUID = -6843840428361841324L;

  /**
   * Campos do filtro
   */
  @SuppressWarnings("javadoc")
  public static final class CAMPOS {
    public static final String PROPRIEDADE = "key";
    public static final String OPERACAO = "operator";
    public static final String TIPO = "fieldType";
    public static final String VALOR = "value";
  }

  /**
   * Nome do campo
   */
  private String key;

  /**
   * Operador.
   */
  private OperatorDTO operator;

  /**
   * Tipo do campo.
   */
  private FieldTypeDTO fieldType;

  /**
   * Valor a ser comparado.
   */
  private transient Object value;

  /**
   * Negação
   */
  @Default
  private boolean negate = false;

  @Override
  public FilterRequestDTO cloneObject() {
    return toBuilder().build();
  }
}
