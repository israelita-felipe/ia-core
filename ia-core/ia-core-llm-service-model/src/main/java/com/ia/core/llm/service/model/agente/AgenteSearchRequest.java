package com.ia.core.llm.service.model.agente;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Agente.
 * <p>
 * Define os filtros disponíveis para busca de agentes, incluindo filtros por
 * identificador, título, módulo de origem e status ativo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class AgenteSearchRequest extends SearchRequestDTO {

    /**
     * Mapa de filtros.
     */
    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    /**
     * Construtor padrão.
     */
    protected AgenteSearchRequest() {
        createFilters(filterMap, AgenteTranslator.IDENTIFICADOR,
            AgenteDTO.CAMPOS.IDENTIFICADOR,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, AgenteTranslator.TITULO,
            AgenteDTO.CAMPOS.TITULO,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, AgenteTranslator.MODULO_ORIGEM,
            AgenteDTO.CAMPOS.MODULO_ORIGEM,
            FieldType.STRING, OperatorDTO.LIKE);

        createFilters(filterMap, AgenteTranslator.ATIVO,
            AgenteDTO.CAMPOS.ATIVO,
            FieldType.BOOLEAN, OperatorDTO.EQUAL);
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