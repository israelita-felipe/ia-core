package com.ia.core.quartz.view.quartz.triggers.page;

import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.view.quartz.triggers.form.SchedulerConfigTriggerFormView;
import com.ia.core.quartz.view.quartz.triggers.list.SchedulerConfigTriggerListView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.CollectionPageView;
import com.ia.core.view.components.page.viewModel.ICollectionPageViewModel;

/**
 *
 */
public class SchedulerConfigTriggerCollectionPageView
  extends CollectionPageView<SchedulerConfigTriggerDTO> {

  /**
   * @param viewModel
   */
  public SchedulerConfigTriggerCollectionPageView(ICollectionPageViewModel<SchedulerConfigTriggerDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<SchedulerConfigTriggerDTO> createFormView(IFormViewModel<SchedulerConfigTriggerDTO> formViewModel) {
    return new SchedulerConfigTriggerFormView(formViewModel);
  }

  @Override
  public IListView<SchedulerConfigTriggerDTO> createListView(IListViewModel<SchedulerConfigTriggerDTO> listViewModel) {
    return new SchedulerConfigTriggerListView(listViewModel);
  }

}
