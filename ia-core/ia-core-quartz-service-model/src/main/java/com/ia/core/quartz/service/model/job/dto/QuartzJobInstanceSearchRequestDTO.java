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
 * Request de busca para Instâncias de Job do Quartz.
 * <p>
 * Define os filtros disponíveis para busca de instâncias de job,
 * incluindo filtros por instanceId, jobName e jobGroup.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class QuartzJobInstanceSearchRequestDTO
   extends SearchRequestDTO {

   /**
    * Mapa de filtros.
    */
   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão que configura os filtros disponíveis.
    */
   public QuartzJobInstanceSearchRequestDTO() {
    createFilters(filterMap, QuartzJobTranslator.INSTANCE_ID,
                  QuartzJobInstanceDTO.CAMPOS.INSTANCE_ID, FieldType.STRING,
                  OperatorDTO.EQUAL);

    createFilters(filterMap, QuartzJobTranslator.JOB_NAME,
                  QuartzJobInstanceDTO.CAMPOS.JOB_NAME, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.LIKE);

    createFilters(filterMap, QuartzJobTranslator.JOB_GROUP,
                  QuartzJobInstanceDTO.CAMPOS.JOB_GROUP, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.LIKE);

    createFilters(filterMap, QuartzJobTranslator.TRIGGER_NAME,
                  QuartzJobInstanceDTO.CAMPOS.TRIGGER_NAME, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.LIKE);

    createFilters(filterMap, QuartzJobTranslator.TRIGGER_GROUP,
                  QuartzJobInstanceDTO.CAMPOS.TRIGGER_GROUP, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.LIKE);
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
