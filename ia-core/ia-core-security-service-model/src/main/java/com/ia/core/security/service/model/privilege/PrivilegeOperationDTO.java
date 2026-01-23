package com.ia.core.security.service.model.privilege;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.model.privilege.PrivilegeOperation;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeOperationDTO
  extends AbstractBaseEntityDTO<PrivilegeOperation> {

  private OperationEnum operation;

  @Default
  private Collection<PrivilegeOperationContextDTO> context = new HashSet<>();

  @Override
  public PrivilegeOperationDTO cloneObject() {
    return toBuilder().context(new HashSet<>(context.stream()
        .map(PrivilegeOperationContextDTO::cloneObject)
        .collect(Collectors.toSet()))).build();
  }

  @Override
  public PrivilegeOperationDTO copyObject() {
    return ((PrivilegeOperationDTO) super.copyObject()).toBuilder()
        .context(new HashSet<>(context.stream()
            .map(PrivilegeOperationContextDTO::copyObject)
            .collect(Collectors.toSet())))
        .build();
  }

  /**
   * @return
   */
  public static SearchRequestDTO getSearchRequest() {
    return SearchRequestDTO.builder().build();
  }

}
