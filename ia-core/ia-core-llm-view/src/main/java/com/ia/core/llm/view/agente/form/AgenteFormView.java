package com.ia.core.llm.view.agente.form;

import com.ia.core.llm.service.model.agente.AgenteDTO;
import com.ia.core.llm.service.model.agente.AgenteTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * View para exibição e manipulação de agente form.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AgenteFormView
 * dentro do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class AgenteFormView
  extends FormView<AgenteDTO> {

  /**
   * @param viewModel
   */
  public AgenteFormView(IFormViewModel<AgenteDTO> viewModel) {
    super(viewModel);
  }

  public TextArea createDescricao(String label, String help) {
    TextArea field = createTextArea(label, help);
    add(field, 6);
    return field;
  }

  public TextArea createInstrucoes(String label, String help) {
    TextArea field = createTextArea(label, help);
    add(field, 6);
    return field;
  }

  public TextField createIdentificador(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

  public TextField createModelo(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

  public TextField createTitulo(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("identificador", createIdentificador($(AgenteTranslator.IDENTIFICADOR),
                                           $(AgenteTranslator.HELP.IDENTIFICADOR)));
    bind("titulo", createTitulo($(AgenteTranslator.TITULO),
                                $(AgenteTranslator.HELP.TITULO)));
    bind("descricao", createDescricao($(AgenteTranslator.DESCRICAO),
                                     $(AgenteTranslator.HELP.DESCRICAO)));
    bind("instrucoes", createInstrucoes($(AgenteTranslator.INSTRUCOES),
                                         $(AgenteTranslator.HELP.INSTRUCOES)));
    bind("modelo", createModelo($(AgenteTranslator.MODELO),
                                $(AgenteTranslator.HELP.MODELO)));
  }

  @Override
  public AgenteFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
