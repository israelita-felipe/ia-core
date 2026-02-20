package com.ia.core.security.service.model.privilege;

import java.util.HashSet;
import java.util.Set;

import com.ia.core.security.model.privilege.PrivilegeOperationContext;
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
public class PrivilegeOperationContextDTO
  extends AbstractBaseEntityDTO<PrivilegeOperationContext> {

  private String contextKey;

  @Default
  private Set<String> values = new HashSet<>();

  @Override
  public PrivilegeOperationContextDTO cloneObject() {
    return toBuilder().values(new HashSet<>(values)).build();
  }

  @Override
  public PrivilegeOperationContextDTO copyObject() {
    return ((PrivilegeOperationContextDTO) super.copyObject()).toBuilder()
        .values(new HashSet<>(values)).build();
  }

  /**
   * @return
   */
  public static SearchRequestDTO getSearchRequest() {
    return new SearchRequestDTO();
  }
}
