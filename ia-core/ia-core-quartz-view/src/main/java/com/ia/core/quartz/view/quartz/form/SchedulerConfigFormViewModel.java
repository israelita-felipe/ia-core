package com.ia.core.quartz.view.quartz.form;

import java.util.Collection;

import com.ia.core.model.TSID;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.view.periodicidade.form.PeriodicidadeFormViewModel;
import com.ia.core.quartz.view.quartz.triggers.page.SchedulerConfigTriggerCollectionPageViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.quartz.view.periodicidade.form.PeriodicidadeFormViewModelConfig;
import com.ia.core.quartz.view.quartz.triggers.page.SchedulerConfigTriggerCollectionPageViewModelConfig;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;
import com.ia.core.view.utils.ManagerFactory;

import lombok.Getter;

/**
 *
 */
public class SchedulerConfigFormViewModel
  extends FormViewModel<SchedulerConfigDTO> {
  @Getter
  private PeriodicidadeFormViewModel periodicidadeFormViewModel;
  @Getter
  private SchedulerConfigTriggerCollectionPageViewModel schedulerConfigTriggerCollectionPageViewModel;

  /**
   * @param config
   */
  public SchedulerConfigFormViewModel(SchedulerConfigFormViewModelConfig config) {
    super(config);
    createPeriodicidadeFormViewModel(config);
    createSchedulerConfigTriggerCollecitonPageViewModel(config);
  }

  /**
   * @param config
   */
  public void createSchedulerConfigTriggerCollecitonPageViewModel(SchedulerConfigFormViewModelConfig config) {
    schedulerConfigTriggerCollectionPageViewModel = new SchedulerConfigTriggerCollectionPageViewModel(new SchedulerConfigTriggerCollectionPageViewModelConfig(config.getSchedulerService()));
  }

  /**
   * @param config
   * @return
   */
  private DefaultCollectionBaseManager<SchedulerConfigTriggerDTO> createSchedulerConfigTriggerService(SchedulerConfigFormViewModelConfig config) {
    return ManagerFactory
        .createManagerFromCollection(this::getTriggers, trigger -> TSID
            .from(trigger.getTriggerName()).toLong());
  }

  private Collection<SchedulerConfigTriggerDTO> getTriggers() {
    return getModel().getTriggers();
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
    if (this.schedulerConfigTriggerCollectionPageViewModel != null) {
      this.schedulerConfigTriggerCollectionPageViewModel.setReadOnly(true);
    }
  }

  /**
   * @param readOnly
   */
  protected void createPeriodicidadeFormViewModel(SchedulerConfigFormViewModelConfig config) {
    this.periodicidadeFormViewModel = new PeriodicidadeFormViewModel(new PeriodicidadeFormViewModelConfig(config.isReadOnly(), config.getSchedulerService()));
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
