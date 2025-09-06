package com.ia.core.view.service;

import java.io.Serializable;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.BaseClient;

import lombok.extern.slf4j.Slf4j;

/**
 * Classe padrão para criação de um serviço da view com todos os clientes.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
@Slf4j
public abstract class DefaultBaseService<D extends Serializable>
  extends AbstractBaseService<D>
  implements CountBaseService<D>, FindBaseService<D>, DeleteBaseService<D>,
  ListBaseService<D>, SaveBaseService<D> {

  /**
   * @param client {@link BaseClient} de comunicação
   */
  public DefaultBaseService(DefaultBaseServiceConfig<D> client) {
    super(client);
  }

}
