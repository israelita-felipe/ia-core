package com.ia.core.security.service.model.privilege;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.ia.core.security.model.privilege.Privilege;
import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
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
public class PrivilegeDTO
  extends AbstractBaseEntityDTO<Privilege> {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061623L;

  public static final SearchRequestDTO getSearchRequest() {
    return new PrivilegeSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return getSearchRequest().propertyFilters();
  }

  /**
   * Nome do privilégio
   */
  @NotNull(message = "{validation.privilege.name.required}")
  @Size(min = 3, max = 100, message = "{validation.privilege.name.size}")
  private String name;

  @Default
  @NotNull
  private PrivilegeType type = PrivilegeType.SYSTEM;

  @Default
  private Set<String> values = new HashSet<>();

  @Override
  public PrivilegeDTO cloneObject() {
    return toBuilder().build();
  }

  @Override
  public PrivilegeDTO copyObject() {
    return (PrivilegeDTO) super.copyObject();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (id == null) {
      return this == obj;
    }
    if (!(getClass().isInstance(obj))) {
      return false;
    }
    PrivilegeDTO other = (PrivilegeDTO) obj;
    return Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return Objects.hash(id);
    }
    return super.hashCode();
  }

  @Override
  public String toString() {
    return String.format("%s", name);
  }
}
