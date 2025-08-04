package com.ia.core.view.components.properties;

import java.io.Serializable;

import com.vaadin.flow.data.provider.DataProvider;

/**
 * Interface de propriedade de {@link DataProvider}
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasDataProvider<T extends Serializable> {

  /**
   * @return {@link DataProvider}
   */
  DataProvider<T, ?> getDataProvider();

  /**
   * Aplica o {@link DataProvider};
   *
   * @param dataProvider {@link DataProvider}
   */
  void setDataProvider(DataProvider<T, ?> dataProvider);

}
