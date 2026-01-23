package com.ia.core.security.view.user.page;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.authorization.HasContext;
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
  @Getter
  private List<HasContext> hasContexts;

  /**
   * @param service
   * @param logOperationService
   */
  public UserPageViewModelConfig(AuthenticationDetails authentication,
                                 UserManager service,
                                 PrivilegeManager privilegeService,
                                 RoleManager roleService,
                                 LogOperationManager logOperationService,
                                 List<HasContext> hasContexts) {
    super(service, logOperationService);
    this.authentication = authentication;
    this.privilegeService = privilegeService;
    this.roleService = roleService;
    this.hasContexts = hasContexts;
  }

  @Override
  protected FormViewModelConfig<UserDTO> createFormViewModelConfig(boolean readOnly) {
    return new UserFormViewModelConfig(readOnly, privilegeService,
                                       roleService, hasContexts);
  }
}
