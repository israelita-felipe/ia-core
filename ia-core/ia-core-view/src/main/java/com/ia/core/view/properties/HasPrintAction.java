package com.ia.core.view.properties;

import java.io.Serializable;

import com.ia.core.view.components.list.IListView;

/**
 * Propriedade que indica visualizar item.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasPrintAction<T extends Serializable> {

  /**
   * Após salvar.
   *
   * @param item Item salvo.
   */
  default void afterPrint(IListView<T> item) {

  }

  /**
   * Antes de visualizar.
   *
   * @param item Item a ser salvo.
   */
  default void beforePrint(IListView<T> item) {

  }

  /**
   * Ação de visualizar
   *
   * @param item item a ser visualizado
   */
  default void print(IListView<T> item) {
    beforePrint(item);
    printAction(item);
    afterPrint(item);
  }

  /**
   * Ação de salvar.
   *
   * @param item Item a ser visualizado.
   */
  void printAction(IListView<T> item);
}
