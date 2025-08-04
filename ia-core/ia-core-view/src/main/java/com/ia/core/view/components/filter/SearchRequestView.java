package com.ia.core.view.components.filter;

import java.util.Locale;

import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.dto.request.SearchRequestTranslator;
import com.ia.core.view.components.filter.viewModel.FilterRequestViewModel;
import com.ia.core.view.components.filter.viewModel.IFilterRequestViewModel;
import com.ia.core.view.components.filter.viewModel.ISearchRequestViewModel;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

import lombok.Getter;

/**
 * Implementação padrão de view para {@link SearchRequestDTO}
 *
 * @author Israel Araújo
 */

public class SearchRequestView
  extends CustomField<SearchRequestDTO>
  implements ISearchRequestView {
  /** Serial UID */
  private static final long serialVersionUID = 9016317828665243204L;
  /** Binder */
  @SuppressWarnings("rawtypes")
  @Getter
  private Binder binder;
  /** Layout onde ficarão os filtros */
  private VerticalLayout filters;

  /**
   * @param viewModel {@link ISearchRequestViewModel}
   */
  public SearchRequestView(ISearchRequestViewModel viewModel) {
    binder = createBinder(viewModel);
    setId(createId());
    createMainLayout();
    createLabel($(SearchRequestTranslator.FILTER_REQUEST));
    prepareFilters(viewModel);
  }

  /**
   * Prepara os filtros de acordo o view Model
   *
   * @param viewModel {@link ISearchRequestViewModel}
   */
  protected void prepareFilters(ISearchRequestViewModel viewModel) {
    viewModel.getFiltersViewModel().forEach(filter -> {
      addFilter(createFilter(filter));
    });
  }

  /**
   * Cria o binder
   *
   * @param viewModel {@link ISearchRequestViewModel}
   * @return {@link Binder}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected Binder createBinder(ISearchRequestViewModel viewModel) {
    Binder binder = new Binder<>(viewModel.getClass(), true);
    binder.setBean(viewModel);
    binder.setReadOnly(viewModel.isReadOnly());
    return binder;
  }

  @Override
  public void addFilter() {
    FilterRequestViewModel filterViewModel = new FilterRequestViewModel(getViewModel()
        .getObjectType(), getViewModel().getModel().getAvaliableFilters());
    filterViewModel.setModel(new FilterRequestDTO());
    addFilter(createFilter(filterViewModel));
    getViewModel().getFiltersViewModel().add(filterViewModel);
    getViewModel().getModel().getFilters().add(filterViewModel.getModel());
  }

  @Override
  public void addFilter(IFilterRequestView view) {
    view.setDeleteAction(() -> {
      removeFilter(view);
    });
    filters.add((Component) view);
  }

  @Override
  public void clearFilter() {
    getViewModel().getModel().getFilters().clear();
    getViewModel().getFiltersViewModel().clear();
    filters.removeAll();
    search();
  }

  @Override
  public void closeFilter() {
    setVisible(false);
  }

  /**
   * @return {@link Button} de adição de filtro
   */
  protected Button createAddFilterButton() {
    Button button = new Button(VaadinIcon.PLUS_CIRCLE.create(), onClick -> {
      addFilter();
    });
    button.getStyle().set("margin", "2px");
    return button;
  }

  /**
   * Cria o botão de adicionar filtro no layout específico
   *
   * @param filterToolBar {@link FlexLayout} onde será criado o botão de filtro
   */
  protected void createAddFilterButton(FlexLayout filterToolBar) {
    filterToolBar.add(createAddFilterButton());
  }

  /**
   * Cria o botão de cancelar
   *
   * @return {@link Button}
   */
  protected Button createCancelButton() {
    Button button = new Button(VaadinIcon.CLOSE.create(), onClick -> {
      closeFilter();
    });
    button.getStyle().set("margin", "2px");
    return button;
  }

  /**
   * Cria o botão de cancelar no layout específico
   *
   * @param filterToolBar {@link FlexLayout} onde será criado
   */
  protected void createCancelButton(FlexLayout filterToolBar) {
    filterToolBar.add(createCancelButton());
  }

  /**
   * @return {@link Button} de limpar filtro
   */
  protected Button createClearButton() {
    Button button = new Button(VaadinIcon.ERASER.create(), onClick -> {
      clearFilter();
    });
    button.getStyle().set("margin", "2px");
    return button;
  }

  /**
   * Cria o botão de limpar no layout específico
   *
   * @param filterToolBar {@link FlexLayout} onde será criado o botão
   */
  protected void createClearButton(FlexLayout filterToolBar) {
    filterToolBar.add(createClearButton());
  }

  /**
   * Cria o filtro
   *
   * @param filter view model do filtro
   * @return {@link IFilterRequestViewModel}
   */
  protected IFilterRequestView createFilter(IFilterRequestViewModel filter) {
    return new FilterRequestView(filter);
  }

  /**
   * @return {@link VerticalLayout} onde ficarão os filtros
   */
  protected VerticalLayout createFiltersBar() {
    filters = new VerticalLayout();
    filters.setWidthFull();
    filters.setPadding(false);
    filters.getStyle().set("gap", "var(--lumo-space-m)");
    return filters;
  }

  /**
   * @return {@link FlexLayout} dos botões do filtro
   */
  protected FlexLayout createFilterToolBar() {
    FlexLayout filterToolBar = new FlexLayout();
    filterToolBar.setJustifyContentMode(JustifyContentMode.BETWEEN);
    createFilterToolBarButtons(filterToolBar);
    filterToolBar.setWidthFull();
    return filterToolBar;
  }

  /**
   * Cria a barra de botões do filtro
   *
   * @param filterToolBar {@link FlexLayout} onde será criado
   */
  protected void createFilterToolBarButtons(FlexLayout filterToolBar) {
    createAddFilterButton(filterToolBar);
    createClearButton(filterToolBar);
    createSeparator(filterToolBar);
    createSearchButton(filterToolBar);
    createCancelButton(filterToolBar);
  }

  @Override
  public void createLabel(String label) {
    setLabel(label);
  }

  /**
   *
   */
  protected void createMainLayout() {
    add(createFilterToolBar());
    add(createFiltersBar());
    getStyle().set("width", "60%").set("right", "8px")
        .set("position", "absolute").set("padding", "8px").set("top", "8px")
        .set("bottom", "8px").set("z-index", "2").set("overflow", "auto")
        .set("border-radius", "var(--lumo-border-radius-l)")
        .set("background-color", "white")
        .set("box-shadow",
             "-2px 0px var(--lumo-shade-5pct), var(--lumo-box-shadow-s)");
  }

  /**
   * @return {@link Button} de busca
   */
  protected Button createSearchButton() {
    Button button = new Button(VaadinIcon.SEARCH.create(), onClick -> {
      search();
    });
    button.getStyle().set("margin", "2px");
    return button;
  }

  /**
   * Cria o botão de buscar no layout específico
   *
   * @param filterToolBar {@link FlexLayout}
   */
  protected void createSearchButton(FlexLayout filterToolBar) {
    filterToolBar.add(createSearchButton());
  }

  /**
   * @return {@link Component} separador
   */
  protected Component createSeparator() {
    Div div = new Div();
    div.setWidthFull();
    return div;
  }

  /**
   * Cria o separado no layout específico
   *
   * @param filterToolBar {@link FlexLayout} onde será criado
   */
  protected void createSeparator(FlexLayout filterToolBar) {
    filterToolBar.add(createSeparator());
  }

  @Override
  protected SearchRequestDTO generateModelValue() {
    return getViewModel().getModel();
  }

  @Override
  public Locale getLocale() {
    return super.getLocale();
  }

  @Override
  public void removeFilter(IFilterRequestView view) {
    IFilterRequestViewModel filterViewModel = view.getViewModel();
    getViewModel().getFiltersViewModel().remove(filterViewModel);
    getViewModel().getModel().getFilters()
        .remove(view.getViewModel().getModel());
    filters.remove((Component) view);
  }

  @Override
  protected void setPresentationValue(SearchRequestDTO newPresentationValue) {
    getViewModel().setModel(newPresentationValue);
  }

  @Override
  public ISearchRequestViewModel getViewModel() {
    return ISearchRequestView.super.getViewModel().cast();
  }

  @Override
  public String getModelPrefix() {
    return getViewModel().getModelPrefix();
  }
}
