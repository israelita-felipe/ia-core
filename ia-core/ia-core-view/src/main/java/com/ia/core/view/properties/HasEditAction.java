package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica salvar um items.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasEditAction<T extends Serializable> {

  /**
   * Após salvar.
   *
   * @param item Item salvo.
   */
  default void afterEdit(T item) {

  }

  /**
   * Antes de salvar.
   *
   * @param item Item a ser salvo.
   */
  default void beforeEdit(T item) {

  }

  /**
   * @param item Item a ser editado
   */
  default void edit(T item) {
    beforeEdit(item);
    editAction(item);
    afterEdit(item);
  }

  /**
   * Ação de salvar.
   *
   * @param item Item a ser salvo.
   */
  void editAction(T item);
}
