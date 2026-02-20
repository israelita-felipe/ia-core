package com.ia.core.quartz.view.periodicidade.form;

import com.ia.core.quartz.service.model.periodicidade.dto.IntervaloTemporalDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;

/**
 * @author Israel Araújo
 */
public class IntervaloTemporalFormView
  extends FormView<IntervaloTemporalDTO> {

  private DatePicker startDateField;
  private TimePicker startTimeField;
  private DatePicker endDateField;
  private TimePicker endTimeField;

  /**
   * @param viewModel
   */
  public IntervaloTemporalFormView(IFormViewModel<IntervaloTemporalDTO> viewModel) {
    super(viewModel);
  }

  public DatePicker createStartDateField(String label, String help) {
    startDateField = createDateField(label, help);
    add(startDateField, 3);
    return startDateField;
  }

  public TimePicker createStartTimeField(String label, String help) {
    startTimeField = createTimeField(label, help);
    add(startTimeField, 3);
    return startTimeField;
  }

  public DatePicker createEndDateField(String label, String help) {
    endDateField = createDateField(label, help);
    add(endDateField, 3);
    return endDateField;
  }

  public TimePicker createEndTimeField(String label, String help) {
    endTimeField = createTimeField(label, help);
    add(endTimeField, 3);
    return endTimeField;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("startDate",
         createStartDateField($(PeriodicidadeTranslator.INTERVALO_TEMPORAL.START_DATE),
                              $(PeriodicidadeTranslator.INTERVALO_TEMPORAL.HELP.START_DATE)));
    bind("startTime",
         createStartTimeField($(PeriodicidadeTranslator.INTERVALO_TEMPORAL.START_TIME),
                              $(PeriodicidadeTranslator.INTERVALO_TEMPORAL.HELP.START_TIME)));
    bind("endDate",
         createEndDateField($(PeriodicidadeTranslator.INTERVALO_TEMPORAL.END_DATE),
                            $(PeriodicidadeTranslator.INTERVALO_TEMPORAL.HELP.END_DATE)));
    bind("endTime",
         createEndTimeField($(PeriodicidadeTranslator.INTERVALO_TEMPORAL.END_TIME),
                            $(PeriodicidadeTranslator.INTERVALO_TEMPORAL.HELP.END_TIME)));
  }
}
