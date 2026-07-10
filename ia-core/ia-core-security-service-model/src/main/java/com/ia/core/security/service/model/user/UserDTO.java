package com.ia.core.security.service.model.user;

import com.ia.core.model.HasVersion;
import com.ia.core.security.model.user.User;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager.HasContextDefinitions.PrivilegeContext;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO
    extends AbstractBaseEntityDTO<User> {
    /**
     * Serial UID
     */
    private static final long serialVersionUID = -19560738760061623L;
    /**
     * Nome do usuário
     */
    @NotNull(message = UserTranslator.VALIDATION.USER_NAME_REQUIRED)
    @Size(min = 3, max = 200, message = UserTranslator.VALIDATION.USER_NAME_SIZE)
    private String userName;
    /**
     * Código do usuário
     */
    @NotNull(message = UserTranslator.VALIDATION.USER_CODE_REQUIRED)
    @Size(min = 3, max = 50, message = UserTranslator.VALIDATION.USER_CODE_SIZE)
    private String userCode;
    /**
     * Senha do usuário
     */
    @Size(min = 6, max = 100, message = UserTranslator.VALIDATION.PASSWORD_SIZE)
    private String password;
    @Default
    private boolean enabled = true;
    @Default
    private boolean accountNotExpired = true;
    @Default
    private boolean accountNotLocked = true;
    @Default
    private boolean credentialsNotExpired = true;
    @Default
    private Collection<UserPrivilegeDTO> privileges = new HashSet<>();
    @Default
    private Collection<UserRoleDTO> roles = new HashSet<>();

    public static final SearchRequestDTO getSearchRequest() {
        return new UserSearchRequestDTO();
    }

    public static Set<String> propertyFilters() {
        return Set.of(CAMPOS.USER_NAME, CAMPOS.USER_CODE);
    }

    @Override
    public UserDTO cloneObject() {
        return toBuilder()
            .privileges(privileges != null ? new HashSet<>(getPrivileges().stream()
                .filter(Objects::nonNull)
                .map(UserPrivilegeDTO::cloneObject).toList()) : new HashSet<>())
            .roles(roles != null ? new HashSet<>(getRoles().stream()
                .filter(Objects::nonNull)
                .map(UserRoleDTO::cloneObject).toList()) : new HashSet<>())
            .build();
    }

    @Override
    public UserDTO copyObject() {
        return toBuilder().id(null).version(HasVersion.DEFAULT_VERSION)
            .privileges(privileges != null ? new HashSet<>(getPrivileges().stream()
                .filter(Objects::nonNull)
                .map(UserPrivilegeDTO::copyObject).toList()) : new HashSet<>())
            .roles(roles != null ? new HashSet<>(getRoles().stream()
                .filter(Objects::nonNull)
                .map(UserRoleDTO::copyObject).toList()) : new HashSet<>())
            .build();
    }

    @Transient
    public Collection<PrivilegeDTO> getAllPrivileges() {
        if (privileges == null && roles == null) {
            return Collections.emptySet();
        }
        Stream.Builder<PrivilegeDTO> builder = Stream.builder();
        if (privileges != null) {
            privileges.stream()
                .filter(Objects::nonNull)
                .map(UserPrivilegeDTO::getPrivilege)
                .filter(Objects::nonNull)
                .forEach(builder::add);
        }
        if (roles != null) {
            roles.stream()
                .filter(Objects::nonNull)
                .flatMap(role -> role.getPrivileges() != null
                    ? role.getPrivileges().stream()
                    : Stream.empty())
                .filter(Objects::nonNull)
                .map(RolePrivilegeDTO::getPrivilege)
                .filter(Objects::nonNull)
                .forEach(builder::add);
        }
        return builder.build().collect(Collectors.toSet());
    }

    @Transient
    public Collection<PrivilegeContext> getAllContexts() {

        Map<PrivilegeDTO, Collection<PrivilegeOperationDTO>> map = new HashMap<>();
        if (privileges != null) {
            privileges.stream()
                .filter(Objects::nonNull)
                .forEach(userPrivilege -> {
                    PrivilegeDTO privilege = userPrivilege.getPrivilege();
                    if (privilege != null) {
                        Collection<PrivilegeOperationDTO> values = map
                            .getOrDefault(privilege, new HashSet<>());
                        if (userPrivilege.getOperations() != null) {
                            values.addAll(userPrivilege.getOperations());
                        }
                        map.put(privilege, values);
                    }
                });
        }
        if (roles != null) {
            roles.stream()
                .filter(Objects::nonNull)
                .flatMap(role -> role.getPrivileges() != null
                    ? role.getPrivileges().stream()
                    : Stream.empty())
                .filter(Objects::nonNull)
                .forEach(rolePrivilege -> {
                    PrivilegeDTO privilege = rolePrivilege.getPrivilege();
                    if (privilege != null) {
                        Collection<PrivilegeOperationDTO> values = map
                            .getOrDefault(privilege, new HashSet<>());
                        if (rolePrivilege.getOperations() != null) {
                            values.addAll(rolePrivilege.getOperations());
                        }
                        map.put(privilege, values);
                    }
                });
        }
        return map.entrySet().stream()
            .map(entry -> new PrivilegeContext(entry.getKey(),
                entry.getValue()))
            .toList();
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", userName, userCode);
    }

    @SuppressWarnings("javadoc")
    public static class CAMPOS
        extends AbstractBaseEntityDTO.CAMPOS {
        public static final String USER_NAME = "userName";
        public static final String USER_CODE = "userCode";
        public static final String PASSWORD = "password";
        public static final String ENABLED = "enabled";
        public static final String ACCOUNT_NOT_EXPIRED = "accountNotExpired";
        public static final String ACCOUNT_NOT_LOCKED = "accountNotLocked";
        public static final String CREDENTIALS_NOT_EXPIRED = "credentialsNotExpired";
        public static final String PRIVILEGES = "privileges";
        public static final String ROLES = "roles";
    }
}
