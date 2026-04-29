package com.ia.core.security.view.privilege.operation.context.form;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import lombok.Getter;

import java.util.List;
import java.util.function.Supplier;

/**
 *
 */
/**
 * Classe de configuração para privilege operation context form view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationContextFormViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class PrivilegeOperationContextFormViewModelConfig
  extends FormViewModelConfig<PrivilegeOperationContextDTO> {
  @Getter
  private final List<HasContext> hasContext;
  @Getter
  private final Supplier<PrivilegeDTO> privilege;

  /**
   * @param readOnly
   * @param privileService
   * @param roleService
   */
  public PrivilegeOperationContextFormViewModelConfig(boolean readOnly,
                                                      Supplier<PrivilegeDTO> privilege,
                                                      List<HasContext> hasContext) {
    super(readOnly);
    this.hasContext = hasContext;
    this.privilege = privilege;
  }

}
