package com.ia.core.view.components.editor.pageEditor.viewModel;

import java.io.Serializable;

import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.viewModel.EditorViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;

/**
 * Implementação padrão de um {@link IViewModel} para um editor de página.
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do editor
 */
public abstract class PageEditorViewModel<T extends Serializable>
  extends EditorViewModel<T>
  implements IPageEditorViewModel<T> {
  /** Serial UID */
  private static final long serialVersionUID = 3201696910601038047L;

  @Override
  public IPageViewModel<T> getContentViewModel() {
    return (IPageViewModel<T>) super.getContentViewModel();
  }

  @Override
  public T getModel() {
    return getContentViewModel().getSelected();
  }

  @Override
  public void setModel(T model) {
    getContentViewModel().setSelected(model);
  }
}
