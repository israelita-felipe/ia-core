package com.ia.core.quartz.view.quartz.triggers.form;

import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerDTO;
import com.ia.core.quartz.service.model.scheduler.triggers.SchedulerConfigTriggerTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.textfield.TextField;

/**
 *
 */
public class SchedulerConfigTriggerFormView
  extends FormView<SchedulerConfigTriggerDTO> {

  /**
   * @param viewModel
   */
  public SchedulerConfigTriggerFormView(IFormViewModel<SchedulerConfigTriggerDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind(SchedulerConfigTriggerDTO.CAMPOS.TRIGGER_NAME,
         createAndAddTextField(SchedulerConfigTriggerTranslator.TRIGGER_NAME,
                               SchedulerConfigTriggerTranslator.HELP.TRIGGER_NAME));
    bind(SchedulerConfigTriggerDTO.CAMPOS.SCHEDULER_NAME,
         createAndAddTextField(SchedulerConfigTriggerTranslator.SCHEDULER_NAME,
                               SchedulerConfigTriggerTranslator.HELP.SCHEDULER_NAME));
    bind(SchedulerConfigTriggerDTO.CAMPOS.TRIGGER_GROUP,
         createAndAddTextField(SchedulerConfigTriggerTranslator.TRIGGER_GROUP,
                               SchedulerConfigTriggerTranslator.HELP.TRIGGER_GROUP));
    bind(SchedulerConfigTriggerDTO.CAMPOS.JOB_NAME,
         createAndAddTextField(SchedulerConfigTriggerTranslator.JOB_NAME,
                               SchedulerConfigTriggerTranslator.HELP.JOB_NAME));
    bind(SchedulerConfigTriggerDTO.CAMPOS.JOB_GROUP,
         createAndAddTextField(SchedulerConfigTriggerTranslator.JOB_GROUP,
                               SchedulerConfigTriggerTranslator.HELP.JOB_GROUP));
    bind(SchedulerConfigTriggerDTO.CAMPOS.DESCRIPTION,
         createAndAddTextField(SchedulerConfigTriggerTranslator.DESCRIPTION,
                               SchedulerConfigTriggerTranslator.HELP.DESCRIPTION));
    bind(SchedulerConfigTriggerDTO.CAMPOS.NEXT_FIRE_TIME,
         createAndAddDateTimeField(SchedulerConfigTriggerTranslator.NEXT_FIRE_TIME,
                                   SchedulerConfigTriggerTranslator.HELP.NEXT_FIRE_TIME));
    bind(SchedulerConfigTriggerDTO.CAMPOS.PREV_FIRE_TIME,
         createAndAddDateTimeField(SchedulerConfigTriggerTranslator.PREV_FIRE_TIME,
                                   SchedulerConfigTriggerTranslator.HELP.PREV_FIRE_TIME));
    bind(SchedulerConfigTriggerDTO.CAMPOS.START_TIME,
         createAndAddDateTimeField(SchedulerConfigTriggerTranslator.START_TIME,
                                   SchedulerConfigTriggerTranslator.HELP.START_TIME));
    bind(SchedulerConfigTriggerDTO.CAMPOS.END_TIME,
         createAndAddDateTimeField(SchedulerConfigTriggerTranslator.END_TIME,
                                   SchedulerConfigTriggerTranslator.HELP.END_TIME));
    bindLong(SchedulerConfigTriggerDTO.CAMPOS.PRIORITY,
             createAndAddNumberField(SchedulerConfigTriggerTranslator.PRIORITY,
                                     SchedulerConfigTriggerTranslator.HELP.PRIORITY));
    bind(SchedulerConfigTriggerDTO.CAMPOS.TRIGGER_STATE,
         createAndAddTextField(SchedulerConfigTriggerTranslator.TRIGGER_STATE,
                               SchedulerConfigTriggerTranslator.HELP.TRIGGER_STATE));
    bind(SchedulerConfigTriggerDTO.CAMPOS.TRIGGER_TYPE,
         createAndAddTextField(SchedulerConfigTriggerTranslator.TRIGGER_TYPE,
                               SchedulerConfigTriggerTranslator.HELP.TRIGGER_TYPE));
    bind(SchedulerConfigTriggerDTO.CAMPOS.CALENDAR_NAME,
         createAndAddTextField(SchedulerConfigTriggerTranslator.CALENDAR_NAME,
                               SchedulerConfigTriggerTranslator.HELP.CALENDAR_NAME));
    bindLong(SchedulerConfigTriggerDTO.CAMPOS.MISFIRE_INSTR,
             createAndAddNumberField(SchedulerConfigTriggerTranslator.MISFIRE_INSTR,
                                     SchedulerConfigTriggerTranslator.HELP.MISFIRE_INSTR));
  }

  /**
   * @return
   */
  public TextField createAndAddNumberField(String label, String help) {
    TextField field = createNumeroTextField($(label), $(help));
    add(field, 3);
    return field;
  }

  /**
   * @return
   */
  public DateTimePicker createAndAddDateTimeField(String label,
                                                  String help) {
    DateTimePicker field = createDateTimePickerField($(label), $(help));
    add(field, 3);
    return field;
  }

  /**
   * @return
   */
  public TextField createAndAddTextField(String label, String help) {
    TextField field = createTextField($(label), $(help));
    add(field, 3);
    return field;
  }

}
