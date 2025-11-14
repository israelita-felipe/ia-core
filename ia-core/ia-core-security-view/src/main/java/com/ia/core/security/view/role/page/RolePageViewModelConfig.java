package com.ia.core.security.view.role.page;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.security.view.role.form.RoleFormViewModelConfig;
import com.ia.core.security.view.user.UserManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Getter;

/**
 *
 */
@UIScope
@Component
public class RolePageViewModelConfig
  extends EntityPageViewModelConfig<RoleDTO> {
  /**
   * serviço de privilege
   */
  @Getter
  private final PrivilegeManager privilegeService;
  /**
   * serviço de usuário
   */
  @Getter
  private final UserManager userService;

  /**
   * @param service
   * @param logOperationService
   */
  public RolePageViewModelConfig(RoleManager roleService,
                                 UserManager userService,
                                 PrivilegeManager privilegeService,
                                 LogOperationManager logOperationService) {
    super(roleService, logOperationService);
    this.privilegeService = privilegeService;
    this.userService = userService;
  }

  @Override
  protected FormViewModelConfig<RoleDTO> createFormViewModelConfig(boolean readOnly) {
    return new RoleFormViewModelConfig(readOnly, userService,
                                       privilegeService);
  }
}
