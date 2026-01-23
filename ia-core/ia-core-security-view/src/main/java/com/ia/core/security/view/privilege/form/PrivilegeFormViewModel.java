package com.ia.core.security.view.privilege.form;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ia.core.security.model.privilege.PrivilegeType;
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

  public Collection<String> getContextKeys() {
    PrivilegeDTO model = getModel();
    if (model == null) {
      Collections.emptyList();
    }
    if (PrivilegeType.USER.equals(model.getType())) {
      return getConfig().getHasContext().stream()
          .flatMap(hasContext -> hasContext.getContexts().stream())
          .collect(Collectors.toSet());
    }
    return getConfig().getHasContext().stream()
        .filter(hasContext -> Objects.equals(hasContext.getContextName(),
                                             model.getName()))
        .flatMap(hasContext -> hasContext.getContexts().stream())
        .collect(Collectors.toSet());
  }

  @Override
  public PrivilegeFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
