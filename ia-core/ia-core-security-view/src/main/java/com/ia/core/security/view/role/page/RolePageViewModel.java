package com.ia.core.security.view.role.page;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.log.operation.LogOperationDTO;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.security.view.role.RoleManager;
import com.ia.core.security.view.role.form.RoleFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * View Model para {@link RoleDTO}
 *
 * @author Israel Araújo
 */
@UIScope
@Component
public class RolePageViewModel
  extends EntityPageViewModel<RoleDTO> {

  /**
   * @param roleService         seviço de {@link RoleDTO}
   * @param userService         serviço de {@link UserDTO}
   * @param privilegeService    serviço de {@link PrivilegeDTO}
   * @param logOperationService serviço de {@link LogOperationDTO}
   */
  public RolePageViewModel(RolePageViewModelConfig config) {
    super(config);
  }

  @Override
  public RoleDTO cloneObject(RoleDTO object) {
    return object.cloneObject();
  }

  @Override
  public IFormViewModel<RoleDTO> createFormViewModel(FormViewModelConfig<RoleDTO> config) {
    return new RoleFormViewModel(config.cast());
  }

  @Override
  public RolePageViewModelConfig getConfig() {
    return (RolePageViewModelConfig) super.getConfig();
  }

  @Override
  public RoleDTO createNewObject() {
    return RoleDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return RoleDTO.getSearchRequest();
  }

  @Override
  public Long getId(RoleDTO object) {
    return object.getId();
  }

  @Override
  public RoleManager getService() {
    return (RoleManager) super.getService();
  }

  @Override
  public Class<RoleDTO> getType() {
    return RoleDTO.class;
  }
}
