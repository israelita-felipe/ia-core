package com.ia.core.security.view.role.page;

import com.ia.core.security.service.model.authorization.HasContext;
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
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Classe que representa as configurações para role page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RolePageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
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
  @Getter
  private final List<HasContext> hasContext;

  /**
   * @param service
   * @param logOperationService
   */

public RolePageViewModelConfig(RoleManager roleService,
                                 UserManager userService,
                                 PrivilegeManager privilegeService,
                                 List<HasContext> hasContext,
                                 LogOperationManager logOperationService) {
    super(roleService, logOperationService);
    this.privilegeService = privilegeService;
    this.userService = userService;
    this.hasContext = hasContext;
  }

  @Override
  protected FormViewModelConfig<RoleDTO> createFormViewModelConfig(boolean readOnly) {
    return new RoleFormViewModelConfig(readOnly, userService,
                                       privilegeService, hasContext);
  }
}
