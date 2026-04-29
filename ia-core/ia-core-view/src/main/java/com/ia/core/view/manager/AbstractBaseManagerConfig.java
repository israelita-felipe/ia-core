package com.ia.core.view.manager;

import com.ia.core.view.client.BaseClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 *
 */
/**
 * Classe que representa as configurações para abstract base manager.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AbstractBaseManagerConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
@RequiredArgsConstructor
@Slf4j
@Getter
public class AbstractBaseManagerConfig<D extends Serializable> {
  protected final BaseClient<D> client;
}
