package com.ia.core.quartz.view.quartz.page;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.view.quartz.form.SchedulerConfigFormView;
import com.ia.core.quartz.view.quartz.list.SchedulerConfigListView;
import com.ia.core.security.view.log.operation.page.EntityPageView;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * @author Israel Araújo
 */
public class SchedulerConfigPageView
  extends EntityPageView<SchedulerConfigDTO> {
  /** Serial UUID */
  private static final long serialVersionUID = -2796746737472741060L;
  /** Rota */
  public static final String ROUTE = "scheduler";

  /**
   * @param viewModel
   */
  public SchedulerConfigPageView(IPageViewModel<SchedulerConfigDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormView<SchedulerConfigDTO> createFormView(IFormViewModel<SchedulerConfigDTO> formViewModel) {
    return new SchedulerConfigFormView(formViewModel);
  }

  @Override
  public IListView<SchedulerConfigDTO> createListView(IListViewModel<SchedulerConfigDTO> listViewModel) {
    return new SchedulerConfigListView(listViewModel);
  }

}
