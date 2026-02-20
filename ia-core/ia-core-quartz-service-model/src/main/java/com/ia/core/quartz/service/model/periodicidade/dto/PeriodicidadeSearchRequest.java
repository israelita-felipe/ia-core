package com.ia.core.quartz.service.model.periodicidade.dto;

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
class PeriodicidadeSearchRequest
  extends SearchRequestDTO {
  /** Filtros */
  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>() {
    /** Serial UID */
    private static final long serialVersionUID = -8351015313648757624L;
    /**
     * Zone ID
     */
    {
      put(FilterProperty.builder().label("Zone ID").property("zoneId")
          .build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("zoneId")
                            .fieldType(FieldType.STRING)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("zoneId")
                            .fieldType(FieldType.STRING)
                            .operator(OperatorDTO.NOT_EQUAL).build(),
                        FilterRequestDTO.builder().key("zoneId")
                            .fieldType(FieldType.STRING)
                            .operator(OperatorDTO.LIKE).build()));
    }
    /**
     * Start Time
     */
    {
      put(FilterProperty.builder().label("Data de início")
          .property("intervaloBase.startTime").build(),
          Arrays.asList(FilterRequestDTO.builder()
              .key("intervaloBase.startTime").fieldType(FieldType.DATE_TIME)
              .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder()
                            .key("intervaloBase.startTime")
                            .fieldType(FieldType.DATE_TIME)
                            .operator(OperatorDTO.NOT_EQUAL).build(),
                        FilterRequestDTO.builder()
                            .key("intervaloBase.startTime")
                            .fieldType(FieldType.DATE_TIME)
                            .operator(OperatorDTO.GREATER_THAN).build(),
                        FilterRequestDTO.builder()
                            .key("intervaloBase.startTime")
                            .fieldType(FieldType.DATE_TIME)
                            .operator(OperatorDTO.LESS_THAN).build()));
    }
    /**
     * End Time
     */
    {
      put(FilterProperty.builder().label("Data de fim")
          .property("intervaloBase.endTime").build(),
          Arrays.asList(FilterRequestDTO.builder()
              .key("intervaloBase.endTime").fieldType(FieldType.DATE_TIME)
              .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder()
                            .key("intervaloBase.endTime")
                            .fieldType(FieldType.DATE_TIME)
                            .operator(OperatorDTO.NOT_EQUAL).build(),
                        FilterRequestDTO.builder()
                            .key("intervaloBase.endTime")
                            .fieldType(FieldType.DATE_TIME)
                            .operator(OperatorDTO.GREATER_THAN).build(),
                        FilterRequestDTO.builder()
                            .key("intervaloBase.endTime")
                            .fieldType(FieldType.DATE_TIME)
                            .operator(OperatorDTO.LESS_THAN).build()));
    }
    /**
     * Frequency
     */
    {
      put(FilterProperty.builder().label("Frequência")
          .property("regra.frequency").build(),
          Arrays.asList(
                        FilterRequestDTO.builder().key("regra.frequency")
                            .fieldType(FieldType.ENUM)
                            .operator(OperatorDTO.EQUAL).build(),
                        FilterRequestDTO.builder().key("regra.frequency")
                            .fieldType(FieldType.ENUM)
                            .operator(OperatorDTO.NOT_EQUAL).build()));
    }
    /**
     * Interval Value
     */
    {
      put(FilterProperty.builder().label("Intervalo")
          .property("regra.intervalValue").build(),
          Arrays
              .asList(FilterRequestDTO.builder().key("regra.intervalValue")
                  .fieldType(FieldType.INTEGER).operator(OperatorDTO.EQUAL)
                  .build(),
                      FilterRequestDTO.builder().key("regra.intervalValue")
                          .fieldType(FieldType.INTEGER)
                          .operator(OperatorDTO.NOT_EQUAL).build(),
                      FilterRequestDTO.builder().key("regra.intervalValue")
                          .fieldType(FieldType.INTEGER)
                          .operator(OperatorDTO.GREATER_THAN).build(),
                      FilterRequestDTO.builder().key("regra.intervalValue")
                          .fieldType(FieldType.INTEGER)
                          .operator(OperatorDTO.LESS_THAN).build()));
    }
  };

  /**
   * Construtor padrão
   */
  protected PeriodicidadeSearchRequest() {
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
