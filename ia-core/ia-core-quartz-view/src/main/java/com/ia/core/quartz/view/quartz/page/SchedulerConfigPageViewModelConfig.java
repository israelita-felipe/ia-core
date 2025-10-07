package com.ia.core.quartz.view.quartz.page;

import org.springframework.stereotype.Component;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.view.quartz.QuartzService;
import com.ia.core.quartz.view.quartz.form.SchedulerConfigFormViewModelConfig;
import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.service.DefaultBaseService;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 *
 */
@UIScope
@Component
public class SchedulerConfigPageViewModelConfig
  extends EntityPageViewModelConfig<SchedulerConfigDTO> {

  private final QuartzService schedulerService;

  /**
   * @param service
   * @param logOperationService
   */
  public SchedulerConfigPageViewModelConfig(DefaultBaseService<SchedulerConfigDTO> service,
                                            QuartzService schedulerService,
                                            LogOperationService logOperationService) {
    super(service, logOperationService);
    this.schedulerService = schedulerService;
  }

  @Override
  protected FormViewModelConfig<SchedulerConfigDTO> createFormViewModelConfig(boolean readOnly) {
    return new SchedulerConfigFormViewModelConfig(readOnly,
                                                  schedulerService);
  }
}
