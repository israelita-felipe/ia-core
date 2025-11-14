package com.ia.core.view.manager;

import java.io.Serializable;

import com.ia.core.view.client.BaseClient;

/**
 *
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
