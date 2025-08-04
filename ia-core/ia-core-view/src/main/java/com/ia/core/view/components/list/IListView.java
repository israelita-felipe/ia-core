package com.ia.core.view.components.list;

import java.io.Serializable;
import java.util.Collection;

import com.ia.core.view.components.IView;
import com.ia.core.view.components.list.viewModel.IListViewModel;
import com.ia.core.view.components.properties.HasId;
import com.ia.core.view.components.properties.HasRefresh;
import com.ia.core.view.components.properties.HasSelection;
import com.ia.core.view.properties.AutoCastable;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.grid.dataview.GridDataView;
import com.vaadin.flow.component.grid.dataview.GridLazyDataView;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.data.event.SortEvent.SortNotifier;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.HasDataGenerators;
import com.vaadin.flow.data.provider.HasDataView;
import com.vaadin.flow.data.provider.HasLazyDataView;
import com.vaadin.flow.data.provider.HasListDataView;
import com.vaadin.flow.shared.Registration;

/**
 * Interface que define o contrato de uma lista
 *
 * @author Israel Araújo
 * @param <T> Tipo dos dados da lista
 */
public interface IListView<T extends Serializable>
  extends HasStyle, HasSize, Focusable<Grid<T>>,
  SortNotifier<Grid<T>, GridSortOrder<T>>, HasTheme, HasDataGenerators<T>,
  HasListDataView<T, GridListDataView<T>>,
  HasDataView<T, Void, GridDataView<T>>,
  HasLazyDataView<T, Void, GridLazyDataView<T>>, AutoCastable, HasId,
  IView<T>, HasRefresh<T>, HasSelection<T> {

  /**
   * Adiciona uma coluna
   *
   * @param property propriedade da coluna a ser adicionada
   * @return {@link Column}
   */
  Column<T> addColumn(String property);

  /**
   * Adiciona um escutador ao clicar em um item
   *
   * @param listener {@link ComponentEventListener}
   * @return {@link Registration}
   */
  Registration addItemClickListener(ComponentEventListener<ItemClickEvent<T>> listener);

  /**
   * @return {@link Collection} de items
   */
  default Collection<T> getItems() {
    return getGenericDataView().getItems().toList();
  }

  @Override
  default T getSelectedItem() {
    return getSelectedItems().stream().findFirst().orElse(null);
  }

  @Override
  IListViewModel<T> getViewModel();

  @Override
  default void selectAll() {
    getSelectedItems().forEach(this::deselect);
  }

  /**
   * Atribui o {@link DataProvider}
   *
   * @param dataProvider {@link DataProvider}
   */
  void setDataProvider(DataProvider<T, ?> dataProvider);
}
