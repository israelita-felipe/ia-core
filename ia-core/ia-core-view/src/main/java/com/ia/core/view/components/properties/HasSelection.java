package com.ia.core.view.components.properties;

import java.util.Collection;

/**
 * Propriedade de possuir seleção
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado de seleção
 */
public interface HasSelection<T> {
  /**
   * Desseleciona um item
   *
   * @param item item a ser desselecionado
   */
  void deselect(T item);

  /**
   * Desseleciona tudo
   */
  void deselectAll();

  /**
   * @return Item selecionado
   */
  T getSelectedItem();

  /**
   * Captura os itens selecionados
   *
   * @return {@link Collection} de itens selecionados
   */
  Collection<T> getSelectedItems();

  /**
   * Seleciona um item
   *
   * @param item item a ser selecionado
   */
  void select(T item);

  /**
   * Seleciona todos os itens
   */
  void selectAll();
}
