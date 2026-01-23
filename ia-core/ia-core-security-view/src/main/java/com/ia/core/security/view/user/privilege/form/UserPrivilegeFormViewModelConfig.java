package com.ia.core.security.view.user.privilege.form;

import java.util.List;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;

import lombok.Getter;

/**
 *
 */
public class UserPrivilegeFormViewModelConfig
  extends FormViewModelConfig<UserPrivilegeDTO> {
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
  public UserPrivilegeFormViewModelConfig(boolean readOnly,
                                          PrivilegeManager privileManager,
                                          List<HasContext> hasContext) {
    super(readOnly);
    this.privileManager = privileManager;
    this.hasContext = hasContext;
  }
}
