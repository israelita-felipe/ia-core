package com.ia.core.security.service.model.user;

import java.beans.Transient;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ia.core.model.HasVersion;
import com.ia.core.security.model.user.User;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager.HasContextDefinitions.PrivilegeContext;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

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
public class UserDTO
  extends AbstractBaseEntityDTO<User> {
  /** Serial UID */
  private static final long serialVersionUID = -19560738760061623L;

  public static final SearchRequestDTO getSearchRequest() {
    return new UserSearchRequest();
  }

  public static Set<String> propertyFilters() {
    return Set.of("userName", "userCode");
  }

  @NotNull
  private String userName;
  @NotNull
  private String userCode;
  private String password;
  @Default
  @NotNull
  private boolean enabled = true;
  @Default
  @NotNull
  private boolean accountNotExpired = true;

  @Default
  @NotNull
  private boolean accountNotLocked = true;

  @NotNull
  @Default
  private boolean credentialsNotExpired = true;

  @Default
  private Collection<UserPrivilegeDTO> privileges = new HashSet<>();

  @Default
  private Collection<UserRoleDTO> roles = new HashSet<>();

  @Override
  public UserDTO cloneObject() {
    return toBuilder()
        .privileges(new HashSet<>(getPrivileges().stream()
            .map(UserPrivilegeDTO::cloneObject).toList()))
        .roles(new HashSet<>(getRoles().stream()
            .map(UserRoleDTO::cloneObject).toList()))
        .build();
  }

  @Override
  public UserDTO copyObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .privileges(new HashSet<>(getPrivileges().stream()
            .map(UserPrivilegeDTO::copyObject).toList()))
        .roles(new HashSet<>(getRoles().stream()
            .map(UserRoleDTO::copyObject).toList()))
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
    UserDTO other = (UserDTO) obj;
    return Objects.equals(id, other.id);
  }

  @Transient
  public Collection<PrivilegeDTO> getAllPrivileges() {
    return Stream
        .concat(this.privileges.stream()
            .map(UserPrivilegeDTO::getPrivilege),
                this.roles.stream()
                    .flatMap(role -> role.getPrivileges().stream()
                        .map(RolePrivilegeDTO::getPrivilege)))
        .collect(Collectors.toSet());
  }

  @Transient
  public Collection<PrivilegeContext> getAllContexts() {

    Map<PrivilegeDTO, Collection<PrivilegeOperationDTO>> map = new HashMap<>();
    this.privileges.stream().forEach(userPrivilege -> {
      PrivilegeDTO privilege = userPrivilege.getPrivilege();
      Collection<PrivilegeOperationDTO> values = map
          .getOrDefault(privilege, new HashSet<>());
      values.addAll(userPrivilege.getOperations());
      map.put(privilege, values);
    });
    this.roles.stream().flatMap(role -> role.getPrivileges().stream())
        .forEach(rolePrivilege -> {
          PrivilegeDTO privilege = rolePrivilege.getPrivilege();
          Collection<PrivilegeOperationDTO> values = map
              .getOrDefault(privilege, new HashSet<>());
          values.addAll(rolePrivilege.getOperations());
          map.put(privilege, values);
        });
    return map.entrySet().stream()
        .map(entry -> new PrivilegeContext(entry.getKey(),
                                           entry.getValue()))
        .toList();
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
    return String.format("%s (%s)", userName, userCode);
  }
}
