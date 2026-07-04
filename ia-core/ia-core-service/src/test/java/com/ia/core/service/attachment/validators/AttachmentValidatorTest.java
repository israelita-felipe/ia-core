package com.ia.core.service.attachment.validators;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.ValidationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Testes para AttachmentValidator.
 */
@DisplayName("AttachmentValidator Tests")
class AttachmentValidatorTest {

  @Test
  @DisplayName("validate deve adicionar erro quando ID for null e conteúdo for null")
  void testValidateAddsErrorWhenIdNullAndContentNull() {
    Translator translator = mock(Translator.class);
    AttachmentValidator<TestAttachmentDTO> validator = new AttachmentValidator<>(translator);
    TestAttachmentDTO dto = new TestAttachmentDTO();
    dto.setId(null);
    dto.setContent(null);
    ValidationResult result = ValidationResult.create();

    validator.validate(dto, result);

    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  @DisplayName("validate deve adicionar erro quando ID for null e conteúdo for vazio")
  void testValidateAddsErrorWhenIdNullAndContentEmpty() {
    Translator translator = mock(Translator.class);
    AttachmentValidator<TestAttachmentDTO> validator = new AttachmentValidator<>(translator);
    TestAttachmentDTO dto = new TestAttachmentDTO();
    dto.setId(null);
    dto.setContent("");
    ValidationResult result = ValidationResult.create();

    validator.validate(dto, result);

    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  @DisplayName("validate deve adicionar erro quando ID for null e conteúdo for blank")
  void testValidateAddsErrorWhenIdNullAndContentBlank() {
    Translator translator = mock(Translator.class);
    AttachmentValidator<TestAttachmentDTO> validator = new AttachmentValidator<>(translator);
    TestAttachmentDTO dto = new TestAttachmentDTO();
    dto.setId(null);
    dto.setContent("   ");
    ValidationResult result = ValidationResult.create();

    validator.validate(dto, result);

    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  @DisplayName("validate não deve adicionar erro quando ID não for null")
  void testValidateDoesNotAddErrorWhenIdNotNull() {
    Translator translator = mock(Translator.class);
    AttachmentValidator<TestAttachmentDTO> validator = new AttachmentValidator<>(translator);
    TestAttachmentDTO dto = new TestAttachmentDTO();
    dto.setId(1L);
    dto.setContent(null);
    ValidationResult result = ValidationResult.create();

    validator.validate(dto, result);

    assertThat(result.hasErrors()).isFalse();
  }

  @Test
  @DisplayName("validate não deve adicionar erro quando ID for null e conteúdo não for blank")
  void testValidateDoesNotAddErrorWhenIdNullAndContentNotBlank() {
    Translator translator = mock(Translator.class);
    AttachmentValidator<TestAttachmentDTO> validator = new AttachmentValidator<>(translator);
    TestAttachmentDTO dto = new TestAttachmentDTO();
    dto.setId(null);
    dto.setContent("content");
    ValidationResult result = ValidationResult.create();

    validator.validate(dto, result);

    assertThat(result.hasErrors()).isFalse();
  }

  static class TestAttachmentDTO extends AttachmentDTO<TestEntity> {
    @Override
    public TestAttachmentDTO cloneObject() {
      return new TestAttachmentDTO();
    }
  }

  static class TestEntity extends Attachment {
  }
}
