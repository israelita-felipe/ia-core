package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.quartz.view.periodicidade.form.PeriodicidadeFormViewModelConfig;

/**
 * @author Israel Araújo
 */
public class PeriodicidadeFormViewModel
  extends FormViewModel<PeriodicidadeDTO> {

  /**
   * @param readOnly
   */
  public PeriodicidadeFormViewModel(PeriodicidadeFormViewModelConfig config) {
    super(config);
  }

}
