package com.ia.core.view.service;

import java.io.Serializable;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.BaseClient;

/**
 * Interface base para o cliente.
 *
 * @author Israel Araújo
 * @param <D> Tipo do {@link DTO}
 */
public interface BaseService<D extends Serializable> {

  /**
   * Captura o cliente ativo.
   *
   * @param <C> Tipo do cliente.
   * @return {@link BaseClient}
   */
  <C extends BaseClient<D>> C getClient();
}
