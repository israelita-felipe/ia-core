package com.ia.core.security.view.role;

import org.springframework.stereotype.Component;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.role.RoleDTO;
import com.ia.core.security.view.service.DefaultSecuredViewBaseServiceConfig;

/**
 *
 */
@Component
public class RoleServiceConfig
  extends DefaultSecuredViewBaseServiceConfig<RoleDTO> {

  /**
   * @param client cliente do serviço
   */
  public RoleServiceConfig(RoleClient client,
                           CoreSecurityAuthorizationManager authorizationManager) {
    super(client, authorizationManager);
  }

}
