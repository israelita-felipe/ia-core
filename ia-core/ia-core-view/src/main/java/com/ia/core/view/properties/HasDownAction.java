package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica descer item.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasDownAction<T extends Serializable> {

  /**
   * Após descer.
   *
   * @param item Item descido.
   */
  default void afterDown(T item) {

  }

  /**
   * Antes de subir.
   *
   * @param item Item a ser descido.
   */
  default void beforeDown(T item) {

  }

  /**
   * Ação de visualizar
   *
   * @param item item a ser visualizado
   */
  default void down(T item) {
    beforeDown(item);
    downAction(item);
    afterDown(item);
  }

  /**
   * Ação de descer.
   *
   * @param item Item a ser descido.
   */
  void downAction(T item);
}
