package com.ia.core.quartz.service.model.scheduler.triggers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.service.dto.filter.FieldTypeDTO;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * @author Israel Araújo
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
                  FieldTypeDTO.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE,
                  OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
