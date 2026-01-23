package com.ia.core.security.service.authorization;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CoreAuthorizationManager
  implements CoreSecurityAuthorizationManager {

  private static InheritableThreadLocal<Boolean> updateEnabled;
  private static InheritableThreadLocal<Boolean> deleteEnabled;
  private static InheritableThreadLocal<Boolean> createEnabled;
  private static InheritableThreadLocal<Boolean> readEnabled;

  static {
    CoreAuthorizationManager.updateEnabled = new InheritableThreadLocal<Boolean>();
    CoreAuthorizationManager.deleteEnabled = new InheritableThreadLocal<Boolean>();
    CoreAuthorizationManager.createEnabled = new InheritableThreadLocal<Boolean>();
    CoreAuthorizationManager.readEnabled = new InheritableThreadLocal<Boolean>();
    CoreAuthorizationManager.updateEnabled.set(false);
    CoreAuthorizationManager.deleteEnabled.set(false);
    CoreAuthorizationManager.createEnabled.set(false);
    CoreAuthorizationManager.readEnabled.set(false);
  }

  /**
   * @return {@link #updateEnabled}
   */
  @Override
  public boolean isUpdateEnabled() {
    return updateEnabled.get();
  }

  /**
   * @param updateEnabled atualiza {@link #updateEnabled}.
   */
  @Override
  public void setUpdateEnabled(boolean updateEnabled) {
    CoreAuthorizationManager.updateEnabled.set(updateEnabled);
  }

  /**
   * @return {@link #deleteEnabled}
   */
  @Override
  public boolean isDeleteEnabled() {
    return deleteEnabled.get();
  }

  /**
   * @param deleteEnabled atualiza {@link #deleteEnabled}.
   */
  @Override
  public void setDeleteEnabled(boolean deleteEnabled) {
    CoreAuthorizationManager.deleteEnabled.set(deleteEnabled);
  }

  /**
   * @return {@link #createEnabled}
   */
  @Override
  public boolean isCreateEnabled() {
    return createEnabled.get();
  }

  /**
   * @param createEnabled atualiza {@link #createEnabled}.
   */
  @Override
  public void setCreateEnabled(boolean createEnabled) {
    CoreAuthorizationManager.createEnabled.set(createEnabled);
  }

  /**
   * @return {@link #readEnabled}
   */
  @Override
  public boolean isReadEnabled() {
    return readEnabled.get();
  }

  /**
   * @param readEnabled atualiza {@link #readEnabled}.
   */
  @Override
  public void setReadEnabled(boolean readEnabled) {
    CoreAuthorizationManager.readEnabled.set(readEnabled);
  }

}
