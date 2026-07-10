package com.ia.core.communication.service.model.grupocontato.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para GrupoContato.
 * <p>
 * Define os filtros disponíveis para pesquisa de grupos de contatos,
 * incluindo filtros por nome, descrição e status ativo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class GrupoContatoSearchRequestDTO extends SearchRequestDTO {

    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    public GrupoContatoSearchRequestDTO() {
        createFilters(filterMap, GrupoContatoTranslator.NOME,
            GrupoContatoDTO.CAMPOS.NOME, FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
        createFilters(filterMap, GrupoContatoTranslator.DESCRICAO,
            GrupoContatoDTO.CAMPOS.DESCRICAO, FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
        createFilters(filterMap, GrupoContatoTranslator.ATIVO,
            GrupoContatoDTO.CAMPOS.ATIVO, FieldType.BOOLEAN,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
    }

    @Override
    public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
        return filterMap;
    }
}