package com.ia.core.quartz.view.periodicidade.form;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Month;
import java.util.stream.IntStream;

import com.ia.core.quartz.model.periodicidade.OcorrenciaSemanal;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.selection.MultiSelectionEvent;

/**
 * @author Israel Araújo
 */
public class PeriodicidadeFormView
  extends FormView<PeriodicidadeDTO> {

  private Checkbox ativoField;
  private Checkbox diaTodoField;

  // ----------------------
  // PERIÓDICO
  // ----------------------
  private Checkbox periodicoField;
  private DatePicker dataInicioField;
  private TimePicker horaInicioField;
  private DatePicker dataFimField;
  private TimePicker horaFimField;

  // ----------------------
  // INTERVALO
  // ----------------------
  private Checkbox intervaloTempoField;
  private TimePicker tempoIntervaloField;

  // ----------------------
  // OCORRÊNCIAS
  // ----------------------
  private CheckboxGroup<DayOfWeek> diasField;
  private CheckboxGroup<Month> mesesField;
  private CheckboxGroup<OcorrenciaSemanal> ocorrenciaSemanal;
  private CheckboxGroup<Integer> ocorrenciaDiariaField;

  /**
   * @param viewModel
   */
  public PeriodicidadeFormView(IFormViewModel<PeriodicidadeDTO> viewModel) {
    super(viewModel);
  }

  public Checkbox createAtivoField(String label, String help) {
    ativoField = createCheckBoxField(label, help);
    add(ativoField, 6);
    return ativoField;
  }

  public DatePicker createDataFimField(String label, String help) {
    dataFimField = createDateField(label, help);
    add(dataFimField, 3);
    return dataFimField;
  }

  public DatePicker createDataInicioField(String label, String help) {
    dataInicioField = createDateField(label, help);
    add(dataInicioField, 3);
    return dataInicioField;
  }

  public CheckboxGroup<DayOfWeek> createDiasField(String label, String help,
                                                  ItemLabelGenerator<DayOfWeek> itemLabelGenerator) {
    diasField = createEnumCheckBox(label, help, DayOfWeek.class,
                                   itemLabelGenerator);
    diasField.setEnabled(false);
    diasField.addSelectionListener(onSelect -> {
      onDiasValueChange(onSelect);
    });
    add(diasField, 6);
    return diasField;
  }

  public Checkbox createDiaTodoField(String label, String help) {
    diaTodoField = createCheckBoxField(label, help);
    diaTodoField.addValueChangeListener(onChange -> {
      onDiaTodoValueChange(onChange);
    });
    add(diaTodoField, 6);
    return diaTodoField;
  }

  public TimePicker createHoraFimField(String label, String help) {
    horaFimField = createTimeField(label, help);
    add(horaFimField, 3);
    return horaFimField;
  }

  public TimePicker createHoraInicioField(String label, String help) {
    horaInicioField = createTimeField(label, help);
    add(horaInicioField, 3);
    return horaInicioField;
  }

  public Checkbox createIntervaloTempoField(String label, String help) {
    intervaloTempoField = createCheckBoxField(label, help);
    intervaloTempoField.addValueChangeListener(onChange -> {
      onIntervaloTempoValueChange(onChange);
    });
    add(intervaloTempoField, 6);
    return intervaloTempoField;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    add(new Hr(), 6);
    add(new NativeLabel("Status"), 6);
    bind(PeriodicidadeDTO.CAMPOS.ATIVO,
         createAtivoField($(PeriodicidadeTranslator.ATIVO),
                          $(PeriodicidadeTranslator.HELP.ATIVO)));
    add(new Hr(), 6);
    add(new NativeLabel("Tipos de ocorrência"), 6);
    bind(PeriodicidadeDTO.CAMPOS.DIA_TODO,
         createDiaTodoField($(PeriodicidadeTranslator.DIA_TODO),
                            $(PeriodicidadeTranslator.HELP.DIA_TODO)));
    bind(PeriodicidadeDTO.CAMPOS.PERIODICO,
         createPeriodicoField($(PeriodicidadeTranslator.PERIODICO),
                              $(PeriodicidadeTranslator.HELP.PERIODICO)));
    bind(PeriodicidadeDTO.CAMPOS.INTERVALO_TEMPO,
         createIntervaloTempoField($(PeriodicidadeTranslator.INTERVALO_TEMPO),
                                   $(PeriodicidadeTranslator.HELP.INTERVALO_TEMPO)));
    add(new Hr(), 6);
    add(new NativeLabel("Ocorrência periódica"), 6);
    bind(PeriodicidadeDTO.CAMPOS.DATA_INICIO,
         createDataInicioField($(PeriodicidadeTranslator.DATA_INICIO),
                               $(PeriodicidadeTranslator.HELP.DATA_INICIO)));
    bind(PeriodicidadeDTO.CAMPOS.HORA_INICIO,
         createHoraInicioField($(PeriodicidadeTranslator.HORA_INICIO),
                               $(PeriodicidadeTranslator.HELP.HORA_INICIO)));
    bind(PeriodicidadeDTO.CAMPOS.DATA_FIM,
         createDataFimField($(PeriodicidadeTranslator.DATA_FIM),
                            $(PeriodicidadeTranslator.HELP.DATA_FIM)));
    bind(PeriodicidadeDTO.CAMPOS.HORA_FIM,
         createHoraFimField($(PeriodicidadeTranslator.HORA_FIM),
                            $(PeriodicidadeTranslator.HELP.HORA_FIM)));
    add(new Hr(), 6);
    add(new NativeLabel("Ocorrência temporal"), 6);
    bind(PeriodicidadeDTO.CAMPOS.TEMPO_INTERVALO,
         createTempoIntervaloField($(PeriodicidadeTranslator.TEMPO_INTERVALO),
                                   $(PeriodicidadeTranslator.HELP.TEMPO_INTERVALO)));
    add(new Hr(), 6);
    add(new NativeLabel("Ocorrências gerais"), 6);
    bind(PeriodicidadeDTO.CAMPOS.OCORRENCIA_DIARIA,
         createOcorrenciaDiariaField($(PeriodicidadeTranslator.OCORRENCIA_DIARIA),
                                     $(PeriodicidadeTranslator.HELP.OCORRENCIA_DIARIA)));
    bind(PeriodicidadeDTO.CAMPOS.DIAS,
         createDiasField($(PeriodicidadeTranslator.DIAS),
                         $(PeriodicidadeTranslator.HELP.DIAS),
                         dia -> $(dia.name())));
    bind(PeriodicidadeDTO.CAMPOS.OCORRENCIA_SEMANAL,
         createOcorrenciaSemanalField($(PeriodicidadeTranslator.OCORRENCIA_SEMANAL),
                                      $(PeriodicidadeTranslator.HELP.OCORRENCIA_SEMANAL),
                                      semana -> $(semana.name())));
    bind(PeriodicidadeDTO.CAMPOS.MESES,
         createMesesField($(PeriodicidadeTranslator.MESES),
                          $(PeriodicidadeTranslator.HELP.MESES),
                          mes -> $(mes.name())));
  }

  @Override
  public void setValue(PeriodicidadeDTO value) {
    if (value != null) {
      onDiaTodoValueChange(value.isDiaTodo(), false);
      onPeriodicoValueChange(value.isPeriodico(), false);
      onIntervaloTempoValueChange(value.isIntervaloTempo(), false);
      onDiasValueChange(!value.getDias().isEmpty(), false);
      onOcorrenciaDiariaChange(!value.getOcorrenciaDiaria().isEmpty(),
                               false);
    }
    super.setValue(value);
  }

  public CheckboxGroup<Month> createMesesField(String label, String help,
                                               ItemLabelGenerator<Month> itemLabelGenerator) {
    mesesField = createEnumCheckBox(label, help, Month.class,
                                    itemLabelGenerator);
    mesesField.setEnabled(false);
    add(mesesField, 6);
    return mesesField;
  }

  public CheckboxGroup<Integer> createOcorrenciaDiariaField(String label,
                                                            String help) {
    ocorrenciaDiariaField = new CheckboxGroup<>(label);
    setHelp(ocorrenciaDiariaField, help);
    ocorrenciaDiariaField
        .setItems(IntStream.range(1, 32).mapToObj(value -> value).toList());
    ocorrenciaDiariaField.setEnabled(false);
    ocorrenciaDiariaField.addSelectionListener(onSelect -> {
      onOcorrenciaDiariaChange(onSelect);
    });
    add(ocorrenciaDiariaField, 6);
    return ocorrenciaDiariaField;
  }

  public CheckboxGroup<OcorrenciaSemanal> createOcorrenciaSemanalField(String label,
                                                                       String help,
                                                                       ItemLabelGenerator<OcorrenciaSemanal> itemLabelGenerator) {
    ocorrenciaSemanal = createEnumCheckBox(label, help,
                                           OcorrenciaSemanal.class,
                                           itemLabelGenerator);
    ocorrenciaSemanal.setEnabled(false);
    add(ocorrenciaSemanal, 6);
    return ocorrenciaSemanal;
  }

  public Checkbox createPeriodicoField(String label, String help) {
    periodicoField = createCheckBoxField(label, help);
    periodicoField.addValueChangeListener(onChange -> {
      onPeriodicoValueChange(onChange);
    });
    add(periodicoField, 6);
    return periodicoField;
  }

  public TimePicker createTempoIntervaloField(String label, String help) {
    tempoIntervaloField = createTimeField(label, help);
    tempoIntervaloField.setStep(Duration.ofSeconds(1));
    add(tempoIntervaloField, 3);
    return tempoIntervaloField;
  }

  public NativeLabel createTitulo(String titulo) {
    NativeLabel label = new NativeLabel(titulo);
    label.setFor(this);
    add(label, 6);
    return label;
  }

  /**
   * @param onSelect
   */
  public void onDiasValueChange(MultiSelectionEvent<CheckboxGroup<DayOfWeek>, DayOfWeek> onSelect) {
    boolean hasDiasSelecionados = !onSelect.getAllSelectedItems().isEmpty();
    onDiasValueChange(hasDiasSelecionados, true);
  }

  /**
   * @param hasDiasSelecionados
   * @param clearField          TODO
   */
  public void onDiasValueChange(boolean hasDiasSelecionados,
                                boolean clearField) {
    if (ocorrenciaDiariaField != null) {
      if (clearField) {
        ocorrenciaDiariaField.clear();
      }
      ocorrenciaDiariaField.setEnabled(!hasDiasSelecionados);
    }
  }

  /**
   * @param onChangeEvent
   */
  protected void onDiaTodoValueChange(ComponentValueChangeEvent<Checkbox, Boolean> onChangeEvent) {
    Boolean isDiaTodoSelecionado = onChangeEvent.getValue();
    onDiaTodoValueChange(isDiaTodoSelecionado, true);
  }

  /**
   * @param isDiaTodoSelecionado
   * @param clearField           TODO
   */
  public void onDiaTodoValueChange(Boolean isDiaTodoSelecionado,
                                   boolean clearField) {
    if (this.horaInicioField != null) {
      if (!isDiaTodoSelecionado && clearField) {
        this.horaInicioField.setValue(LocalTime.of(0, 0, 0));
      }
      this.horaInicioField.setEnabled(!isDiaTodoSelecionado);
    }
    if (this.horaFimField != null) {
      if (!isDiaTodoSelecionado && clearField) {
        this.horaFimField.setValue(LocalTime.of(23, 59, 59));
      }
      this.horaFimField.setEnabled(!isDiaTodoSelecionado);
    }
  }

  /**
   * @param value
   */
  protected void onIntervaloTempoValueChange(ComponentValueChangeEvent<Checkbox, Boolean> onChangeEvent) {
    Boolean isIntervaloTempoSelecionado = onChangeEvent.getValue();
    onIntervaloTempoValueChange(isIntervaloTempoSelecionado, true);

  }

  /**
   * @param isIntervaloTempoSelecionado
   * @param clearField                  TODO
   */
  public void onIntervaloTempoValueChange(Boolean isIntervaloTempoSelecionado,
                                          boolean clearField) {
    if (tempoIntervaloField != null) {
      if (!isIntervaloTempoSelecionado && clearField) {
        tempoIntervaloField.clear();
      }
      tempoIntervaloField.setEnabled(isIntervaloTempoSelecionado);
    }
  }

  /**
   * @param onSelect
   */
  public void onOcorrenciaDiariaChange(MultiSelectionEvent<CheckboxGroup<Integer>, Integer> onSelect) {
    boolean hasOcorrenciasDiariasSelecionadas = !onSelect
        .getAllSelectedItems().isEmpty();
    onOcorrenciaDiariaChange(hasOcorrenciasDiariasSelecionadas, true);
  }

  /**
   * @param hasOcorrenciasDiariasSelecionadas
   * @param clearField                        TODO
   */
  public void onOcorrenciaDiariaChange(boolean hasOcorrenciasDiariasSelecionadas,
                                       boolean clearField) {
    if (diasField != null) {
      if (clearField) {
        diasField.clear();
      }
      diasField.setEnabled(!hasOcorrenciasDiariasSelecionadas);
    }
  }

  /**
   * @param value
   */
  protected void onPeriodicoValueChange(ComponentValueChangeEvent<Checkbox, Boolean> onChangeEvent) {
    Boolean isPeriodico = onChangeEvent.getValue();
    onPeriodicoValueChange(isPeriodico, true);
  }

  /**
   * @param isPeriodico
   * @param clearField  TODO
   */
  public void onPeriodicoValueChange(Boolean isPeriodico,
                                     boolean clearField) {
    if (ocorrenciaDiariaField != null) {
      if (!isPeriodico && clearField) {
        ocorrenciaDiariaField.clear();
      }
      ocorrenciaDiariaField.setEnabled(isPeriodico);
    }
    if (ocorrenciaSemanal != null) {
      if (!isPeriodico && clearField) {
        ocorrenciaSemanal.clear();
      }
      ocorrenciaSemanal.setEnabled(isPeriodico);
    }
    if (mesesField != null) {
      if (!isPeriodico && clearField) {
        mesesField.clear();
      }
      mesesField.setEnabled(isPeriodico);
    }
    if (diasField != null) {
      if (!isPeriodico && clearField) {
        diasField.clear();
      }
      diasField.setEnabled(isPeriodico);
    }
  }
}
