package com.ia.core.view.components.editor.viewModel;

import java.io.Serializable;

import com.ia.core.view.components.IViewModel;

import lombok.Getter;

/**
 * Implementação padrão do View Model de um editor
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado do editor
 */
public abstract class EditorViewModel<T extends Serializable>
  implements IEditorViewModel<T> {
  /** Serial UID */
  private static final long serialVersionUID = 6851791511477819L;
  /** View Model do editor */
  @Getter
  private final IViewModel<T> contentViewModel;

  /**
   * Construtor padrão
   */
  public EditorViewModel() {
    super();
    this.contentViewModel = createContentViewModel();
  }

  /**
   * @return {@link IViewModel} do conteúdo do editor
   */
  protected abstract IViewModel<T> createContentViewModel();

}
