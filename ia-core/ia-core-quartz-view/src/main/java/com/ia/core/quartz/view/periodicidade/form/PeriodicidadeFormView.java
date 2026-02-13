package com.ia.core.quartz.view.periodicidade.form;

import java.time.ZoneId;

import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.NativeLabel;

/**
 * @author Israel Araújo
 */
public class PeriodicidadeFormView
  extends FormView<PeriodicidadeDTO> {

  /**
   * @param viewModel
   */
  public PeriodicidadeFormView(IFormViewModel<PeriodicidadeDTO> viewModel) {
    super(viewModel);
  }

  /**
   * @param recorrenciaFormViewModel
   * @return
   */
  public RecorrenciaFormView createRecorrenciaField(String label,
                                                    String help,
                                                    RecorrenciaFormViewModel recorrenciaFormViewModel) {
    // Criar subformulário de recorrência
    RecorrenciaFormView field = new RecorrenciaFormView(recorrenciaFormViewModel);
    add(new Hr(), 6);
    add(new NativeLabel(label), 6);
    setHelp(field, help);
    add(field, 6);
    return field;
  }

  /**
   * @param intervaloTemporalFormViewModel
   * @return
   */
  public IntervaloTemporalFormView createIntervaloTemporalField(String label,
                                                               String help,
                                                               IntervaloTemporalFormViewModel intervaloTemporalFormViewModel) {
    // Criar subformulário de intervalo temporal
    IntervaloTemporalFormView field = new IntervaloTemporalFormView(intervaloTemporalFormViewModel);
    add(new Hr(), 6);
    add(new NativeLabel(label), 6);
    setHelp(field, help);
    add(field, 6);
    return field;
  }

  public Checkbox createAtivoField(String label, String help) {
    Checkbox ativoField = createCheckBoxField(label, help);
    add(ativoField, 6);
    return ativoField;
  }

  public ComboBox<String> createZoneIdField(String label, String help) {
    ComboBox<String> zoneIdField = createComboBox(label, help);
    zoneIdField.setItems(ZoneId.getAvailableZoneIds());
    add(zoneIdField, 6);
    return zoneIdField;
  }

  /**
   * @param label
   * @param help
   * @return
   */
  public DateSetView createExceptionDatesField(String label, String help) {
    DateSetView exceptionDatesField = new DateSetView();
    exceptionDatesField.setLabel(label);
    setHelp(exceptionDatesField, help);
    add(exceptionDatesField, 6);
    return exceptionDatesField;
  }

  /**
   * @param label
   * @param help
   * @return
   */
  public DateSetView createIncludeDatesField(String label, String help) {
    DateSetView includeDatesField = new DateSetView();
    includeDatesField.setLabel(label);
    setHelp(includeDatesField, help);
    add(includeDatesField, 6);
    return includeDatesField;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind(PeriodicidadeDTO.CAMPOS.ATIVO,
         createAtivoField($(PeriodicidadeTranslator.ATIVO),
                          $(PeriodicidadeTranslator.HELP.ATIVO)));
    bind(PeriodicidadeDTO.CAMPOS.ZONE_ID,
         createZoneIdField($(PeriodicidadeTranslator.ZONE_ID),
                           $(PeriodicidadeTranslator.HELP.ZONE_ID)));
    bind("intervaloBase",
         createIntervaloTemporalField($(PeriodicidadeTranslator.INTERVALO_BASE),
                                      $(PeriodicidadeTranslator.HELP.INTERVALO_BASE),
                                      getViewModel()
                                          .getIntervaloTemporalFormViewModel()));
    bind("regra",
         createRecorrenciaField($(PeriodicidadeTranslator.REGRA),
                                $(PeriodicidadeTranslator.HELP.REGRA),
                                getViewModel()
                                    .getRecorrenciaFormViewModel()));
    bind(PeriodicidadeDTO.CAMPOS.INCLUDE_DATES,
         createIncludeDatesField($(PeriodicidadeTranslator.INCLUDE_DATES),
                                $(PeriodicidadeTranslator.HELP.INCLUDE_DATES)));
    bind(PeriodicidadeDTO.CAMPOS.EXCEPTION_DATES,
         createExceptionDatesField($(PeriodicidadeTranslator.EXCEPTION_DATES),
                                   $(PeriodicidadeTranslator.HELP.EXCEPTION_DATES)));
  }

  @Override
  public PeriodicidadeFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }

}
