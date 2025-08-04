package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica salvar um items.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasDeleteAction<T extends Serializable> {

  /**
   * Após deletar.
   *
   * @param item Item deletado.
   */
  default void afterDelete(T item) {

  }

  /**
   * Antes de deletar.
   *
   * @param item Item a ser deletado.
   */
  default void beforeDelete(T item) {

  }

  /**
   * Deleta um item
   *
   * @param item Item a ser deletado
   */
  default void delete(T item) {
    beforeDelete(item);
    deleteAction(item);
    afterDelete(item);
  }

  /**
   * Ação de deletar.
   *
   * @param item Item a ser deletado.
   */
  void deleteAction(T item);
}
