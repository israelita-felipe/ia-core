package com.ia.core.quartz.view.quartz.page;

import com.ia.core.quartz.service.model.scheduler.dto.SchedulerConfigDTO;
import com.ia.core.quartz.view.quartz.QuartzManager;
import com.ia.core.quartz.view.quartz.form.SchedulerConfigFormViewModelConfig;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

/**
 * Classe que representa as configurações para scheduler config page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SchedulerConfigPageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@UIScope
@Component
public class SchedulerConfigPageViewModelConfig
  extends EntityPageViewModelConfig<SchedulerConfigDTO> {

  private final QuartzManager schedulerService;

  /**
   * @param service
   * @param logOperationService
   */

public SchedulerConfigPageViewModelConfig(DefaultBaseManager<SchedulerConfigDTO> service,
                                            QuartzManager schedulerService,
                                            LogOperationManager logOperationService) {
    super(service, logOperationService);
    this.schedulerService = schedulerService;
  }

  @Override
  protected FormViewModelConfig<SchedulerConfigDTO> createFormViewModelConfig(boolean readOnly) {
    return new SchedulerConfigFormViewModelConfig(readOnly,
                                                  schedulerService);
  }
}
