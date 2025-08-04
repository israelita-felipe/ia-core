package com.ia.core.view.components.editor;

import java.io.Serializable;
import java.util.Collection;

import com.ia.core.view.components.IView;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel.EditorAction;
import com.ia.core.view.components.properties.HasId;
import com.ia.core.view.properties.AutoCastable;
import com.ia.core.view.properties.HasClose;
import com.ia.core.view.properties.HasErrorHandle;
import com.ia.core.view.utils.Size;
import com.vaadin.flow.component.HasSize;

/**
 * @author Israel Araújo
 * @param <T> Tipo do objeto editado
 * @param <V> Tipo da {@link IView} do objeto editado
 */
public interface IEditorView<T extends Serializable, V extends IView<T>>
  extends AutoCastable, HasErrorHandle, HasSize, HasId, IView<T>, HasClose {

  /**
   * Adiciona uma ação no editor
   *
   * @param action Ação a ser adicionada
   */
  void addAction(EditorAction<T> action);

  /**
   * Adiciona uma coleção de ações no editor
   *
   * @param actions {@link EditorAction}
   */
  void addActions(Collection<EditorAction<T>> actions);

  /**
   * Cria o conteúdo do editor
   *
   * @param viewModel {@link IViewModel} do editor
   * @return {@link IView}
   */
  V createContentView(IViewModel<T> viewModel);

  /**
   * @return {@link Collection} de {@link EditorAction} padrão do editor
   */
  Collection<EditorAction<T>> createDefaultEditorActions();

  @Override
  IEditorViewModel<T> getViewModel();

  /**
   * Atualiza uma ação
   *
   * @param action {@link EditorAction}
   * @return <code>true</code> se foi atualizada, <code>false</code> caso
   *         contrário
   */
  boolean refreshAction(EditorAction<T> action);

  /**
   * Atualiza os botões
   */
  void refreshButtons();

  /**
   * Atribui o título
   *
   * @param title Título do editor
   */
  void setHeaderTitle(String title);

  /**
   * Atribui o tamanho
   *
   * @param width  largura
   * @param height altura
   */
  public void setSize(Size width, Size height);

  /**
   * Exibe o editor
   */
  void show();

}
