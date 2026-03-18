package com.ia.core.quartz.view.job.page;

import org.springframework.stereotype.Component;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.quartz.view.job.QuartzJobManager;
import com.ia.core.quartz.view.job.form.QuartzJobFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;
import com.ia.core.view.manager.DefaultBaseManager;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * Configuração do ViewModel para a página de Jobs do Quartz.
 *
 * @author Israel Araújo
 */
@UIScope
@Component
public class QuartzJobPageViewModelConfig
  extends PageViewModelConfig<QuartzJobDTO> {

  private final QuartzJobManager quartzJobManager;

  /**
   * @param service          Serviço base
   * @param quartzJobManager Manager de Jobs do Quartz
   */
  public QuartzJobPageViewModelConfig(DefaultBaseManager<QuartzJobDTO> service,
                                      QuartzJobManager quartzJobManager) {
    super(service);
    this.quartzJobManager = quartzJobManager;
  }

  @Override
  protected FormViewModelConfig<QuartzJobDTO> createFormViewModelConfig(boolean readOnly) {
    return new QuartzJobFormViewModelConfig(readOnly, quartzJobManager);
  }

  public QuartzJobManager getQuartzJobManager() {
    return quartzJobManager;
  }
}
