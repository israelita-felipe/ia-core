package com.ia.core.view.components.page;

import java.io.Serializable;
import java.util.Collection;

import com.ia.core.view.components.IView;
import com.ia.core.view.components.editor.formEditor.IFormEditorView;
import com.ia.core.view.components.editor.formEditor.viewModel.IFormEditorViewModel;
import com.ia.core.view.components.filter.ISearchRequestView;
import com.ia.core.view.components.filter.viewModel.ISearchRequestViewModel;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;
import com.ia.core.view.components.properties.HasConfirmDialog;
import com.ia.core.view.components.properties.HasDataProviderCreator;
import com.ia.core.view.components.properties.HasId;
import com.ia.core.view.components.properties.HasMessageNotification;
import com.ia.core.view.properties.AutoCastable;
import com.ia.core.view.properties.HasCopyAction;
import com.ia.core.view.properties.HasCreateAction;
import com.ia.core.view.properties.HasDeleteAction;
import com.ia.core.view.properties.HasEditAction;
import com.ia.core.view.properties.HasErrorHandle;
import com.ia.core.view.properties.HasSaveAction;
import com.ia.core.view.properties.HasViewAction;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasLabel;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

/**
 * Interface que define o contrato de uma página
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado da página
 */
public interface IPageView<T extends Serializable>
  extends AutoCastable, HasLabel, HasComponents, HasErrorHandle, HasId,
  HasConfirmDialog, HasMessageNotification, HasDataProviderCreator<T>,
  IView<T>, HasViewAction<T>, HasSaveAction<T>, HasCreateAction<T>,
  HasEditAction<T>, HasDeleteAction<T>, HasCopyAction<T> {

  /**
   * Adiciona uma ação na página
   *
   * @param action {@link PageAction} a ser adicionada
   */
  void addAction(PageAction<T> action);

  /**
   * Adiciona uma coleção de ações na página
   *
   * @param actions coleção de ações a serem executadas
   */
  void addActions(Collection<PageAction<T>> actions);

  /**
   * Cria a barra de menus
   *
   * @return {@link FlexLayout}
   */
  FlexLayout createButtonsBar();

  /**
   * @return {@link Collection} de {@link PageAction} da página
   */
  Collection<PageAction<T>> createDefaultPageActions();

  /**
   * Cria o editor da página
   *
   * @param viewModel View Model do editor
   * @return {@link IFormEditorView}
   */
  IFormEditorView<T> createEditorView(IFormEditorViewModel<T> viewModel);

  /**
   * Cria o filtro
   *
   * @param searchViewModel viewModel do filtro
   * @return {@link ISearchRequestView}
   */
  ISearchRequestView createFilter(ISearchRequestViewModel searchViewModel);

  /**
   * Cria o formulário de edição
   *
   * @param formViewModel View Model do formulário
   * @return {@link IFormView}
   */
  IFormView<T> createFormView(IFormViewModel<T> formViewModel);

  /**
   * Cria a lista da página
   *
   * @param listViewModel View Model
   * @return {@link IListView}
   */
  IListView<T> createListView(IListViewModel<T> listViewModel);

  /**
   * Executa uma ação
   *
   * @param action {@link PageAction} a ser executada
   */
  default void executePageAction(PageAction<T> action) {
    try {
      action.getAction().run();
    } catch (Exception e) {
      handleError(e);
    }
  }

  @Override
  IPageViewModel<T> getViewModel();

  /**
   * @return se o botão de exclusão pode ser exibido
   */
  default boolean isDeleteButtonVisible() {
    return true;
  }

  /**
   * @return se o botão de edição pode ser exibido
   */
  default boolean isEditButtonVisible() {
    return true;
  }

  /**
   * @return se o botão se criar/novo pode ser exibido
   */
  default boolean isNewButtonVisible() {
    return true;
  }

  /**
   * @return se o butão de visualização pode ser exibido
   */
  default boolean isViewButtonVisible() {
    return true;
  }

  /**
   * @return se o butão de copiar pode ser exibido
   */
  default boolean isCopyButtonVisible() {
    return true;
  }

  /**
   * @param action atualiza uma {@link PageAction}
   * @return <code>true</code> se pode ser atualizado
   */
  boolean refreshAction(PageAction<T> action);

  /**
   * Atualiza os botões
   */
  void refreshButtons();

  /**
   * Exibe o filtro
   */
  void showFilter();

}
