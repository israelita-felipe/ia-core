package com.ia.core.llm.service.model.skill;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Skill.
 * <p>
 * Define os filtros disponíveis para busca de skills,
 * incluindo filtros por identificador, título, tipo e status ativo.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
class SkillSearchRequest extends SearchRequestDTO {

    /**
     * Mapa de filtros.
     */
    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    /**
     * Construtor padrão.
     */
    protected SkillSearchRequest() {
        createFilters(filterMap, SkillTranslator.IDENTIFICADOR,
            SkillDTO.CAMPOS.IDENTIFICADOR,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, SkillTranslator.TITULO,
            SkillDTO.CAMPOS.TITULO,
            FieldType.STRING, OperatorDTO.LIKE,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        createFilters(filterMap, SkillTranslator.ATIVO,
            SkillDTO.CAMPOS.ATIVO,
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