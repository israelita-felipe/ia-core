package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica confirmar um item..
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasCancelAction<T extends Serializable> {

  /**
   * Após cancelar
   *
   * @param item Item a ser cancelado
   */
  default void afterCancel(T item) {

  }

  /**
   * Antes de cancelar
   *
   * @param item Item a ser cancelado
   */
  default void beforeCancel(T item) {

  }

  /**
   * Cancelamento
   *
   * @param item Item a ser cancelado
   */
  default void cancel(T item) {
    beforeCancel(item);
    cancelAction(item);
    afterCancel(item);
  }

  /**
   * Ação de cancelar.
   *
   * @param item Item a ser cancelado.
   */
  void cancelAction(T item);

}
