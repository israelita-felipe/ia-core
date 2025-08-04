package com.ia.core.llm.view.comando.form;

import com.ia.core.llm.model.comando.FinalidadeComandoEnum;
import com.ia.core.llm.service.model.comando.ComandoSistemaDTO;
import com.ia.core.llm.service.model.comando.ComandoSistemaTranslator;
import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.model.template.TemplateTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.utils.DataProviderFactory;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;

/**
 * @author Israel Araújo
 */
public class ComandoSistemaFormView
  extends FormView<ComandoSistemaDTO> {

  /**
   * @param type
   * @param viewModel
   */
  public ComandoSistemaFormView(IFormViewModel<ComandoSistemaDTO> viewModel) {
    super(viewModel);
  }

  public TextArea createComando(String label, String help) {
    TextArea field = createTextArea(label, help);
    add(field, 6);
    return field;
  }

  public ComboBox<FinalidadeComandoEnum> createFinalidade(String label,
                                                          String help,
                                                          ItemLabelGenerator<FinalidadeComandoEnum> labelGenerator) {
    ComboBox<FinalidadeComandoEnum> field = createEnumComboBox(label, help,
                                                               FinalidadeComandoEnum.class,
                                                               labelGenerator);
    add(field, 6);
    return field;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("finalidade",
         createFinalidade($(ComandoSistemaTranslator.FINALIDADE),
                          $(ComandoSistemaTranslator.HELP.FINALIDADE),
                          FinalidadeComandoEnum::name));
    bind("titulo",
         createTitulo($(ComandoSistemaTranslator.TITULO),
                      $(ComandoSistemaTranslator.HELP.TITULO)));
    bind("comando",
         createComando($(ComandoSistemaTranslator.COMANDO),
                       $(ComandoSistemaTranslator.HELP.COMANDO)));
    bind("template",
         createTemplate($(TemplateTranslator.TEMPLATE),
                        $(TemplateTranslator.HELP.TEMPLATE),
                        DataProviderFactory
                            .createBaseDataProviderFromService(getViewModel()
                                .getTemplateService(), TemplateDTO.propertyFilters()),
                        TemplateDTO::getTitulo));
  }

  public ComboBox<TemplateDTO> createTemplate(String label, String help,
                                              DataProvider<TemplateDTO, String> dataProvider,
                                              ItemLabelGenerator<TemplateDTO> labelGenerator) {
    ComboBox<TemplateDTO> field = createComboBox(label, help, dataProvider,
                                                 labelGenerator);
    add(field, 6);
    return field;
  }

  public TextField createTitulo(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

  @Override
  public ComandoSistemaFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
