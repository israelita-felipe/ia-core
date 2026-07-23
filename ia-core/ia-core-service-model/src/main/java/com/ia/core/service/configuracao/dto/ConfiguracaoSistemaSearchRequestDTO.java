package com.ia.core.service.configuracao.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Request DTO for searching ConfiguracaoSistema entities.
 * <p>
 * Extends the base SearchRequestDTO to provide module-specific search capabilities.
 * Define os filtros disponíveis para busca de configurações do sistema.
 *
 * @author Israel Araújo
 * @since 1.0.0
 */
public class ConfiguracaoSistemaSearchRequestDTO extends SearchRequestDTO {

    private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

    public ConfiguracaoSistemaSearchRequestDTO() {
        // Filtro por chave
        createFilters(filterMap, ConfiguracaoSistemaTranslator.CHAVE,
            ConfiguracaoSistemaDTO.CAMPOS.CHAVE,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);

        // Filtro por módulo
        createFilters(filterMap, ConfiguracaoSistemaTranslator.MODULO,
            ConfiguracaoSistemaDTO.CAMPOS.MODULO,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);

        // Filtro por categoria
        createFilters(filterMap, ConfiguracaoSistemaTranslator.CATEGORIA,
            ConfiguracaoSistemaDTO.CAMPOS.CATEGORIA,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);

        // Filtro por tipo
        createFilters(filterMap, ConfiguracaoSistemaTranslator.TIPO,
            ConfiguracaoSistemaDTO.CAMPOS.TIPO,
            FieldType.ENUM,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);

        // Filtro por descrição
        createFilters(filterMap, ConfiguracaoSistemaTranslator.DESCRICAO,
            ConfiguracaoSistemaDTO.CAMPOS.DESCRICAO,
            FieldType.STRING,
            OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
    }

    @Override
    public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
        return filterMap;
    }
}