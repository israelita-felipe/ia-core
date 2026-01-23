package com.ia.core.security.view.role.form;

import java.util.Collection;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.view.role.privilege.page.RolePrivilegePageViewModel;
import com.ia.core.security.view.role.privilege.page.RolePrivilegePageViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;
import com.ia.core.view.utils.ManagerFactory;

import lombok.Getter;

/**
 * View Model de formulário para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
public class RoleFormViewModel
  extends FormViewModel<RoleDTO> {
  @Getter
  private RolePrivilegePageViewModel rolePrivilegePageViewModel;

  /**
   * @param readOnly         indicativo de somente leitura
   * @param userService      serviço de usuário
   * @param privilegeService serviço de privilégio
   */
  public RoleFormViewModel(RoleFormViewModelConfig config) {
    super(config);
    this.rolePrivilegePageViewModel = createRolePrivilegePageViewModel(config);
  }

  /**
   * @param config
   * @return
   */
  private RolePrivilegePageViewModel createRolePrivilegePageViewModel(RoleFormViewModelConfig config) {
    return new RolePrivilegePageViewModel(new RolePrivilegePageViewModelConfig(createRolePrivilegeManager(config),
                                                                               config
                                                                                   .getPrivilegeService(),
                                                                               config
                                                                                   .getHasContext()));
  }

  /**
   * @param config
   * @return
   */
  private DefaultCollectionBaseManager<RolePrivilegeDTO> createRolePrivilegeManager(RoleFormViewModelConfig config) {
    return ManagerFactory
        .createManagerFromCollection(this::getRolePrivileges,
                                     RolePrivilegeDTO::getId);
  }

  public Collection<RolePrivilegeDTO> getRolePrivileges() {
    return getModel().getPrivileges();
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.rolePrivilegePageViewModel != null) {
      this.rolePrivilegePageViewModel.setReadOnly(readOnly);
    }
  }

  @Override
  public RoleFormViewModelConfig getConfig() {
    return (RoleFormViewModelConfig) super.getConfig();
  }
}
