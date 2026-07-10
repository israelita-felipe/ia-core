package com.ia.core.llm.service.model.ferramenta;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequestDTO para Ferramenta.
 * <p>
 * Define os filtros disponíveis para busca de ferramentas, incluindo filtros por título, identificador, tipo e ativo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class FerramentaSearchRequestDTO extends SearchRequestDTO {

    /**
     * Mapa de filtros.
     */
    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    /**
     * Construtor padrão.
     */
    protected FerramentaSearchRequestDTO() {
        createFilters(filterMap, FerramentaTranslator.TITULO,
            FerramentaDTO.CAMPOS.TITULO,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, FerramentaTranslator.IDENTIFICADOR,
            FerramentaDTO.CAMPOS.IDENTIFICADOR,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, FerramentaTranslator.TIPO,
            FerramentaDTO.CAMPOS.TIPO,
            FieldType.ENUM, OperatorDTO.EQUAL);

        createFilters(filterMap, FerramentaTranslator.ATIVO,
            FerramentaDTO.CAMPOS.ATIVO,
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