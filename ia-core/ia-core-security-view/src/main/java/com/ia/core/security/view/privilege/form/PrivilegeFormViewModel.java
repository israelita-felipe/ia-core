package com.ia.core.security.view.privilege.form;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

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
  public PrivilegeFormViewModel(boolean readOnly) {
    super(readOnly);
  }

}
