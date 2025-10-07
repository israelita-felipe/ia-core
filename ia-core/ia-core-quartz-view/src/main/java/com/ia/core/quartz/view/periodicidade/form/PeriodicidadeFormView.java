package com.ia.core.quartz.view.periodicidade.form;

import java.time.DayOfWeek;
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
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.timepicker.TimePicker;

/**
 * @author Israel Araújo
 */
public class PeriodicidadeFormView
  extends FormView<PeriodicidadeDTO> {

  private Checkbox ativoField;
  private Checkbox diaTodoField;
  private Checkbox periodicoField;
  private DatePicker dataInicioField;
  private TimePicker horaInicioField;
  private DatePicker dataFimField;
  private TimePicker horaFimField;
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
    add(diasField, 6);
    return diasField;
  }

  public Checkbox createDiaTodoField(String label, String help) {
    diaTodoField = createCheckBoxField(label, help);
    diaTodoField.addValueChangeListener(onChange -> {
      onDiaTodoValueChange(onChange.getValue());
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

  @Override
  public void createLayout() {
    super.createLayout();
    bind("ativo", createAtivoField($(PeriodicidadeTranslator.ATIVO),
                                   $(PeriodicidadeTranslator.HELP.ATIVO)));
    bind("diaTodo",
         createDiaTodoField($(PeriodicidadeTranslator.DIA_TODO),
                            $(PeriodicidadeTranslator.HELP.DIA_TODO)));
    bind("periodico",
         createPeriodicoField($(PeriodicidadeTranslator.PERIODICO),
                              $(PeriodicidadeTranslator.HELP.PERIODICO)));
    bind("dataInicio",
         createDataInicioField($(PeriodicidadeTranslator.DATA_INICIO),
                               $(PeriodicidadeTranslator.HELP.DATA_INICIO)));
    bind("horaInicio",
         createHoraInicioField($(PeriodicidadeTranslator.HORA_INICIO),
                               $(PeriodicidadeTranslator.HELP.HORA_INICIO)));
    bind("dataFim",
         createDataFimField($(PeriodicidadeTranslator.DATA_FIM),
                            $(PeriodicidadeTranslator.HELP.DATA_FIM)));
    bind("horaFim",
         createHoraFimField($(PeriodicidadeTranslator.HORA_FIM),
                            $(PeriodicidadeTranslator.HELP.HORA_FIM)));
    bind("ocorrenciaDiaria",
         createOcorrenciaDiariaField($(PeriodicidadeTranslator.OCORRENCIA_DIARIA),
                                     $(PeriodicidadeTranslator.HELP.OCORRENCIA_DIARIA)));
    bind("dias",
         createDiasField($(PeriodicidadeTranslator.DIAS),
                         $(PeriodicidadeTranslator.HELP.DIAS),
                         dia -> $(dia.name())));
    bind("ocorrenciaSemanal",
         createOcorrenciaSemanalField($(PeriodicidadeTranslator.OCORRENCIA_SEMANAL),
                                      $(PeriodicidadeTranslator.HELP.OCORRENCIA_SEMANAL),
                                      semana -> $(semana.name())));
    bind("meses",
         createMesesField($(PeriodicidadeTranslator.MESES),
                          $(PeriodicidadeTranslator.HELP.MESES),
                          mes -> $(mes.name())));
    onDiaTodoValueChange(getViewModel().getModel().isDiaTodo());
    onPeriodicoValueChange(getViewModel().getModel().isPeriodico());
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
      onPeriodicoValueChange(onChange.getValue());
    });
    add(periodicoField, 6);
    return periodicoField;
  }

  public NativeLabel createTitulo(String titulo) {
    NativeLabel label = new NativeLabel(titulo);
    label.setFor(this);
    add(label, 6);
    return label;
  }

  /**
   * @param value
   */
  protected void onDiaTodoValueChange(Boolean value) {
    if (this.horaInicioField != null) {
      if (!value) {
        this.horaInicioField.clear();
      }
      this.horaInicioField.setEnabled(!value);
    }
    if (this.horaFimField != null) {
      if (!value) {
        this.horaFimField.clear();
      }
      this.horaFimField.setEnabled(!value);
    }
  }

  /**
   * @param value
   */
  protected void onPeriodicoValueChange(Boolean value) {
    if (ocorrenciaDiariaField != null) {
      if (!value) {
        ocorrenciaDiariaField.clear();
      }
      ocorrenciaDiariaField.setEnabled(value);
    }
    if (ocorrenciaSemanal != null) {
      if (!value) {
        ocorrenciaSemanal.clear();
      }
      ocorrenciaSemanal.setEnabled(value);
    }
    if (mesesField != null) {
      if (!value) {
        mesesField.clear();
      }
      mesesField.setEnabled(value);
    }
    if (diasField != null) {
      if (!value) {
        diasField.clear();
      }
      diasField.setEnabled(value);
    }
  }
}
