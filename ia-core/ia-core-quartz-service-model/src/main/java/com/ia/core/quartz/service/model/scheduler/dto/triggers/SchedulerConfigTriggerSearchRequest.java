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
 * Entidade de domínio que representa scheduler config trigger search request.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a SchedulerConfigTriggerSearchRequest
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */
class SchedulerConfigTriggerSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   *
   */
  public SchedulerConfigTriggerSearchRequest() {
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
