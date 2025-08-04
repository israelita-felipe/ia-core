package com.ia.core.view.properties;

/**
 * Itentifica que o elemento é fechável.
 *
 * @author Israel Araújo
 */
public interface HasClose {

  /**
   * Método para fechar algo.
   */
  default void close() {

  }
}
