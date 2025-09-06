package com.ia.core.security.view.user.form;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.privilege.PrivilegeService;
import com.ia.core.security.view.role.RoleService;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * View Model formulário de usuário
 *
 * @author Israel Araújo
 */
public class UserFormViewModel
  extends FormViewModel<UserDTO> {

  /**
   * @param readOnly         indicativo de somente leitura
   * @param privilegeService {@link PrivilegeService}
   * @param roleService      {@link RoleService}
   */
  public UserFormViewModel(UserFormViewModelConfig config) {
    super(config);
  }

  @Override
  public UserFormViewModelConfig getConfig() {
    return (UserFormViewModelConfig) super.getConfig();
  }
}
