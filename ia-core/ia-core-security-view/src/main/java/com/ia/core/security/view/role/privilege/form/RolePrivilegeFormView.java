package com.ia.core.security.view.role.privilege.form;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeTranslator;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.service.model.user.UserTranslator;
import com.ia.core.security.view.privilege.operation.page.PrivilegeOperationPageView;
import com.ia.core.security.view.privilege.operation.page.PrivilegeOperationPageViewModel;
import com.ia.core.view.components.form.FormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.manager.DefaultBaseManager;
import com.ia.core.view.utils.DataProviderFactory;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;

/**
 * View de formulário de usuário
 *
 * @author Israel Araújo
 */
public class RolePrivilegeFormView
  extends FormView<RolePrivilegeDTO> {
  /** Serial UUID */
  private static final long serialVersionUID = 650617024016045875L;

  /**
   * @param viewModel View Model
   */
  public RolePrivilegeFormView(IFormViewModel<RolePrivilegeDTO> viewModel) {
    super(viewModel);
  }

  @Override
  public void createLayout() {
    super.createLayout();
    bind(RolePrivilegeDTO.CAMPOS.PRIVILEGE,
         createPrivilegeField($(PrivilegeTranslator.PRIVILEGE),
                              $(PrivilegeTranslator.HELP.PRIVILEGE),
                              PrivilegeDTO::getName, getViewModel()
                                  .getConfig().getPrivileManager()));
    createPrivilegeOperationField($(UserTranslator.NOME),
                                  $(UserTranslator.HELP.NOME),
                                  getViewModel()
                                      .getPrivilegeOperationPageViewModel());
  }

  /**
   * @param label
   * @param help
   * @param privilegeOperationPageViewModel
   * @return
   */
  public PrivilegeOperationPageView createPrivilegeOperationField(String label,
                                                                  String help,
                                                                  PrivilegeOperationPageViewModel privilegeOperationPageViewModel) {
    PrivilegeOperationPageView field = new PrivilegeOperationPageView(privilegeOperationPageViewModel);
    field.setLabel(label);
    setHelp(field, help);
    add(field, 6);
    return field;
  }

  /**
   * @param label          Título do campo
   * @param labelGenerator {@link ItemLabelGenerator}
   * @param manager        {@link DefaultBaseManager}
   * @return {@link CheckboxGroup}
   */
  public ComboBox<PrivilegeDTO> createPrivilegeField(String label,
                                                     String help,
                                                     ItemLabelGenerator<PrivilegeDTO> labelGenerator,
                                                     DefaultBaseManager<PrivilegeDTO> manager) {
    ComboBox<PrivilegeDTO> field = createComboBox(label, help,
                                                  DataProviderFactory
                                                      .createBaseDataProviderFromManager(manager,
                                                                                         PrivilegeDTO
                                                                                             .propertyFilters()),
                                                  labelGenerator);

    add(field, 6);
    return field;
  }

  @Override
  public RolePrivilegeFormViewModel getViewModel() {
    return (RolePrivilegeFormViewModel) super.getViewModel();
  }
}
