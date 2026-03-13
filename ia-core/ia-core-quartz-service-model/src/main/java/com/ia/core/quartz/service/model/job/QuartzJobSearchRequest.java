package com.ia.core.quartz.service.model.job;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * Request de busca para Jobs do Quartz.
 * 
 * @author Israel Araújo
 */
class QuartzJobSearchRequest extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   * Construtor padrão que configura os filtros disponíveis.
   */
  public QuartzJobSearchRequest() {
    createFilters(filterMap, QuartzJobTranslator.JOB_NAME,
                  QuartzJobDTO.CAMPOS.JOB_NAME,
                  FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);
    
    createFilters(filterMap, QuartzJobTranslator.JOB_GROUP,
                  QuartzJobDTO.CAMPOS.JOB_GROUP,
                  FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);
    
    createFilters(filterMap, QuartzJobTranslator.JOB_STATE,
                  QuartzJobDTO.CAMPOS.JOB_STATE,
                  FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
