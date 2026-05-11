package com.ia.core.security.service.model.role;

import com.ia.core.model.HasVersion;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.user.UserDTO;
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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Israel Araújo
 */
@Getter
@Setter
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

  /**
   * Nome do papel
   */
  @NotNull(message = "validation.role.name.required")
  @Size(min = 3, max = 100, message = "validation.role.name.size")
  private String name;

  @Default
  private Collection<UserDTO> users = new HashSet<>();

  @Default
  private Collection<RolePrivilegeDTO> privileges = new HashSet<>();

  /**
   * Retorna uma visão imutável dos usuários associados a esta função.
   *
   * @return coleção de usuários não modificável
   * @bugfix SECURITY: Evita modificação externa não controlada do estado interno
   */
  public Collection<UserDTO> getUsers() {
    return Collections.unmodifiableCollection(users);
  }

  /**
   * Define os usuários (faz uma cópia defensiva).
   *
   * @param users nova coleção de usuários (não pode ser null)
   * @throws NullPointerException se users for null
   * @bugfix SECURITY: Cópia defensiva para evitar retenção de referência mutável
   */
  public void setUsers(Collection<UserDTO> users) {
    Objects.requireNonNull(users, "Users collection cannot be null");
    this.users = new HashSet<>(users);
  }

  /**
   * Retorna uma visão imutável dos privilégios associados a esta função.
   *
   * @return coleção de privilégios não modificável
   * @bugfix SECURITY: Evita modificação externa não controlada do estado interno
   */
  public Collection<RolePrivilegeDTO> getPrivileges() {
    return Collections.unmodifiableCollection(privileges);
  }

  /**
   * Define os privilégios (faz uma cópia defensiva).
   *
   * @param privileges nova coleção de privilégios (não pode ser null)
   * @throws NullPointerException se privileges for null
   * @bugfix SECURITY: Cópia defensiva para evitar retenção de referência mutável
   */
  public void setPrivileges(Collection<RolePrivilegeDTO> privileges) {
    Objects.requireNonNull(privileges, "Privileges collection cannot be null");
    this.privileges = new HashSet<>(privileges);
  }

  @Override
  public RoleDTO cloneObject() {
    return toBuilder()
        .users(new HashSet<>(getUsers().stream().map(UserDTO::cloneObject)
            .toList()))
        .privileges(new HashSet<>(getPrivileges().stream()
            .map(RolePrivilegeDTO::cloneObject).toList()))
        .build();
  }

  @Override
  public RoleDTO copyObject() {
    return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
        .users(new HashSet<>(getUsers().stream().map(UserDTO::copyObject)
            .toList()))
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
