package com.ia.core.quartz.view.quartz.form;

import com.ia.core.quartz.service.model.scheduler.SchedulerConfigDTO;
import com.ia.core.quartz.service.model.scheduler.SchedulerConfigTranslator;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.view.periodicidade.form.PeriodicidadeFormView;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;

/**
 *
 */
public class SchedulerConfigFormView
  extends FormView<SchedulerConfigDTO> {

  /**
   * @param viewModel
   */
  public SchedulerConfigFormView(IFormViewModel<SchedulerConfigDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind(SchedulerConfigDTO.CAMPOS.JOB_CLASS_NAME,
         createJobClassNameField());
    bind(SchedulerConfigDTO.CAMPOS.PERIODICIDADE,
         createPeriodicidadeField(getViewModel()
             .getPeriodicidadeFormViewModel()));
  }

  public PeriodicidadeFormView createPeriodicidadeField(IFormViewModel<PeriodicidadeDTO> viewModel) {
    PeriodicidadeFormView field = new PeriodicidadeFormView(viewModel);
    add(field, 6);
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
    add(field, 6);
    return field;
  }

  /**
   * @return
   */
  protected TextField createPeriodicidadeField() {
    TextField field = createTextField($(SchedulerConfigTranslator.PERIODICIDADE),
                                      $(SchedulerConfigTranslator.HELP.PERIODICIDADE));
    add(field, 6);
    return field;
  }

  @Override
  public SchedulerConfigFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
