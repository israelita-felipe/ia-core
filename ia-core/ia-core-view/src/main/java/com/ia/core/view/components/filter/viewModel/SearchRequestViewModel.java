package com.ia.core.view.components.filter.viewModel;

import java.util.ArrayList;
import java.util.Collection;

import com.ia.core.service.dto.request.SearchRequestDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Implementação padrão de uma requisição de busca
 *
 * @author Israel Araújo
 */
@RequiredArgsConstructor
public class SearchRequestViewModel
  implements ISearchRequestViewModel {
  /** Indicativo de somente leitura */
  @Getter
  @Setter
  private boolean readOnly = false;

  /** Coleção de filtros */
  @Getter
  private Collection<FilterRequestViewModel> filtersViewModel = new ArrayList<>();
  /** modelo */
  @Getter
  @Setter
  private SearchRequestDTO model;
  /** Tipo de objeto a ser filtrado */
  @Getter
  private final Class<?> objectType;
}
