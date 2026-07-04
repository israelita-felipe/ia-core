package com.ia.core.service.attachment;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.validators.IServiceValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Testes para AttachmentServiceConfig.
 */
@DisplayName("AttachmentServiceConfig Tests")
class AttachmentServiceConfigTest {

  @Test
  @DisplayName("construtor deve criar configuração com dependências")
  void testConstructorCreatesConfigWithDependencies() {
    BaseEntityRepository<TestAttachment> repository = mock(BaseEntityRepository.class);
    BaseEntityMapper<TestAttachment, TestAttachmentDTO> mapper = mock(BaseEntityMapper.class);
    SearchRequestMapper searchRequestMapper = mock(SearchRequestMapper.class);
    Translator translator = mock(Translator.class);
    List<IServiceValidator<TestAttachmentDTO>> validators = List.of();

    AttachmentServiceConfig<TestAttachment, TestAttachmentDTO> config =
      new AttachmentServiceConfig<>(repository, mapper, searchRequestMapper, translator, validators);

    assertThat(config).isNotNull();
  }

  static class TestAttachment extends Attachment {
  }

  static class TestAttachmentDTO extends AttachmentDTO<TestAttachment> {
    @Override
    public TestAttachmentDTO cloneObject() {
      return new TestAttachmentDTO();
    }
  }
}
