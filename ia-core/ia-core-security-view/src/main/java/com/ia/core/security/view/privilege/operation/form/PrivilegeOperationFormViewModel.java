package com.ia.core.security.view.privilege.operation.form;

import java.util.Collection;

import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.privilege.operation.context.page.PrivilegeOperationContextPageViewModel;
import com.ia.core.security.view.privilege.operation.context.page.PrivilegeOperationContextPageViewModelConfig;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;
import com.ia.core.view.utils.ManagerFactory;

import lombok.Getter;

/**
 * View Model formulário de usuário
 *
 * @author Israel Araújo
 */
public class PrivilegeOperationFormViewModel
  extends FormViewModel<PrivilegeOperationDTO> {

  @Getter
  private PrivilegeOperationContextPageViewModel privilegeOperationContextPageViewModel;

  /**
   * @param readOnly         indicativo de somente leitura
   * @param privilegeService {@link PrivilegeManager}
   * @param roleService      {@link RoleManager}
   */
  public PrivilegeOperationFormViewModel(PrivilegeOperationFormViewModelConfig config) {
    super(config);
    createPrivilegeOperationContextPageViewModel(config);
  }

  /**
   * @param config
   */
  private void createPrivilegeOperationContextPageViewModel(PrivilegeOperationFormViewModelConfig config) {
    this.privilegeOperationContextPageViewModel = new PrivilegeOperationContextPageViewModel(new PrivilegeOperationContextPageViewModelConfig(createPrivilegeOperationContextManager(config),
                                                                                                                                              config
                                                                                                                                                  .getPrivilege(),
                                                                                                                                              config
                                                                                                                                                  .getHasContext()));
  }

  /**
   * @param config
   * @return
   */
  private DefaultCollectionBaseManager<PrivilegeOperationContextDTO> createPrivilegeOperationContextManager(PrivilegeOperationFormViewModelConfig config) {
    return ManagerFactory
        .createManagerFromCollection(this::getPrivilegeOperationContexts,
                                     PrivilegeOperationContextDTO::getId);
  }

  public Collection<PrivilegeOperationContextDTO> getPrivilegeOperationContexts() {
    return getModel().getContext();
  }

  @Override
  public PrivilegeOperationFormViewModelConfig getConfig() {
    return (PrivilegeOperationFormViewModelConfig) super.getConfig();
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.privilegeOperationContextPageViewModel != null) {
      this.privilegeOperationContextPageViewModel.setReadOnly(readOnly);
    }
  }
}
