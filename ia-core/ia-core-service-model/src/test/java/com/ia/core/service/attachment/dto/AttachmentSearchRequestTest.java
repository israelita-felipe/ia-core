package com.ia.core.service.attachment.dto;

import com.ia.core.model.filter.FieldType;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para AttachmentSearchRequest.
 */
@DisplayName("AttachmentSearchRequest Tests")
class AttachmentSearchRequestTest {

  @Test
  @DisplayName("deve criar instância com construtor padrão")
  void testDefaultConstructor() {
    AttachmentSearchRequest request = new AttachmentSearchRequest();

    assertThat(request).isNotNull();
  }

  @Test
  @DisplayName("getAvaliableFilters deve retornar mapa de filtros")
  void testGetAvaliableFilters() {
    AttachmentSearchRequest request = new AttachmentSearchRequest();
    Map<FilterProperty, Collection<FilterRequestDTO>> filters = request.getAvaliableFilters();

    assertThat(filters).isNotNull();
    assertThat(filters).hasSize(2);
  }

  @Test
  @DisplayName("filtro de nome deve ter operadores LIKE, EQUAL e NOT_EQUAL")
  void testFileNameFilterHasCorrectOperators() {
    AttachmentSearchRequest request = new AttachmentSearchRequest();
    Map<FilterProperty, Collection<FilterRequestDTO>> filters = request.getAvaliableFilters();

    FilterProperty fileNameProperty = filters.keySet().stream()
      .filter(p -> p.getProperty().equals(AttachmentDTO.CAMPOS.FILE_NAME))
      .findFirst()
      .orElse(null);

    assertThat(fileNameProperty).isNotNull();
    assertThat(fileNameProperty.getLabel()).isEqualTo(AttachmentTranslator.NOME);

    Collection<FilterRequestDTO> fileNameFilters = filters.get(fileNameProperty);
    assertThat(fileNameFilters).hasSize(3);

    assertThat(fileNameFilters).anyMatch(f ->
      f.getKey().equals(AttachmentDTO.CAMPOS.FILE_NAME) &&
      f.getFieldType() == FieldType.STRING &&
      f.getOperator() == OperatorDTO.LIKE
    );

    assertThat(fileNameFilters).anyMatch(f ->
      f.getKey().equals(AttachmentDTO.CAMPOS.FILE_NAME) &&
      f.getFieldType() == FieldType.STRING &&
      f.getOperator() == OperatorDTO.EQUAL
    );

    assertThat(fileNameFilters).anyMatch(f ->
      f.getKey().equals(AttachmentDTO.CAMPOS.FILE_NAME) &&
      f.getFieldType() == FieldType.STRING &&
      f.getOperator() == OperatorDTO.NOT_EQUAL
    );
  }

  @Test
  @DisplayName("filtro de descrição deve ter operadores LIKE, EQUAL e NOT_EQUAL")
  void testDescriptionFilterHasCorrectOperators() {
    AttachmentSearchRequest request = new AttachmentSearchRequest();
    Map<FilterProperty, Collection<FilterRequestDTO>> filters = request.getAvaliableFilters();

    FilterProperty descriptionProperty = filters.keySet().stream()
      .filter(p -> p.getProperty().equals(AttachmentDTO.CAMPOS.DESCRIPTION))
      .findFirst()
      .orElse(null);

    assertThat(descriptionProperty).isNotNull();
    assertThat(descriptionProperty.getLabel()).isEqualTo(AttachmentTranslator.DESCRICAO);

    Collection<FilterRequestDTO> descriptionFilters = filters.get(descriptionProperty);
    assertThat(descriptionFilters).hasSize(3);

    assertThat(descriptionFilters).anyMatch(f ->
      f.getKey().equals(AttachmentDTO.CAMPOS.DESCRIPTION) &&
      f.getFieldType() == FieldType.STRING &&
      f.getOperator() == OperatorDTO.LIKE
    );

    assertThat(descriptionFilters).anyMatch(f ->
      f.getKey().equals(AttachmentDTO.CAMPOS.DESCRIPTION) &&
      f.getFieldType() == FieldType.STRING &&
      f.getOperator() == OperatorDTO.EQUAL
    );

    assertThat(descriptionFilters).anyMatch(f ->
      f.getKey().equals(AttachmentDTO.CAMPOS.DESCRIPTION) &&
      f.getFieldType() == FieldType.STRING &&
      f.getOperator() == OperatorDTO.NOT_EQUAL
    );
  }
}
