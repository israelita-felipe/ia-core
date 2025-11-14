package com.ia.core.view.components.list;

import java.io.Serializable;
import java.util.Locale;

import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.provider.DataProvider;

import lombok.Getter;

/**
 * Implementação padrão da uma lista de itens
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado da lista
 */
public abstract class ListView<T extends Serializable>
  extends Grid<T>
  implements IListView<T> {
  /** Serial UID */
  private static final long serialVersionUID = -6185281846653265556L;
  /** ViewModel da lista */
  @Getter
  private IListViewModel<T> viewModel;

  /**
   * Construtor padrão
   *
   * @param viewModel ViewModel da lista
   */
  public ListView(IListViewModel<T> viewModel) {
    super(viewModel.getType(), false);
    setId(createId());
    this.viewModel = viewModel;
    createColumns();
    configureColumns();
  }

  /**
   * Configura as colunas
   */
  private void configureColumns() {
    getColumns().forEach(column -> {
      column.setAutoWidth(isColumnAutoWidth());
      column.setResizable(isColumnResizeable());
      column.setWidth(getDefaultColumnWidth());
    });
    setMultiSort(true);
    addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
  }

  /**
   * @return <code>false</code> por padrão
   */
  public boolean isColumnAutoWidth() {
    return false;
  }

  /**
   * @return "30vw"
   */
  public String getDefaultColumnWidth() {
    return "30vw";
  }

  /**
   * @return <code>true</code> por padrão
   */
  public boolean isColumnResizeable() {
    return true;
  }

  /**
   * Método gancho para criação de colunas
   */
  protected void createColumns() {
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  @Override
  public void refreshAll() {
    getDataProvider().refreshAll();
  }

  @Override
  public void refreshItem(T item) {
    getDataProvider().refreshItem(item);
  }

  @Override
  public void setDataProvider(DataProvider<T, ?> dataProvider) {
    this.getDataCommunicator().setDataProvider(dataProvider, null, true);
  }
}
