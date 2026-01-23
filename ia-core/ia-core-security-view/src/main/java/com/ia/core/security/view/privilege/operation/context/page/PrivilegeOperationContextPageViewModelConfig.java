package com.ia.core.security.view.privilege.operation.context.page;

import java.util.List;
import java.util.function.Supplier;

import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.view.privilege.operation.context.form.PrivilegeOperationContextFormViewModelConfig;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModelConfig;
import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

import lombok.Getter;

/**
 *
 */
public class PrivilegeOperationContextPageViewModelConfig
  extends CollectionPageViewModelConfig<PrivilegeOperationContextDTO> {
  @Getter
  private final List<HasContext> hasContext;
  @Getter
  private final Supplier<PrivilegeDTO> privilege;

  /**
   * @param service
   */
  public PrivilegeOperationContextPageViewModelConfig(DefaultCollectionBaseManager<PrivilegeOperationContextDTO> service,
                                                      Supplier<PrivilegeDTO> privilege,
                                                      List<HasContext> hasContext) {
    super(service);
    this.hasContext = hasContext;
    this.privilege = privilege;
  }

  @Override
  protected FormViewModelConfig<PrivilegeOperationContextDTO> createFormViewModelConfig(boolean readOnly) {
    return new PrivilegeOperationContextFormViewModelConfig(readOnly,
                                                            privilege,
                                                            hasContext);
  }
}
