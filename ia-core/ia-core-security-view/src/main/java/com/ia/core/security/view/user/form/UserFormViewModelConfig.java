package com.ia.core.security.view.user.form;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.privilege.PrivilegeService;
import com.ia.core.security.view.role.RoleService;
import com.ia.core.security.view.user.UserRoleService;
import com.ia.core.security.view.user.UserRoleServiceConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class UserFormViewModelConfig
  extends FormViewModelConfig<UserDTO> {
  /** {@link PrivilegeService} */
  @Getter
  private final PrivilegeService privileService;
  /** {@link RoleService} */
  @Getter
  private final RoleService roleService;

  /**
   * @param readOnly
   * @param privileService
   * @param roleService
   */
  public UserFormViewModelConfig(boolean readOnly,
                                 PrivilegeService privileService,
                                 RoleService roleService) {
    super(readOnly);
    this.privileService = privileService;
    this.roleService = roleService;
  }

  /**
   * @return {@link UserRoleService}
   */
  public UserRoleService getUserRoleService() {
    return new UserRoleService(new UserRoleServiceConfig(roleService));
  }
}
