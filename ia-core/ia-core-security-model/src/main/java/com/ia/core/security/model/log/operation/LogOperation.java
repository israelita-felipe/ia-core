package com.ia.core.security.model.log.operation;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.SecurityModel;
import com.ia.core.security.model.functionality.OperationEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Entity
@Table(name = LogOperation.TABLE_NAME, schema = LogOperation.SCHEMA_NAME,
       indexes = {
           @Index(name = "idx_log_operation_type_value_id_date_time_operation",
                  columnList = "type ASC, value_id ASC, date_time_operation DESC") })
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LogOperation
  extends BaseEntity {

  /** NOME DA TABELA */
  public static final String TABLE_NAME = SecurityModel.TABLE_PREFIX
      + "LOG_OPERATION";
  /** NOME DO SCHEMA */
  public static final String SCHEMA_NAME = SecurityModel.SCHEMA;

  @Column(name = "user_name", length = 500, nullable = false)
  private String userName;

  @Column(name = "user_code", length = 500, nullable = false)
  private String userCode;

  @Column(name = "value_id")
  private UUID valueId;

  @Column(name = "type", length = 500)
  private String type;

  @Lob
  @Column(name = "old_value")
  private String oldValue;
  @Lob
  @Column(name = "new_value")
  private String newValue;

  @Default
  @Column(name = "date_time_operation")
  private LocalDateTime dateTimeOperation = LocalDateTime.now();

  @Column(name = "operataion")
  private OperationEnum operation;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("LogOperation [");
    if (userName != null) {
      builder.append("userName=");
      builder.append(userName);
      builder.append(", ");
    }
    if (userCode != null) {
      builder.append("userCode=");
      builder.append(userCode);
      builder.append(", ");
    }
    if (valueId != null) {
      builder.append("valueId=");
      builder.append(valueId);
      builder.append(", ");
    }
    if (type != null) {
      builder.append("type=");
      builder.append(type);
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
      builder.append(", ");
    }
    if (dateTimeOperation != null) {
      builder.append("dateTimeOperation=");
      builder.append(dateTimeOperation);
      builder.append(", ");
    }
    if (operation != null) {
      builder.append("operation=");
      builder.append(operation);
    }
    builder.append("]");
    return builder.toString();
  }

}
