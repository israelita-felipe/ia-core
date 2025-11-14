package com.ia.core.security.view.privilege.page;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.log.operation.LogOperationManager;
import com.ia.core.security.view.log.operation.page.EntityPageViewModelConfig;
import com.ia.core.security.view.privilege.PrivilegeManager;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 *
 */
@UIScope
@Component
public class PrivilegePageViewModelConfig
  extends EntityPageViewModelConfig<PrivilegeDTO> {

  /**
   * @param service
   * @param logOperationService
   */
  public PrivilegePageViewModelConfig(PrivilegeManager service,
                                      LogOperationManager logOperationService) {
    super(service, logOperationService);
  }

}
