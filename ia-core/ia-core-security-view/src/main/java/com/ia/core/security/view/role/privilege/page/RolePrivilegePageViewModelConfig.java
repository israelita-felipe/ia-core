package com.ia.core.security.view.role.privilege.page;

import java.util.List;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.role.privilege.form.RolePrivilegeFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

import lombok.Getter;

/**
 *
 */
public class RolePrivilegePageViewModelConfig
  extends CollectionPageViewModelConfig<RolePrivilegeDTO> {
  @Getter
  private final List<HasContext> hasContext;
  @Getter
  private final PrivilegeManager privilegeManager;

  /**
   * @param service
   */
  public RolePrivilegePageViewModelConfig(DefaultCollectionBaseManager<RolePrivilegeDTO> service,
                                          PrivilegeManager privilegeManager,
                                          List<HasContext> hasContext) {
    super(service);
    this.hasContext = hasContext;
    this.privilegeManager = privilegeManager;
  }

  @Override
  protected FormViewModelConfig<RolePrivilegeDTO> createFormViewModelConfig(boolean readOnly) {
    return new RolePrivilegeFormViewModelConfig(readOnly, privilegeManager,
                                                hasContext);
  }
}
