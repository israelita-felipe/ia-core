package com.ia.core.llm.view.template.form;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 *
 */
/**
 * Model de dados para a view de template form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class TemplateFormViewModel
  extends FormViewModel<TemplateDTO> {

  /**
   * @param readOnly
   */
  public TemplateFormViewModel(TemplateFormViewModelConfig config) {
    super(config);
  }

}
