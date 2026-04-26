package com.ia.core.view.components.properties;

import com.vaadin.flow.data.provider.DataProvider;

import java.io.Serializable;

/**
 * Interface de propriedade de criar um {@link DataProvider}
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasDataProviderCreator<T extends Serializable> {

  /**
   * Cria um {@link DataProvider}
   *
   * @return {@link DataProvider}
   */
  DataProvider<T, ?> createDataProvider();

}
