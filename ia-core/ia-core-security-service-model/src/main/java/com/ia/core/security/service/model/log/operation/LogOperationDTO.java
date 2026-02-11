package com.ia.core.security.service.model.log.operation;

import java.time.LocalDateTime;
import java.util.Set;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.log.operation.LogOperation;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LogOperationDTO
  extends AbstractBaseEntityDTO<LogOperation> {

  public static final SearchRequestDTO getSearchRequest() {
    return new LogOperationSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  @NotNull(message = "{validation.logoperation.username.required}")
  private String userName;
  @NotNull(message = "{validation.logoperation.usercode.required}")
  private String userCode;
  @NotNull(message = "{validation.logoperation.type.required}")
  private String type;
  @NotNull(message = "{validation.logoperation.valueid.required}")
  private Long valueId;
  private String oldValue;
  private String newValue;

  @Default
  @NotNull(message = "{validation.logoperation.datetime.required}")
  private LocalDateTime dateTimeOperation = LocalDateTime.now();

  @NotNull(message = "{validation.logoperation.operation.required}")
  private OperationEnum operation;

  @Override
  public LogOperationDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("LogOperationDTO [");
    if (id != null) {
      builder.append("id=");
      builder.append(id);
      builder.append(", ");
    }
    if (userCode != null) {
      builder.append("userCode=");
      builder.append(userCode);
      builder.append(", ");
    }
    if (operation != null) {
      builder.append("operation=");
      builder.append(operation);
      builder.append(", ");
    }
    if (type != null) {
      builder.append("type=");
      builder.append(type);
      builder.append(", ");
    }
    if (dateTimeOperation != null) {
      builder.append("dateTimeOperation=");
      builder.append(dateTimeOperation);
    }
    builder.append("]");
    return builder.toString();
  }

  @SuppressWarnings("javadoc")
  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String USER_CODE = "userCode";
    public static final String OPERATION = "operation";
    public static final String TYPE = "type";
    public static final String DATE_TIME_OPERATION = "dateTimeOperation";
    public static final String USER_NAME = "userName";
    public static final String VALUE_ID = "valueId";
    public static final String OLD_VALUE = "oldValue";
    public static final String NEW_VALUE = "newValue";
  }
}
