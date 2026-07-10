package com.ia.core.security.service.model.user;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para User.
 * <p>
 * Define os filtros disponíveis para pesquisa de usuários,
 * incluindo filtros por nome e código do usuário.
 *
 * @author IA
 * @since 1.0.0
 */
class UserSearchRequestDTO extends SearchRequestDTO {

   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected UserSearchRequestDTO() {
    createFilters(filterMap, UserTranslator.NOME, UserDTO.CAMPOS.USER_NAME,
                  FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, UserTranslator.CODIGO, UserDTO.CAMPOS.USER_CODE,
                  FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
