package com.ia.core.view.components.page;

import java.io.Serializable;

import com.ia.core.view.components.page.viewModel.ICollectionPageViewModel;
import com.ia.core.view.properties.HasDownAction;
import com.ia.core.view.properties.HasUpAction;

/**
 * Página para edição de uma coleção
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto a ser exibido na página
 */
public interface ICollectionPageView<T extends Serializable>
  extends IPageView<T>, HasUpAction<T>, HasDownAction<T> {

  @Override
  ICollectionPageViewModel<T> getViewModel();

  /**
   * @return Se o botão baixar objeto será exibido
   */
  default boolean isDownButtonVisible() {
    return false;
  }

  /**
   * @return se o botão subir objeto será exibido
   */
  default boolean isUpButtonVisible() {
    return false;
  }
}
