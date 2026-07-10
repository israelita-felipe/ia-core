package com.ia.core.llm.service.model.chat;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para ChatSession.
 * <p>
 * Define os filtros disponíveis para pesquisa de sessões de chat.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class ChatSessionSearchRequest extends SearchRequestDTO {

    /**
     * Mapa de filtros.
     */
    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    /**
     * Construtor padrão.
     */
    protected ChatSessionSearchRequest() {
        createFilters(filterMap, ChatTranslator.SESSION_ID,
            ChatSessionDTO.CAMPOS.SESSION_ID,
            FieldType.STRING, OperatorDTO.EQUAL, OperatorDTO.LIKE);

        createFilters(filterMap, ChatTranslator.TITULO,
            ChatSessionDTO.CAMPOS.TITULO,
            FieldType.STRING, OperatorDTO.LIKE, OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, ChatTranslator.STATUS,
            ChatSessionDTO.CAMPOS.STATUS,
            FieldType.ENUM, OperatorDTO.EQUAL);

        createFilters(filterMap, ChatTranslator.USUARIO_ID,
            ChatSessionDTO.CAMPOS.USUARIO_ID,
            FieldType.STRING, OperatorDTO.EQUAL);
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