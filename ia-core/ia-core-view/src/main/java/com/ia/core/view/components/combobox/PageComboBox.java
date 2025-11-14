package com.ia.core.view.components.combobox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.editor.pageEditor.PageEditorView;
import com.ia.core.view.components.editor.pageEditor.viewModel.IPageEditorViewModel;
import com.ia.core.view.components.editor.viewModel.IEditorViewModel.EditorAction;
import com.ia.core.view.components.page.IPageView;
import com.ia.core.view.components.page.viewModel.IPageViewModel;
import com.ia.core.view.manager.DefaultBaseManager;
import com.ia.core.view.utils.DataProviderFactory;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBox.ItemFilter;
import com.vaadin.flow.component.combobox.dataview.ComboBoxDataView;
import com.vaadin.flow.component.combobox.dataview.ComboBoxLazyDataView;
import com.vaadin.flow.component.combobox.dataview.ComboBoxListDataView;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.BackEndDataProvider;
import com.vaadin.flow.data.provider.CallbackDataProvider.CountCallback;
import com.vaadin.flow.data.provider.CallbackDataProvider.FetchCallback;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.InMemoryDataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.SerializablePredicate;

/**
 * Implementação de um combobox que permite cadastro em uma page
 *
 * @author Israel Araújo
 * @param <T> Tipo do dado do campo
 */
