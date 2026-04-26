package com.ia.core.view.manager.collection;

import com.ia.core.view.client.BaseClient;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

import java.io.Serializable;

/**
 *
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
