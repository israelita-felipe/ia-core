package com.ia.core.security.view.manager;

import com.ia.core.security.service.model.authorization.CoreSecurityAuthorizationManager;
import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.BaseClient;
import com.ia.core.view.manager.DefaultBaseManagerConfig;
import lombok.Getter;

import java.io.Serializable;

/**
 *
 */
/**
 * Classe de configuração para default secured view base manger.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a DefaultSecuredViewBaseMangerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class DefaultSecuredViewBaseMangerConfig<D extends DTO<? extends Serializable>>
  extends DefaultBaseManagerConfig<D> {
  /** Gestor de autorizações */
  @Getter
  private final CoreSecurityAuthorizationManager authorizationManager;

  /**
   *
   */
  public DefaultSecuredViewBaseMangerConfig(BaseClient<D> client,
                                             CoreSecurityAuthorizationManager authorizationManager) {
    super(client);
    this.authorizationManager = authorizationManager;
  }
}
