package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.security.service.model.authorization.HasContext;
import com.ia.core.security.service.model.functionality.HasFunctionality;
import com.ia.core.service.BaseService;
import com.ia.core.service.dto.DTO;

/**
 * Interface base para criação de um serviço.
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface BaseSecuredService<T extends BaseEntity, D extends DTO<?>>
  extends BaseService<T, D>, HasFunctionality, HasContext, HasLogOperation {

  /**
   * @return {@link CoreSecurityAuthorizationManager} para restão das
   *         autorização baseadas em funcionalidades
   */
  CoreSecurityAuthorizationManager getAuthorizationManager();

  @Override
  default String getContextName() {
    return getFunctionalityTypeName();
  }
}
