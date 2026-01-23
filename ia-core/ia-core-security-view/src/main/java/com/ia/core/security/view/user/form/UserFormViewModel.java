package com.ia.core.security.view.user.form;

import java.util.Collection;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.security.view.user.privilege.page.UserPrivilegePageViewModel;
import com.ia.core.security.view.user.privilege.page.UserPrivilegePageViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModel;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;
import com.ia.core.view.utils.ManagerFactory;

import lombok.Getter;

/**
 * View Model formulário de usuário
 *
 * @author Israel Araújo
 */
public class UserFormViewModel
  extends FormViewModel<UserDTO> {
  @Getter
  private UserPrivilegePageViewModel userPrivilegePageViewModel;

  /**
   * @param readOnly         indicativo de somente leitura
   * @param privilegeService {@link PrivilegeManager}
   * @param roleService      {@link RoleManager}
   */
  public UserFormViewModel(UserFormViewModelConfig config) {
    super(config);
    this.userPrivilegePageViewModel = createUserPrivilegePageViewModel(config);
  }

  /**
   * @param config
   * @return
   */
  private UserPrivilegePageViewModel createUserPrivilegePageViewModel(UserFormViewModelConfig config) {
    return new UserPrivilegePageViewModel(new UserPrivilegePageViewModelConfig(createUserPrivilegeManager(config),
                                                                               config
                                                                                   .getPrivileService(),
                                                                               config
                                                                                   .getHasContext()));
  }

  /**
   * @param config
   * @return
   */
  private DefaultCollectionBaseManager<UserPrivilegeDTO> createUserPrivilegeManager(UserFormViewModelConfig config) {
    return ManagerFactory
        .createManagerFromCollection(this::getUserPrivileges,
                                     UserPrivilegeDTO::getId);
  }

  public Collection<UserPrivilegeDTO> getUserPrivileges() {
    return getModel().getPrivileges();
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.userPrivilegePageViewModel != null) {
      this.userPrivilegePageViewModel.setReadOnly(readOnly);
    }
  }

  @Override
  public UserFormViewModelConfig getConfig() {
    return (UserFormViewModelConfig) super.getConfig();
  }
}
