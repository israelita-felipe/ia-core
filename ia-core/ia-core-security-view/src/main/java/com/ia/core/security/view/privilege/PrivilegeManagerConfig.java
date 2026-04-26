package com.ia.core.security.view.privilege;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.privilege.PrivilegeDTO;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import org.springframework.stereotype.Component;

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
