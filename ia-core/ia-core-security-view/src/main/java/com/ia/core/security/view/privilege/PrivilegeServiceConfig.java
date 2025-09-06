package com.ia.core.security.view.privilege;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.service.DefaultSecuredViewBaseServiceConfig;

/**
 *
 */
@Component
public class PrivilegeServiceConfig
  extends DefaultSecuredViewBaseServiceConfig<PrivilegeDTO> {

  /**
   * @param client cliente do serviço
   */
  public PrivilegeServiceConfig(PrivilegeClient client,
                                CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

}
