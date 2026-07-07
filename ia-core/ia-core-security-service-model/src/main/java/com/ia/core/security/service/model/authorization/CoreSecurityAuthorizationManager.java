package com.ia.core.security.service.model.authorization;

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

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
/**
 * Interface que define o contrato para core security authorization.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a CoreSecurityAuthorizationManager
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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

  /**
   * Checks if the user has the required permission for the given object and operation.
   * <p>
   * Security model:
   * <ul>
   *   <li>object == null → CREATE operation without entity → ALLOW</li>
   *   <li>contexto vazio → No context restrictions → ALLOW</li>
   *   <li>contexto não vazio → Must match privilege contexts → ALLOW/DENY</li>
   * </ul>
   *
   * @param hasContext the context holder containing privilege information
   * @param operation the operation being checked (CREATE, READ, UPDATE, DELETE)
   * @param object the object being accessed for authorization
   * @return true if user has permission, false otherwise
   */
  default boolean check(HasContext hasContext, Operation operation,
                        Object object) {
    // CREATE operations: no object to check yet
    if (object == null) {
      return true;
    }

    // No context restrictions defined → allow access
    Map<String, String> serviceContextValue = hasContext.getContextValue(object);
    if (serviceContextValue.isEmpty()) {
      return true;
    }

    // Get user privileges for this functionality and operation
    Collection<PrivilegeOperationDTO> userPrivileges = getUserPrivileges(operation, hasContext);

    // Verify if all privileges have matching context
    return userPrivileges.stream().allMatch(userPrivilege ->
        hasMatchingContext(userPrivilege, serviceContextValue, hasContext));
  }

  /**
   * Obtém os privilégios do usuário para a operação e funcionalidade.
   */
  private Collection<PrivilegeOperationDTO> getUserPrivileges(Operation operation, HasContext hasContext) {
    return getCurrentContextDefinitions().definitions().getContext().stream()
        .filter(context -> hasMatchingPrivilege(context, hasContext))
        .filter(context -> hasMatchingOperation(context, operation))
        .flatMap(context -> context.getPrivilegeOperations().stream())
        .toList();
  }

  /**
   * Verifica se a funcionalidade corresponde.
   */
  private boolean hasMatchingPrivilege(HasContextDefinitions.PrivilegeContext context, HasContext hasContext) {
    return Objects.equals(context.getPrivilege().getName(), hasContext.getContextName());
  }

  /**
   * Verifica se a operação corresponde às operações privilegiadas.
   */
  private boolean hasMatchingOperation(HasContextDefinitions.PrivilegeContext context, Operation operation) {
    return context.getPrivilegeOperations().stream()
        .map(PrivilegeOperationDTO::getOperation)
        .anyMatch(op -> Objects.equals(operation, op));
  }

  /**
   * Verifica se o privilégio do usuário tem contexto válido para acesso.
   * Retorna true se: não há restrição de contexto OU se algum contexto corresponde.
   */
  private boolean hasMatchingContext(PrivilegeOperationDTO userPrivilege,
                                     Map<String, String> serviceContextValue,
                                     HasContext hasContext) {
    Collection<PrivilegeOperationContextDTO> relevantContexts = userPrivilege.getContext().stream()
        .filter(c -> serviceContextValue.containsKey(c.getContextKey()))
        .toList();

    // Sem restrição de contexto
    if (relevantContexts.isEmpty()) {
      return true;
    }

    // Verifica se algum contexto corresponde
    return relevantContexts.stream().anyMatch(userContext ->
        userContext.getValues().stream()
            .anyMatch(userContextValue -> {
              String contextKey = userContext.getContextKey();
              return hasContext.matches(contextKey,
                                        serviceContextValue.get(contextKey),
                                        userContextValue);
            }));
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

  boolean isReadEnabled();

  boolean isUpdateEnabled();

  default boolean isEnabledAll() {
    return isUpdateEnabled() && isCreateEnabled() && isDeleteEnabled()
        && isReadEnabled();
  }

  void setCreateEnabled(boolean createEnabled);

  void setDeleteEnabled(boolean deleteEnabled);

  void setReadEnabled(boolean readEnabled);

  void setUpdateEnabled(boolean updateEnabled);
}
