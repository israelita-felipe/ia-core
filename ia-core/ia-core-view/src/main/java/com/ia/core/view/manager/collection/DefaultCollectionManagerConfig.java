package com.ia.core.view.manager.collection;

import com.ia.core.view.client.BaseClient;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

import java.io.Serializable;

/**
 *
 */
/**
 * Classe de configuração para default collection manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a DefaultCollectionManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class DefaultCollectionManagerConfig<D extends Serializable>
  extends DefaultBaseManagerConfig<D> {

  /**
   * @param client {@link CollectionClient} de comunicação
   */
  public DefaultCollectionManagerConfig(BaseClient<D> client) {
    super(client);
  }

}
