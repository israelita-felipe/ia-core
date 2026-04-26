package com.ia.core.view.manager;

import com.ia.core.view.client.BaseClient;

import java.io.Serializable;

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
