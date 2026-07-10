package com.ia.core.security.service.model.log.operation;

import com.ia.core.service.dto.DTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Detalhes de um item de operação de log.
 * <p>
 * Representa uma propriedade específica que foi modificada em uma operação,
 * contendo o nome da propriedade e seus valores antigo e novo.
 *
 * @author Israel Araújo
 * @see LogOperationDetailsDTO
 * @since 1.0.0
 */
@SuperBuilder(toBuilder = true)
@Data
@RequiredArgsConstructor
public class OperationItemDetailsDTO
  implements DTO<String>, Comparable<OperationItemDetailsDTO> {
  private static final long serialVersionUID = 6428653331930122948L;

  /**
   * Nome da propriedade que foi modificada.
   */
  private final String property;

  /**
   * Valor antigo da propriedade.
   */
  private final Object oldValue;

  /**
   * Novo valor da propriedade.
   */
  private final Object newValue;

  /**
   * Compara este item com outro baseado na propriedade.
   *
   * @param arg0 item a ser comparado
   * @return valor negativo, zero ou positivo se este item for menor, igual ou maior
   */
  @Override
  public int compareTo(OperationItemDetailsDTO arg0) {
    return property.compareToIgnoreCase(arg0.property);
  }

  /**
   * Cria uma cópia superficial (clone) deste objeto.
   *
   * @return novo objeto {@link OperationItemDetailsDTO} com os mesmos valores
   */
  @Override
  public OperationItemDetailsDTO cloneObject() {
    return toBuilder().build();
  }

  /**
   * Retorna uma representação em string deste objeto.
   *
   * @return string contendo informações do item
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("OperationItemDetailsDTO [");
    if (property != null) {
      builder.append("property=");
      builder.append(property);
      builder.append(", ");
    }
    if (oldValue != null) {
      builder.append("oldValue=");
      builder.append(oldValue);
      builder.append(", ");
    }
    if (newValue != null) {
      builder.append("newValue=");
      builder.append(newValue);
    }
    builder.append("]");
    return builder.toString();
  }
}
