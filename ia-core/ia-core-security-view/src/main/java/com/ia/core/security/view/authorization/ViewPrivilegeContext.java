package com.ia.core.security.view.authorization;

import com.ia.core.security.model.authentication.JwtManager.Context;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager.HasContextDefinitions.PrivilegeContext;

import java.util.Collection;

/**
 *
 */
public class ViewPrivilegeContext
  extends Context<PrivilegeContext> {

  /**
   *
   */
  public ViewPrivilegeContext() {
    super();
  }

  /**
   * @param context
   */
  public ViewPrivilegeContext(Collection<PrivilegeContext> context) {
    super(context);
  }

}
