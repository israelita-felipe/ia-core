package com.ia.core.view.manager.collection;

import java.io.Serializable;

import com.ia.core.view.client.BaseClient;
import com.ia.core.view.manager.DefaultBaseManagerConfig;

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
