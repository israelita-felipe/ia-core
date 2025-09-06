package com.ia.core.security.view.service;

import java.io.Serializable;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.view.service.DefaultBaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe padrão para criação de um serviço da view com todos os clientes.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
@Slf4j
public abstract class DefaultSecuredViewBaseService<D extends DTO<? extends Serializable>>
  extends DefaultBaseService<D>
  implements CountSecuredViewBaseService<D>, FindSecuredViewBaseService<D>,
  DeleteSecuredViewBaseService<D>, ListSecuredViewBaseService<D>,
  SaveSecuredViewBaseService<D> {

  /**
   * @param client               Cliente de comunicação
   * @param authorizationManager Gestor de autorizações
   */
  public DefaultSecuredViewBaseService(DefaultSecuredViewBaseServiceConfig<D> config) {
    super(config);
  }

  @Override
  public CoreSecurityAuthorizationManager getAuthorizationManager() {
    return getConfig().getAuthorizationManager();
  }

  @Override
  public DefaultSecuredViewBaseServiceConfig<D> getConfig() {
    return (DefaultSecuredViewBaseServiceConfig<D>) super.getConfig();
  }
}
