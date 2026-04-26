package com.ia.core.view.components.filter.viewModel;

import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.view.components.IViewModel;
import com.ia.core.view.components.properties.HasTranslator;
import com.ia.core.view.properties.HasModel;

import java.util.Collection;
import java.util.Map;

/**
 * Interface que define o viewModel de um filtro
 *
 * @author Israel Araújo
 */
public interface IFilterRequestViewModel
  extends HasModel<FilterRequestDTO>, HasTranslator,
  IViewModel<FilterRequestDTO> {
  /**
   * @return Mapa dos filtros
   */
  Map<FilterProperty, Collection<FilterRequestDTO>> getFilterMap();

  /**
   * Captura o tipo da propriedade do filtro
   *
   * @return {@link Class} do tipo do filtro
   */
  Class<?> getType();

  /**
   * Captura o tipo da propriedade do filtro como {@link Enum}.
   *
   * @return {@link Enum} do tipo do filtro
   */
  Class<? extends Enum<?>> getEnumType();
}
