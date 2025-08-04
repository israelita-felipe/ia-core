package com.ia.core.security.view.role.form;

import com.ia.core.security.model.functionality.Operation;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RoleTranslator;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.service.DefaultBaseService;
import com.ia.core.view.utils.DataProviderFactory;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Formulário para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
public class RoleFormView
  extends FormView<RoleDTO> {
  /** Serial UID */
  private static final long serialVersionUID = -1605967534164418961L;
  /** Tab principal */
  private TabSheet tabSheet;

  /**
   * @param viewModel View Model do formulário
   */
  public RoleFormView(IFormViewModel<RoleDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind("name", createNameField($(RoleTranslator.ROLE),
                                       $(RoleTranslator.HELP.ROLE)));
    add(this.tabSheet = createTabSheet(), 6);
    bind("privileges",
         createPrivilegesField($(PrivilegeTranslator.PRIVILEGE),
                               privilege -> {
                                 String[] p = privilege.getName()
                                     .split(Operation.SEPARATOR);
                                 return String.format("%s - %s", $(p[0]),
                                                      $(p[1]));
                               }, getViewModel().getPrivilegeService()));
    bind("users",
         createUsersField($(UserTranslator.USER), UserDTO::toString,
                          getViewModel().getUserService()));
  }

  /**
   * @param label Título do campo
   * @param help  Ajuda do campo
   * @return {@link TextField}
   */
  public TextField createNameField(String label, String help) {
    TextField field = createTextField(label, help);
    add(field, 6);
    return field;
  }

  /**
   * @param label          Título do campo
   * @param labelGenerator Gerador de label dos itens
   * @param data           serviço de dados
   * @return {@link CheckboxGroup}
   */
  public CheckboxGroup<PrivilegeDTO> createPrivilegesField(String label,
                                                           ItemLabelGenerator<PrivilegeDTO> labelGenerator,
                                                           DefaultBaseService<PrivilegeDTO> data) {

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
   * @param labelGenerator Gerador de label dos itens
   * @param data           serviço de dados
   * @return {@link CheckboxGroup}
   */
  public CheckboxGroup<UserDTO> createUsersField(String label,
                                                 ItemLabelGenerator<UserDTO> labelGenerator,
                                                 DefaultBaseService<UserDTO> data) {

    CheckboxGroup<UserDTO> field = new CheckboxGroup<>();
    field.setItems(DataProviderFactory
        .createBaseDataProviderFromService(data, UserDTO.propertyFilters())
        .withConfigurableFilter());
    field.setItemLabelGenerator(labelGenerator);
    field.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
    createTab(tabSheet, VaadinIcon.USER.create(), label, field);
    return field;
  }

  @Override
  public RoleFormViewModel getViewModel() {
    return (RoleFormViewModel) super.getViewModel();
  }

}
