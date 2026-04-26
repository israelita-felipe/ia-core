package com.ia.core.communication.service.model.mensagem.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * SearchRequest para Mensagem.
 *
 * @author Israel Araújo
 */
class MensagemSearchRequest extends SearchRequestDTO {

  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   * Construtor padrão
   */
  protected MensagemSearchRequest() {
    createFilters(filterMap, MensagemTranslator.TELEFONE_DESTINATARIO,
                  MensagemDTO.CAMPOS.TELEFONE_DESTINATARIO, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
    createFilters(filterMap, MensagemTranslator.NOME_DESTINATARIO,
                  MensagemDTO.CAMPOS.NOME_DESTINATARIO, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL, OperatorDTO.LIKE);
    createFilters(filterMap, MensagemTranslator.TIPO_CANAL,
                  MensagemDTO.CAMPOS.TIPO_CANAL, FieldType.ENUM,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, MensagemTranslator.STATUS_MENSAGEM,
                  MensagemDTO.CAMPOS.STATUS_MENSAGEM, FieldType.ENUM,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
    createFilters(filterMap, MensagemTranslator.ID_EXTERNO,
                  MensagemDTO.CAMPOS.ID_EXTERNO, FieldType.STRING,
                  OperatorDTO.EQUAL, OperatorDTO.NOT_EQUAL);
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
