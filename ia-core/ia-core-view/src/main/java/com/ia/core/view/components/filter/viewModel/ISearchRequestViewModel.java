package com.ia.core.view.components.filter.viewModel;

import java.util.Collection;

import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.HasModel;

/**
 * Interface que define um contrato de comportamento de uma requisição de busca.
 *
 * @author Israel Araújo
 */
public interface ISearchRequestViewModel
  extends HasModel<SearchRequestDTO>, HasTranslator,
  IViewModel<SearchRequestDTO> {
  /**
   * @return coleção dos viewModel das requisições dos filtros
   */
  Collection<FilterRequestViewModel> getFiltersViewModel();

  /**
   * Captura o tipo de objeto filtrado
   *
   * @return Tipo do objeto filtrado
   */
  Class<?> getObjectType();
}
