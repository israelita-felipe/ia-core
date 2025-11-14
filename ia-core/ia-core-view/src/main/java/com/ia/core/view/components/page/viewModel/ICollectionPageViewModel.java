package com.ia.core.view.components.page.viewModel;

import java.io.Serializable;
import java.util.function.BiConsumer;

import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

/**
 * Interface que define um contrato para páginas de coleções em memória
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado da página
 */
public interface ICollectionPageViewModel<T extends Serializable>
  extends IPageViewModel<T> {

  /**
   * @param object Objeto a descer
   * @return se pode ser descido na coleção
   */
  boolean canDown(T object);

  /**
   * @param object Objeto a subir
   * @return se pode ser subido na coleção
   */
  boolean canUp(T object);

  /**
   * @param object           objeto a ser descido
   * @param onChangeListener escutador ao descer o item
   */
  void down(T object, BiConsumer<T, Integer> onChangeListener);

  @Override
  DefaultCollectionBaseManager<T> getService();

  /**
   * @param object           objeto a ser subido
   * @param onChangeListener escutador a ser subido
   */
  void up(T object, BiConsumer<T, Integer> onChangeListener);
}
