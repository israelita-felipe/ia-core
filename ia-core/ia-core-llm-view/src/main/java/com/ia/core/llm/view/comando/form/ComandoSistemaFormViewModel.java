package com.ia.core.llm.view.comando.form;

import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.view.template.TemplateService;
import com.ia.core.view.components.form.viewModel.FormViewModel;

import lombok.Getter;

/**
 * @author Israel Araújo
 */
public class ComandoSistemaFormViewModel
  extends FormViewModel<ComandoSistemaDTO> {
  @Getter
  private TemplateService templateService;

  /**
   * @param readOnly
   */
  public ComandoSistemaFormViewModel(boolean readOnly,
                                     TemplateService templateService) {
    super(readOnly);
    this.templateService = templateService;
  }

}
