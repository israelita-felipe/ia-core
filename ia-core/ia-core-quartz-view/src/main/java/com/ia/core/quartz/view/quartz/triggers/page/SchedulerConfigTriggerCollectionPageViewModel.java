package com.ia.core.quartz.view.quartz.triggers.page;

import java.util.UUID;

import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModel;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;

/**
 *
 */
public class SchedulerConfigTriggerCollectionPageViewModel
  extends CollectionPageViewModel<SchedulerConfigTriggerDTO> {

  /**
   * @param config
   */
  public SchedulerConfigTriggerCollectionPageViewModel(CollectionPageViewModelConfig<SchedulerConfigTriggerDTO> config) {
    super(config);
  }

  @Override
  public SchedulerConfigTriggerDTO cloneObject(SchedulerConfigTriggerDTO object) {
    return object.cloneObject();
  }

  @Override
  public Class<SchedulerConfigTriggerDTO> getType() {
    return SchedulerConfigTriggerDTO.class;
  }

  @Override
  public SchedulerConfigTriggerDTO createNewObject() {
    return SchedulerConfigTriggerDTO.builder().build();
  }

  @Override
  public SchedulerConfigTriggerDTO copyObject(SchedulerConfigTriggerDTO object) {
    return object.copyObject();
  }

  @Override
  public IFormViewModel<SchedulerConfigTriggerDTO> createFormViewModel(FormViewModelConfig<SchedulerConfigTriggerDTO> config) {
    return new FormViewModel<SchedulerConfigTriggerDTO>(config) {
    };
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return SchedulerConfigTriggerDTO.getSearchRequest();
  }

  @Override
  public UUID getId(SchedulerConfigTriggerDTO object) {
    return UUID.fromString(object.getTriggerName());
  }

}
