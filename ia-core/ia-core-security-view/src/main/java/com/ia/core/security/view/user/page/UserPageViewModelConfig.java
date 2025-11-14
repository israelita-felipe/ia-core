package com.ia.core.security.view.user.page;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.security.view.user.UserManager;
import com.ia.core.security.view.user.form.UserFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.Getter;

/**
 *
 */
@UIScope
@Component
public class UserPageViewModelConfig
  extends EntityPageViewModelConfig<UserDTO> {
  @Getter
  private AuthenticationDetails authentication;
  @Getter
  private PrivilegeManager privilegeService;
  @Getter
  private RoleManager roleService;

  /**
   * @param service
   * @param logOperationService
   */
  public UserPageViewModelConfig(AuthenticationDetails authentication,
                                 UserManager service,
                                 PrivilegeManager privilegeService,
                                 RoleManager roleService,
                                 LogOperationManager logOperationService) {
    super(service, logOperationService);
    this.authentication = authentication;
    this.privilegeService = privilegeService;
    this.roleService = roleService;
  }

  @Override
  protected FormViewModelConfig<UserDTO> createFormViewModelConfig(boolean readOnly) {
    return new UserFormViewModelConfig(readOnly, privilegeService,
                                       roleService);
  }
}
