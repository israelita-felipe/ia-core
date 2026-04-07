package com.ia.core.security.service.authorization;

import java.util.Collection;

import com.ia.core.security.model.authentication.JwtManager.Context;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager.HasContextDefinitions.PrivilegeContext;

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
