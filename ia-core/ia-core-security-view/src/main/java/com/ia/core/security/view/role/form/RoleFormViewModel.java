package com.ia.core.security.view.role.form;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.view.privilege.PrivilegeService;
import com.ia.core.security.view.user.UserService;
import com.ia.core.view.components.form.viewModel.FormViewModel;

import lombok.Getter;

/**
 * View Model de formulário para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
public class RoleFormViewModel
  extends FormViewModel<RoleDTO> {
  /** Serviço de usuário */
  @Getter
  private UserService userService;
  /**
   * serviço de privilégio
   */
  @Getter
  private PrivilegeService privilegeService;

  /**
   * @param readOnly         indicativo de somente leitura
   * @param userService      serviço de usuário
   * @param privilegeService serviço de privilégio
   */
  public RoleFormViewModel(boolean readOnly, UserService userService,
                           PrivilegeService privilegeService) {
    super(readOnly);
    this.userService = userService;
    this.privilegeService = privilegeService;
  }

}
