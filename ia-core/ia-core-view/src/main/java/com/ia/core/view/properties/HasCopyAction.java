package com.ia.core.view.properties;

import java.io.Serializable;

/**
 * Propriedade que indica copiar um item.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado.
 */
public interface HasCopyAction<T extends Serializable> {

  /**
   * Após copiar.
   *
   * @param item Item a ser copiado.
   */
  default void afterCopy(T item) {

  }

  /**
   * Antes de copiar.
   *
   * @param item Item a ser copiado.
   */
  default void beforeCopy(T item) {

  }

  /**
   * @param item Item a ser copiado
   */
  default void copy(T item) {
    beforeCopy(item);
    copyAction(item);
    afterCopy(item);
  }

  /**
   * Ação de copiar.
   *
   * @param item Item a ser copiado.
   */
  void copyAction(T item);
}
