package com.ia.core.quartz.view.quartz.triggers.list;

import com.ia.core.quartz.service.model.scheduler.dto.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 *
 */
/**
 * View para exibição e manipulação de scheduler config trigger list.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SchedulerConfigTriggerListView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
