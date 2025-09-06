package com.ia.core.security.view.service;

import java.io.Serializable;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.BaseClient;
import com.ia.core.view.service.DefaultBaseServiceConfig;

import lombok.Getter;

/**
 *
 */
public class DefaultSecuredViewBaseServiceConfig<D extends DTO<? extends Serializable>>
  extends DefaultBaseServiceConfig<D> {
  /** Gestor de autorizações */
  @Getter
  private final CoreSecurityAuthorizationManager authorizationManager;

  /**
   *
   */
  public DefaultSecuredViewBaseServiceConfig(BaseClient<D> client,
                                             CoreSecurityAuthorizationManager authorizationManager) {
    super(client);
    this.authorizationManager = authorizationManager;
  }
}
