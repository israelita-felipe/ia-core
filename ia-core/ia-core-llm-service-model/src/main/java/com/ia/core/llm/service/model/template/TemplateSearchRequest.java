package com.ia.core.llm.service.model.template;

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
 * Entidade de domínio que representa template search request.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a TemplateSearchRequest
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
class TemplateSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   *
   */
  public TemplateSearchRequest() {
    filterMap
        .put(FilterProperty.builder().label("Título").property("titulo")
            .build(),
             Arrays.asList(
                           FilterRequestDTO.builder().key("titulo")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.LIKE).build(),
                           FilterRequestDTO.builder().key("titulo")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.EQUAL).build(),
                           FilterRequestDTO.builder().key("titulo")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.NOT_EQUAL).build()));
    filterMap
        .put(FilterProperty.builder().label("Conteudo").property("conteudo")
            .build(),
             Arrays.asList(
                           FilterRequestDTO.builder().key("conteudo")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.LIKE).build(),
                           FilterRequestDTO.builder().key("conteudo")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.EQUAL).build(),
                           FilterRequestDTO.builder().key("conteudo")
                               .fieldType(FieldType.STRING)
                               .operator(OperatorDTO.NOT_EQUAL).build()));
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
