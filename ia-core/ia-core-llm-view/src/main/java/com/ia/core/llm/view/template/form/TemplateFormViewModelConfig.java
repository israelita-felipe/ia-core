package com.ia.core.llm.view.template.form;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.view.template.TemplateManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

/**
 *
 */
/**
 * Classe de configuração para template form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class TemplateFormViewModelConfig
  extends FormViewModelConfig<TemplateDTO> {

  @Getter
  private final TemplateManager templateManager;

  /**
   * @param readOnly
   */
  public TemplateFormViewModelConfig(boolean readOnly,
                                     TemplateManager templateManager) {
    super(readOnly);
    this.templateManager = templateManager;
  }

}
