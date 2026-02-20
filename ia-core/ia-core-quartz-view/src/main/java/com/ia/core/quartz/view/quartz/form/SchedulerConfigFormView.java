package com.ia.core.quartz.view.quartz.form;

import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigTranslator;
import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerTranslator;
import com.ia.core.quartz.view.periodicidade.form.PeriodicidadeFormView;
import com.ia.core.quartz.view.quartz.triggers.page.SchedulerConfigTriggerCollectionPageView;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.TabSheet;

/**
 *
 */
public class SchedulerConfigFormView
  extends FormView<SchedulerConfigDTO> {

  private TabSheet mainTabSheet;
  private FormLayout mainTabLayout;
  private FormLayout periodicidadeTabLayout;
  private FormLayout tarefasTabLayout;

  /**
   * @param viewModel
   */
  public SchedulerConfigFormView(IFormViewModel<SchedulerConfigDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    createMainTabSheet();
    bind(SchedulerConfigDTO.CAMPOS.JOB_CLASS_NAME,
         createJobClassNameField());
    bind(SchedulerConfigDTO.CAMPOS.PERIODICIDADE,
         createPeriodicidadeField(getViewModel()
             .getPeriodicidadeFormViewModel()));
    createTriggersPageView();
  }

  /**
   *
   */
  public void createMainTabSheet() {
    mainTabSheet = createTabSheet();
    createTab(mainTabSheet, VaadinIcon.PENCIL.create(),
              $(SchedulerConfigTranslator.JOB_CLASS_NAME),
              mainTabLayout = createFormLayout());
    createTab(mainTabSheet, VaadinIcon.TIMER.create(),
              $(SchedulerConfigTranslator.PERIODICIDADE),
              periodicidadeTabLayout = createFormLayout());
    createTab(mainTabSheet, VaadinIcon.TASKS.create(),
              $(SchedulerConfigTriggerTranslator.SCHEDULER_CONFIG_CLASS),
              tarefasTabLayout = createFormLayout());
    add(mainTabSheet, 6);
  }

  /**
   *
   */
  public void createTriggersPageView() {
    tarefasTabLayout
        .add(new SchedulerConfigTriggerCollectionPageView(getViewModel()
            .getSchedulerConfigTriggerCollectionPageViewModel()), 6);
  }

  public PeriodicidadeFormView createPeriodicidadeField(IFormViewModel<PeriodicidadeDTO> viewModel) {
    PeriodicidadeFormView field = new PeriodicidadeFormView(viewModel);
    periodicidadeTabLayout.add(field, 6);
    return field;
  }

  /**
   * @return
   */
  protected ComboBox<String> createJobClassNameField() {
    ComboBox<String> field = createComboBox($(SchedulerConfigTranslator.JOB_CLASS_NAME),
                                            $(SchedulerConfigTranslator.HELP.JOB_CLASS_NAME),
                                            getViewModel()
                                                .getJobClassNames(),
                                            this::$);
    mainTabLayout.add(field, 6);
    return field;
  }

  @Override
  public SchedulerConfigFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
