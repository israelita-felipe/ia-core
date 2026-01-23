package com.ia.core.security.service.model.authorization;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import com.ia.core.security.model.authentication.JwtManager.Context;
import com.ia.core.security.model.functionality.Operation;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Israel Araújo
 */
public interface CoreSecurityAuthorizationManager {

  public static interface HasRoles {
    Collection<String> roles();
  }

  public static interface HasContextDefinitions {
    Context<PrivilegeContext> definitions();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrivilegeContext {
      private PrivilegeDTO privilege;
      private Collection<PrivilegeOperationDTO> privilegeOperations;
    }
  }

  default boolean canCreate(HasFunctionality root, Object object) {
    if (isCreateEnabled()) {
      return true;
    }
    return check(root, OperationEnum.CREATE, object);
  }

  default boolean canDelete(HasFunctionality root, Object object) {
    if (isDeleteEnabled()) {
      return true;
    }
    return check(root, OperationEnum.DELETE, object);
  }

  default boolean canRead(HasFunctionality root, Object object) {
    if (isReadEnabled()) {
      return true;
    }
    return check(root, OperationEnum.READ, object);
  }

  default boolean canUpdate(HasFunctionality root, Object object) {
    if (isUpdateEnabled()) {
      return true;
    }
    return check(root, OperationEnum.UPDATE, object);
  }

  /**
   * @param root
   * @param delete
   * @return
   */
  default boolean check(HasFunctionality root, Operation operation,
                        Object object) {
    if (isEnabledAll()) {
      return true;
    }
    final String name = root.getFunctionalityTypeName();
    boolean hasAuthority = getCurrentRoles().roles().stream()
        .anyMatch(authority -> {
          return Objects.equals(name, authority);
        });
    if (HasContext.class.isInstance(root)) {
      return hasAuthority && check((HasContext) root, operation, object);
    }
    return hasAuthority;
  }

  default boolean check(HasContext hasContext, Operation operation,
                        Object object) {
    if (object == null) {
      return true;
    }
    Map<String, String> serviceContextValue = hasContext
        .getContextValue(object);
    if (serviceContextValue.isEmpty()) {
      return true;
    }
    Collection<PrivilegeOperationDTO> userPrivileges = getCurrentContextDefinitions()
        .definitions().getContext().stream()
        .filter(cd -> Objects.equals(cd.getPrivilege().getName(),
                                     hasContext.getContextName()))
        // filtra a mesma operação
        .filter(cd -> cd.getPrivilegeOperations().stream()
            .map(p -> p.getOperation())
            .anyMatch(op -> Objects.equals(operation, op)))
        .flatMap(entry -> entry.getPrivilegeOperations().stream()).toList();

    return userPrivileges.stream().allMatch(userPrivilege -> {
      // filtra os elementos que possuem o mesmo contexto
      Collection<PrivilegeOperationContextDTO> userContexts = userPrivilege
          .getContext().stream()
          .filter(c -> serviceContextValue.containsKey(c.getContextKey()))
          .toList();
      // se contém a propriedade de contexto
      boolean containsContext = !userContexts.isEmpty();
      // não contém a chave ou contém a chave e algum dos valores corresponde ao
      // valor do contexto
      return !containsContext || (containsContext && userContexts.stream()
          // existe algum elemento no contexto correspondente com acesso ao
          // valor
          .anyMatch(userContextDefinition -> {
            return userContextDefinition.getValues().stream()
                .anyMatch(userContextDefinitionValue -> {
                  String contextKey = userContextDefinition.getContextKey();
                  return hasContext.matches(contextKey,
                                            serviceContextValue
                                                .get(contextKey),
                                            userContextDefinitionValue);
                });
          }));
    });
  }

  default void enableAll(boolean enabled) {
    setReadEnabled(enabled);
    setUpdateEnabled(enabled);
    setDeleteEnabled(enabled);
    setCreateEnabled(enabled);
  }

  HasRoles getCurrentRoles();

  HasContextDefinitions getCurrentContextDefinitions();

  boolean isCreateEnabled();

  boolean isDeleteEnabled();

  default boolean isEnabledAll() {
    return isUpdateEnabled() && isCreateEnabled() && isDeleteEnabled()
        && isReadEnabled();
  }

  boolean isReadEnabled();

  boolean isUpdateEnabled();

  void setCreateEnabled(boolean createEnabled);

  void setDeleteEnabled(boolean deleteEnabled);

  void setReadEnabled(boolean readEnabled);

  void setUpdateEnabled(boolean updateEnabled);

}
