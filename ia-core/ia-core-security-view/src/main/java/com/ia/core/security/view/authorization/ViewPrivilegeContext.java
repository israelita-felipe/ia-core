package com.ia.core.security.view.authorization;

import java.util.Collection;

import com.ia.core.security.model.authentication.JwtManager.Context;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager.HasContextDefinitions.PrivilegeContext;

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
    // TODO Auto-generated constructor stub
  }

  /**
   * @param context
   */
  public ViewPrivilegeContext(Collection<PrivilegeContext> context) {
    super(context);
    // TODO Auto-generated constructor stub
  }

}
