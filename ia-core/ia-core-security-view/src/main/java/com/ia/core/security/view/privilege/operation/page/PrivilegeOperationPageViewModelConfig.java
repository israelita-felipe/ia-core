package com.ia.core.security.view.privilege.operation.page;

import java.util.List;
import java.util.function.Supplier;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.view.privilege.operation.form.PrivilegeOperationFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

import lombok.Getter;

/**
 *
 */
public class PrivilegeOperationPageViewModelConfig
  extends CollectionPageViewModelConfig<PrivilegeOperationDTO> {
  @Getter
  private final List<HasContext> hasContext;
  @Getter
  private final Supplier<PrivilegeDTO> privilege;

  /**
   * @param service
   */
  public PrivilegeOperationPageViewModelConfig(DefaultCollectionBaseManager<PrivilegeOperationDTO> service,
                                               Supplier<PrivilegeDTO> privilege,
                                               List<HasContext> hasContext) {
    super(service);
    this.hasContext = hasContext;
    this.privilege = privilege;
  }

  @Override
  protected FormViewModelConfig<PrivilegeOperationDTO> createFormViewModelConfig(boolean readOnly) {
    return new PrivilegeOperationFormViewModelConfig(readOnly, privilege,
                                                     hasContext);
  }
}
