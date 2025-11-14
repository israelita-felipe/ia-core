package com.ia.core.security.view.manager;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.service.dto.DTO;
import com.ia.core.view.manager.BaseManager;

/**
 * Interface base para o cliente.
 *
 * @author Israel Araújo
 * @param <D> Tipo do {@link DTO}
 */
public interface BaseSecuredViewManager<D extends DTO<? extends Serializable>>
  extends BaseManager<D>, HasFunctionality {
  /**
   * @return {@link CoreSecurityAuthorizationManager} para restão das
   *         autorização baseadas em funcionalidades
   */
  CoreSecurityAuthorizationManager getAuthorizationManager();

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager manager) {
    return new HashSet<>();
  }
}
