package com.ia.core.security.service.model.role;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Role.
 * <p>
 * Define os filtros disponíveis para pesquisa de papéis,
 * incluindo filtros por nome do papel.
 *
 * @author IA
 * @since 1.0.0
 */
class RoleSearchRequestDTO extends SearchRequestDTO {

   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected RoleSearchRequestDTO() {
    createFilters(filterMap, RoleTranslator.NOME, RoleDTO.CAMPOS.NAME,
                  FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
