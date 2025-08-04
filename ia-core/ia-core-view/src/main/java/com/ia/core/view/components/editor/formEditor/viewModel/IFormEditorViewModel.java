package com.ia.core.view.components.editor.formEditor.viewModel;

import java.io.Serializable;

import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel;
import com.ia.core.view.components.form.viewModel.IFormViewModel;

/**
 * Interface que define o contrato de comportamento padrão do {@link IViewModel}
 * do editor de formulário.
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do editor
 */
public interface IFormEditorViewModel<T extends Serializable>
  extends IEditorViewModel<T> {

  @Override
  IFormViewModel<T> getContentViewModel();

  @Override
  default T getModel() {
    return getContentViewModel().getModel();
  }

  @Override
  default boolean isReadOnly() {
    return getContentViewModel().isReadOnly();
  }

  @Override
  default void setModel(T model) {
    getContentViewModel().setModel(model);
  }

  @Override
  default void setReadOnly(boolean readOnly) {
    getContentViewModel().setReadOnly(readOnly);
  }
}
