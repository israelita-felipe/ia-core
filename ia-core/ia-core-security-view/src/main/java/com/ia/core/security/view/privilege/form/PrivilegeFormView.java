package com.ia.core.security.view.privilege.form;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Formulário de {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
public class PrivilegeFormView
  extends FormView<PrivilegeDTO> {
  /** Serial UID */
  private static final long serialVersionUID = 6821054423452888759L;

  /**
   * @param viewModel View Model do formulário
   */
  public PrivilegeFormView(IFormViewModel<PrivilegeDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    bind("name",
         createNomeField($(PrivilegeTranslator.NOME),
                         $(PrivilegeTranslator.HELP.NOME)),
         getViewModel().getModel().getId() != null);
    bind("values", createValuesField($(PrivilegeTranslator.VALUES),
                                     $(PrivilegeTranslator.HELP.VALUES)));
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link TextField}
   */
  public TextField createNomeField(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

  public CheckboxGroup<String> createValuesField(String label,
                                                 String help) {
    var field = new CheckboxGroup<String>();
    field.setItems(getViewModel().getContextKeys());
    field.setLabel(label);
    setHelp(field, help);
    add(field, 6);
    return field;
  }

  @Override
  public PrivilegeFormViewModel getViewModel() {
    return super.getViewModel().cast();
  }
}
