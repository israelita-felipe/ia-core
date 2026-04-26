package com.ia.core.view.components.page.viewModel;

import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

import java.io.Serializable;

/**
 *
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
