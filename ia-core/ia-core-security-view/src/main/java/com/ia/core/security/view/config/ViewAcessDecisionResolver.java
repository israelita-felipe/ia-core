package com.ia.core.security.view.config;

import com.ia.core.security.view.authorization.CoreSecurityViewAuthorizationManager;
import com.vaadin.flow.server.auth.AccessCheckDecisionResolver;
import com.vaadin.flow.server.auth.AccessCheckResult;
import com.vaadin.flow.server.auth.NavigationContext;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ViewAcessDecisionResolver
  implements AccessCheckDecisionResolver {

  private final CoreSecurityViewAuthorizationManager authorizationManager;

  @Override
  public AccessCheckResult resolve(List<AccessCheckResult> results,
                                   NavigationContext context) {
    if (context.isErrorHandling()) {
      return AccessCheckResult.allow();
    }
    if (authorizationManager.check(context.getNavigationTarget(),
                                   context.getPrincipal() != null)) {
      return AccessCheckResult.allow();
    }
    return AccessCheckResult.deny("Access denied");
  }

}
