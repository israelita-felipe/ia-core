package com.ia.core.quartz.service.model.scheduler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * @author Israel Araújo
 */
class SchedulerConfigSearchRequest
  extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   *
   */
  public SchedulerConfigSearchRequest() {
    filterMap
        .put(FilterProperty.builder()
            .label(SchedulerConfigTranslator.JOB_CLASS_NAME)
            .property(SchedulerConfigDTO.CAMPOS.JOB_CLASS_NAME).build(),
             Arrays
                 .asList(FilterRequestDTO.builder()
                     .key(SchedulerConfigTranslator.JOB_CLASS_NAME)
                     .fieldType(FieldType.STRING)
                     .operator(OperatorDTO.LIKE).build(),
                         FilterRequestDTO.builder()
                             .key(SchedulerConfigTranslator.JOB_CLASS_NAME)
                             .fieldType(FieldType.STRING)
                             .operator(OperatorDTO.EQUAL).build(),
                         FilterRequestDTO.builder()
                             .key(SchedulerConfigTranslator.JOB_CLASS_NAME)
                             .fieldType(FieldType.STRING)
                             .operator(OperatorDTO.NOT_EQUAL).build()));
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
