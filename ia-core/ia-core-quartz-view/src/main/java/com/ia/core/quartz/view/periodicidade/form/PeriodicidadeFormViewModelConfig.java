package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.view.quartz.QuartzManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class PeriodicidadeFormViewModelConfig
  extends FormViewModelConfig<PeriodicidadeDTO> {

  @Getter
  private final QuartzManager schedulerService;

  /**
   * @param readOnly
   */
  public PeriodicidadeFormViewModelConfig(boolean readOnly,
                                          QuartzManager schedulerService) {
    super(readOnly);
    this.schedulerService = schedulerService;
  }

}
