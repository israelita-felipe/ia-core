package com.ia.core.llm.service.model.template;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Template.
 * <p>
 * Define os filtros disponíveis para busca de templates, incluindo filtros por título, identificador, conteúdo e exigeContexto.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class TemplateSearchRequest extends SearchRequestDTO {

    /**
     * Mapa de filtros.
     */
    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    /**
     * Construtor padrão.
     */
    protected TemplateSearchRequest() {
        createFilters(filterMap, TemplateTranslator.TITULO,
            TemplateDTO.CAMPOS.TITULO,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, TemplateTranslator.IDENTIFICADOR,
            TemplateDTO.CAMPOS.IDENTIFICADOR,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, TemplateTranslator.CONTEUDO,
            TemplateDTO.CAMPOS.CONTEUDO,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, TemplateTranslator.EXIGE_CONTEXTO,
            TemplateDTO.CAMPOS.EXIGE_CONTEXTO,
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
