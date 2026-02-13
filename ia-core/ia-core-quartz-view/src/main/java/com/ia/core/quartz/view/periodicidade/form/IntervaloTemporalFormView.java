package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.timepicker.TimePicker;

/**
 * @author Israel Araújo
 */
public class IntervaloTemporalFormView
  extends FormView<IntervaloTemporalDTO> {

  private TimePicker startTimeField;
  private TimePicker endTimeField;

  /**
   * @param viewModel
   */
  public IntervaloTemporalFormView(IFormViewModel<IntervaloTemporalDTO> viewModel) {
    super(viewModel);
  }

  public TimePicker createStartTimeField(String label, String help) {
    startTimeField = createTimeField(label, help);
    add(startTimeField, 6);
    return startTimeField;
  }

  public TimePicker createEndTimeField(String label, String help) {
    endTimeField = createTimeField(label, help);
    add(endTimeField, 6);
    return endTimeField;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("startTime",
         createStartTimeField($(PeriodicidadeTranslator.INTERVALO_TEMPORAL.START_TIME),
                              $(PeriodicidadeTranslator.INTERVALO_TEMPORAL.HELP.START_TIME)));
    bind("endTime",
         createEndTimeField($(PeriodicidadeTranslator.INTERVALO_TEMPORAL.END_TIME),
                            $(PeriodicidadeTranslator.INTERVALO_TEMPORAL.HELP.END_TIME)));
  }
}
