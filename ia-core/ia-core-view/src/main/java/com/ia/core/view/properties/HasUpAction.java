package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica subir item.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasUpAction<T extends Serializable> {

  /**
   * Após salvar.
   *
   * @param item Item salvo.
   */
  default void afterUp(T item) {

  }

  /**
   * Antes de subir.
   *
   * @param item Item a ser subido.
   */
  default void beforeUp(T item) {

  }

  /**
   * Ação de visualizar
   *
   * @param item item a ser visualizado
   */
  default void up(T item) {
    beforeUp(item);
    upAction(item);
    afterUp(item);
  }

  /**
   * Ação de subir.
   *
   * @param item Item a ser subido.
   */
  void upAction(T item);
}
