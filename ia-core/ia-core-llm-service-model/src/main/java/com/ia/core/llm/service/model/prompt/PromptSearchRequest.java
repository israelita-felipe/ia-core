package com.ia.core.llm.service.model.prompt;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Prompt.
 * <p>
 * Define os filtros disponíveis para busca de prompts, incluindo filtros por título e entrada.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class PromptSearchRequest extends SearchRequestDTO {

    /**
     * Mapa de filtros.
     */
    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    /**
     * Construtor padrão.
     */
    protected PromptSearchRequest() {
        createFilters(filterMap, PromptTranslator.TITULO,
            PromptDTO.CAMPOS.TITULO,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, PromptTranslator.ENTRADA,
            PromptDTO.CAMPOS.ENTRADA,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
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