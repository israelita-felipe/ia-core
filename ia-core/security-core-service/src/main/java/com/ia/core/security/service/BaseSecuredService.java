package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
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
public interface BaseSecuredService<T extends BaseEntity, D extends DTO<T>>
  extends BaseService<T, D>, HasFunctionality, HasLogOperation {

  /**
   * @return {@link CoreSecurityAuthorizationManager} para restão das
   *         autorização baseadas em funcionalidades
   */
  CoreSecurityAuthorizationManager getAuthorizationManager();

}
