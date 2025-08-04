package com.ia.core.service.attachment.dto;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.service.dto.filter.FieldTypeDTO;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * Filtro para um {@link AttachmentDTO}
 *
 * @author Israel Araújo
 */
class AttachmentSearchRequest
  extends SearchRequestDTO {
  /** Serial UID */
  private static final long serialVersionUID = 7464468034452533005L;
  /**
   * Filtros disponíveis
   */
  private static final Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();

  /**
   * Construtor padrão
   */
  public AttachmentSearchRequest() {
    filterMap
        .put(FilterProperty.builder().label(AttachmentTranslator.NOME)
            .property(AttachmentDTO.CAMPOS.FILE_NAME).build(),
             Arrays.asList(
                           FilterRequestDTO.builder()
                               .key(AttachmentDTO.CAMPOS.FILE_NAME)
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.LIKE).build(),
                           FilterRequestDTO.builder()
                               .key(AttachmentDTO.CAMPOS.FILE_NAME)
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.EQUAL).build(),
                           FilterRequestDTO.builder()
                               .key(AttachmentDTO.CAMPOS.FILE_NAME)
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.NOT_EQUAL).build()));
    filterMap
        .put(FilterProperty.builder().label(AttachmentTranslator.DESCRICAO)
            .property(AttachmentDTO.CAMPOS.DESCRIPTION).build(),
             Arrays.asList(
                           FilterRequestDTO.builder()
                               .key(AttachmentDTO.CAMPOS.DESCRIPTION)
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.LIKE).build(),
                           FilterRequestDTO.builder()
                               .key(AttachmentDTO.CAMPOS.DESCRIPTION)
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.EQUAL).build(),
                           FilterRequestDTO.builder()
                               .key(AttachmentDTO.CAMPOS.DESCRIPTION)
                               .fieldType(FieldTypeDTO.STRING)
                               .operator(OperatorDTO.NOT_EQUAL).build()));
  }

  @Override
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return filterMap;
  }
}
