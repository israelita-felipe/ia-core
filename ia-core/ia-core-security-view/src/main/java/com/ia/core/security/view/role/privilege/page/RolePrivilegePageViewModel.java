package com.ia.core.security.view.role.privilege.page;

import com.ia.core.security.service.model.role.RolePrivilegeDTO;
import com.ia.core.security.view.role.privilege.form.RolePrivilegeFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.page.viewModel.CollectionPageViewModel;
/**
 * Model de dados para a view de role privilege page.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a RolePrivilegePageViewModel
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
public class RolePrivilegePageViewModel
  extends CollectionPageViewModel<RolePrivilegeDTO> {

  /**
   * @param service
   */
  public RolePrivilegePageViewModel(RolePrivilegePageViewModelConfig config) {
    super(config);

  }

  @Override
  public IFormViewModel<RolePrivilegeDTO> createFormViewModel(FormViewModelConfig<RolePrivilegeDTO> config) {
    return new RolePrivilegeFormViewModel(config.cast());
  }

  @Override
  public RolePrivilegePageViewModelConfig getConfig() {
    return (RolePrivilegePageViewModelConfig) super.getConfig();
  }

  @Override
  public RolePrivilegeDTO createNewObject() {
    return RolePrivilegeDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return RolePrivilegeDTO.getSearchRequest();
  }

  @Override
  public Class<RolePrivilegeDTO> getType() {
    return RolePrivilegeDTO.class;
  }

  @Override
  public RolePrivilegeDTO cloneObject(RolePrivilegeDTO object) {
    return object.cloneObject();
  }

  @Override
  public RolePrivilegeDTO copyObject(RolePrivilegeDTO object) {
    return object.copyObject();
  }

}
