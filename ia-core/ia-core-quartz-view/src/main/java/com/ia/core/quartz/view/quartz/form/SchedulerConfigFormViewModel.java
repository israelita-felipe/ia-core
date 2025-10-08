package com.ia.core.quartz.view.quartz.form;

import java.util.Collection;
import java.util.UUID;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.view.periodicidade.form.PeriodicidadeFormViewModel;
import com.ia.core.quartz.view.quartz.triggers.page.SchedulerConfigTriggerCollectionPageViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.service.collection.DefaultCollectionBaseService;
import com.ia.core.view.utils.ServiceFactory;

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
    schedulerConfigTriggerCollectionPageViewModel = new SchedulerConfigTriggerCollectionPageViewModel(new CollectionPageViewModelConfig<>(createSchedulerConfigTriggerService(config)));
  }

  /**
   * @param config
   * @return
   */
  private DefaultCollectionBaseService<SchedulerConfigTriggerDTO> createSchedulerConfigTriggerService(SchedulerConfigFormViewModelConfig config) {
    return ServiceFactory
        .createServiceFromCollection(this::getTriggers, trigger -> UUID
            .nameUUIDFromBytes(trigger.getTriggerName().getBytes()));
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
