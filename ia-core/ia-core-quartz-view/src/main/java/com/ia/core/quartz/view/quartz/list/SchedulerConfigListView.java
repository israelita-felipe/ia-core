package com.ia.core.quartz.view.quartz.list;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigTranslator;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeFormatter;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 *
 */
public class SchedulerConfigListView
  extends ListView<SchedulerConfigDTO> {

  /**
   * @param viewModel
   */
  public SchedulerConfigListView(IListViewModel<SchedulerConfigDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(SchedulerConfigDTO.CAMPOS.JOB_CLASS_NAME);
    addColumn(scheduler -> PeriodicidadeFormatter
        .format(scheduler.getPeriodicidade(), this.getTranslator()))
            .setHeader($(SchedulerConfigTranslator.PERIODICIDADE));
    addColumn(scheduler -> PeriodicidadeFormatter
        .asCronExpression(scheduler.getPeriodicidade()))
            .setHeader($(SchedulerConfigTranslator.PERIODICIDADE));
  }
}
