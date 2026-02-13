package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.view.quartz.QuartzManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

/**
 * @author Israel Araújo
 */
public class PeriodicidadeFormViewModelConfig
  extends FormViewModelConfig<PeriodicidadeDTO> {

  private final QuartzManager schedulerService;

  public PeriodicidadeFormViewModelConfig(boolean readOnly,
                                          QuartzManager schedulerService) {
    super(readOnly);
    this.schedulerService = schedulerService;
  }

  public QuartzManager getSchedulerService() {
    return schedulerService;
  }
}
