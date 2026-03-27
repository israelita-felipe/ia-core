package com.ia.core.quartz.service.model.job.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * Request de busca para Triggers do Quartz.
 *
 * @author Israel Araújo
 */
class QuartzJobTriggerSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   * Construtor padrão que configura os filtros disponíveis.
   */
  public QuartzJobTriggerSearchRequest() {
    createFilters(filterMap, QuartzJobTranslator.TRIGGER_NAME,
                  QuartzJobTriggerDTO.CAMPOS.TRIGGER_NAME, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);

    createFilters(filterMap, QuartzJobTranslator.TRIGGER_GROUP,
                  QuartzJobTriggerDTO.CAMPOS.TRIGGER_GROUP,
                  FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);

    createFilters(filterMap, QuartzJobTranslator.JOB_NAME,
                  QuartzJobTriggerDTO.CAMPOS.JOB_NAME, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);

    createFilters(filterMap, QuartzJobTranslator.TRIGGER_STATE,
                  QuartzJobTriggerDTO.CAMPOS.TRIGGER_STATE,
                  FieldType.STRING, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
