package com.ia.core.security.view.user.form;

import java.util.List;

import com.ia.core.security.service.model.authorization.HasContext;
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
  @Getter
  private final List<HasContext> hasContext;

  /**
   * @param readOnly
   * @param privileService
   * @param roleService
   */
  public UserFormViewModelConfig(boolean readOnly,
                                 PrivilegeManager privileService,
                                 RoleManager roleService,
                                 List<HasContext> hasContext) {
    super(readOnly);
    this.privileService = privileService;
    this.roleService = roleService;
    this.hasContext = hasContext;
  }

  /**
   * @return {@link UserRoleManager}
   */
  public UserRoleManager getUserRoleManager() {
    return new UserRoleManager(new UserRoleManagerConfig(roleService));
  }
}
