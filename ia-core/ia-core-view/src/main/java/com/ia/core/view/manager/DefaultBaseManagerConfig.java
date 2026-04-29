package com.ia.core.view.manager;

import com.ia.core.view.client.BaseClient;

import java.io.Serializable;

/**
 *
 */
/**
 * Classe de configuração para default base manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a DefaultBaseManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class DefaultBaseManagerConfig<T extends Serializable>
  extends AbstractBaseManagerConfig<T> {

  /**
   * @param client {@link BaseClient} de comunicação
   */
  public DefaultBaseManagerConfig(BaseClient<T> client) {
    super(client);
  }

}
