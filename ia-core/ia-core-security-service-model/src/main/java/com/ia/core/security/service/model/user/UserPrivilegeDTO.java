package com.ia.core.security.service.model.user;

import java.util.HashSet;
import java.util.Set;

import com.ia.core.security.model.user.UserPrivilege;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPrivilegeDTO
  extends AbstractBaseEntityDTO<UserPrivilege> {

  @NotNull
  private PrivilegeDTO privilege;

  @Default
  private Set<PrivilegeOperationDTO> operations = new HashSet<>();

  @Override
  public UserPrivilegeDTO cloneObject() {
    return toBuilder()
        .privilege(privilege != null ? privilege.cloneObject() : null)
        .operations(new HashSet<>(operations.stream()
            .map(PrivilegeOperationDTO::cloneObject).toList()))
        .build();
  }

  @Override
  public UserPrivilegeDTO copyObject() {
    return ((UserPrivilegeDTO) super.copyObject()).toBuilder()
        .privilege(privilege != null ? privilege.copyObject() : null)
        .operations(new HashSet<>(operations.stream()
            .map(PrivilegeOperationDTO::copyObject).toList()))
        .build();
  }

  public static class CAMPOS
    extends AbstractBaseEntityDTO.CAMPOS {
    public static final String PRIVILEGE = create("privilege");
    public static final String OPERATIONS = create("operations");
  }

  /**
   * @return
   */
  public static SearchRequestDTO getSearchRequest() {
    return SearchRequestDTO.builder().build();
  }
}
