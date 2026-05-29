package com.ia.core.llm.view.prompt.form;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
/**
 * Model de dados para a view de comando sistema form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptFormViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PromptFormViewModel
  extends FormViewModel<PromptDTO> {

  /**
   * @param readOnly
   */
  public PromptFormViewModel(PromptFormViewModelConfig config) {
    super(config);
  }

  @Override
  public PromptFormViewModelConfig getConfig() {
    return (PromptFormViewModelConfig) super.getConfig();
  }
}
