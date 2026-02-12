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

  private Checkbox ativoField;
  private ComboBox<String> zoneIdField;

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
    ativoField = createCheckBoxField(label, help);
    add(ativoField, 6);
    return ativoField;
  }

  public ComboBox<String> createZoneIdField(String label, String help) {
    zoneIdField = createComboBox(label, help);
    zoneIdField.setItems(ZoneId.getAvailableZoneIds());
    add(zoneIdField, 6);
    return zoneIdField;
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
  }

  @Override
  public PeriodicidadeFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }

}
