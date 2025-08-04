package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica salvar um items.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasSaveAction<T extends Serializable> {

  /**
   * Após salvar.
   *
   * @param item Item salvo.
   */
  default void afterSave(T item) {

  }

  /**
   * Antes de salvar.
   *
   * @param item Item a ser salvo.
   */
  default void beforeSave(T item) {

  }

  /**
   * Ação de salvar um item
   *
   * @param item item a ser salvo
   */
  default void save(T item) {
    beforeSave(item);
    saveAction(item);
    afterSave(item);
  }

  /**
   * Ação de salvar.
   *
   * @param item Item a ser salvo.
   */
  void saveAction(T item);
}
