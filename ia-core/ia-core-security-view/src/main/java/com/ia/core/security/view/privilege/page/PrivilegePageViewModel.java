package com.ia.core.security.view.privilege.page;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.log.operation.LogOperationService;
import com.ia.core.security.view.log.operation.page.EntityPageViewModel;
import com.ia.core.security.view.privilege.PrivilegeService;
import com.ia.core.security.view.privilege.form.PrivilegeFormViewModel;
import com.ia.core.service.dto.request.SearchRequestDTO;
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
   * @param logOperationService {@link LogOperationService}
   */
  public PrivilegePageViewModel(PrivilegeService service,
                                LogOperationService logOperationService) {
    super(service, logOperationService);
  }

  @Override
  public PrivilegeDTO cloneObject(PrivilegeDTO object) {
    return object.cloneObject();
  }

  @Override
  public PrivilegeDTO createNewObject() {
    return PrivilegeDTO.builder().build();
  }

  @Override
  protected SearchRequestDTO createSearchRequest() {
    return PrivilegeDTO.getSearchRequest();
  }

  @Override
  public UUID getId(PrivilegeDTO object) {
    return object.getId();
  }

  @Override
  public Class<PrivilegeDTO> getType() {
    return PrivilegeDTO.class;
  }

  @Override
  public IFormViewModel<PrivilegeDTO> createFormViewModel(boolean readOnly) {
    return new PrivilegeFormViewModel(readOnly);
  }
}
