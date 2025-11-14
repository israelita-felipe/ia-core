package com.ia.core.view.manager;

import java.io.Serializable;

import com.ia.core.view.client.BaseClient;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe abstrata base para um service da view.
 *
 * @author Israel Araújo
 * @param <D> {@link Serializable}
 */
@RequiredArgsConstructor
@Slf4j
@Getter
public abstract class AbstractBaseManager<D extends Serializable>
  implements BaseManager<D> {

  /**
   * Serviço do controlador.
   */
  protected final AbstractBaseManagerConfig<D> config;

  /**
   * Inicialização do serviço
   */
  @PostConstruct
  public void initBaseService() {
    log.info("{} inicializado com client {}", this.getClass(),
             config.getClient().getClass());
  }

  @SuppressWarnings("unchecked")
  @Override
  public <C extends BaseClient<D>> C getClient() {
    return (C) getConfig().getClient();
  }
}
