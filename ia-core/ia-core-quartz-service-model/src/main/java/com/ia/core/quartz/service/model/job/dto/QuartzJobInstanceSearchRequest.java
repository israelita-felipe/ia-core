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
 * Request de busca para Instâncias de Job do Quartz.
 * 
 * @author Israel Araújo
 */
class QuartzJobInstanceSearchRequest extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   * Construtor padrão que configura os filtros disponíveis.
   */
  public QuartzJobInstanceSearchRequest() {
    createFilters(filterMap, QuartzJobTranslator.INSTANCE_ID,
                  QuartzJobInstanceDTO.CAMPOS.INSTANCE_ID,
                  FieldType.STRING, OperatorDTO.EQUAL);
    
    createFilters(filterMap, QuartzJobTranslator.JOB_NAME,
                  QuartzJobInstanceDTO.CAMPOS.JOB_NAME,
                  FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE);
    
    createFilters(filterMap, QuartzJobTranslator.JOB_GROUP,
                  QuartzJobInstanceDTO.CAMPOS.JOB_GROUP,
                  FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
