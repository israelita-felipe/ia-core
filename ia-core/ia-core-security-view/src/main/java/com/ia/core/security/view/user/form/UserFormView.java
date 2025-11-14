package com.ia.core.security.view.user.form;

import com.ia.core.security.model.functionality.Operation;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.security.service.model.role.RoleTranslator;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserRoleDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.manager.DefaultBaseManager;
import com.ia.core.view.utils.DataProviderFactory;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;

/**
 * View de formulário de usuário
 *
 * @author Israel Araújo
 */
public class UserFormView
  extends FormView<UserDTO> {
  /** Serial UUID */
  private static final long serialVersionUID = 650617024016045875L;
  /**
   * Tab principal
   */
  private TabSheet tabSheet;

  /**
   * @param viewModel View Model
   */
  public UserFormView(IFormViewModel<UserDTO> viewModel) {
    super(viewModel);
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link Checkbox}
   */
  public Checkbox createAccountNotExpiredField(String label, String help) {
    Checkbox field = createCheckBoxField(label, help);
    add(field, 3);
    return field;
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link Checkbox}
   */
  public Checkbox createAccountNotLockedField(String label, String help) {
    Checkbox field = createCheckBoxField(label, help);
    add(field, 3);
    return field;
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link Checkbox}
   */
  public Checkbox createCredentialsNotExpiredField(String label,
                                                   String help) {
    Checkbox field = createCheckBoxField(label, help);
    add(field, 3);
    return field;
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link Checkbox}
   */
  public Checkbox createEnabledField(String label, String help) {
    Checkbox field = createCheckBoxField(label, help);
    add(field, 3);
    return field;
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("userCode", createUserCodeField($(UserTranslator.CODIGO),
                                         $(UserTranslator.HELP.CODIGO)));
    bind("userName", createUserNameField($(UserTranslator.NOME),
                                         $(UserTranslator.HELP.NOME)));
    bind("enabled", createEnabledField($(UserTranslator.HABILITADO),
                                       $(UserTranslator.HELP.HABILITADO)));
    bind("accountNotExpired",
         createAccountNotExpiredField($(UserTranslator.CONTA_NAO_EXPIRADA),
                                      $(UserTranslator.HELP.CONTA_NAO_EXPIRADA)));
    bind("accountNotLocked",
         createAccountNotLockedField($(UserTranslator.CONTA_NAO_BLOQUEADA),
                                     $(UserTranslator.HELP.CONTA_NAO_BLOQUEADA)));
    bind("credentialsNotExpired",
         createCredentialsNotExpiredField($(UserTranslator.CREDENCIAIS_NAO_EXPIRADAS),
                                          $(UserTranslator.HELP.CREDENCIAIS_NAO_EXPIRADAS)));
    add(this.tabSheet = createTabSheet(), 6);
    bind("privileges",
         createPrivilegesField($(PrivilegeTranslator.PRIVILEGE),
                               privilege -> {
                                 String[] p = privilege.getName()
                                     .split(Operation.SEPARATOR);
                                 return String.format("%s - %s", $(p[0]),
                                                      $(p[1]));
                               }, getViewModel().getConfig()
                                   .getPrivileService()));
    bind("roles",
         createRolesField($(RoleTranslator.ROLE), role -> $(role.getName()),
                          getViewModel().getConfig().getUserRoleService()));
  }

  /**
   * @param label          Título do campo
   * @param labelGenerator {@link ItemLabelGenerator}
   * @param data           {@link DefaultBaseManager}
   * @return {@link CheckboxGroup}
   */
  public CheckboxGroup<PrivilegeDTO> createPrivilegesField(String label,
                                                           ItemLabelGenerator<PrivilegeDTO> labelGenerator,
                                                           DefaultBaseManager<PrivilegeDTO> data) {
    CheckboxGroup<PrivilegeDTO> field = new CheckboxGroup<>();
    field
        .setItems(DataProviderFactory
            .createBaseDataProviderFromService(data,
                                               PrivilegeDTO
                                                   .propertyFilters())
            .withConfigurableFilter());
    field.setItemLabelGenerator(labelGenerator);
    field.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    createTab(tabSheet, VaadinIcon.KEY.create(), label, field);
    return field;
  }

  /**
   * @param label          Título do campo
   * @param labelGenerator {@link ItemLabelGenerator}
   * @param data           {@link DefaultBaseManager}
   * @return {@link CheckboxGroup}
   */
  public CheckboxGroup<UserRoleDTO> createRolesField(String label,
                                                     ItemLabelGenerator<UserRoleDTO> labelGenerator,
                                                     DefaultBaseManager<UserRoleDTO> data) {
    CheckboxGroup<UserRoleDTO> field = new CheckboxGroup<>();
    field
        .setItems(DataProviderFactory
            .createBaseDataProviderFromService(data,
                                               UserRoleDTO
                                                   .propertyFilters())
            .withConfigurableFilter());
    field.setItemLabelGenerator(labelGenerator);
    field.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    createTab(tabSheet, VaadinIcon.DIPLOMA_SCROLL.create(), label, field);
    return field;
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link TextField}
   */
  public TextField createUserCodeField(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link TextField}
   */
  public TextField createUserNameField(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

  @Override
  public UserFormViewModel getViewModel() {
    return (UserFormViewModel) super.getViewModel();
  }
}
