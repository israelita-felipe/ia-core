package com.ia.core.security.service.model.privilege;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Privilege.
 * <p>
 * Define os filtros disponíveis para pesquisa de privilégios,
 * incluindo filtros por nome e tipo do privilégio.
 *
 * @author IA
 * @since 1.0.0
 */
class PrivilegeSearchRequestDTO extends SearchRequestDTO {

   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected PrivilegeSearchRequestDTO() {
    createFilters(filterMap, PrivilegeTranslator.NOME, PrivilegeDTO.CAMPOS.NAME,
                  FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, PrivilegeTranslator.TYPE, PrivilegeDTO.CAMPOS.TYPE,
                  FieldType.ENUM, OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
