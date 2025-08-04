package com.ia.core.view.components.editor.formEditor;

import java.io.Serializable;

import com.ia.core.view.components.editor.EditorView;
import com.ia.core.view.components.editor.formEditor.viewModel.IFormEditorViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel;
import com.ia.core.view.components.form.IFormView;

/**
 * Implementação padrão do editor de formulário
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do editor
 */
public abstract class FormEditorView<T extends Serializable>
  extends EditorView<T, IFormView<T>>
  implements IFormEditorView<T> {
  /** Serial UID */
  private static final long serialVersionUID = 4096752212938703927L;

  /**
   * @param viewModel {@link IEditorViewModel} do editor
   */
  public FormEditorView(IEditorViewModel<T> viewModel) {
    super(viewModel);
  }

  @Override
  public IFormEditorViewModel<T> getViewModel() {
    return (IFormEditorViewModel<T>) super.getViewModel();
  }
}
