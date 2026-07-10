package com.ia.core.quartz.service.model.periodicidade.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Periodicidade.
 * <p>
 * Define os filtros disponíveis para busca de periodicidades,
 * incluindo filtros por zoneId, startTime, endTime, frequency e intervalValue.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class PeriodicidadeSearchRequestDTO
   extends SearchRequestDTO {

   /**
    * Mapa de filtros.
    */
   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected PeriodicidadeSearchRequestDTO() {
    createFilters(filterMap, PeriodicidadeTranslator.ZONE_ID,
                  PeriodicidadeDTO.CAMPOS.ZONE_ID,
                  FieldType.STRING, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);

    // Filtro usando path aninhado para intervaloBase.startTime
    createFilters(filterMap, PeriodicidadeTranslator.INTERVALO_BASE_START_TIME,
                  PeriodicidadeDTO.CAMPOS.INTERVALO_BASE_START_TIME,
                  FieldType.DATE_TIME, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL, OperatorDTO.GREATER_THAN,
                  OperatorDTO.LESS_THAN);

    // Filtro usando path aninhado para intervaloBase.endTime
    createFilters(filterMap, PeriodicidadeTranslator.INTERVALO_BASE_END_TIME,
                  PeriodicidadeDTO.CAMPOS.INTERVALO_BASE_END_TIME,
                  FieldType.DATE_TIME, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL, OperatorDTO.GREATER_THAN,
                  OperatorDTO.LESS_THAN);

    // Filtro usando path aninhado para regra.frequency
    createFilters(filterMap, PeriodicidadeTranslator.REGRA_FREQUENCY,
                  PeriodicidadeDTO.CAMPOS.REGRA_FREQUENCY,
                  FieldType.ENUM, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);

    // Filtro usando path aninhado para regra.intervalValue
    createFilters(filterMap, PeriodicidadeTranslator.REGRA_INTERVAL_VALUE,
                  PeriodicidadeDTO.CAMPOS.REGRA_INTERVAL_VALUE,
                  FieldType.INTEGER, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL, OperatorDTO.GREATER_THAN,
                  OperatorDTO.LESS_THAN);
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
