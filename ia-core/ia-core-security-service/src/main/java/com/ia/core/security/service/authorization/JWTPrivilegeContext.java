package com.ia.core.security.service.authorization;

import com.ia.core.security.model.authentication.JwtManager.Context;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager.HasContextDefinitions.PrivilegeContext;

import java.util.Collection;

public class JWTPrivilegeContext
  extends Context<PrivilegeContext> {

  /**
   *
   */
  public JWTPrivilegeContext() {
    super();
  }

  /**
   * @param context
   */
  public JWTPrivilegeContext(Collection<PrivilegeContext> context) {
    super(context);
  }

}
