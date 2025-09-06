package com.ia.core.llm.view.template.form;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

/**
 *
 */
public class TemplateFormViewModel
  extends FormViewModel<TemplateDTO> {

  /**
   * @param readOnly
   */
  public TemplateFormViewModel(FormViewModelConfig<TemplateDTO> config) {
    super(config);
  }

}
