package com.ia.core.security.view.role.privilege.form;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

import java.util.List;

/**
 *
 */
/**
 * Classe de configuração para role privilege form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RolePrivilegeFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class RolePrivilegeFormViewModelConfig
  extends FormViewModelConfig<RolePrivilegeDTO> {
  /** {@link PrivilegeManager} */
  @Getter
  private final PrivilegeManager privileManager;
  @Getter
  private final List<HasContext> hasContext;

  /**
   * @param readOnly
   * @param privileManager
   * @param roleService
   */
  public RolePrivilegeFormViewModelConfig(boolean readOnly,
                                          PrivilegeManager privileManager,
                                          List<HasContext> hasContext) {
    super(readOnly);
    this.privileManager = privileManager;
    this.hasContext = hasContext;
  }
}
