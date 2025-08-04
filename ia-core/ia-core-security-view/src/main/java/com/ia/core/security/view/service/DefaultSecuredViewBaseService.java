package com.ia.core.security.view.service;

import java.io.Serializable;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.BaseClient;
import com.ia.core.view.service.DefaultBaseService;

import lombok.Getter;
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
  /** Gestor de autorizações */
  @Getter
  private final CoreSecurityAuthorizationManager authorizationManager;

  /**
   * @param client               Cliente de comunicação
   * @param authorizationManager Gestor de autorizações
   */
  public DefaultSecuredViewBaseService(BaseClient<D> client,
                                       CoreSecurityAuthorizationManager authorizationManager) {
    super(client);
    this.authorizationManager = authorizationManager;
  }

}
