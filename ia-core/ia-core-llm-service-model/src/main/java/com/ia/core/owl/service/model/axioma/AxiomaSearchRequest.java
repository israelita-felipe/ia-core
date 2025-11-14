package com.ia.core.owl.service.model.axioma;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.service.dto.filter.FieldTypeDTO;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * @author Israel Araújo
 */
class AxiomaSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   *
   */
  public AxiomaSearchRequest() {
    createFilters(filterMap, AxiomaTranslator.EXPRESSAO,
                  AxiomaDTO.CAMPOS.EXPRESSAO, FieldTypeDTO.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL,
                  OperatorDTO.LIKE);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
