package com.ia.core.quartz.view.quartz.triggers.page;

import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.view.quartz.QuartzManager;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class SchedulerConfigTriggerCollectionPageViewModelConfig
  extends CollectionPageViewModelConfig<SchedulerConfigTriggerDTO> {

  @Getter
  private final QuartzManager schedulerService;

  /**
   * @param schedulerService
   */
  public SchedulerConfigTriggerCollectionPageViewModelConfig(QuartzManager schedulerService) {
    super(null);
    this.schedulerService = schedulerService;
  }

}
