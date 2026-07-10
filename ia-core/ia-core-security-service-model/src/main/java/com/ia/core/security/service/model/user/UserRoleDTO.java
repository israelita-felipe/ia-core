package com.ia.core.security.service.model.user;

import com.ia.core.model.HasVersion;
import com.ia.core.security.model.role.Role;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe que representa o objeto de transferência de dados para user role.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserRoleDTO
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRoleDTO
    extends AbstractBaseEntityDTO<Role> {
    /**
     * Nome
     */
    @NotNull(message = UserRoleTranslator.VALIDATION.NAME_REQUIRED)
    private String name;

    /** Serial UID */
    @Default
    private Collection<RolePrivilegeDTO> privileges = new HashSet<>();

    public static Set<String> propertyFilters() {
        return Set.of(CAMPOS.NAME);
    }

    @Override
    public UserRoleDTO cloneObject() {
        return toBuilder().privileges(privileges != null ? new HashSet<>(getPrivileges().stream()
            .filter(java.util.Objects::nonNull)
            .map(RolePrivilegeDTO::cloneObject).toList()) : new HashSet<>()).build();
    }

    @Override
    public UserRoleDTO copyObject() {
        return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
            .privileges(privileges != null ? new HashSet<>(getPrivileges().stream()
                .filter(java.util.Objects::nonNull)
                .map(RolePrivilegeDTO::copyObject).toList()) : new HashSet<>())
            .build();
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", name, privileges);
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS
        extends AbstractBaseEntityDTO.CAMPOS {
        public static final String NAME = "name";
        public static final String PRIVILEGES = "privileges";
    }
}
