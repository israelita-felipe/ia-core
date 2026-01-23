package com.ia.core.security.view.privilege.operation.form;

import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.security.view.privilege.operation.context.page.PrivilegeOperationContextPageView;
import com.ia.core.security.view.privilege.operation.context.page.PrivilegeOperationContextPageViewModel;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;

/**
 * View de formulário de usuário
 *
 * @author Israel Araújo
 */
public class PrivilegeOperationFormView
  extends FormView<PrivilegeOperationDTO> {
  /** Serial UUID */
  private static final long serialVersionUID = 650617024016045875L;

  /** Campo de valores */
  private CheckboxGroup<String> valuesField;

  /**
   * @param viewModel View Model
   */
  public PrivilegeOperationFormView(IFormViewModel<PrivilegeOperationDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("operation",
         createOperationField($(UserTranslator.CODIGO),
                              $(UserTranslator.HELP.CODIGO), this::$));
    createValuesField($(UserTranslator.NOME), $(UserTranslator.HELP.NOME),
                      getViewModel()
                          .getPrivilegeOperationContextPageViewModel());

  }

  /**
   * @param label          Título do campo
   * @param labelGenerator {@link ItemLabelGenerator}
   * @return {@link CheckboxGroup}
   */
  public PrivilegeOperationContextPageView createValuesField(String label,
                                                             String help,
                                                             PrivilegeOperationContextPageViewModel pageViewModel) {
    PrivilegeOperationContextPageView privilegeOperationContextFormView = new PrivilegeOperationContextPageView(pageViewModel);
    add(privilegeOperationContextFormView, 6);
    return privilegeOperationContextFormView;
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link TextField}
   */
  public ComboBox<OperationEnum> createOperationField(String label,
                                                      String help,
                                                      ItemLabelGenerator<OperationEnum> labelGenerator) {
    ComboBox<OperationEnum> field = createEnumComboBox(label, help,
                                                       OperationEnum.class,
                                                       labelGenerator);
    add(field, 6);
    return field;
  }

  @Override
  public PrivilegeOperationFormViewModel getViewModel() {
    return (PrivilegeOperationFormViewModel) super.getViewModel();
  }
}
