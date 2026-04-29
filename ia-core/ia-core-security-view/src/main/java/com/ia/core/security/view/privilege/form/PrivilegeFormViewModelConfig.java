package com.ia.core.security.view.privilege.form;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

import java.util.List;

/**
 *
 */
/**
 * Classe de configuração para privilege form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class PrivilegeFormViewModelConfig
  extends FormViewModelConfig<PrivilegeDTO> {
  @Getter
  private final List<HasContext> hasContext;

  /**
   * @param readOnly
   * @param privileService
   * @param roleService
   */
  public PrivilegeFormViewModelConfig(boolean readOnly,
                                      List<HasContext> hasContext) {
    super(readOnly);
    this.hasContext = hasContext;
  }

}
