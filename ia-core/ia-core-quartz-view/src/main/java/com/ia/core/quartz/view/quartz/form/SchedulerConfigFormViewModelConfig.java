package com.ia.core.quartz.view.quartz.form;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.view.quartz.QuartzService;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class SchedulerConfigFormViewModelConfig
  extends FormViewModelConfig<SchedulerConfigDTO> {

  @Getter
  private final QuartzService schedulerService;

  /**
   * @param readOnly
   */
  public SchedulerConfigFormViewModelConfig(boolean readOnly,
                                            QuartzService quartzService) {
    super(readOnly);
    this.schedulerService = quartzService;
  }

}
