package com.ia.core.owl.service.model.axioma;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Axioma.
 * <p>
 * Define os filtros disponíveis para pesquisa de axiomas,
 * incluindo filtros por prefixo e expressão.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class AxiomaSearchRequestDTO
   extends SearchRequestDTO {

   /** Mapa de filtros. */
   private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

   /**
    * Construtor padrão.
    */
   protected AxiomaSearchRequestDTO() {
    createFilters(filterMap, AxiomaTranslator.PREFIX, AxiomaDTO.CAMPOS.PREFIX,
                  FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, AxiomaTranslator.EXPRESSAO, AxiomaDTO.CAMPOS.EXPRESSAO,
                  FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL,
                  OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, AxiomaTranslator.IS_ATIVO, AxiomaDTO.CAMPOS.IS_ATIVO,
                  FieldType.BOOLEAN, OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
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
