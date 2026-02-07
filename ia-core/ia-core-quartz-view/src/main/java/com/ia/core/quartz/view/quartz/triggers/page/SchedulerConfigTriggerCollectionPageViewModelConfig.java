package com.ia.core.quartz.view.quartz.triggers.page;

import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.view.quartz.QuartzManager;

import lombok.Getter;

/**
 *
 */
public class SchedulerConfigTriggerCollectionPageViewModelConfig {

  @Getter
  private final QuartzManager schedulerService;

  /**
   * @param schedulerService
   */
  public SchedulerConfigTriggerCollectionPageViewModelConfig(QuartzManager schedulerService) {
    this.schedulerService = schedulerService;
  }

}
