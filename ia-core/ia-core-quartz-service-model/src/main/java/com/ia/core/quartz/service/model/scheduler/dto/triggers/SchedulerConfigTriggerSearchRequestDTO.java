package com.ia.core.quartz.service.model.scheduler.dto.triggers;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Request de pesquisa para SchedulerConfigTrigger.
 * <p>
 * Define os filtros disponíveis para busca de triggers de configuração.
 *
 * @author Israel Araújo
 * @see SchedulerConfigTriggerDTO
 * @see SchedulerConfigTriggerTranslator
 * @since 1.0.0
 */
class SchedulerConfigTriggerSearchRequestDTO
   extends SearchRequestDTO {

   /**
    * Mapa de filtros.
    */
   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected SchedulerConfigTriggerSearchRequestDTO() {
    createFilters(filterMap, SchedulerConfigTriggerTranslator.TRIGGER_NAME,
                  SchedulerConfigTriggerDTO.CAMPOS.TRIGGER_NAME,
                  FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
