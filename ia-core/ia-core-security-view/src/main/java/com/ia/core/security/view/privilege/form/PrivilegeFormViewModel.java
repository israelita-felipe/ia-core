package com.ia.core.security.view.privilege.form;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

/**
 * View Model de formulário para {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
public class PrivilegeFormViewModel
  extends FormViewModel<PrivilegeDTO> {

  /**
   * @param readOnly indicativo de somente leitura
   */
  public PrivilegeFormViewModel(FormViewModelConfig<PrivilegeDTO> config) {
    super(config);
  }

}
