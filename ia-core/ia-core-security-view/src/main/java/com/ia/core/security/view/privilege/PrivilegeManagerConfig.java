package com.ia.core.security.view.privilege;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;

/**
 *
 */
@Component
public class PrivilegeManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<PrivilegeDTO> {

  /**
   * @param client cliente do serviço
   */
  public PrivilegeManagerConfig(PrivilegeClient client,
                                CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

}
