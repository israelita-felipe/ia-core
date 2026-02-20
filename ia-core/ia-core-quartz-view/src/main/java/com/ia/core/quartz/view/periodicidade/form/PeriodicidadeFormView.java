package com.ia.core.quartz.view.periodicidade.form;

import java.time.ZoneId;

import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeDTO;
import com.ia.core.quartz.service.model.periodicidade.dto.PeriodicidadeTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.TabSheet;

/**
 * @author Israel Araújo
 */
public class PeriodicidadeFormView
  extends FormView<PeriodicidadeDTO> {

  private TabSheet mainTabSheet;
  private FormLayout basicTabLayout;
  private FormLayout recurrenceTabLayout;
  private FormLayout exceptionsTabLayout;

  /**
   * @param viewModel
   */
  public PeriodicidadeFormView(IFormViewModel<PeriodicidadeDTO> viewModel) {
    super(viewModel);
  }

  /**
   * @param label
   * @param help
   * @param layout
   * @return
   */
  public Checkbox createAtivoField(String label, String help,
                                   FormLayout layout) {
    Checkbox ativoField = createCheckBoxField(label, help);
    layout.add(ativoField, 6);
    bind(PeriodicidadeDTO.CAMPOS.ATIVO, ativoField);
    return ativoField;
  }

  /**
   * @param label
   * @param help
   * @param layout
   * @return
   */
  public ComboBox<String> createZoneIdField(String label, String help,
                                            FormLayout layout) {
    ComboBox<String> zoneIdField = createComboBox(label, help);
    zoneIdField.setItems(ZoneId.getAvailableZoneIds());
    layout.add(zoneIdField, 6);
    bind(PeriodicidadeDTO.CAMPOS.ZONE_ID, zoneIdField);
    return zoneIdField;
  }

  /**
   * @param label
   * @param help
   * @param layout
   * @return
   */
  public IntervaloTemporalFormView createIntervaloTemporalField(String label,
                                                                String help,
                                                                IntervaloTemporalFormViewModel intervaloTemporalFormViewModel,
                                                                FormLayout layout) {
    IntervaloTemporalFormView field = new IntervaloTemporalFormView(intervaloTemporalFormViewModel);
    setHelp(field, help);
    layout.add(field, 6);
    bind("intervaloBase", field);
    return field;
  }

  /**
   * @param label
   * @param help
   * @param layout
   * @return
   */
  public RecorrenciaFormView createRecorrenciaField(String label,
                                                    String help,
                                                    RecorrenciaFormViewModel recorrenciaFormViewModel,
                                                    FormLayout layout) {
    RecorrenciaFormView field = new RecorrenciaFormView(recorrenciaFormViewModel);
    setHelp(field, help);
    layout.add(field, 6);
    bind("regra", field);
    return field;
  }

  /**
   * @param label
   * @param help
   * @param layout
   * @return
   */
  public DateSetView createIncludeDatesField(String label, String help,
                                             FormLayout layout) {
    DateSetView includeDatesField = new DateSetView();
    includeDatesField.setLabel(label);
    setHelp(includeDatesField, help);
    layout.add(includeDatesField, 6);
    bind(PeriodicidadeDTO.CAMPOS.INCLUDE_DATES, includeDatesField);
    return includeDatesField;
  }

  /**
   * @param label
   * @param help
   * @param layout
   * @return
   */
  public DateSetView createExceptionDatesField(String label, String help,
                                               FormLayout layout) {
    DateSetView exceptionDatesField = new DateSetView();
    exceptionDatesField.setLabel(label);
    setHelp(exceptionDatesField, help);
    layout.add(exceptionDatesField, 6);
    bind(PeriodicidadeDTO.CAMPOS.EXCEPTION_DATES, exceptionDatesField);
    return exceptionDatesField;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    createMainTabSheet();
  }

  private void createMainTabSheet() {
    mainTabSheet = createTabSheet();

    // Aba Básico (junta básico + intervalo)
    createTab(mainTabSheet, VaadinIcon.COG.create(),
              $(PeriodicidadeTranslator.TAB_BASIC),
              basicTabLayout = createFormLayout());

    // Aba Recorrência
    createTab(mainTabSheet, VaadinIcon.REFRESH.create(),
              $(PeriodicidadeTranslator.TAB_RECURRENCE),
              recurrenceTabLayout = createFormLayout());

    // Aba Exceções
    createTab(mainTabSheet, VaadinIcon.WRENCH.create(),
              $(PeriodicidadeTranslator.TAB_EXCEPTIONS),
              exceptionsTabLayout = createFormLayout());

    add(mainTabSheet, 6);

    // Campos da aba Básico (junta ativo, zoneId e intervalo)
    createAtivoField($(PeriodicidadeTranslator.ATIVO),
                     $(PeriodicidadeTranslator.HELP.ATIVO), basicTabLayout);
    createZoneIdField($(PeriodicidadeTranslator.ZONE_ID),
                      $(PeriodicidadeTranslator.HELP.ZONE_ID),
                      basicTabLayout);
    createIntervaloTemporalField($(PeriodicidadeTranslator.INTERVALO_BASE),
                                 $(PeriodicidadeTranslator.HELP.INTERVALO_BASE),
                                 getViewModel()
                                     .getIntervaloTemporalFormViewModel(),
                                 basicTabLayout);

    // Campos da aba Recorrência
    createRecorrenciaField($(PeriodicidadeTranslator.REGRA),
                           $(PeriodicidadeTranslator.HELP.REGRA),
                           getViewModel().getRecorrenciaFormViewModel(),
                           recurrenceTabLayout);

    // Campos da aba Exceções
    createIncludeDatesField($(PeriodicidadeTranslator.INCLUDE_DATES),
                            $(PeriodicidadeTranslator.HELP.INCLUDE_DATES),
                            exceptionsTabLayout);
    createExceptionDatesField($(PeriodicidadeTranslator.EXCEPTION_DATES),
                              $(PeriodicidadeTranslator.HELP.EXCEPTION_DATES),
                              exceptionsTabLayout);
  }

  @Override
  public PeriodicidadeFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }

}
