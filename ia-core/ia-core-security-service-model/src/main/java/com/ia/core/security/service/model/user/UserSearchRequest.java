package com.ia.core.security.service.model.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * @author Israel Araújo
 */
class UserSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   *
   */
  public UserSearchRequest() {
    filterMap
        .put(FilterProperty.builder().label("Nome").property("userName")
            .build(),
             Arrays.asList(
                           FilterRequestDTO.builder().key("userName")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.LIKE).build(),
                           FilterRequestDTO.builder().key("userName")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.EQUAL).build(),
                           FilterRequestDTO.builder().key("userName")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.NOT_EQUAL).build()));
    filterMap
        .put(FilterProperty.builder().label("Código de Usuário")
            .property("userCode").build(),
             Arrays.asList(
                           FilterRequestDTO.builder().key("userCode")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.LIKE).build(),
                           FilterRequestDTO.builder().key("userCode")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.EQUAL).build(),
                           FilterRequestDTO.builder().key("userCode")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.NOT_EQUAL).build()));
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
