package com.ia.core.view.components.page.viewModel;

import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

import java.io.Serializable;

/**
 *
 */
/**
 * Classe de configuração para collection page view model.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a CollectionPageViewModelConfig
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class CollectionPageViewModelConfig<T extends Serializable>
  extends PageViewModelConfig<T> {

  /**
   * @param service
   */
  public CollectionPageViewModelConfig(DefaultCollectionBaseManager<T> service) {
    super(service);
  }

}
