package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica visualizar item.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasViewAction<T extends Serializable> {

  /**
   * Após salvar.
   *
   * @param item Item salvo.
   */
  default void afterView(T item) {

  }

  /**
   * Antes de visualizar.
   *
   * @param item Item a ser salvo.
   */
  default void beforeView(T item) {

  }

  /**
   * Ação de visualizar
   *
   * @param item item a ser visualizado
   */
  default void view(T item) {
    beforeView(item);
    viewAction(item);
    afterView(item);
  }

  /**
   * Ação de salvar.
   *
   * @param item Item a ser visualizado.
   */
  void viewAction(T item);
}
