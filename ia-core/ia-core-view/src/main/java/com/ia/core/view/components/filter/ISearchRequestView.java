package com.ia.core.view.components.filter;

import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.IView;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.properties.HasBinder;
import com.ia.core.view.components.properties.HasId;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;

/**
 * @author Israel Araújo
 */
@Tag("search-request-view")
public interface ISearchRequestView
  extends HasSize, HasId, IView<SearchRequestDTO>,
  HasBinder<IViewModel<SearchRequestDTO>> {
  /**
   * Adiciona o filtro
   */
  void addFilter();

  /**
   * @param filter filtro a ser adicionado
   */
  void addFilter(IFilterRequestView filter);

  /**
   * Limpa o filtro
   */
  void clearFilter();

  /**
   * Fecha o filtro
   */
  void closeFilter();

  /**
   * @param label Título do componente
   */
  void createLabel(String label);

  /**
   * @return se o componente está visível
   */
  public boolean isVisible();

  /**
   * @param filter filtro a ser removido
   */
  void removeFilter(IFilterRequestView filter);

  /**
   * Realiza a busca
   */
  default void search() {
    setVisible(false);
  }

  /**
   * @param visible Indicativo de visibilidade
   */
  public void setVisible(boolean visible);

  @Override
  default IViewModel<SearchRequestDTO> getViewModel() {
    return getBinder().getBean().cast();
  }
}
