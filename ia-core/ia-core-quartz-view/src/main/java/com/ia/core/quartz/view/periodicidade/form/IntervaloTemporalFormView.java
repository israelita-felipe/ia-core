package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;

/**
 * @author Israel Araújo
 */
public class IntervaloTemporalFormView
  extends FormView<IntervaloTemporalDTO> {

  private DateTimePicker startTimeField;
  private DateTimePicker endTimeField;

  /**
   * @param viewModel
   */
  public IntervaloTemporalFormView(IFormViewModel<IntervaloTemporalDTO> viewModel) {
    super(viewModel);
  }

  public DateTimePicker createStartTimeField(String label, String help) {
    startTimeField = createDateTimePickerField(label, help);
    add(startTimeField, 6);
    return startTimeField;
  }

  public DateTimePicker createEndTimeField(String label, String help) {
    endTimeField = createDateTimePickerField(label, help);
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
