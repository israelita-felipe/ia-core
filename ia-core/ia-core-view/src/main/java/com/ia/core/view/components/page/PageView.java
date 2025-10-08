package com.ia.core.view.components.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.dialog.exception.ExceptionViewFactory;
import com.ia.core.view.components.editor.formEditor.FormEditorView;
import com.ia.core.view.components.editor.formEditor.IFormEditorView;
import com.ia.core.view.components.editor.formEditor.viewModel.IFormEditorViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel.EditorAction;
import com.ia.core.view.components.filter.ISearchRequestView;
import com.ia.core.view.components.filter.SearchRequestView;
import com.ia.core.view.components.filter.viewModel.ISearchRequestViewModel;
import com.ia.core.view.components.form.IFormView;
import com.ia.core.view.components.form.viewModel.IFormViewModel;
import com.ia.core.view.components.list.IListView;
import com.ia.core.view.components.page.viewModel.IPageViewModel;
import com.ia.core.view.components.page.viewModel.IPageViewModel.PageAction;
import com.ia.core.view.utils.DataProviderFactory;
import com.ia.core.view.utils.Size;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;

import lombok.Getter;

/**
 * Abstração de uma página padrão
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado da page
 */
public abstract class PageView<T extends Serializable>
  extends VerticalLayout
  implements IPageView<T> {
  /** Serial UID */
  private static final long serialVersionUID = -777141442460268806L;

  /** {@link IPageViewModel} da view */
  @Getter
  private IPageViewModel<T> viewModel;

  /** Mapa de botão x ação */
  private Map<PageAction<T>, Button> pageButtons = new LinkedHashMap<>();

  /** Barra de botões */
  private FlexLayout buttonsBar;
  /** Barra de pesquisa */
  @Getter
  private ISearchRequestView search;
  /** Lista de dados */
  @Getter
  private IListView<T> listView;
  /** Editor da página */
  @Getter
  private IFormEditorView<T> editorView;

  /**
   * Construtor padrão
   *
   * @param viewModel {@link IPageViewModel}
   */
  public PageView(IPageViewModel<T> viewModel) {
    setId(createId());
    this.viewModel = viewModel;
    this.createLayout();
    this.refreshButtons();
  }

  @Override
  public void addAction(PageAction<T> action) {
    Button button = createActionButton(action);
    pageButtons.put(action, button);
  }

  @Override
  public void addActions(Collection<PageAction<T>> actions) {
    actions.forEach(this::addAction);
  }

  /**
   * @param object objeto a ser excluído
   * @return se o objeto pode ser excluído
   */
  protected boolean canDelete(T object) {
    return getViewModel().canDelete(object);
  }

  /**
   * @param object objeto a ser copiado
   * @return se o objeto pode ser copiado
   */
  protected boolean canCopy(T object) {
    return getViewModel().canCopy(object);
  }

  @Override
  public void afterCopy(T item) {
    IPageView.super.afterCopy(item);
  }

  /**
   * @param object objeto editado
   * @return se o objeto pode ser editado
   */
  protected boolean canEdit(T object) {
    return getViewModel().canEdit(object);
  }

  /**
   * @return se é possível inserir
   */
  protected boolean canInsert() {
    return getViewModel().canInsert();
  }

  /**
   * @param object Objeto a ser salvo
   * @return se é possível salvar o objeto
   */
  protected boolean canSave(T object) {
    return getViewModel().canSave(object);
  }

  /**
   * @param object Objeto a ser visualizado
   * @return se o objeto pode ser visualizado
   */
  protected boolean canView(T object) {
    return getViewModel().canView(object);
  }

  @Override
  public T createAction() {
    try {
      var editorViewModel = getViewModel().create();
      var newObject = editorViewModel.getModel();
      this.editorView = createEditorView(editorViewModel);
      this.editorView.setHeaderTitle($(newObject.getClass()));
      this.editorView.setSize(getEditorWidth(), getEditorHeight());
      return newObject;
    } catch (Exception e) {
      handleError(e);
      return null;
    }
  }

  @Override
  public void copyAction(T object) {
    try {
      var editorViewModel = getViewModel().copy(object);
      var newObject = editorViewModel.getModel();
      this.editorView = createEditorView(editorViewModel);
      this.editorView.setHeaderTitle($(newObject.getClass()));
      this.editorView.setSize(getEditorWidth(), getEditorHeight());
    } catch (Exception e) {
      handleError(e);
    }
  }

  /**
   * Cria um botão de ação dado uma {@link PageAction}
   *
   * @param action {@link PageAction} que executará a ação
   * @return {@link Button}
   */
  public Button createActionButton(PageAction<T> action) {
    Button actionButton = new Button(action.getLabel(),
                                     action.getIcon().create(), click -> {
                                       executePageAction(action);
                                     });
    actionButton.setTooltipText(action.getLabel());
    actionButton.getStyle().set("margin", "2px");
    return actionButton;
  }

  @Override
  public FlexLayout createButtonsBar() {
    this.buttonsBar = new FlexLayout();
    this.pageButtons.values().forEach(buttonsBar::add);
    return this.buttonsBar;
  }

  /**
   * Cria a barra de botões no {@link VerticalLayout}
   *
   * @param dataWrapper {@link VerticalLayout} wrapper da aplicação
   */
  public void createButtonsBar(VerticalLayout dataWrapper) {
    dataWrapper.add(createButtonsBar());
  }

  @Override
  public DataProvider<T, ?> createDataProvider() {
    DataProvider<T, SearchRequestDTO> dataProvider = DataProviderFactory
        .createDataProviderFromService(getViewModel().getService());
    ConfigurableFilterDataProvider<T, Void, SearchRequestDTO> configurableFilter = dataProvider
        .withConfigurableFilter();
    configurableFilter
        .setFilter(getViewModel().getSearchRequestViewModel().getModel());
    return configurableFilter;
  }

  /**
   * @return {@link VerticalLayout} contendo o wrapper da página
   */
  protected VerticalLayout createDataWrapper() {
    VerticalLayout dataWrapper = new VerticalLayout();
    dataWrapper.setPadding(false);
    createButtonsBar(dataWrapper);
    createListView(dataWrapper);
    return dataWrapper;
  }

  /**
   * Wrapper principal do página
   *
   * @param wrapper {@link HorizontalLayout}
   */
  protected void createDataWrapper(HorizontalLayout wrapper) {
    VerticalLayout dataWrapper = createDataWrapper();
    dataWrapper.setSizeFull();
    wrapper.add(dataWrapper);
  }

  /**
   * Cria as {@link EditorAction} do editor da página
   *
   * @return {@link Collection} de {@link EditorAction} da página
   */
  public Collection<EditorAction<T>> createDefaultEditorActions() {
    return Arrays.asList(createSalvarAction());
  }

  @Override
  public Collection<PageAction<T>> createDefaultPageActions() {
    Collection<PageAction<T>> actions = new ArrayList<>();
    if (isNewButtonVisible()) {
      actions.add(createNewAction());
    }
    if (isEditButtonVisible()) {
      actions.add(createEditAction());
    }
    if (isCopyButtonVisible()) {
      actions.add(createCopyAction());
    }
    if (isViewButtonVisible()) {
      actions.add(createViewAction());
    }
    if (isDeleteButtonVisible()) {
      actions.add(createDeleteAction());
    }
    if (isFilterButtonVisible()) {
      actions.add(createFilterAction());
    }
    return actions;
  }

  /**
   * @return {@link PageAction} do botão de exclusão
   */
  public PageAction<T> createDeleteAction() {
    return PageAction.<T> builder().icon(VaadinIcon.TRASH)
        .enableFunction(this::canDelete).action(this::delete).build();
  }

  /**
   * @return {@link PageAction} do botão editar
   */
  public PageAction<T> createEditAction() {
    return PageAction.<T> builder().icon(VaadinIcon.PENCIL)
        .enableFunction(this::canEdit).action(this::edit).build();
  }

  /**
   * @return {@link PageAction} do botão copiar
   */
  public PageAction<T> createCopyAction() {
    return PageAction.<T> builder().icon(VaadinIcon.COPY)
        .enableFunction(this::canCopy).action(this::copy).build();
  }

  @Override
  public IFormEditorView<T> createEditorView(IFormEditorViewModel<T> editorViewModel) {
    FormEditorView<T> editorView = new FormEditorView<>(editorViewModel) {

      @Override
      public IFormView<T> createContentView(IViewModel<T> viewModel) {
        return createFormView((IFormViewModel<T>) viewModel);
      }

      @Override
      public Collection<EditorAction<T>> createDefaultEditorActions() {
        Collection<EditorAction<T>> actions = new ArrayList<>(PageView.this
            .createDefaultEditorActions());
        actions.addAll(super.createDefaultEditorActions());
        return actions;
      }

    };
    editorView.show();
    return editorView;
  }

  /**
   * Cria o filtro no wrapper
   *
   * @param wrapper {@link HorizontalLayout}
   */
  protected void createFilter(HorizontalLayout wrapper) {
    search = createFilter(this.viewModel.getSearchRequestViewModel());
    search.setVisible(false);
    wrapper.add((Component) search);
  }

  @Override
  public ISearchRequestView createFilter(ISearchRequestViewModel searchViewModel) {
    return new SearchRequestView(searchViewModel) {
      /** UUID */
      private static final long serialVersionUID = 1279579319204324063L;

      @Override
      public void search() {
        try {
          super.search();
          listView.deselectAll();
          listView.refreshAll();
          refreshButtons();
        } catch (Exception e) {
          handleError(e);
        }
      }
    };
  }

  /**
   * @return {@link PageAction} do botão filtrar
   */
  public PageAction<T> createFilterAction() {
    return PageAction.<T> builder().icon(VaadinIcon.FILTER)
        .enableFunction(object -> true).action(this::showFilter).build();
  }

  /**
   * Criação do layout principal
   */
  protected void createLayout() {
    setSizeFull();
    addActions(createDefaultPageActions());
    HorizontalLayout wrapper = new HorizontalLayout();
    createDataWrapper(wrapper);
    createFilter(wrapper);
    wrapper.setPadding(false);
    wrapper.setMargin(false);
    wrapper.setSizeFull();
    add(wrapper);
  }

  /**
   * Cria a lista de dados no wrapper da aplicação
   *
   * @param dataWrapper {@link VerticalLayout} wrapper da aplicação
   */
  public void createListView(VerticalLayout dataWrapper) {
    listView = createListView(this.viewModel.getListViewModel());
    listView.addItemClickListener(click -> {
      refreshButtons();
      getViewModel().setSelected(click.getItem());
    });
    listView.setDataProvider(createDataProvider());
    dataWrapper.add(listView.cast(Component.class));
  }

  /**
   * @return {@link PageAction} do botão novo/criar
   */
  public PageAction<T> createNewAction() {
    return PageAction.<T> builder().icon(VaadinIcon.PLUS_CIRCLE)
        .enableFunction(object -> canInsert()).action(this::create).build();
  }

  /**
   * @return {@link EditorAction} com a ação de salvar
   */
  private EditorAction<T> createSalvarAction() {
    return EditorAction.<T> builder().icon(VaadinIcon.CHECK)
        .label($("Salvar")).enableFunction(this::canSave).action(this::save)
        .build();
  }

  /**
   * @return {@link PageAction} do botão visualizar
   */
  public PageAction<T> createViewAction() {
    return PageAction.<T> builder().icon(VaadinIcon.EYE)
        .enableFunction(this::canView).action(this::view).build();
  }

  /**
   * Deleta o item selecionado
   */
  protected void delete() {
    var item = getViewModel().getSelected();
    delete(item);
  }

  @Override
  public void deleteAction(T item) {
    confirm(() -> {
      try {
        getViewModel().delete(item);
        getViewModel().setSelected(null);
        showSucessMessage($("Excluir"));
      } catch (Exception e) {
        handleError(e);
      } finally {
        this.listView.refreshAll();
      }
    }, $("Excluir"), $("Deseja realmente excluir este item?"));
  }

  /**
   * Edita o item selecionado
   */
  protected void edit() {
    T object = getViewModel().getSelected();
    edit(object);
  }

  /**
   * Copia o item selecionado
   */
  protected void copy() {
    T object = getViewModel().getSelected();
    copy(object);
  }

  /**
   * Ação de editar
   *
   * @param object objeto a ser editado
   */
  @Override
  public void editAction(T object) {
    try {
      IFormEditorViewModel<T> editorViewModel = getViewModel().edit(object);
      this.editorView = createEditorView(editorViewModel);
      this.editorView.setHeaderTitle($(object.getClass()));
      this.editorView.setSize(getEditorWidth(), getEditorHeight());
    } catch (Exception e) {
      handleError(e);
    }
  }

  /**
   * Tamanho da altura do editor
   *
   * @return 600
   */
  protected Size getEditorHeight() {
    return Size.builder().size(600).unit(Unit.PIXELS).build();
  }

  /**
   * Tamanho da largura do editor
   *
   * @return 800
   */
  protected Size getEditorWidth() {
    return Size.builder().size(800).unit(Unit.PIXELS).build();
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  @Override
  public void handleError(Exception ex) {
    ExceptionViewFactory.showError(ex);
  }

  /**
   * @return se o botão de exclusão deverá ser exibido
   */
  @Override
  public boolean isDeleteButtonVisible() {
    return !viewModel.isReadOnly();
  }

  /**
   * @return se o botão de editar deverá ser exibido
   */
  @Override
  public boolean isEditButtonVisible() {
    return !viewModel.isReadOnly();
  }

  @Override
  public boolean isCopyButtonVisible() {
    return !viewModel.isReadOnly();
  }

  /**
   * @return se o botão de filtro deverá ser exibido
   */
  public boolean isFilterButtonVisible() {
    return true;
  }

  /**
   * @return se o botão de novo/criar deverá ser exibido
   */
  @Override
  public boolean isNewButtonVisible() {
    return !viewModel.isReadOnly();
  }

  /**
   * @return se o botão de visualização deverá ser exibido
   */
  @Override
  public boolean isViewButtonVisible() {
    return true;
  }

  @Override
  public boolean refreshAction(PageAction<T> action) {
    return action.getEnableFunction()
        .apply(this.listView.getSelectedItem());
  }

  /**
   * Atualiza o botão
   *
   * @param action {@link PageAction} a ser atualizada
   * @param button {@link Button} relativo a ação
   */
  public void refreshButton(PageAction<T> action, Button button) {
    boolean status = refreshAction(action);
    button.setEnabled(status);
  }

  @Override
  public void refreshButtons() {
    pageButtons.entrySet().forEach(entry -> {
      refreshButton(entry.getKey(), entry.getValue());
    });
  }

  @Override
  public void saveAction(T object) {
    try {
      T salvo = null;
      T selected = getViewModel().getSelected();
      if (selected == null) {
        salvo = getViewModel().save(object);
      } else {
        salvo = getViewModel().update(selected, object);
      }
      editorView.close();
      showSucessMessage($("Salvo com sucesso", salvo));
    } catch (Exception e) {
      handleError(e);
    } finally {
      this.listView.deselectAll();
      this.listView.refreshAll();
      refreshButtons();
    }
  }

  @Override
  public void showFilter() {
    this.search.setVisible(!this.search.isVisible());
  }

  /**
   * Visualiza o objeto selecionado
   */
  protected void view() {
    T object = getViewModel().getSelected();
    view(object);
  }

  @Override
  public void viewAction(T object) {
    try {
      IFormEditorViewModel<T> editorViewModel = getViewModel().view(object);
      this.editorView = createEditorView(editorViewModel);
      this.editorView.setHeaderTitle($(object.getClass()));
      this.editorView.setSize(getEditorWidth(), getEditorHeight());
    } catch (Exception e) {
      handleError(e);
    }
  }

}
