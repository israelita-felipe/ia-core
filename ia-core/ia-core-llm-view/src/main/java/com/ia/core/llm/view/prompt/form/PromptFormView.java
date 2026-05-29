package com.ia.core.llm.view.prompt.form;

import com.ia.core.llm.model.prompt.FinalidadePromptEnum;
import com.ia.core.llm.service.model.prompt.PromptDTO;
import com.ia.core.llm.service.model.prompt.PromptTranslator;
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
 * View para exibição e manipulação de comando sistema form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PromptFormView
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PromptFormView
  extends FormView<PromptDTO> {

  /**
   * @param type
   * @param viewModel
   */
  public PromptFormView(IFormViewModel<PromptDTO> viewModel) {
    super(viewModel);
  }

  public TextArea createEntrada(String label, String help) {
    TextArea field = createTextArea(label, help);
    add(field, 6);
    return field;
  }

  public ComboBox<FinalidadePromptEnum> createFinalidade(String label,
                                                          String help,
                                                          ItemLabelGenerator<FinalidadePromptEnum> labelGenerator) {
    ComboBox<FinalidadePromptEnum> field = createEnumComboBox(label, help,
                                                               FinalidadePromptEnum.class,
                                                               labelGenerator);
    add(field, 6);
    return field;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("finalidade",
         createFinalidade($(PromptTranslator.FINALIDADE),
                          $(PromptTranslator.HELP.FINALIDADE),
                          FinalidadePromptEnum::name));
    bind("titulo", createTitulo($(PromptTranslator.TITULO),
                                $(PromptTranslator.HELP.TITULO)));
    bind("entrada",
         createEntrada($(PromptTranslator.ENTRADA),
                       $(PromptTranslator.HELP.ENTRADA)));
    bind("template",
         createTemplate($(TemplateTranslator.TEMPLATE),
                        $(TemplateTranslator.HELP.TEMPLATE),
                        DataProviderFactory
                            .createBaseDataProviderFromManager(getViewModel()
                                .getConfig().getTemplateService(),
                                                               TemplateDTO
                                                                   .propertyFilters()),
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
  public PromptFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
