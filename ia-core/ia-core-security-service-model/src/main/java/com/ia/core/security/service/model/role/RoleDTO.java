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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO (Data Transfer Object) para representar papéis no sistema.
 * <p>
 * Esta classe é utilizada para transferir dados de papéis entre as camadas
 * de apresentação e serviço, contendo informações sobre nome, usuários
 * e privilégios associados a um papel específico.
 *
 * @author Israel Araújo
 * @see Role
 * @see AbstractBaseEntityDTO
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO
    extends AbstractBaseEntityDTO<Role> {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -19560738760061623L;
    /**
     * Nome do papel
     * <p>
     * Deve conter entre 3 e 100 caracteres e não pode ser nulo.
     */
    @NotNull(message = RoleTranslator.VALIDATION.NAME_REQUIRED)
    @Size(min = 3, max = 100, message = RoleTranslator.VALIDATION.NAME_SIZE)
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
        if (users == null) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableCollection(users);
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
        if (privileges == null) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableCollection(privileges);
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

    public static SearchRequestDTO getSearchRequest() {
        return new RoleSearchRequestDTO();
    }

    /**
     * Retorna o conjunto de propriedades disponíveis para filtragem.
     *
     * @return conjunto de strings representando as propriedades que podem ser usadas como filtros
     */
    public static Set<String> propertyFilters() {
        return getSearchRequest().propertyFilters();
    }

    @Override
    public RoleDTO cloneObject() {
        return toBuilder()
            .users(users != null ? new HashSet<>(getUsers().stream()
                .filter(Objects::nonNull)
                .map(UserDTO::cloneObject)
                .toList()) : new HashSet<>())
            .privileges(privileges != null ? new HashSet<>(getPrivileges().stream()
                .filter(Objects::nonNull)
                .map(RolePrivilegeDTO::cloneObject).toList()) : new HashSet<>())
            .build();
    }

    @Override
    public RoleDTO copyObject() {
        return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
            .users(users != null ? new HashSet<>(getUsers().stream()
                .filter(Objects::nonNull)
                .map(UserDTO::copyObject)
                .toList()) : new HashSet<>())
            .privileges(privileges != null ? new HashSet<>(getPrivileges().stream()
                .filter(Objects::nonNull)
                .map(RolePrivilegeDTO::copyObject).toList()) : new HashSet<>())
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

    @SuppressWarnings("javadoc")
    public static class CAMPOS extends AbstractBaseEntityDTO.CAMPOS {
        public static final String NAME = "name";
        public static final String USERS = "users";
        public static final String PRIVILEGES = "privileges";

        /**
         * Retorna todos os nomes de campos deste DTO incluindo os da superclasse.
         *
         * @return conjunto de strings com os nomes dos campos
         */
        public static Set<String> values() {
            var baseValues = AbstractBaseEntityDTO.CAMPOS.values();
            var currentValues = Set.of(NAME, USERS, PRIVILEGES);
            var allValues = new java.util.HashSet<String>();
            allValues.addAll(baseValues);
            allValues.addAll(currentValues);
            return java.util.Collections.unmodifiableSet(allValues);
        }
    }
}
