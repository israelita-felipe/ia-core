package com.ia.core.quartz.view.periodicidade.form;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.stream.IntStream;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.quartz.service.periodicidade.dto.RecorrenciaDTO;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.textfield.TextField;

/**
 * @author Israel Araújo
 */
public class RecorrenciaFormView
  extends FormView<RecorrenciaDTO> {

  private ComboBox<Frequencia> frequencyField;
  private TextField intervalValueField;
  private CheckboxGroup<DayOfWeek> byDayField;
  private CheckboxGroup<Integer> byMonthDayField;
  private CheckboxGroup<Month> byMonthField;
  private DateTimePicker untilDateField;
  private TextField countLimitField;

  /**
   * @param viewModel
   */
  public RecorrenciaFormView(IFormViewModel<RecorrenciaDTO> viewModel) {
    super(viewModel);
  }

  public ComboBox<Frequencia> createFrequencyField(String label,
                                                   String help) {
    frequencyField = createComboBox(label, help);
    frequencyField.setItems(Frequencia.values());
    frequencyField.setItemLabelGenerator(f -> f.name());
    add(frequencyField, 6);
    return frequencyField;
  }

  public TextField createCountLimitField(String label, String help) {
    countLimitField = createInteiroTextField(label, help);
    setHelp(intervalValueField, help);
    add(countLimitField, 6);
    return countLimitField;
  }

  public CheckboxGroup<DayOfWeek> createByDayField(String label,
                                                   String help,
                                                   ItemLabelGenerator<DayOfWeek> itemLabelGenerator) {
    byDayField = createEnumCheckBox(label, help, DayOfWeek.class,
                                    itemLabelGenerator);
    add(byDayField, 6);
    return byDayField;
  }

  public CheckboxGroup<Integer> createByMonthDayField(String label,
                                                      String help) {
    byMonthDayField = new CheckboxGroup<>(label);
    setHelp(byMonthDayField, help);
    byMonthDayField
        .setItems(IntStream.range(1, 32).mapToObj(value -> value).toList());
    add(byMonthDayField, 6);
    return byMonthDayField;
  }

  public CheckboxGroup<Month> createByMonthField(String label, String help,
                                                 ItemLabelGenerator<Month> itemLabelGenerator) {
    byMonthField = createEnumCheckBox(label, help, Month.class,
                                      itemLabelGenerator);
    add(byMonthField, 6);
    return byMonthField;
  }

  public DateTimePicker createUntilDateField(String label, String help) {
    untilDateField = createDateTimePickerField(label, help);
    add(untilDateField, 3);
    return untilDateField;
  }

  public TextField createIntervalValueField(String label, String help) {
    intervalValueField = createInteiroTextField(label, help);
    add(intervalValueField, 3);
    return intervalValueField;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    add(new Hr(), 6);
    add(new NativeLabel("Frequência"), 6);
    bind("frequency",
         createFrequencyField($(PeriodicidadeTranslator.RECORRENCIA.FREQUENCY),
                              $(PeriodicidadeTranslator.RECORRENCIA.HELP.FREQUENCY)));
    bindInteger("intervalValue",
                createIntervalValueField($(PeriodicidadeTranslator.RECORRENCIA.INTERVAL_VALUE),
                                         $(PeriodicidadeTranslator.RECORRENCIA.HELP.INTERVAL_VALUE)));
    add(new Hr(), 6);
    bind("byDay",
         createByDayField($(PeriodicidadeTranslator.RECORRENCIA.BY_DAY),
                          $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_DAY),
                          dia -> $(dia.name())));
    add(new Hr(), 6);
    bind("byMonthDay",
         createByMonthDayField($(PeriodicidadeTranslator.RECORRENCIA.BY_MONTH_DAY),
                               $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_MONTH_DAY)));
    add(new Hr(), 6);
    add(new NativeLabel("Meses"), 6);
    bind("byMonth",
         createByMonthField($(PeriodicidadeTranslator.RECORRENCIA.BY_MONTH),
                            $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_MONTH),
                            mes -> $(mes.name())));
    add(new Hr(), 6);
    add(new NativeLabel("Limites"), 6);
    bind("untilDate",
         createUntilDateField($(PeriodicidadeTranslator.RECORRENCIA.UNTIL_DATE),
                              $(PeriodicidadeTranslator.RECORRENCIA.HELP.UNTIL_DATE)));
    bindInteger("countLimit",
                createCountLimitField($(PeriodicidadeTranslator.RECORRENCIA.COUNT_LIMIT),
                                      $(PeriodicidadeTranslator.RECORRENCIA.HELP.COUNT_LIMIT)));
  }

}
