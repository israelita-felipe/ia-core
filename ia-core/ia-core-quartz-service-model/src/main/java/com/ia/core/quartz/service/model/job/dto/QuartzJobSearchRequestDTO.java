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
 * SearchRequest para QuartzJob.
 * <p>
 * Define os filtros disponíveis para busca de jobs do Quartz,
 * incluindo filtros por nome, grupo e estado do job.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class QuartzJobSearchRequestDTO
   extends SearchRequestDTO {

   /**
    * Mapa de filtros.
    */
   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected QuartzJobSearchRequestDTO() {
    createFilters(filterMap, QuartzJobTranslator.JOB_NAME,
                  QuartzJobDTO.CAMPOS.JOB_NAME, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);

    createFilters(filterMap, QuartzJobTranslator.JOB_GROUP,
                  QuartzJobDTO.CAMPOS.JOB_GROUP, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);

    createFilters(filterMap, QuartzJobTranslator.JOB_STATE,
                  QuartzJobDTO.CAMPOS.JOB_STATE, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
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
