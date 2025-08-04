package com.ia.core.view.components.properties;

/**
 * Interface que indica que possui refresh.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado a ser dado um refresh
 */
public interface HasRefresh<T> {
  /**
   * Ação padrão do refresh all.
   */
  void refreshAll();

  /**
   * Ação padrão do refresh.
   *
   * @param item Item a ser dado o refresh
   */
  void refreshItem(T item);
}
