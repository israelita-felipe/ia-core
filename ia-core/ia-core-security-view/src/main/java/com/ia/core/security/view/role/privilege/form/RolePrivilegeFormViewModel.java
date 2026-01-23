package com.ia.core.security.view.role.privilege.form;

import java.util.Collection;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.privilege.operation.page.PrivilegeOperationPageViewModel;
import com.ia.core.security.view.privilege.operation.page.PrivilegeOperationPageViewModelConfig;
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
public class RolePrivilegeFormViewModel
  extends FormViewModel<RolePrivilegeDTO> {

  @Getter
  private PrivilegeOperationPageViewModel privilegeOperationPageViewModel;

  /**
   * @param readOnly         indicativo de somente leitura
   * @param privilegeService {@link PrivilegeManager}
   * @param roleService      {@link RoleManager}
   */
  public RolePrivilegeFormViewModel(RolePrivilegeFormViewModelConfig config) {
    super(config);
    this.privilegeOperationPageViewModel = createPrivilegeOperationPageViewModel(config);
  }

  /**
   * @param config
   * @return
   */
  private PrivilegeOperationPageViewModel createPrivilegeOperationPageViewModel(RolePrivilegeFormViewModelConfig config) {
    return new PrivilegeOperationPageViewModel(new PrivilegeOperationPageViewModelConfig(createPrivilegeOperationManager(config),
                                                                                         this::getPrivilege,
                                                                                         config
                                                                                             .getHasContext()));
  }

  public PrivilegeDTO getPrivilege() {
    RolePrivilegeDTO model = getModel();
    if (model == null) {
      return null;
    }
    return model.getPrivilege();
  }

  /**
   * @param config
   * @return
   */
  private DefaultCollectionBaseManager<PrivilegeOperationDTO> createPrivilegeOperationManager(RolePrivilegeFormViewModelConfig config) {
    return ManagerFactory
        .createManagerFromCollection(this::getPrivilegeOperations,
                                     PrivilegeOperationDTO::getId);
  }

  public Collection<PrivilegeOperationDTO> getPrivilegeOperations() {
    return getModel().getOperations();
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.privilegeOperationPageViewModel != null) {
      this.privilegeOperationPageViewModel.setReadOnly(readOnly);
    }
  }

  @Override
  public RolePrivilegeFormViewModelConfig getConfig() {
    return (RolePrivilegeFormViewModelConfig) super.getConfig();
  }
}
