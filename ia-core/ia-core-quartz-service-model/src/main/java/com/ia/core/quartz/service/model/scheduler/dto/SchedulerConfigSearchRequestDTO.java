package com.ia.core.quartz.service.model.scheduler.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Request de pesquisa para SchedulerConfig.
 * <p>
 * Define os filtros disponíveis para busca de configurações de scheduler.
 *
 * @author Israel Araújo
 * @see SchedulerConfigDTO
 * @see SchedulerConfigTranslator
 * @since 1.0.0
 */
class SchedulerConfigSearchRequestDTO
   extends SearchRequestDTO {

   /**
    * Mapa de filtros.
    */
   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected SchedulerConfigSearchRequestDTO() {
    createFilters(filterMap, SchedulerConfigTranslator.JOB_CLASS_NAME,
                  SchedulerConfigDTO.CAMPOS.JOB_CLASS_NAME,
                  FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
