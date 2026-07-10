package com.ia.core.security.service.model.user;

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

import java.util.HashSet;
import java.util.Set;

/**
 * Classe que representa o objeto de transferência de dados para user privilege.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a UserPrivilegeDTO
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
public class UserPrivilegeDTO
    extends AbstractBaseEntityDTO<UserPrivilege> {

    @NotNull(message = UserPrivilegeTranslator.VALIDATION.PRIVILEGE_REQUIRED)
    private PrivilegeDTO privilege;

    @Default
    private Set<PrivilegeOperationDTO> operations = new HashSet<>();

    /**
     * @return
     */
    public static SearchRequestDTO getSearchRequest() {
        return SearchRequestDTO.builder().build();
    }

    @Override
    public UserPrivilegeDTO cloneObject() {
        return toBuilder()
            .privilege(privilege != null ? privilege.cloneObject() : null)
            .operations(operations != null ? new HashSet<>(getOperations().stream()
                .filter(java.util.Objects::nonNull)
                .map(PrivilegeOperationDTO::cloneObject).toList()) : new HashSet<>())
            .build();
    }

    @Override
    public UserPrivilegeDTO copyObject() {
        return ((UserPrivilegeDTO) super.copyObject()).toBuilder()
            .privilege(privilege != null ? privilege.copyObject() : null)
            .operations(operations != null ? new HashSet<>(getOperations().stream()
                .filter(java.util.Objects::nonNull)
                .map(PrivilegeOperationDTO::copyObject).toList()) : new HashSet<>())
            .build();
    }

    public static class CAMPOS
        extends AbstractBaseEntityDTO.CAMPOS {
        public static final String PRIVILEGE = "privilege";
        public static final String OPERATIONS = "operations";

        public static Set<String> values() {
            return Set.of(PRIVILEGE, OPERATIONS);
        }
    }
}
