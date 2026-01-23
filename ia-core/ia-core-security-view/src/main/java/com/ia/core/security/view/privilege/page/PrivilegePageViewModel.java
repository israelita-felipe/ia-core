package com.ia.core.security.view.privilege.page;

import org.springframework.stereotype.Component;

import com.ia.core.security.model.privilege.PrivilegeType;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.security.view.privilege.form.PrivilegeFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * View Model para página de {@link PrivilegeDTO}
 *
 * @author Israel Araújo
 */
@UIScope
@Component
public class PrivilegePageViewModel
  extends EntityPageViewModel<PrivilegeDTO> {

  /**
   * @param service             serviço para {@link PrivilegeDTO}
   * @param logOperationService {@link LogOperationManager}
   */
  public PrivilegePageViewModel(PrivilegePageViewModelConfig config) {
    super(config);
  }

  @Override
  public PrivilegeDTO cloneObject(PrivilegeDTO object) {
    return object.cloneObject();
  }

  @Override
  public PrivilegeDTO createNewObject() {
    return PrivilegeDTO.builder().type(PrivilegeType.USER).build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return PrivilegeDTO.getSearchRequest();
  }

  @Override
  public Long getId(PrivilegeDTO object) {
    return object.getId();
  }

  @Override
  public Class<PrivilegeDTO> getType() {
    return PrivilegeDTO.class;
  }

  @Override
  public IFormViewModel<PrivilegeDTO> createFormViewModel(FormViewModelConfig<PrivilegeDTO> config) {
    return new PrivilegeFormViewModel(config);
  }

  @Override
  public boolean canDelete(PrivilegeDTO object) {
    return super.canDelete(object)
        && !PrivilegeType.SYSTEM.equals(object.getType());
  }

  @Override
  public boolean canCopy(PrivilegeDTO object) {
    return super.canCopy(object)
        && !PrivilegeType.SYSTEM.equals(object.getType());
  }
}
