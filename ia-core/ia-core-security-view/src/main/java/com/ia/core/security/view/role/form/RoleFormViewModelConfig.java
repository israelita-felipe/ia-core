package com.ia.core.security.view.role.form;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.view.privilege.PrivilegeService;
import com.ia.core.security.view.user.UserService;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class RoleFormViewModelConfig
  extends FormViewModelConfig<RoleDTO> {
  /** Serviço de usuário */
  @Getter
  private final UserService userService;
  /**
   * serviço de privilégio
   */
  @Getter
  private final PrivilegeService privilegeService;

  /**
   * @param readOnly
   * @param userService
   * @param privilegeService
   */
  public RoleFormViewModelConfig(boolean readOnly, UserService userService,
                                 PrivilegeService privilegeService) {
    super(readOnly);
    this.userService = userService;
    this.privilegeService = privilegeService;
  }

}
