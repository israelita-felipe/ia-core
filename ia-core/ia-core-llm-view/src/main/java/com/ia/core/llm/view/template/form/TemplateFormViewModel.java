package com.ia.core.llm.view.template.form;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 *
 */
public class TemplateFormViewModel
  extends FormViewModel<TemplateDTO> {

  /**
   * @param readOnly
   */
  public TemplateFormViewModel(boolean readOnly) {
    super(readOnly);
  }

}
