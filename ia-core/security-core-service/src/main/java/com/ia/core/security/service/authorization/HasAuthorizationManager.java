package com.ia.core.security.service.authorization;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;

/**
 * Interface segregada para fornecer gerenciamento de autorização.
 * 
 * Princípio: ISP (Interface Segregation Principle)
 * Responsabilidade Única: Fornecer acesso ao gerenciador de autorização.
 *
 * @author Israel Araújo
 */
public interface HasAuthorizationManager {

  /**
   * Obtém o gerenciador de autorização baseado em funcionalidades.
   *
   * @return {@link CoreSecurityAuthorizationManager}
   */
  CoreSecurityAuthorizationManager getAuthorizationManager();
}
