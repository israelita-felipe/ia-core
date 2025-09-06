package com.ia.core.security.view.role.form;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * View Model de formulário para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
public class RoleFormViewModel
  extends FormViewModel<RoleDTO> {

  /**
   * @param readOnly         indicativo de somente leitura
   * @param userService      serviço de usuário
   * @param privilegeService serviço de privilégio
   */
  public RoleFormViewModel(RoleFormViewModelConfig config) {
    super(config);
  }

  @Override
  public RoleFormViewModelConfig getConfig() {
    return (RoleFormViewModelConfig) super.getConfig();
  }
}
