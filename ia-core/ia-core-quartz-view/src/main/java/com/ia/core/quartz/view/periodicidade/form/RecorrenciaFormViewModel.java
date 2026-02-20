package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.recorrencia.dto.RecorrenciaDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * @author Israel Araújo
 */
public class RecorrenciaFormViewModel
  extends FormViewModel<RecorrenciaDTO> {

  /**
   * @param config
   */
  public RecorrenciaFormViewModel(RecorrenciaFormViewModelConfig config) {
    super(config);
  }

  @Override
  public RecorrenciaFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
