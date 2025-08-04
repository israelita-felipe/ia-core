package com.ia.core.security.service.model.privilege;

import java.util.Arrays;
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
class PrivilegeSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   *
   */
  public PrivilegeSearchRequest() {
    filterMap
        .put(FilterProperty.builder().label("Nome").property("name")
            .build(),
             Arrays.asList(
                           FilterRequestDTO.builder().key("name")
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.LIKE).build(),
                           FilterRequestDTO.builder().key("name")
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.EQUAL).build(),
                           FilterRequestDTO.builder().key("name")
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.NOT_EQUAL).build()));
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
