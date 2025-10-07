package com.ia.core.quartz.view.quartz.form;

import java.util.Collection;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.view.periodicidade.form.PeriodicidadeFormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class SchedulerConfigFormViewModel
  extends FormViewModel<SchedulerConfigDTO> {
  @Getter
  private PeriodicidadeFormViewModel periodicidadeFormViewModel;

  /**
   * @param config
   */
  public SchedulerConfigFormViewModel(SchedulerConfigFormViewModelConfig config) {
    super(config);
    createPeriodicidadeFormViewModel(config);
  }

  @Override
  public void setModel(SchedulerConfigDTO model) {
    super.setModel(model);
    if (model != null) {
      if (this.periodicidadeFormViewModel != null) {
        this.periodicidadeFormViewModel.setModel(model.getPeriodicidade());
      }
    }
  }

  @Override
  public void setReadOnly(boolean value) {
    super.setReadOnly(value);
    if (periodicidadeFormViewModel != null) {
      this.periodicidadeFormViewModel.setReadOnly(value);
    }
  }

  /**
   * @param readOnly
   */
  protected void createPeriodicidadeFormViewModel(SchedulerConfigFormViewModelConfig config) {
    this.periodicidadeFormViewModel = new PeriodicidadeFormViewModel(new FormViewModelConfig<>(config
        .isReadOnly()));
  }

  public Collection<String> getJobClassNames() {
    return getConfig().getSchedulerService().getAvaliableJobClasses()
        .keySet();
  }

  @Override
  public SchedulerConfigFormViewModelConfig getConfig() {
    return super.getConfig().cast();
  }
}
