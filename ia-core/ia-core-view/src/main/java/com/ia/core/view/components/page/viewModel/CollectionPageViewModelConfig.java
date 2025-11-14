package com.ia.core.view.components.page.viewModel;

import java.io.Serializable;

import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

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
