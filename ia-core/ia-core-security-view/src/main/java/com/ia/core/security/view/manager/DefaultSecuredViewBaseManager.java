package com.ia.core.security.view.manager;

import java.io.Serializable;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.view.manager.DefaultBaseManager;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe padrão para criação de um serviço da view com todos os clientes.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
@Slf4j
public abstract class DefaultSecuredViewBaseManager<D extends DTO<? extends Serializable>>
  extends DefaultBaseManager<D>
  implements CountSecuredViewBaseManager<D>, FindSecuredViewBaseManager<D>,
  DeleteSecuredViewBaseManager<D>, ListSecuredViewBaseManager<D>,
  SaveSecuredViewBaseManager<D> {

  /**
   * @param client               Cliente de comunicação
   * @param authorizationManager Gestor de autorizações
   */
  public DefaultSecuredViewBaseManager(DefaultSecuredViewBaseMangerConfig<D> config) {
    super(config);
  }

  @Override
  public CoreSecurityAuthorizationManager getAuthorizationManager() {
    return getConfig().getAuthorizationManager();
  }

  @Override
  public DefaultSecuredViewBaseMangerConfig<D> getConfig() {
    return (DefaultSecuredViewBaseMangerConfig<D>) super.getConfig();
  }
}
