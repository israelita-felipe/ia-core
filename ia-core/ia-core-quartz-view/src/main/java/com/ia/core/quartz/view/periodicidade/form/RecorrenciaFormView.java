package com.ia.core.quartz.view.periodicidade.form;

import java.time.DayOfWeek;
import java.time.Month;

import com.ia.core.quartz.model.periodicidade.Frequencia;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.quartz.service.model.recorrencia.dto.RecorrenciaDTO;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.textfield.TextField;

/**
 * @author Israel Araújo
 */
public class RecorrenciaFormView
  extends FormView<RecorrenciaDTO> {

  private FormLayout mainLayout;

  // Campos para visibilidade
  private ComboBox<Frequencia> frequencyField;
  private TextField intervalValueField;
  private CheckboxGroup<DayOfWeek> byDayField;
  private IntegerSetView byMonthDayField;
  private CheckboxGroup<Month> byMonthField;
  private IntegerSetView bySetPositionField;
  private ComboBox<DayOfWeek> weekStartDayField;
  private IntegerSetView byYearDayField;
  private IntegerSetView byWeekNoField;
  private IntegerSetView byHourField;
  private IntegerSetView byMinuteField;
  private IntegerSetView bySecondField;
  private DatePicker untilDateField;
  private TextField countLimitField;

  /**
   * @param viewModel
   */
  public RecorrenciaFormView(IFormViewModel<RecorrenciaDTO> viewModel) {
    super(viewModel);
  }

  // ========================
  // Métodos de criação de campos - públicos para uso externo
  // ========================

  protected ComboBox<Frequencia> createFrequencyField(String label,
                                                      String help) {
    ComboBox<Frequencia> field = createComboBox(label, help);
    field.setItems(Frequencia.values());
    field.setItemLabelGenerator(f -> f.name());
    return field;
  }

  protected TextField createIntervalValueField(String label, String help) {
    return createInteiroTextField(label, help);
  }

  protected CheckboxGroup<DayOfWeek> createByDayField(String label,
                                                      String help) {
    return createEnumCheckBox(label, help, DayOfWeek.class,
                              dia -> $(dia.name()));
  }

  protected IntegerSetView createByMonthDayField(String label,
                                                 String help) {
    IntegerSetView field = new IntegerSetView();
    field.setLabel(label);
    setHelp(field, help);
    field.setRange(1, 31);
    return field;
  }

  protected CheckboxGroup<Month> createByMonthField(String label,
                                                    String help) {
    return createEnumCheckBox(label, help, Month.class,
                              mes -> $(mes.name()));
  }

  protected IntegerSetView createBySetPositionField(String label,
                                                    String help) {
    IntegerSetView field = new IntegerSetView();
    field.setLabel(label);
    setHelp(field, help);
    return field;
  }

  protected ComboBox<DayOfWeek> createWeekStartDayField(String label,
                                                        String help) {
    ComboBox<DayOfWeek> field = createComboBox(label, help);
    field.setItems(DayOfWeek.values());
    field.setItemLabelGenerator(dia -> $(dia.name()));
    field.setValue(DayOfWeek.MONDAY);
    return field;
  }

  protected IntegerSetView createByYearDayField(String label, String help) {
    IntegerSetView field = new IntegerSetView();
    field.setLabel(label);
    setHelp(field, help);
    field.setRange(1, 366);
    return field;
  }

  protected IntegerSetView createByWeekNoField(String label, String help) {
    IntegerSetView field = new IntegerSetView();
    field.setLabel(label);
    setHelp(field, help);
    field.setRange(1, 53);
    return field;
  }

  protected IntegerSetView createByHourField(String label, String help) {
    IntegerSetView field = new IntegerSetView();
    field.setLabel(label);
    setHelp(field, help);
    field.setRange(0, 23);
    return field;
  }

  protected IntegerSetView createByMinuteField(String label, String help) {
    IntegerSetView field = new IntegerSetView();
    field.setLabel(label);
    setHelp(field, help);
    field.setRange(0, 59);
    return field;
  }

  protected IntegerSetView createBySecondField(String label, String help) {
    IntegerSetView field = new IntegerSetView();
    field.setLabel(label);
    setHelp(field, help);
    field.setRange(0, 59);
    return field;
  }

  protected DatePicker createUntilDateField(String label, String help) {
    return createDateField(label, help);
  }

  protected TextField createCountLimitField(String label, String help) {
    return createInteiroTextField(label, help);
  }

  // ========================
  // Métodos de criação de campos com layout
  // ========================

  private void createFrequencyField(FormLayout layout) {
    frequencyField = createFrequencyField($(PeriodicidadeTranslator.RECORRENCIA.FREQUENCY),
                                          $(PeriodicidadeTranslator.RECORRENCIA.HELP.FREQUENCY));
    layout.add(frequencyField, 2);
    bind(RecorrenciaDTO.CAMPOS.FREQUENCY, frequencyField);

  }

  private void createIntervalValueField(FormLayout layout) {
    intervalValueField = createIntervalValueField($(PeriodicidadeTranslator.RECORRENCIA.INTERVAL_VALUE),
                                                  $(PeriodicidadeTranslator.RECORRENCIA.HELP.INTERVAL_VALUE));
    layout.add(intervalValueField, 2);
    bindInteger(RecorrenciaDTO.CAMPOS.INTERVAL_VALUE, intervalValueField);
  }

  private void createByDayField(FormLayout layout) {
    byDayField = createByDayField($(PeriodicidadeTranslator.RECORRENCIA.BY_DAY),
                                  $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_DAY));
    layout.add(byDayField, 6);
    bind(RecorrenciaDTO.CAMPOS.BY_DAY, byDayField);
  }

  private void createByMonthDayField(FormLayout layout) {
    byMonthDayField = createByMonthDayField($(PeriodicidadeTranslator.RECORRENCIA.BY_MONTH_DAY),
                                            $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_MONTH_DAY));
    layout.add(byMonthDayField, 6);
    bind(RecorrenciaDTO.CAMPOS.BY_MONTH_DAY, byMonthDayField);
  }

  private void createByMonthField(FormLayout layout) {
    byMonthField = createByMonthField($(PeriodicidadeTranslator.RECORRENCIA.BY_MONTH),
                                      $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_MONTH));
    layout.add(byMonthField, 6);
    bind(RecorrenciaDTO.CAMPOS.BY_MONTH, byMonthField);
  }

  private void createBySetPositionField(FormLayout layout) {
    bySetPositionField = createBySetPositionField($(PeriodicidadeTranslator.RECORRENCIA.BY_SET_POSITION),
                                                  $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_SET_POSITION));
    layout.add(bySetPositionField, 2);
    bind(RecorrenciaDTO.CAMPOS.BY_SET_POSITION, bySetPositionField);
  }

  private void createWeekStartDayField(FormLayout layout) {
    weekStartDayField = createWeekStartDayField($(PeriodicidadeTranslator.RECORRENCIA.WEEK_START_DAY),
                                                $(PeriodicidadeTranslator.RECORRENCIA.HELP.WEEK_START_DAY));
    layout.add(weekStartDayField, 2);
    bind(RecorrenciaDTO.CAMPOS.WEEK_START_DAY, weekStartDayField);
  }

  private void createByYearDayField(FormLayout layout) {
    byYearDayField = createByYearDayField($(PeriodicidadeTranslator.RECORRENCIA.BY_YEAR_DAY),
                                          $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_YEAR_DAY));
    layout.add(byYearDayField, 2);
    bind(RecorrenciaDTO.CAMPOS.BY_YEAR_DAY, byYearDayField);
  }

  private void createByWeekNoField(FormLayout layout) {
    byWeekNoField = createByWeekNoField($(PeriodicidadeTranslator.RECORRENCIA.BY_WEEK_NO),
                                        $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_WEEK_NO));
    layout.add(byWeekNoField, 2);
    bind(RecorrenciaDTO.CAMPOS.BY_WEEK_NO, byWeekNoField);
  }

  private void createByHourField(FormLayout layout) {
    byHourField = createByHourField($(PeriodicidadeTranslator.RECORRENCIA.BY_HOUR),
                                    $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_HOUR));
    layout.add(byHourField, 2);
    bind(RecorrenciaDTO.CAMPOS.BY_HOUR, byHourField);
  }

  private void createByMinuteField(FormLayout layout) {
    byMinuteField = createByMinuteField($(PeriodicidadeTranslator.RECORRENCIA.BY_MINUTE),
                                        $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_MINUTE));
    layout.add(byMinuteField, 2);
    bind(RecorrenciaDTO.CAMPOS.BY_MINUTE, byMinuteField);
  }

  private void createBySecondField(FormLayout layout) {
    bySecondField = createBySecondField($(PeriodicidadeTranslator.RECORRENCIA.BY_SECOND),
                                        $(PeriodicidadeTranslator.RECORRENCIA.HELP.BY_SECOND));
    layout.add(bySecondField, 2);
    bind(RecorrenciaDTO.CAMPOS.BY_SECOND, bySecondField);
  }

  private void createUntilDateField(FormLayout layout) {
    untilDateField = createUntilDateField($(PeriodicidadeTranslator.RECORRENCIA.UNTIL_DATE),
                                          $(PeriodicidadeTranslator.RECORRENCIA.HELP.UNTIL_DATE));
    layout.add(untilDateField, 3);
    bind(RecorrenciaDTO.CAMPOS.UNTIL_DATE, untilDateField);
  }

  private void createCountLimitField(FormLayout layout) {
    countLimitField = createCountLimitField($(PeriodicidadeTranslator.RECORRENCIA.COUNT_LIMIT),
                                            $(PeriodicidadeTranslator.RECORRENCIA.HELP.COUNT_LIMIT));
    layout.add(countLimitField, 3);
    bindInteger(RecorrenciaDTO.CAMPOS.COUNT_LIMIT, countLimitField);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    createMainLayout();
  }

  private void createMainLayout() {
    mainLayout = createFormLayout();
    add(mainLayout, 6);

    // ========================
    // Criação dos campos em um único layout
    // ========================

    // Seção: Frequência
    createFrequencyField(mainLayout);
    createIntervalValueField(mainLayout);
    createBySetPositionField(mainLayout);

    // Seção: Diária
    createByDayField(mainLayout);

    // Seção: Mensal
    mainLayout
        .add(new NativeLabel($(PeriodicidadeTranslator.RECORRENCIA.TAB_MONTHLY)),
             6);
    createByMonthDayField(mainLayout);
    createByMonthField(mainLayout);

    // Seção: Avançado
    mainLayout
        .add(new NativeLabel($(PeriodicidadeTranslator.RECORRENCIA.TAB_ADVANCED)),
             6);
    createWeekStartDayField(mainLayout);
    createByYearDayField(mainLayout);
    createByWeekNoField(mainLayout);

    // Seção: Horário
    mainLayout
        .add(new NativeLabel($(PeriodicidadeTranslator.RECORRENCIA.TAB_TIME)),
             6);
    createByHourField(mainLayout);
    createByMinuteField(mainLayout);
    createBySecondField(mainLayout);

    // Seção: Limites
    mainLayout
        .add(new NativeLabel($(PeriodicidadeTranslator.RECORRENCIA.TAB_LIMITS)),
             6);
    createUntilDateField(mainLayout);
    createCountLimitField(mainLayout);

  }

}
