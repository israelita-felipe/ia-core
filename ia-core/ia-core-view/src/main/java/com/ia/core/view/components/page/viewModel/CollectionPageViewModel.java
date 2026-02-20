package com.ia.core.view.components.page.viewModel;

import java.io.Serializable;
import java.util.function.BiConsumer;

import com.ia.core.view.manager.collection.DefaultCollectionBaseManager;

/**
 * Implementação padrão de um ViewModel de página para itens de coleção em
 * memória
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto da página
 */
public abstract class CollectionPageViewModel<T extends Serializable>
  extends PageViewModel<T>
  implements ICollectionPageViewModel<T> {

  /**
   * Construtor padrão
   *
   * @param service {@link DefaultCollectionBaseManager} da página
   */
  public CollectionPageViewModel(CollectionPageViewModelConfig<T> config) {
    super(config);
  }

  @Override
  public boolean canDown(T object) {
    return object != null && !isReadOnly();
  }

  @Override
  public boolean canUp(T object) {
    return object != null && !isReadOnly();
  }

  @Override
  public void delete(T selectedItem) {
    super.delete(selectedItem);
    onDelete(selectedItem);
  }

  @Override
  public void down(T object, BiConsumer<T, Integer> onDown) {
    BiConsumer<T, Integer> runOnDown = (objectUp, index) -> onDown(object,
                                                                   objectUp,
                                                                   index);
    getService().down(object, runOnDown.andThen(onDown));
  }

  @Override
  public DefaultCollectionBaseManager<T> getService() {
    return (DefaultCollectionBaseManager<T>) super.getService();
  }

  /**
   * Gatilho ao deletar um item
   *
   * @param object Objeto a ser excluído
   */
  public void onDelete(T object) {

  }

  /**
   * Gatilho disparado ao descer um item
   *
   * @param object   objeto
   * @param objectUp objeto que subiu
   * @param newIndex novo índice
   */
  public void onDown(T object, T objectUp, int newIndex) {

  }

  /**
   * Gatilho disparado ao subir um item
   *
   * @param object     Objeto
   * @param objectDown Objeto que desceu
   * @param newIndex   novo índice
   */
  public void onUp(T object, T objectDown, int newIndex) {

  }

  @Override
  public void up(T object, BiConsumer<T, Integer> onUp) {
    BiConsumer<T, Integer> runOnUp = (objectDown, index) -> onUp(object,
                                                                 objectDown,
                                                                 index);
    getService().up(object, runOnUp.andThen(onUp));
  }

  @Override
  public Long getId(T object) {
    return getService().getId(object);
  }
}
