package com.ia.core.view.components.editor.formEditor.viewModel;

import java.io.Serializable;

import com.ia.core.view.components.editor.viewModel.EditorViewModel;
import com.ia.core.view.components.form.viewModel.IFormViewModel;

/**
 * Implementação padrão do editor de formulário
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto do formulário
 */
public abstract class FormEditorViewModel<T extends Serializable>
  extends EditorViewModel<T>
  implements IFormEditorViewModel<T> {

  /** Serial UID */
  private static final long serialVersionUID = -8235047476867346931L;

  @Override
  public IFormViewModel<T> getContentViewModel() {
    return (IFormViewModel<T>) super.getContentViewModel();
  }

}
