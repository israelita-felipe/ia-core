package com.ia.core.quartz.view.quartz.page;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.view.quartz.form.SchedulerConfigFormViewModel;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;

/**
 * @author Israel Araújo
 */
public class SchedulerConfigPageViewModel
  extends EntityPageViewModel<SchedulerConfigDTO> {

  /**
   * @param service
   */
  public SchedulerConfigPageViewModel(SchedulerConfigPageViewModelConfig config) {
    super(config);
  }

  @Override
  public SchedulerConfigDTO createNewObject() {
    return SchedulerConfigDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return SchedulerConfigDTO.getSearchRequest();
  }

  @Override
  public Class<SchedulerConfigDTO> getType() {
    return SchedulerConfigDTO.class;
  }

  @Override
  public IFormViewModel<SchedulerConfigDTO> createFormViewModel(FormViewModelConfig<SchedulerConfigDTO> config) {
    return new SchedulerConfigFormViewModel(config.cast());
  }
}
