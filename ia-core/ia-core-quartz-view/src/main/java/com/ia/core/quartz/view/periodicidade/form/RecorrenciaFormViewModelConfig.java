package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.periodicidade.dto.RecorrenciaDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

/**
 * @author Israel Araújo
 */
public class RecorrenciaFormViewModelConfig
  extends FormViewModelConfig<RecorrenciaDTO> {

  public RecorrenciaFormViewModelConfig(boolean readOnly) {
    super(readOnly);
  }
}
