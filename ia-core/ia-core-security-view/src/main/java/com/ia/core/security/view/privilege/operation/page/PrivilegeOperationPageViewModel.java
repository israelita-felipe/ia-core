package com.ia.core.security.view.privilege.operation.page;

import com.ia.core.security.service.model.privilege.PrivilegeOperationDTO;
import com.ia.core.security.view.privilege.operation.form.PrivilegeOperationFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModel;

/**
 * @author Israel Araújo
 */
public class PrivilegeOperationPageViewModel
  extends CollectionPageViewModel<PrivilegeOperationDTO> {

  /**
   * @param service
   */
  public PrivilegeOperationPageViewModel(PrivilegeOperationPageViewModelConfig config) {
    super(config);

  }

  @Override
  public IFormViewModel<PrivilegeOperationDTO> createFormViewModel(FormViewModelConfig<PrivilegeOperationDTO> config) {
    return new PrivilegeOperationFormViewModel(config.cast());
  }

  @Override
  public PrivilegeOperationPageViewModelConfig getConfig() {
    return (PrivilegeOperationPageViewModelConfig) super.getConfig();
  }

  @Override
  public PrivilegeOperationDTO createNewObject() {
    return PrivilegeOperationDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return PrivilegeOperationDTO.getSearchRequest();
  }

  @Override
  public Class<PrivilegeOperationDTO> getType() {
    return PrivilegeOperationDTO.class;
  }

  @Override
  public PrivilegeOperationDTO cloneObject(PrivilegeOperationDTO object) {
    return object.cloneObject();
  }

  @Override
  public PrivilegeOperationDTO copyObject(PrivilegeOperationDTO object) {
    return object.copyObject();
  }

  @Override
  public Long getId(PrivilegeOperationDTO object) {
    return object.getId();
  }
}
