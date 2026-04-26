package com.ia.core.communication.service.model.modelomensagem.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para ModeloMensagem.
 *
 * @author Israel Araújo
 */
class ModeloMensagemSearchRequest extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   * Construtor padrão
   */
  protected ModeloMensagemSearchRequest() {
    createFilters(filterMap, ModeloMensagemTranslator.NOME,
                  ModeloMensagemDTO.CAMPOS.NOME, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
    createFilters(filterMap, ModeloMensagemTranslator.DESCRICAO,
                  ModeloMensagemDTO.CAMPOS.DESCRICAO, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
    createFilters(filterMap, ModeloMensagemTranslator.TIPO_CANAL,
                  ModeloMensagemDTO.CAMPOS.TIPO_CANAL, FieldType.ENUM,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, ModeloMensagemTranslator.ATIVO,
                  ModeloMensagemDTO.CAMPOS.ATIVO, FieldType.BOOLEAN,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
