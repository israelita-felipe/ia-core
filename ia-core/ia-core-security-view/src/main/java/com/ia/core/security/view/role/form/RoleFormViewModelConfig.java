package com.ia.core.security.view.role.form;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.security.view.user.UserManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

import java.util.List;

/**
 *
 */
/**
 * Classe de configuração para role form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RoleFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class RoleFormViewModelConfig
  extends FormViewModelConfig<RoleDTO> {
  /** Serviço de usuário */
  @Getter
  private final UserManager userService;
  /**
   * serviço de privilégio
   */
  @Getter
  private final PrivilegeManager privilegeService;

  @Getter
  private final List<HasContext> hasContext;

  /**
   * @param readOnly
   * @param userService
   * @param privilegeService
   */
  public RoleFormViewModelConfig(boolean readOnly, UserManager userService,
                                 PrivilegeManager privilegeService,
                                 List<HasContext> hasContext) {
    super(readOnly);
    this.userService = userService;
    this.privilegeService = privilegeService;
    this.hasContext = hasContext;
  }

}
