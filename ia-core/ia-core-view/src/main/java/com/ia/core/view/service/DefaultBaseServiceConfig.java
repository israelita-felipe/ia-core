package com.ia.core.view.service;

import java.io.Serializable;

import com.ia.core.view.client.BaseClient;

/**
 *
 */
public class DefaultBaseServiceConfig<T extends Serializable>
  extends AbstractBaseServiceConfig<T> {

  /**
   * @param client {@link BaseClient} de comunicação
   */
  public DefaultBaseServiceConfig(BaseClient<T> client) {
    super(client);
  }

}
