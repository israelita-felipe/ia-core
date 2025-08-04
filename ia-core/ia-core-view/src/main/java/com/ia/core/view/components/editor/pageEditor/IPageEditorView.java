package com.ia.core.view.components.editor.pageEditor;

import java.io.Serializable;

import com.ia.core.view.components.editor.IEditorView;
import com.ia.core.view.components.editor.pageEditor.viewModel.IPageEditorViewModel;
import com.ia.core.view.components.page.IPageView;

/**
 * Interface que define o contrato do comportamento padrão do editor de página.
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do editor de página
 */
public interface IPageEditorView<T extends Serializable>
  extends IEditorView<T, IPageView<T>> {

  @Override
  IPageEditorViewModel<T> getViewModel();
}
