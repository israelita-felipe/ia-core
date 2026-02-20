package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;

/**
 * @author Israel Araújo
 */
public class IntervaloTemporalFormViewModel
  extends FormViewModel<IntervaloTemporalDTO> {

  /**
   * @param config
   */
  public IntervaloTemporalFormViewModel(IntervaloTemporalFormViewModelConfig config) {
    super(config);
  }

  @Override
  public IntervaloTemporalFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
