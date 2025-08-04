package com.ia.core.security.view.user.form;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.privilege.PrivilegeService;
import com.ia.core.security.view.role.RoleService;
import com.ia.core.security.view.user.UserRoleService;
import com.ia.core.view.components.form.viewModel.FormViewModel;

import lombok.Getter;

/**
 * View Model formulário de usuário
 *
 * @author Israel Araújo
 */
public class UserFormViewModel
  extends FormViewModel<UserDTO> {
  /** {@link PrivilegeService} */
  @Getter
  private PrivilegeService privileService;
  /** {@link RoleService} */
  @Getter
  private RoleService roleService;

  /**
   * @param readOnly         indicativo de somente leitura
   * @param privilegeService {@link PrivilegeService}
   * @param roleService      {@link RoleService}
   */
  public UserFormViewModel(boolean readOnly,
                           PrivilegeService privilegeService,
                           RoleService roleService) {
    super(readOnly);
    this.privileService = privilegeService;
    this.roleService = roleService;
  }

  /**
   * @return {@link UserRoleService}
   */
  public UserRoleService getUserRoleService() {
    return new UserRoleService(roleService);
  }

}
