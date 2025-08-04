package com.ia.core.view.components.editor.pageEditor;

import java.io.Serializable;

import com.ia.core.view.components.editor.EditorView;
import com.ia.core.view.components.editor.pageEditor.viewModel.IPageEditorViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel;
import com.ia.core.view.components.page.IPageView;

/**
 * Implementação padrão de um editor de página
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado do editor
 */
public abstract class PageEditorView<T extends Serializable>
  extends EditorView<T, IPageView<T>>
  implements IPageEditorView<T> {
  /** Serial UID */
  private static final long serialVersionUID = 75156802585674464L;

  /**
   * @param viewModel {@link IEditorViewModel} padrão deste editor
   */
  public PageEditorView(IEditorViewModel<T> viewModel) {
    super(viewModel);
  }

  @Override
  public IPageEditorViewModel<T> getViewModel() {
    return (IPageEditorViewModel<T>) super.getViewModel();
  }

}
