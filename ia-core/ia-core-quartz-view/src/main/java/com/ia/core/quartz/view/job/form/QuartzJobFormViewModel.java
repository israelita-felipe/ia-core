package com.ia.core.quartz.view.job.form;

import com.ia.core.quartz.service.model.job.dto.QuartzJobDTO;
import com.ia.core.quartz.service.model.job.dto.QuartzJobTriggerDTO;
import com.ia.core.quartz.view.job.QuartzJobManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import lombok.Getter;

import java.util.List;

/**
 * ViewModel para o formulário de Jobs do Quartz.
 *
 * @author Israel Araújo
 */
public class QuartzJobFormViewModel
  extends FormViewModel<QuartzJobDTO> {

  @Getter
  private List<QuartzJobTriggerDTO> triggers;

  /**
   * @param config Configuração do ViewModel
   */
  public QuartzJobFormViewModel(QuartzJobFormViewModelConfig config) {
    super(config);
  }

  @Override
  public void setModel(QuartzJobDTO model) {
    super.setModel(model);
    if (model != null && getConfig() != null) {
      loadTriggers(model);
    }
  }

  private void loadTriggers(QuartzJobDTO model) {
    QuartzJobManager manager = getConfig().getQuartzJobManager();
    if (manager != null && model.getJobName() != null
        && model.getJobGroup() != null) {
      this.triggers = manager.findTriggersOfJob(model.getJobName(),
                                                model.getJobGroup());
    }
  }

  @Override
  public void setReadOnly(boolean value) {
    super.setReadOnly(value);
  }

  @Override
  public QuartzJobFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
