package com.ia.core.quartz.service.model.job.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para QuartzJobTrigger.
 * <p>
 * Define os filtros disponíveis para busca de triggers do Quartz,
 * incluindo filtros por nome, grupo, job e estado do trigger.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class QuartzJobTriggerSearchRequestDTO
   extends SearchRequestDTO {

   /**
    * Mapa de filtros.
    */
   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected QuartzJobTriggerSearchRequestDTO() {
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

  /**
   * Retorna os filtros disponíveis para busca.
   *
   * @return mapa de filtros disponíveis
   */
  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
