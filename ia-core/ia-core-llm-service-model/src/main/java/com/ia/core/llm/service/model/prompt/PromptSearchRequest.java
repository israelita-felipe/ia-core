package com.ia.core.llm.service.model.prompt;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Requisição de busca para Prompt.
 * <p>
 * Define filtros disponíveis para busca de prompts.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class PromptSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> FILTER_MAP = new HashMap<>();

  static {
    FILTER_MAP.put(
        FilterProperty.builder().label("Título").property("titulo").build(),
        Arrays.asList(
            FilterRequestDTO.builder().key("titulo").fieldType(FieldType.STRING).operator(OperatorDTO.LIKE).build(),
            FilterRequestDTO.builder().key("titulo").fieldType(FieldType.STRING).operator(OperatorDTO.EQUAL).build()));
    FILTER_MAP.put(
        FilterProperty.builder().label("Entrada").property("entrada").build(),
        Arrays.asList(
            FilterRequestDTO.builder().key("entrada").fieldType(FieldType.STRING).operator(OperatorDTO.LIKE).build()));
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return FILTER_MAP;
  }
}
