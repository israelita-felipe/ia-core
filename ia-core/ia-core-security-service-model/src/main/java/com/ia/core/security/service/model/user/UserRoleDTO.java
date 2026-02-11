package com.ia.core.security.service.model.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.ia.core.model.HasVersion;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;

import jakarta.validation.constraints.NotNull;
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
public class UserRoleDTO
  extends AbstractBaseEntityDTO<Role> {
  public static Set<String> propertyFilters() {
    return Set.of("name");
  }

  /** Serial UID */
  /** Nome */
  @NotNull(message = "{validation.user.role.name.required}")
  private String name;

  @Default
  private Collection<RolePrivilegeDTO> privileges = new HashSet<>();

  @Override
  public UserRoleDTO cloneObject() {
    return toBuilder().privileges(new HashSet<>(getPrivileges().stream()
        .map(RolePrivilegeDTO::cloneObject).toList())).build();
  }

  @Override
  public UserRoleDTO copyObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .privileges(new HashSet<>(getPrivileges().stream()
            .map(RolePrivilegeDTO::copyObject).toList()))
        .build();
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
    UserRoleDTO other = (UserRoleDTO) obj;
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
    return String.format("%s [%s]", name, privileges);
  }
}