public abstract class PageComboBox<T extends Serializable>
  extends CustomField<T> {
  /** Serial UID */
  private static final long serialVersionUID = 1705011927299720694L;
  /** {@link ComboBox} */
  private ComboBox<T> delegate;
  /** View Model de edição */
  private IPageEditorViewModel<T> viewModel;
  /** Container */
  private HorizontalLayout container;
  /** Botão de criação de página para cadastro */
  private Button button;
  /** Título da página */
  private String pageTitle;

  /**
   * Construtor padrão
   *
   * @param label      Título do campo
   * @param pageTitle  Título da página
   * @param viewModel  {@link IPageEditorViewModel}
   * @param properties Propriedades de filtro
   */
  public PageComboBox(String label, String pageTitle,
                      IPageEditorViewModel<T> viewModel,
                      Set<String> properties) {
    super();
    this.viewModel = viewModel;
    setLabel(label);
    this.pageTitle = pageTitle;
    container = new HorizontalLayout();
    container.setWidthFull();
    add(container);
    createComboBox();
    createNewButton();
    setItems(createDataProvider(getService(), properties));
  }

  /**
   * @return {@link DefaultBaseManager} para listagem dos itens
   */
  protected DefaultBaseManager<T> getService() {
    return this.viewModel.getContentViewModel().getService();
  }

  /**
   * @param service    {@link DefaultBaseManager}
   * @param properties propriedades de filtro
   * @return {@link DataProvider}
   */
  protected DataProvider<T, String> createDataProvider(DefaultBaseManager<T> service,
                                                       Set<String> properties) {
    return DataProviderFactory
        .createBaseDataProviderFromService(service, properties);
  }

  /**
   * Cria o combobox padrão
   */
  protected void createComboBox() {
    delegate = new ComboBox<>();
    delegate.setWidthFull();
    container.add(delegate);
  }

  /**
   * Cria o botão de inserir
   */
  protected void createNewButton() {
    button = new Button(VaadinIcon.INSERT.create(), onClick -> {
      insert();
    });
    container.add(button);
  }

  /**
   * @param viewModel {@link IPageEditorViewModel} do editor
   * @return {@link PageEditorView}
   */
  protected PageEditorView<T> createPageEditorView(IPageEditorViewModel<T> viewModel) {
    PageEditorView<T> pageEditorView = new PageEditorView<T>(viewModel) {
      /** Serial UID */
      private static final long serialVersionUID = 7605733752133358691L;

      @Override
      public IPageView<T> createContentView(IViewModel<T> viewModel) {
        return createPageView(viewModel.cast());
      }

      @Override
      public Collection<EditorAction<T>> createDefaultEditorActions() {
        ArrayList<EditorAction<T>> actions = new ArrayList<>(super.createDefaultEditorActions());
        actions.add(createOkAction());
        return actions;
      }

      public EditorAction<T> createOkAction() {
        return EditorAction.<T> builder().label($("OK"))
            .icon(VaadinIcon.CHECK).action(object -> {
              ok();
              close();
            }).build();
      }

    };
    pageEditorView.setWidth(getEditorWidth());
    pageEditorView.setHeight(getEditorHeight());
    pageEditorView.setHeaderTitle(pageTitle);
    return pageEditorView;
  }

  /**
   * @return 80vh
   */
  private String getEditorHeight() {
    return "80vh";
  }

  /**
   * @return 80vw
   */
  private String getEditorWidth() {
    return "80vw";
  }

  /**
   * Cria a página do combobox
   *
   * @param viewModel {@link IPageViewModel} da página de cadastro
   * @return {@link IPageView}
   */
  public abstract IPageView<T> createPageView(IPageViewModel<T> viewModel);

  @Override
  protected T generateModelValue() {
    return delegate.getValue();
  }

  /**
   * Ação do botão inserir
   */
  protected void insert() {
    PageEditorView<T> pageEditorView = createPageEditorView(viewModel);
    pageEditorView.open();
  }

  /**
   * Ação do botão OK
   */
  protected void ok() {
    T selected = viewModel.getContentViewModel().getSelected();
    delegate.getDataProvider().refreshAll();
    viewModel.setModel(selected);
    delegate.setValue(selected);
  }

  /**
   * @param itemLabelGenerator {@link ItemLabelGenerator}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItemLabelGenerator(com.vaadin.flow.component.ItemLabelGenerator)
   */
  public void setItemLabelGenerator(ItemLabelGenerator<T> itemLabelGenerator) {
    delegate.setItemLabelGenerator(itemLabelGenerator);
  }

  /**
   * @param dataProvider {@link BackEndDataProvider}
   * @return {@link ComboBoxLazyDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.data.provider.BackEndDataProvider)
   */
  public ComboBoxLazyDataView<T> setItems(BackEndDataProvider<T, String> dataProvider) {
    return delegate.setItems(dataProvider);
  }

  /**
   * @param items item a serem adicionados
   * @return {@link ComboBoxListDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(java.util.Collection)
   */
  public ComboBoxListDataView<T> setItems(Collection<T> items) {
    return delegate.setItems(items);
  }

  /**
   * @param dataProvider {@link DataProvider}
   * @return {@link ComboBoxDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.data.provider.DataProvider)
   */
  public ComboBoxDataView<T> setItems(DataProvider<T, String> dataProvider) {
    return delegate.setItems(dataProvider);
  }

  /**
   * @param fetchCallback {@link FetchCallback}
   * @return {@link ComboBoxListDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.data.provider.CallbackDataProvider.FetchCallback)
   */
  public ComboBoxLazyDataView<T> setItems(FetchCallback<T, String> fetchCallback) {
    return delegate.setItems(fetchCallback);
  }

  /**
   * @param fetchCallback {@link FetchCallback}
   * @param countCallback {@link CountCallback}
   * @return {@link ComboBoxLazyDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.data.provider.CallbackDataProvider.FetchCallback,
   *      com.vaadin.flow.data.provider.CallbackDataProvider.CountCallback)
   */
  public ComboBoxLazyDataView<T> setItems(FetchCallback<T, String> fetchCallback,
                                          CountCallback<T, String> countCallback) {
    return delegate.setItems(fetchCallback, countCallback);
  }

  /**
   * @param inMemoryDataProvider {@link InMemoryDataProvider}
   * @param filterConverter      {@link SerializableFunction}
   * @return {@link ComboBoxDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.data.provider.InMemoryDataProvider,
   *      com.vaadin.flow.function.SerializableFunction)
   */
  public ComboBoxDataView<T> setItems(InMemoryDataProvider<T> inMemoryDataProvider,
                                      SerializableFunction<String, SerializablePredicate<T>> filterConverter) {
    return delegate.setItems(inMemoryDataProvider, filterConverter);
  }

  /**
   * @param itemFilter {@link ItemFilter}
   * @param items      items a serem adicionados
   * @return {@link ComboBoxListDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.component.combobox.ComboBox.ItemFilter,
   *      java.util.Collection)
   */
  public ComboBoxListDataView<T> setItems(ItemFilter<T> itemFilter,
                                          Collection<T> items) {
    return delegate.setItems(itemFilter, items);
  }

  /**
   * @param itemFilter       {@link ItemFilter}
   * @param listDataProvider {@link ListDataProvider}
   * @return {@link ComboBoxListDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.component.combobox.ComboBox.ItemFilter,
   *      com.vaadin.flow.data.provider.ListDataProvider)
   */
  public ComboBoxListDataView<T> setItems(ItemFilter<T> itemFilter,
                                          ListDataProvider<T> listDataProvider) {
    return delegate.setItems(itemFilter, listDataProvider);
  }

  /**
   * @param itemFilter {@link ItemFilter}
   * @param items      itens a serem adicionados
   * @return {@link ComboBoxListDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.component.combobox.ComboBox.ItemFilter,
   *      java.lang.Object[])
   */
  @SuppressWarnings("unchecked")
  public ComboBoxListDataView<T> setItems(ItemFilter<T> itemFilter,
                                          T... items) {
    return delegate.setItems(itemFilter, items);
  }

  /**
   * @param dataProvider {@link ListDataProvider}
   * @return {@link ComboBoxListDataView}
   * @see com.vaadin.flow.component.combobox.ComboBoxBase#setItems(com.vaadin.flow.data.provider.ListDataProvider)
   */
  public ComboBoxListDataView<T> setItems(ListDataProvider<T> dataProvider) {
    return delegate.setItems(dataProvider);
  }

  /**
   * @param items items a serem adicionados
   * @return {@link ComboBoxListDataView}
   * @see com.vaadin.flow.data.provider.HasListDataView#setItems(java.lang.Object[])
   */
  @SuppressWarnings("unchecked")
  public ComboBoxListDataView<T> setItems(T... items) {
    return delegate.setItems(items);
  }

  @Override
  protected void setPresentationValue(T newPresentationValue) {
    delegate.setValue(newPresentationValue);
  }

  @Override
  public void setReadOnly(boolean readOnly) {
    delegate.setReadOnly(readOnly);
    button.setVisible(!readOnly);
  }

}
