package com.ia.core.service.attachment.dto;

import com.ia.core.model.attachment.Attachment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes para AttachmentDTO.
 */
@DisplayName("AttachmentDTO Tests")
class AttachmentDTOTest {

  @Test
  @DisplayName("deve ter constantes de chave definidas")
  void testKeyConstants() {
    assertThat(AttachmentDTO.PRIVATE_KEY).isNotNull();
    assertThat(AttachmentDTO.PUBLIC_KEY).isNotNull();
  }

  @Test
  @DisplayName("deve criar instância com builder")
  void testBuilder() {
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder()
      .filename("test.txt")
      .mediaType("text/plain")
      .size(100L)
      .description("Test file")
      .build();

    assertThat(dto.getFilename()).isEqualTo("test.txt");
    assertThat(dto.getMediaType()).isEqualTo("text/plain");
    assertThat(dto.getSize()).isEqualTo(100L);
    assertThat(dto.getDescription()).isEqualTo("Test file");
  }

  @Test
  @DisplayName("hasContent deve retornar true quando conteúdo não é nulo")
  void testHasContentReturnsTrueWhenContentNotNull() {
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder()
      .content("base64content")
      .build();

    assertThat(dto.hasContent()).isTrue();
  }

  @Test
  @DisplayName("hasContent deve retornar false quando conteúdo é nulo")
  void testHasContentReturnsFalseWhenContentNull() {
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder()
      .content(null)
      .build();

    assertThat(dto.hasContent()).isFalse();
  }

  @Test
  @DisplayName("isEmpty deve retornar true quando size é nulo")
  void testIsEmptyReturnsTrueWhenSizeNull() {
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder()
      .size(null)
      .build();

    assertThat(dto.isEmpty()).isTrue();
  }

  @Test
  @DisplayName("isEmpty deve retornar false quando size não é nulo")
  void testIsEmptyReturnsFalseWhenSizeNotNull() {
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder()
      .size(100L)
      .build();

    assertThat(dto.isEmpty()).isFalse();
  }

  @Test
  @DisplayName("toString deve formatar corretamente")
  void testToStringFormatsCorrectly() {
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder()
      .filename("test.txt")
      .mediaType("text/plain")
      .size(100L)
      .content("base64content")
      .build();

    String result = dto.toString();

    assertThat(result).contains("filename=test.txt");
    assertThat(result).contains("mediaType=text/plain");
    assertThat(result).contains("size=100");
    assertThat(result).contains("content=base64content");
  }

  @Test
  @DisplayName("CAMPOS deve ter constantes definidas")
  void testCamposConstants() {
    assertThat(AttachmentDTO.CAMPOS.DESCRIPTION).isEqualTo("description");
    assertThat(AttachmentDTO.CAMPOS.FILE_NAME).isEqualTo("filename");
    assertThat(AttachmentDTO.CAMPOS.CONTENT).isEqualTo("content");
    assertThat(AttachmentDTO.CAMPOS.SIZE).isEqualTo("size");
    assertThat(AttachmentDTO.CAMPOS.MEDIA_TYPE).isEqualTo("mediaType");
  }

  @Test
  @DisplayName("CAMPOS.values deve retornar conjunto de valores")
  void testCamposValues() {
    assertThat(AttachmentDTO.CAMPOS.values()).containsExactlyInAnyOrder(
      "description", "filename", "content", "size", "mediaType"
    );
  }

  @Test
  @DisplayName("cloneObject deve criar cópia")
  void testCloneObject() {
    AttachmentDTO<Attachment> original = AttachmentDTO.<Attachment>builder()
      .filename("test.txt")
      .mediaType("text/plain")
      .size(100L)
      .build();

    AttachmentDTO<Attachment> cloned = original.cloneObject();

    assertThat(cloned.getFilename()).isEqualTo(original.getFilename());
    assertThat(cloned.getMediaType()).isEqualTo(original.getMediaType());
    assertThat(cloned.getSize()).isEqualTo(original.getSize());
  }

  @Test
  @DisplayName("getSearchRequest deve retornar AttachmentSearchRequest")
  void testGetSearchRequest() {
    // Act
    var searchRequest = AttachmentDTO.getSearchRequest();

    // Assert
    assertThat(searchRequest).isNotNull();
    assertThat(searchRequest).isInstanceOf(AttachmentSearchRequest.class);
  }

  @Test
  @DisplayName("propertyFilters deve retornar conjunto de propriedades filtráveis")
  void testPropertyFilters() {
    // Act
    var filters = AttachmentDTO.propertyFilters();

    // Assert
    assertThat(filters).isNotNull();
    assertThat(filters).contains("description", "filename");
  }

  @Test
  @DisplayName("setDescription deve disparar PropertyChangeEvent")
  void testSetDescriptionFiresPropertyChangeEvent() {
    // Arrange
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder().build();
    final boolean[] eventFired = {false};
    dto.addPropertyChangeListener(evt -> {
      if (evt.getPropertyName().equals(AttachmentDTO.CAMPOS.DESCRIPTION)) {
        eventFired[0] = true;
      }
    });

    // Act
    dto.setDescription("New Description");

    // Assert
    assertThat(eventFired[0]).isTrue();
  }

  @Test
  @DisplayName("setFilename deve disparar PropertyChangeEvent")
  void testSetFilenameFiresPropertyChangeEvent() {
    // Arrange
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder().build();
    final boolean[] eventFired = {false};
    dto.addPropertyChangeListener(evt -> {
      if (evt.getPropertyName().equals(AttachmentDTO.CAMPOS.FILE_NAME)) {
        eventFired[0] = true;
      }
    });

    // Act
    dto.setFilename("test.txt");

    // Assert
    assertThat(eventFired[0]).isTrue();
  }

  @Test
  @DisplayName("setContent deve disparar PropertyChangeEvent")
  void testSetContentFiresPropertyChangeEvent() {
    // Arrange
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder().build();
    final boolean[] eventFired = {false};
    dto.addPropertyChangeListener(evt -> {
      if (evt.getPropertyName().equals(AttachmentDTO.CAMPOS.CONTENT)) {
        eventFired[0] = true;
      }
    });

    // Act
    dto.setContent("base64content");

    // Assert
    assertThat(eventFired[0]).isTrue();
  }

  @Test
  @DisplayName("setSize deve disparar PropertyChangeEvent")
  void testSetSizeFiresPropertyChangeEvent() {
    // Arrange
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder().build();
    final boolean[] eventFired = {false};
    dto.addPropertyChangeListener(evt -> {
      if (evt.getPropertyName().equals(AttachmentDTO.CAMPOS.SIZE)) {
        eventFired[0] = true;
      }
    });

    // Act
    dto.setSize(100L);

    // Assert
    assertThat(eventFired[0]).isTrue();
  }

  @Test
  @DisplayName("setMediaType deve disparar PropertyChangeEvent")
  void testSetMediaTypeFiresPropertyChangeEvent() {
    // Arrange
    AttachmentDTO<Attachment> dto = AttachmentDTO.<Attachment>builder().build();
    final boolean[] eventFired = {false};
    dto.addPropertyChangeListener(evt -> {
      if (evt.getPropertyName().equals(AttachmentDTO.CAMPOS.MEDIA_TYPE)) {
        eventFired[0] = true;
      }
    });

    // Act
    dto.setMediaType("text/plain");

    // Assert
    assertThat(eventFired[0]).isTrue();
  }
}
