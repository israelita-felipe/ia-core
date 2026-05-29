package com.ia.core.llm.view.prompt.form;

import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.view.template.TemplateManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

/**
 *
 */
/**
 * Classe de configuração para comando sistema form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class PromptFormViewModelConfig
  extends FormViewModelConfig<PromptDTO> {
  @Getter
  private final TemplateManager templateService;

  /**
   * @param readOnly
   * @param templateService
   */
  public PromptFormViewModelConfig(boolean readOnly,
                                           TemplateManager templateService) {
    super(readOnly);
    this.templateService = templateService;
  }

}
