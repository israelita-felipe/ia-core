package com.ia.core.view.components.editor.formEditor;

import java.io.Serializable;

import com.ia.core.view.components.editor.IEditorView;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel;
import com.ia.core.view.components.form.IFormView;

/**
 * Interface que define o contrato de comportamento padrão para o editor de
 * formulário
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do editor
 */
public interface IFormEditorView<T extends Serializable>
  extends IEditorView<T, IFormView<T>> {

  @Override
  IEditorViewModel<T> getViewModel();
}
