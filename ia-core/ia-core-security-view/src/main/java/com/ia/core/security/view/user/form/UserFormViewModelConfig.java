package com.ia.core.security.view.user.form;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.security.view.user.UserRoleManager;
import com.ia.core.security.view.user.UserRoleManagerConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class UserFormViewModelConfig
  extends FormViewModelConfig<UserDTO> {
  /** {@link PrivilegeManager} */
  @Getter
  private final PrivilegeManager privileService;
  /** {@link RoleManager} */
  @Getter
  private final RoleManager roleService;

  /**
   * @param readOnly
   * @param privileService
   * @param roleService
   */
  public UserFormViewModelConfig(boolean readOnly,
                                 PrivilegeManager privileService,
                                 RoleManager roleService) {
    super(readOnly);
    this.privileService = privileService;
    this.roleService = roleService;
  }

  /**
   * @return {@link UserRoleManager}
   */
  public UserRoleManager getUserRoleService() {
    return new UserRoleManager(new UserRoleManagerConfig(roleService));
  }
}
