package com.ia.core.view.components.page.viewModel;

import java.io.Serializable;
import java.util.function.Function;

import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.formEditor.viewModel.IFormEditorViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel;
import com.ia.core.view.components.filter.viewModel.ISearchRequestViewModel;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.exception.ValidationException;
import com.ia.core.view.manager.DefaultBaseManager;
import com.ia.core.view.properties.AutoCastable;
import com.ia.core.view.properties.HasErrorHandle;
import com.ia.core.view.properties.HasObjectCoping;
import com.ia.core.view.properties.HasObjectCreation;
import com.vaadin.flow.component.icon.VaadinIcon;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

/**
 * Interface que define um contrato para um view model de página
 *
 * @author Israel Araújo
 * @param <T> Tipo do objeto
 */
public interface IPageViewModel<T extends Serializable>
  extends HasTranslator, AutoCastable, HasObjectCreation<T>,
  HasObjectCoping<T>, HasErrorHandle, IViewModel<T> {

  /**
   * @param <T> Tipo do Objeto alvo da {@link PageAction}
   * @author Israel Araújo
   */
  @Builder
  @Getter
  public static class PageAction<T> {
    /** Ícone */
    private VaadinIcon icon;
    /** Título */
    private String label;
    /** Ação */
    private Runnable action;
    /** Função de habilitação da ação */
    @Default
    private Function<T, Boolean> enableFunction = cloneObject -> true;
  }

  /**
   * Se é possível excluir
   *
   * @param object Objeto a ser excluído
   * @return se é possível excluir o objeto
   */
  boolean canDelete(T object);

  /**
   * @param object Objeto a ser editado
   * @return se é possível editar
   */
  boolean canEdit(T object);

  /**
   * @return se é possível inserir
   */
  boolean canInsert();

  /**
   * @param object Objeto a ser salvo
   * @return se o objeto pode ser salvo
   */
  boolean canSave(T object);

  /**
   * @param object Objeto a ser visualizado
   * @return se é possível visualizar o objeto
   */
  boolean canView(T object);

  /**
   * Clona um objeto
   *
   * @param object Objeto a ser clonado
   * @return Objeto clone
   */
  T cloneObject(T object);

  /**
   * Cria o editor
   *
   * @return {@link IFormEditorViewModel}
   */
  IFormEditorViewModel<T> create();

  /**
   * Cria o editor
   *
   * @param object Objeto a ser copiado
   * @return {@link IFormEditorViewModel}
   */
  IFormEditorViewModel<T> copy(T object);

  /**
   * Cria um view model de edição
   *
   * @return {@link IEditorViewModel}
   */
  IEditorViewModel<T> createEditorViewModel();

  /**
   * Delete um objeto
   *
   * @param object Objeto a ser exclluído
   */
  void delete(T object);

  /**
   * Edita um objeto
   *
   * @param object Objeto a ser editado
   * @return {@link IFormEditorViewModel}
   */
  IFormEditorViewModel<T> edit(T object);

  /**
   * @return {@link IEditorViewModel}
   */
  IEditorViewModel<T> getEditorViewModel();

  /**
   * @return {@link IListViewModel}
   */
  IListViewModel<T> getListViewModel();

  /**
   * @return {@link ISearchRequestViewModel}
   */
  ISearchRequestViewModel getSearchRequestViewModel();

  /**
   * @return Item selecionado
   */
  T getSelected();

  /**
   * @return {@link DefaultBaseManager} da página
   */
  DefaultBaseManager<T> getService();

  /**
   * Salva um novo objeto
   *
   * @param object Objeto a ser salvo
   * @return O objeto salvo
   * @throws ValidationException caso ocorra exceção ao salvar um objeto
   */
  T save(T object)
    throws ValidationException;

  /**
   * @param item Item a ser selecionado
   */
  void setSelected(T item);

  /**
   * Atualiza um objeto
   *
   * @param old    Objeto antigo
   * @param object Novo objeto
   * @return Objeto atualizado
   * @throws ValidationException caso ocorra exceção ao atualizar o objeto
   */
  T update(T old, T object)
    throws ValidationException;

  /**
   * Visualiza um objeto
   *
   * @param object Objeto a ser visualizado
   * @return {@link IFormEditorViewModel}
   */
  IFormEditorViewModel<T> view(T object);

  /**
   * @return Tipo do dado
   */
  Class<T> getType();

  /**
   * Se é possível copiar
   *
   * @param object Objeto a ser copiado
   * @return se é possível copiar o objeto
   */
  boolean canCopy(T object);
}
