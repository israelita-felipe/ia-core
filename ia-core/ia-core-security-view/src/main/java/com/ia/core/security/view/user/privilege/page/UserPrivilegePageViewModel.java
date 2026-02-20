package com.ia.core.security.view.user.privilege.page;

import com.ia.core.security.service.model.user.UserPrivilegeDTO;
import com.ia.core.security.view.user.privilege.form.UserPrivilegeFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModel;

/**
 * @author Israel Araújo
 */
public class UserPrivilegePageViewModel
  extends CollectionPageViewModel<UserPrivilegeDTO> {

  /**
   * @param service
   */
  public UserPrivilegePageViewModel(UserPrivilegePageViewModelConfig config) {
    super(config);

  }

  @Override
  public IFormViewModel<UserPrivilegeDTO> createFormViewModel(FormViewModelConfig<UserPrivilegeDTO> config) {
    return new UserPrivilegeFormViewModel(config.cast());
  }

  @Override
  public UserPrivilegePageViewModelConfig getConfig() {
    return (UserPrivilegePageViewModelConfig) super.getConfig();
  }

  @Override
  public UserPrivilegeDTO createNewObject() {
    return UserPrivilegeDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return UserPrivilegeDTO.getSearchRequest();
  }

  @Override
  public Class<UserPrivilegeDTO> getType() {
    return UserPrivilegeDTO.class;
  }

  @Override
  public UserPrivilegeDTO cloneObject(UserPrivilegeDTO object) {
    return object.cloneObject();
  }

  @Override
  public UserPrivilegeDTO copyObject(UserPrivilegeDTO object) {
    return object.copyObject();
  }

}
