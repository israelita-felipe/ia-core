package com.ia.core.communication.service.model.contatomensagem.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * SearchRequest para ContatoMensagem.
 *
 * @author Israel Araújo
 */
class ContatoMensagemSearchRequest extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   * Construtor padrão
   */
  protected ContatoMensagemSearchRequest() {
    createFilters(filterMap, ContatoMensagemTranslator.TELEFONE,
                  ContatoMensagemDTO.CAMPOS.TELEFONE, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
    createFilters(filterMap, ContatoMensagemTranslator.NOME,
                  ContatoMensagemDTO.CAMPOS.NOME, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}