package com.ia.core.quartz.view.quartz.triggers.page;

import com.ia.core.quartz.service.model.scheduler.dto.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

/**
 *
 */
/**
 * Classe de configuração para scheduler config trigger collection page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SchedulerConfigTriggerCollectionPageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
