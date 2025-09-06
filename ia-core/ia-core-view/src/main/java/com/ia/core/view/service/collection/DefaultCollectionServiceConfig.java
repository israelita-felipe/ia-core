package com.ia.core.view.service.collection;

import java.io.Serializable;

import com.ia.core.view.client.BaseClient;
import com.ia.core.view.service.DefaultBaseServiceConfig;

/**
 *
 */
public class DefaultCollectionServiceConfig<D extends Serializable>
  extends DefaultBaseServiceConfig<D> {

  /**
   * @param client {@link CollectionClient} de comunicação
   */
  public DefaultCollectionServiceConfig(BaseClient<D> client) {
    super(client);
  }

}
