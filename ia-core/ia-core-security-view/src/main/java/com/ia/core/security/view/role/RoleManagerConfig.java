package com.ia.core.security.view.role;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.view.manager.DefaultSecuredViewBaseMangerConfig;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class RoleManagerConfig
  extends DefaultSecuredViewBaseMangerConfig<RoleDTO> {

  /**
   * @param client cliente do serviço
   */
  public RoleManagerConfig(RoleClient client,
                           CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

}
