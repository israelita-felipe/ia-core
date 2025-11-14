package com.ia.core.llm.view.comando.form;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.view.template.TemplateManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class ComandoSistemaFormViewModelConfig
  extends FormViewModelConfig<ComandoSistemaDTO> {
  @Getter
  private final TemplateManager templateService;

  /**
   * @param readOnly
   * @param templateService
   */
  public ComandoSistemaFormViewModelConfig(boolean readOnly,
                                           TemplateManager templateService) {
    super(readOnly);
    this.templateService = templateService;
  }

}
