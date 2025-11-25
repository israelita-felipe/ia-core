package com.ia.core.view.components.page.viewModel;

import java.io.Serializable;
import java.util.UUID;

import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.formEditor.viewModel.FormEditorViewModel;
import com.ia.core.view.components.editor.formEditor.viewModel.IFormEditorViewModel;
import com.ia.core.view.components.filter.viewModel.ISearchRequestViewModel;
import com.ia.core.view.components.filter.viewModel.SearchRequestViewModel;
import com.ia.core.view.components.form.viewModel.FormViewModelConfig;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.list.viewModel.ListViewModel;
import com.ia.core.view.components.list.viewModel.ListViewModelConfig;
import com.ia.core.view.exception.ValidationException;
import com.ia.core.view.manager.DefaultBaseManager;

import lombok.Getter;
import lombok.Setter;

/**
 * Implementação padrão para ViewModel de uma Página
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado da página
 */
public abstract class PageViewModel<T extends Serializable>
  implements IPageViewModel<T> {

  /** ViewModel para a lista */
  @Getter
  private IListViewModel<T> listViewModel;
  /** ViewModel para o editor */
  @Getter
  private IFormEditorViewModel<T> editorViewModel;
  /** View Model para search request */
  @Getter
  private ISearchRequestViewModel searchRequestViewModel;

  /** Indicativo somente leitura */
  @Getter
  private boolean readOnly = false;

  /** Item selecionado */
  @Getter
  @Setter
  private T selected;

  @Getter
  private PageViewModelConfig<T> config;

  /**
   * Construtor padrão
   *
   * @param service {@link DefaultBaseManager} da página
   */
  public PageViewModel(PageViewModelConfig<T> config) {
    this.config = config;
    this.listViewModel = createListViewModel(config
        .createListViewModelConfig(readOnly));
    this.searchRequestViewModel = createSearchRequestViewModel();
  }

  @Override
  public boolean canDelete(T object) {
    return object != null && !isReadOnly();
  }

  @Override
  public boolean canEdit(T object) {
    return object != null && !isReadOnly();
  }

  @Override
  public boolean canCopy(T object) {
    return canView(object) && canInsert() && !isReadOnly();
  }

  @Override
  public boolean canInsert() {
    return !isReadOnly();
  }

  @Override
  public boolean canSave(T object) {
    return object != null
        && !getEditorViewModel().getContentViewModel().isReadOnly();
  }

  @Override
  public boolean canView(T object) {
    return object != null;
  }

  @Override
  public boolean canPrint() {
    return true;
  }

  @Override
  public IFormEditorViewModel<T> create() {
    var editorViewModel = createEditorViewModel();
    T newObject = createNewObject();
    // setSelected(newObject);
    editorViewModel.setModel(newObject);
    editorViewModel.setReadOnly(false);
    return editorViewModel;
  }

  /**
   * @return {@link IFormEditorViewModel}
   */
  @Override
  public IFormEditorViewModel<T> createEditorViewModel() {
    this.editorViewModel = new FormEditorViewModel<T>() {
      /** Serial UID */
      private static final long serialVersionUID = 7327610861210089029L;

      @Override
      protected IViewModel<T> createContentViewModel() {
        return createFormViewModel(getConfig()
            .createFormViewModelConfig(readOnly));
      }

    };
    return this.editorViewModel;
  }

  /**
   * Cria o modelo do {@link IFormViewModel} utilização para manipulação do
   * objeto
   *
   * @param readOnly Indicativo de somente leitura
   * @return {@link IFormViewModel}
   */
  public abstract IFormViewModel<T> createFormViewModel(FormViewModelConfig<T> config);

  /**
   * @return {@link ListViewModel}
   */
  protected ListViewModel<T> createListViewModel(ListViewModelConfig<T> config) {
    return new ListViewModel<T>(config) {

      @Override
      public Class<T> getType() {
        return PageViewModel.this.getType();
      }
    };
  }

  /**
   * @return {@link SearchRequestDTO}
   */
  protected abstract SearchRequestDTO createSearchRequest();

  /**
   * @return {@link ISearchRequestViewModel}
   */
  protected ISearchRequestViewModel createSearchRequestViewModel() {
    SearchRequestViewModel request = new SearchRequestViewModel(getType());
    request.setModel(createSearchRequest());
    return request;
  }

  @Override
  public void delete(T selectedItem) {
    getService().delete(getId(selectedItem));
  }

  @Override
  public IFormEditorViewModel<T> edit(T object) {
    var editorViewModel = createEditorViewModel();
    editorViewModel.setModel(cloneObject(object));
    editorViewModel.setReadOnly(false);
    return editorViewModel;
  }

  @Override
  public IFormEditorViewModel<T> copy(T object) {
    var editorViewModel = createEditorViewModel();
    editorViewModel.setModel(copyObject(object));
    setSelected(editorViewModel.getModel());
    editorViewModel.setReadOnly(false);
    return editorViewModel;
  }

  /**
   * Captura o identificador do objeto
   *
   * @param object Objeto avaliado
   * @return {@link UUID} do objeto
   */
  public abstract UUID getId(T object);

  /**
   * @return {@link #service}
   */
  @Override
  public DefaultBaseManager<T> getService() {
    return getConfig().getService();
  }

  @Override
  public T save(T object)
    throws ValidationException {
    return getService().save(object);
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
    if (this.listViewModel != null) {
      this.listViewModel.setReadOnly(readOnly);
    }
    if (this.editorViewModel != null) {
      this.editorViewModel.setReadOnly(readOnly);
    }
    if (this.searchRequestViewModel != null) {
      this.searchRequestViewModel.setReadOnly(readOnly);
    }
  }

  @Override
  public T update(T old, T object)
    throws ValidationException {
    return getService().update(old, object);
  }

  @Override
  public IFormEditorViewModel<T> view(T object) {
    var editorViewModel = createEditorViewModel();
    editorViewModel.setModel(cloneObject(object));
    editorViewModel.setReadOnly(true);
    return editorViewModel;
  }

}
