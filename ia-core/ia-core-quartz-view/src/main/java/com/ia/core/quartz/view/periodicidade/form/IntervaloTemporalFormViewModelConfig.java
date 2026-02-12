package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

/**
 * @author Israel Araújo
 */
public class IntervaloTemporalFormViewModelConfig
  extends FormViewModelConfig<IntervaloTemporalDTO> {

  public IntervaloTemporalFormViewModelConfig(boolean readOnly) {
    super(readOnly);
  }
}
