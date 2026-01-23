package com.ia.core.security.view.user.privilege.page;

import java.util.List;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.user.privilege.form.UserPrivilegeFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

import lombok.Getter;

/**
 *
 */
public class UserPrivilegePageViewModelConfig
  extends CollectionPageViewModelConfig<UserPrivilegeDTO> {
  @Getter
  private final List<HasContext> hasContext;
  @Getter
  private final PrivilegeManager privilegeManager;

  /**
   * @param service
   */
  public UserPrivilegePageViewModelConfig(DefaultCollectionBaseManager<UserPrivilegeDTO> service,
                                          PrivilegeManager privilegeManager,
                                          List<HasContext> hasContext) {
    super(service);
    this.hasContext = hasContext;
    this.privilegeManager = privilegeManager;
  }

  @Override
  protected FormViewModelConfig<UserPrivilegeDTO> createFormViewModelConfig(boolean readOnly) {
    return new UserPrivilegeFormViewModelConfig(readOnly, privilegeManager,
                                                hasContext);
  }
}
