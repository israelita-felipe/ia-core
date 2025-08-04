package com.ia.core.security.service.model.authorization;

import java.util.Collection;
import java.util.Objects;

import com.ia.core.security.model.functionality.Operation;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.functionality.HasFunctionality;

/**
 * @author Israel Araújo
 */
public interface CoreSecurityAuthorizationManager {

  public static interface HasRoles {
    Collection<String> roles();
  }

  default boolean canCreate(HasFunctionality root) {
    if (isCreateEnabled()) {
      return true;
    }
    return check(root, OperationEnum.CREATE);
  }

  default boolean canDelete(HasFunctionality root) {
    if (isDeleteEnabled()) {
      return true;
    }
    return check(root, OperationEnum.DELETE);
  }

  default boolean canRead(HasFunctionality root) {
    if (isReadEnabled()) {
      return true;
    }
    return check(root, OperationEnum.READ);
  }

  default boolean canUpdate(HasFunctionality root) {
    if (isUpdateEnabled()) {
      return true;
    }
    return check(root, OperationEnum.UPDATE);
  }

  /**
   * @param root
   * @param delete
   * @return
   */
  default boolean check(HasFunctionality root, Operation operation) {
    if (isEnabledAll()) {
      return true;
    }
    final String name = root.getFunctionalityTypeName();
    return getCurrentRoles().roles().stream().anyMatch(authority -> {
      return Objects.equals(operation.create(name), authority);
    });
  }

  default void enableAll(boolean enabled) {
    setReadEnabled(enabled);
    setUpdateEnabled(enabled);
    setDeleteEnabled(enabled);
    setCreateEnabled(enabled);
  }

  HasRoles getCurrentRoles();

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
