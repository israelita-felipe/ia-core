package com.ia.core.flyway.service.model.flywayexecution.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para FlywayExecution.
 * <p>
 * Define os filtros disponíveis para busca de execuções de migrations do Flyway.
 * </p>
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class FlywayExecutionSearchRequestDTO extends SearchRequestDTO {

    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    public FlywayExecutionSearchRequestDTO() {
        createFilters(filterMap, FlywayExecutionTranslator.RANK,
            FlywayExecutionDTO.CAMPOS.ID,
            FieldType.LONG,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL,
            OperatorDTO.GREATER_THAN, OperatorDTO.LESS_THAN);
        createFilters(filterMap, FlywayExecutionTranslator.VERSION,
            FlywayExecutionDTO.CAMPOS.MIGRATION_VERSION,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
        createFilters(filterMap, FlywayExecutionTranslator.DESCRIPTION,
            FlywayExecutionDTO.CAMPOS.DESCRIPTION,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
        createFilters(filterMap, FlywayExecutionTranslator.TYPE,
            FlywayExecutionDTO.CAMPOS.TYPE,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
        createFilters(filterMap, FlywayExecutionTranslator.SCRIPT,
            FlywayExecutionDTO.CAMPOS.SCRIPT,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
        createFilters(filterMap, FlywayExecutionTranslator.INSTALLED_BY,
            FlywayExecutionDTO.CAMPOS.INSTALLED_BY,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
        createFilters(filterMap, FlywayExecutionTranslator.SUCCESS,
            FlywayExecutionDTO.CAMPOS.SUCCESS,
            FieldType.BOOLEAN,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
    }

    @Override
    public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
        return filterMap;
    }
}