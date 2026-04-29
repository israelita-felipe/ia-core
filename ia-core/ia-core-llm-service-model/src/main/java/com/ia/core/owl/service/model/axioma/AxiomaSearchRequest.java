package com.ia.core.owl.service.model.axioma;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * Entidade de domínio que representa axioma search request.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a AxiomaSearchRequest
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
class AxiomaSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   *
   */
  public AxiomaSearchRequest() {
    createFilters(filterMap, AxiomaTranslator.EXPRESSAO,
                  AxiomaDTO.CAMPOS.EXPRESSAO, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL,
                  OperatorDTO.LIKE);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
