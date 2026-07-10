package com.ia.core.security.service.model.log.operation;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para LogOperation.
 * <p>
 * Define os filtros disponíveis para pesquisa de operações de log,
 * incluindo filtros por tipo, ID do valor e código do usuário.
 *
 * @author IA
 * @since 1.0.0
 */
class LogOperationSearchRequestDTO
   extends SearchRequestDTO {

   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected LogOperationSearchRequestDTO() {
    createFilters(filterMap, LogOperationTranslator.TYPE, LogOperationDTO.CAMPOS.TYPE,
                  FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, LogOperationTranslator.VALUE_ID, LogOperationDTO.CAMPOS.VALUE_ID,
                  FieldType.LONG, OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, LogOperationTranslator.USER_CODE, LogOperationDTO.CAMPOS.USER_CODE,
                  FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
