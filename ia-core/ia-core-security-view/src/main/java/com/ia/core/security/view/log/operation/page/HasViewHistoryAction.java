package com.ia.core.security.view.log.operation.page;

import java.io.Serializable;

/**
 * Propriedade que indica visualizar histórico de um item.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasViewHistoryAction<T extends Serializable> {

  /**
   * Após visualizar o histórico.
   *
   * @param item Item a visualizar o histórico.
   */
  default void afterViewHistory(T item) {

  }

  /**
   * Antes de visualizar o histórico.
   *
   * @param item Item a ser visualizado o histórico.
   */
  default void beforeViewHistory(T item) {

  }

  /**
   * Ação de visualizar histórico
   *
   * @param item item a ser visualizado o histórico
   */
  default void viewHistory(T item) {
    beforeViewHistory(item);
    viewHistoryAction(item);
    afterViewHistory(item);
  }

  /**
   * Ação de visualizar histórico.
   *
   * @param item Item a ser visualizado o histórico.
   */
  void viewHistoryAction(T item);
}
