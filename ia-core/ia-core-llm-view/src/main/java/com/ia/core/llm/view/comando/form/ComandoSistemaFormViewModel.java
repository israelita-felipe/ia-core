package com.ia.core.llm.view.comando.form;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * @author Israel Araújo
 */
public class ComandoSistemaFormViewModel
  extends FormViewModel<ComandoSistemaDTO> {

  /**
   * @param readOnly
   */
  public ComandoSistemaFormViewModel(ComandoSistemaFormViewModelConfig config) {
    super(config);
  }

  @Override
  public ComandoSistemaFormViewModelConfig getConfig() {
    return (ComandoSistemaFormViewModelConfig) super.getConfig();
  }
}
