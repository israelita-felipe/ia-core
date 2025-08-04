package com.ia.core.llm.view.template.form;

import com.ia.core.llm.service.model.template.TemplateDTO;
import com.ia.core.llm.service.model.template.TemplateTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * @author Israel Araújo
 */
public class TemplateFormView
  extends FormView<TemplateDTO> {

  /**
   * @param type
   * @param viewModel
   */
  public TemplateFormView(IFormViewModel<TemplateDTO> viewModel) {
    super(viewModel);
  }

  public TextArea createConteudo(String label, String help) {
    TextArea field = createTextArea(label, help);
    add(field, 6);
    return field;
  }

  public Checkbox createExigeContexto(String label, String help) {
    Checkbox field = createCheckBoxField(label, help);
    add(field, 6);
    return field;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("titulo", createTitulo($(TemplateTranslator.TITULO),
                                      $(TemplateTranslator.HELP.TITULO)));
    bind("exigeContexto",
         createExigeContexto($(TemplateTranslator.EXIGE_CONTEXTO),
                             $(TemplateTranslator.HELP.EXIGE_CONTEXTO)));
    bind("conteudo",
         createConteudo($(TemplateTranslator.CONTEUDO),
                        $(TemplateTranslator.HELP.CONTEUDO)));
  }

  public TextField createTitulo(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

}
