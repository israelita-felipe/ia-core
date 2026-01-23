package com.ia.core.security.view.privilege.operation.context.form;

import java.util.List;
import java.util.function.Supplier;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
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
