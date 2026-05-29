package com.ia.core.llm.service.model.ferramenta;

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
 * Requisição de busca para Ferramenta.
 * <p>
 * Define filtros disponíveis para busca de ferramentas.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class FerramentaSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> FILTER_MAP = new HashMap<>();

  static {
    FILTER_MAP.put(
        FilterProperty.builder().label("Título").property("titulo").build(),
        Arrays.asList(
            FilterRequestDTO.builder().key("titulo").fieldType(FieldType.STRING).operator(OperatorDTO.LIKE).build()));
    FILTER_MAP.put(
        FilterProperty.builder().label("Identificador").property("identificador").build(),
        Arrays.asList(
            FilterRequestDTO.builder().key("identificador").fieldType(FieldType.STRING).operator(OperatorDTO.EQUAL).build()));
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return FILTER_MAP;
  }
}
