package com.ia.core.security.view.privilege.operation.context.page;

import com.ia.core.security.service.model.privilege.PrivilegeOperationContextDTO;
import com.ia.core.security.view.privilege.operation.context.form.PrivilegeOperationContextFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModel;
/**
 * Model de dados para a view de privilege operation context page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a PrivilegeOperationContextPageViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class PrivilegeOperationContextPageViewModel
  extends CollectionPageViewModel<PrivilegeOperationContextDTO> {

  /**
   * @param service
   */
  public PrivilegeOperationContextPageViewModel(PrivilegeOperationContextPageViewModelConfig config) {
    super(config);

  }

  @Override
  public IFormViewModel<PrivilegeOperationContextDTO> createFormViewModel(FormViewModelConfig<PrivilegeOperationContextDTO> config) {
    return new PrivilegeOperationContextFormViewModel(config.cast());
  }

  @Override
  public PrivilegeOperationContextPageViewModelConfig getConfig() {
    return (PrivilegeOperationContextPageViewModelConfig) super.getConfig();
  }

  @Override
  public PrivilegeOperationContextDTO createNewObject() {
    return PrivilegeOperationContextDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return PrivilegeOperationContextDTO.getSearchRequest();
  }

  @Override
  public Class<PrivilegeOperationContextDTO> getType() {
    return PrivilegeOperationContextDTO.class;
  }

  @Override
  public PrivilegeOperationContextDTO cloneObject(PrivilegeOperationContextDTO object) {
    return object.cloneObject();
  }

  @Override
  public PrivilegeOperationContextDTO copyObject(PrivilegeOperationContextDTO object) {
    return object.copyObject();
  }

}
