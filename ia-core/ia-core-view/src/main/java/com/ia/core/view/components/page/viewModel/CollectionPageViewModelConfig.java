package com.ia.core.view.components.page.viewModel;

import java.io.Serializable;

import com.ia.core.view.service.collection.DefaultCollectionBaseService;

/**
 *
 */
public class CollectionPageViewModelConfig<T extends Serializable>
  extends PageViewModelConfig<T> {

  /**
   * @param service
   */
  public CollectionPageViewModelConfig(DefaultCollectionBaseService<T> service) {
    super(service);
  }

}
