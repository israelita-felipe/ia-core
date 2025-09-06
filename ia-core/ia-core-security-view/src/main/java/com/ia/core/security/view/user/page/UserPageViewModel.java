package com.ia.core.security.view.user.page;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.user.UserDTO;
import com.ia.core.security.view.authentication.AuthenticationDetails;
import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.security.view.log.operation.list.AuditOperationListViewModel;
import com.ia.core.security.view.log.operation.list.AuditOperationListViewModelConfig;
import com.ia.core.security.view.log.operation.list.LogOperationListViewModel;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.security.view.privilege.PrivilegeService;
import com.ia.core.security.view.role.RoleService;
import com.ia.core.security.view.user.UserService;
import com.ia.core.security.view.user.form.UserFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * @author Israel Araújo
 */
@UIScope
@Component
public class UserPageViewModel
  extends EntityPageViewModel<UserDTO> {

  /** View Model de listagem de adição */
  private AuditOperationListViewModel auditOperationListViewModel;

  /**
   * @param authentication      {@link AuthenticationDetails}
   * @param service             {@link UserService}
   * @param privilegeService    {@link PrivilegeService}
   * @param roleService         {@link RoleService}
   * @param logOperationService {@link LogOperationService}
   */
  public UserPageViewModel(UserPageViewModelConfig config) {
    super(config);
    this.auditOperationListViewModel = createAuditOperationListViewModel();
  }

  /**
   * Cria o viewModel para log de operação
   *
   * @param logOperationService {@link LogOperationService}
   * @return {@link AuditOperationListViewModel} criado
   */
  protected AuditOperationListViewModel createAuditOperationListViewModel() {
    return new AuditOperationListViewModel(new AuditOperationListViewModelConfig(isReadOnly(),
                                                                                 getConfig()
                                                                                     .getLogOperationService()));
  }

  @Override
  public UserDTO cloneObject(UserDTO object) {
    return object.cloneObject();
  }

  @Override
  public IFormViewModel<UserDTO> createFormViewModel(FormViewModelConfig<UserDTO> config) {
    return new UserFormViewModel(config.cast());
  }

  @Override
  public UserDTO createNewObject() {
    return UserDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return UserDTO.getSearchRequest();
  }

  @Override
  public UUID getId(UserDTO object) {
    return object.getId();
  }

  @Override
  public UserService getService() {
    return (UserService) super.getService();
  }

  @Override
  public Class<UserDTO> getType() {
    return UserDTO.class;
  }

  /**
   * @param user {@link UserDTO} que será resetado.
   */
  public void resetPassword(UserDTO user) {
    getService().resetPassword(user, getConfig().getAuthentication()
        .getAuthenticatedUser());
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    super.setReadOnly(readOnly);
    if (this.auditOperationListViewModel != null) {
      this.auditOperationListViewModel.setReadOnly(readOnly);
    }
  }

  @Override
  public UserPageViewModelConfig getConfig() {
    return (UserPageViewModelConfig) super.getConfig();
  }

  /**
   * @param object Objeto a ser visualizado a auditoria
   * @return {@link LogOperationListViewModel}
   */
  public AuditOperationListViewModel viewAudit(UserDTO object) {
    this.auditOperationListViewModel.refreshAudit(object.getUserCode());
    return this.auditOperationListViewModel;
  }
}
