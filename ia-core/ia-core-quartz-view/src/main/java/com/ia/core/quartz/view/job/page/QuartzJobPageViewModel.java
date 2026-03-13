package com.ia.core.quartz.view.job.page;

import java.io.Serializable;

import com.ia.core.quartz.service.model.job.QuartzJobDTO;
import com.ia.core.quartz.view.job.form.QuartzJobFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.PageViewModel;
import com.ia.core.view.components.page.viewModel.PageViewModelConfig;

/**
 * ViewModel para a página de Jobs do Quartz.
 * 
 * @author Israel Araújo
 */
public class QuartzJobPageViewModel
  extends PageViewModel<QuartzJobDTO> {

  /**
   * @param config Configuração do ViewModel
   */
  public QuartzJobPageViewModel(QuartzJobPageViewModelConfig config) {
    super(config);
  }

  @Override
  public QuartzJobDTO createNewObject() {
    return QuartzJobDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return QuartzJobDTO.getSearchRequest();
  }

  @Override
  public Class<QuartzJobDTO> getType() {
    return QuartzJobDTO.class;
  }

  @Override
  public Long getId(QuartzJobDTO object) {
    // QuartzJob não tem ID tradicional, usa jobName + jobGroup
    if (object == null) {
      return null;
    }
    return (long) (object.getJobName() + object.getJobGroup()).hashCode();
  }

  @Override
  public IFormViewModel<QuartzJobDTO> createFormViewModel(FormViewModelConfig<QuartzJobDTO> config) {
    return new QuartzJobFormViewModel(config.cast());
  }

  @Override
  public QuartzJobDTO cloneObject(QuartzJobDTO object) {
    return object != null ? object.cloneObject() : null;
  }

  @Override
  public QuartzJobDTO copyObject(QuartzJobDTO object) {
    return object != null ? object.copyObject() : null;
  }
}
