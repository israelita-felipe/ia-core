package com.ia.core.security.view.privilege.operation.context.form;

import com.ia.core.security.service.model.authorization.ContextManager.ContextDefinition;
import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.security.view.privilege.operation.context.converter.ContextDefinitionConverter;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;

/**
 * View de formulário de usuário
 *
 * @author Israel Araújo
 */
public class PrivilegeOperationContextFormView
  extends FormView<PrivilegeOperationContextDTO> {
  /** Serial UUID */
  private static final long serialVersionUID = 650617024016045875L;

  /** Campo de valores */
  private CheckboxGroup<ContextDefinition> valuesField;

  /**
   * @param viewModel View Model
   */
  public PrivilegeOperationContextFormView(IFormViewModel<PrivilegeOperationContextDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("contextKey",
         createContextKeyField($(UserTranslator.CODIGO),
                               $(UserTranslator.HELP.CODIGO), this::$));
    this.bindWithConverter("values",
                           createValuesField($(UserTranslator.NOME),
                                             $(UserTranslator.HELP.NOME),
                                             ContextDefinition::label),
                           new ContextDefinitionConverter());

  }

  /**
   * @param label          Título do campo
   * @param labelGenerator {@link ItemLabelGenerator}
   * @return {@link CheckboxGroup}
   */
  public CheckboxGroup<ContextDefinition> createValuesField(String label,
                                                            String help,
                                                            ItemLabelGenerator<ContextDefinition> labelGenerator) {
    valuesField = new CheckboxGroup<ContextDefinition>(label);
    loadContextValues(valuesField);
    valuesField.setItemLabelGenerator(labelGenerator);
    valuesField.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    setHelp(valuesField, help);
    add(valuesField, 6);
    return valuesField;
  }

  /**
   * @param field
   * @param data
   */
  public void loadContextValues(CheckboxGroup<ContextDefinition> field) {
    if (field != null) {
      field.setItems(getViewModel().getContextValues());
    }
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link TextField}
   */
  public ComboBox<String> createContextKeyField(String label, String help,
                                                ItemLabelGenerator<String> labelGenerator) {
    ComboBox<String> field = createComboBox(label, help,
                                            getViewModel().getContextKeys(),
                                            labelGenerator);
    field.addValueChangeListener(onChange -> {
      loadContextValues(valuesField);
    });
    add(field, 6);
    return field;
  }

  @Override
  public PrivilegeOperationContextFormViewModel getViewModel() {
    return (PrivilegeOperationContextFormViewModel) super.getViewModel();
  }
}
