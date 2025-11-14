package com.ia.core.security.view.role.form;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.user.UserManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class RoleFormViewModelConfig
  extends FormViewModelConfig<RoleDTO> {
  /** Serviço de usuário */
  @Getter
  private final UserManager userService;
  /**
   * serviço de privilégio
   */
  @Getter
  private final PrivilegeManager privilegeService;

  /**
   * @param readOnly
   * @param userService
   * @param privilegeService
   */
  public RoleFormViewModelConfig(boolean readOnly, UserManager userService,
                                 PrivilegeManager privilegeService) {
    super(readOnly);
    this.userService = userService;
    this.privilegeService = privilegeService;
  }

}
