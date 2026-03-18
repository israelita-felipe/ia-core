package com.ia.core.quartz.view.job.list;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.view.components.list.ListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;

/**
 * Lista de Jobs do Quartz.
 *
 * @author Israel Araújo
 */
public class QuartzJobListView
  extends ListView<QuartzJobDTO> {

  /**
   * Construtor com o ViewModel.
   *
   * @param viewModel ViewModel da lista
   */
  public QuartzJobListView(IListViewModel<QuartzJobDTO> viewModel) {
    super(viewModel);
  }

  @Override
  protected void createColumns() {
    super.createColumns();
    addColumn(QuartzJobDTO.CAMPOS.JOB_NAME);
    addColumn(QuartzJobDTO.CAMPOS.JOB_GROUP);
    addColumn(QuartzJobDTO.CAMPOS.JOB_CLASS_NAME);
    addColumn(QuartzJobDTO.CAMPOS.JOB_STATE);
  }
}
