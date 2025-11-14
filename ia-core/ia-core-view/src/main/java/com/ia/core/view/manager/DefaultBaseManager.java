package com.ia.core.view.manager;

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
public abstract class DefaultBaseManager<D extends Serializable>
  extends AbstractBaseManager<D>
  implements CountBaseManager<D>, FindBaseManager<D>, DeleteBaseManager<D>,
  ListBaseManager<D>, SaveBaseManager<D> {

  /**
   * @param client {@link BaseClient} de comunicação
   */
  public DefaultBaseManager(DefaultBaseManagerConfig<D> client) {
    super(client);
  }

}
