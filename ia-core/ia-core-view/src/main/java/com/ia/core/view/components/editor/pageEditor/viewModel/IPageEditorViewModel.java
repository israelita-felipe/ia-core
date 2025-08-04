package com.ia.core.view.components.editor.pageEditor.viewModel;

import java.io.Serializable;

import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * Interface que define o contrato do comportamento padrão de um
 * {@link IViewModel} para um editor de página
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do editor
 */
public interface IPageEditorViewModel<T extends Serializable>
  extends IEditorViewModel<T> {

  @Override
  IPageViewModel<T> getContentViewModel();

  @Override
  default boolean isReadOnly() {
    return getContentViewModel().isReadOnly();
  }

  @Override
  default void setReadOnly(boolean readOnly) {
    getContentViewModel().setReadOnly(readOnly);
  }

}
