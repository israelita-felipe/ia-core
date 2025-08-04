package com.ia.core.security.service.model.role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.ia.core.model.HasVersion;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO
  extends AbstractBaseEntityDTO<Role> {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061623L;

  public static final SearchRequestDTO getSearchRequest() {
    return new RoleSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return Set.of("name");
  }

  /** Nome */
  @NotNull
  private String name;

  @Default
  private Collection<UserDTO> users = new HashSet<>();

  @Default
  private Collection<PrivilegeDTO> privileges = new HashSet<>();

  @Override
  public RoleDTO cloneObject() {
    return toBuilder()
        .users(new HashSet<>(getUsers().stream().map(UserDTO::cloneObject)
            .toList()))
        .privileges(new HashSet<>(getPrivileges().stream()
            .map(PrivilegeDTO::cloneObject).toList()))
        .build();
  }

  @Override
  public RoleDTO copyObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .users(new HashSet<>(getUsers().stream().map(UserDTO::copyObject)
            .toList()))
        .privileges(new HashSet<>(getPrivileges().stream()
            .map(PrivilegeDTO::copyObject).toList()))
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
    RoleDTO other = (RoleDTO) obj;
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
