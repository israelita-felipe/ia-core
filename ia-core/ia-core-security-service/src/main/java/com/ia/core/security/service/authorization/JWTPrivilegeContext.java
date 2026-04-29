package com.ia.core.security.service.authorization;

import com.ia.core.security.model.authentication.JwtManager.Context;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager.HasContextDefinitions.PrivilegeContext;

import java.util.Collection;
/**
 * Classe que representa os serviços de negócio para j w t privilege context.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a JWTPrivilegeContext
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

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
