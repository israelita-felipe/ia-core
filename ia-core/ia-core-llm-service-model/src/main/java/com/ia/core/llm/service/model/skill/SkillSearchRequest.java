package com.ia.core.llm.service.model.skill;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class SkillSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> FILTER_MAP = new HashMap<>();

  static {
    FILTER_MAP.put(
        FilterProperty.builder().label("Título").property("titulo").build(),
        Arrays.asList(
            FilterRequestDTO.builder().key("titulo").fieldType(FieldType.STRING).operator(OperatorDTO.LIKE).build()));
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return FILTER_MAP;
  }
}
