package com.ia.core.quartz.view.quartz.triggers.list;

import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 *
 */
public class SchedulerConfigTriggerListView
  extends ListView<SchedulerConfigTriggerDTO> {

  /**
   * @param viewModel
   */
  public SchedulerConfigTriggerListView(IListViewModel<SchedulerConfigTriggerDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(SchedulerConfigTriggerDTO.CAMPOS.TRIGGER_NAME);
    addColumn(SchedulerConfigTriggerDTO.CAMPOS.TRIGGER_STATE);
  }
}
