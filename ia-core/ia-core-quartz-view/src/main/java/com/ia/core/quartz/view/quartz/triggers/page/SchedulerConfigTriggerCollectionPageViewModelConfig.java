package com.ia.core.quartz.view.quartz.triggers.page;

import com.ia.core.quartz.service.model.scheduler.dto.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

/**
 *
 */
public class SchedulerConfigTriggerCollectionPageViewModelConfig
  extends CollectionPageViewModelConfig<SchedulerConfigTriggerDTO> {

  /**
   * @param schedulerService
   */
  public SchedulerConfigTriggerCollectionPageViewModelConfig(DefaultCollectionBaseManager<SchedulerConfigTriggerDTO> schedulerService) {
    super(schedulerService);
  }

}
