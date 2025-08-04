package com.ia.core.security.service.model.log.operation;

import com.ia.core.service.dto.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@SuperBuilder(toBuilder = true)
@Data
@RequiredArgsConstructor
public class OperationItemDetails
  implements DTO<String>, Comparable<OperationItemDetails> {
  private static final long serialVersionUID = 6428653331930122948L;
  private final String property;
  private final Object oldValue;
  private final Object newValue;

  @Override
  public int compareTo(OperationItemDetails arg0) {
    return property.compareToIgnoreCase(arg0.property);
  }

  @Override
  public OperationItemDetails cloneObject() {
    return toBuilder().build();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("LogOperationItemDetails [");
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
