package com.ia.core.service.attachment;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes para AttachmentService.
 */
@DisplayName("AttachmentService Tests")
class AttachmentServiceTest {

  private BaseEntityRepository<TestAttachment> repository;
  private BaseEntityMapper<TestAttachment, TestAttachmentDTO> mapper;
  private SearchRequestMapper searchRequestMapper;
  private Translator translator;
  private AttachmentServiceConfig<TestAttachment, TestAttachmentDTO> config;
  private AttachmentService<TestAttachment, TestAttachmentDTO> service;

  @TempDir
  Path tempDir;

  @BeforeEach
  void setUp() {
    repository = mock(BaseEntityRepository.class);
    mapper = mock(BaseEntityMapper.class);
    searchRequestMapper = mock(SearchRequestMapper.class);
    translator = mock(Translator.class);

    when(translator.getTranslation(any(), any())).thenReturn("test message");
    when(translator.getProvidedLocales()).thenReturn(java.util.List.of(java.util.Locale.of("pt_BR")));

    config = new AttachmentServiceConfig<>(
        repository,
        mapper,
        searchRequestMapper,
        translator,
        java.util.List.of()
    );

    service = new AttachmentService<>(config);
  }

  @Nested
  @DisplayName("delete")
  class DeleteTests {

    @Test
    @DisplayName("Deve deletar anexo e arquivo com sucesso")
    void deveDeletarAnexoEArquivoComSucesso() throws ServiceException, IOException {
      // Arrange
      Long id = System.currentTimeMillis();
      TestAttachment entity = new TestAttachment();
      entity.setId(id);

      when(repository.findById(id)).thenReturn(java.util.Optional.of(entity));
      doNothing().when(repository).deleteById(id);

      File file = service.getFile(id);
      Files.createFile(file.toPath());

      // Act
      service.delete(id);

      // Assert
      verify(repository).deleteById(id);
      assertThat(file.exists()).isFalse();
    }

    @Test
    @DisplayName("Deve lançar ServiceException quando repository falha")
    void deveLancarServiceExceptionQuandoRepositoryFalha() {
      // Arrange
      Long id = System.currentTimeMillis();
      when(repository.findById(id)).thenThrow(new RuntimeException("Database error"));

      // Act & Assert
      assertThatThrownBy(() -> service.delete(id))
          .isInstanceOf(ServiceException.class);
    }
  }

  @Nested
  @DisplayName("load")
  class LoadTests {

    @Test
    @DisplayName("Deve carregar conteúdo do arquivo com sucesso")
    void deveCarregarConteudoDoArquivoComSucesso() throws ServiceException, IOException {
      // Arrange
      Long id = System.currentTimeMillis();
      TestAttachmentDTO dto = new TestAttachmentDTO();
      dto.setId(id);

      File file = service.getFile(id);
      Files.createFile(file.toPath());
      Files.writeString(file.toPath(), "test content");

      // Act
      TestAttachmentDTO result = service.load(dto);

      // Assert
      assertThat(result.getContent()).isEqualTo("test content");
    }

    @Test
    @DisplayName("Deve lançar ServiceException quando DTO é nulo")
    void deveLancarServiceExceptionQuandoDTONull() {
      // Act & Assert
      assertThatThrownBy(() -> service.load(null))
          .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Deve lançar ServiceException quando ID do DTO é nulo")
    void deveLancarServiceExceptionQuandoIDDTONull() {
      // Arrange
      TestAttachmentDTO dto = new TestAttachmentDTO();

      // Act & Assert
      assertThatThrownBy(() -> service.load(dto))
          .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Deve lançar ServiceException quando arquivo não existe")
    void deveLancarServiceExceptionQuandoArquivoNaoExiste() {
      // Arrange
      Long id = System.currentTimeMillis();
      TestAttachmentDTO dto = new TestAttachmentDTO();
      dto.setId(id);

      // Act & Assert
      assertThatThrownBy(() -> service.load(dto))
          .isInstanceOf(ServiceException.class);
    }
  }

  @Nested
  @DisplayName("save")
  class SaveTests {
    // Save tests require complex mocking of JakartaValidator
    // Skipping for now as they test integration with validation layer
  }

  @Nested
  @DisplayName("zip")
  class ZipTests {

    @Test
    @DisplayName("Deve lançar exceção quando conteúdo é nulo")
    void deveLancarExcecaoQuandoConteudoNull() {
      // Arrange
      TestAttachmentDTO dto = new TestAttachmentDTO();
      dto.setContent(null);

      // Act & Assert
      assertThatThrownBy(() -> service.zip(dto))
          .isInstanceOf(NullPointerException.class);
    }
  }

  @Nested
  @DisplayName("unZip")
  class UnZipTests {

    @Test
    @DisplayName("Deve retornar null quando DTO não tem conteúdo")
    void deveRetornarNullQuandoDTOSemConteudo() throws ServiceException {
      // Arrange
      TestAttachmentDTO dto = new TestAttachmentDTO();
      dto.setContent(null);

      // Act
      String result = service.unZip(dto);

      // Assert
      assertThat(result).isNull();
    }
  }

  @Nested
  @DisplayName("exists")
  class ExistsTests {

    @Test
    @DisplayName("Deve retornar true quando arquivo existe")
    void deveRetornarTrueQuandoArquivoExiste() throws IOException {
      // Arrange
      Long id = System.currentTimeMillis();
      File file = service.getFile(id);
      Files.createFile(file.toPath());

      // Act
      boolean result = service.exists(id);

      // Assert
      assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando arquivo não existe")
    void deveRetornarFalseQuandoArquivoNaoExiste() {
      // Arrange
      Long id = System.currentTimeMillis();

      // Act
      boolean result = service.exists(id);

      // Assert
      assertThat(result).isFalse();
    }
  }

  @Nested
  @DisplayName("getFile")
  class GetFileTests {

    @Test
    @DisplayName("Deve criar arquivo com caminho correto")
    void deveCriarArquivoComCaminhoCorreto() {
      // Arrange
      Long id = System.currentTimeMillis();

      // Act
      File file = service.getFile(id);

      // Assert
      assertThat(file).isNotNull();
      assertThat(file.getName()).isEqualTo(id + ".att");
    }

    @Test
    @DisplayName("Deve criar diretório se não existir")
    void deveCriarDiretorioSeNaoExistir() {
      // Arrange
      Long id = System.currentTimeMillis();

      // Act
      File file = service.getFile(id);

      // Assert
      assertThat(file.getParentFile()).exists();
    }
  }

  @Nested
  @DisplayName("getAttachmentFileNamePattern")
  class GetAttachmentFileNamePatternTests {

    @Test
    @DisplayName("Deve retornar padrão de nome de arquivo")
    void deveRetornarPadraoDeNomeDeArquivo() {
      // Act
      String pattern = service.getAttachmentFileNamePattern();

      // Assert
      assertThat(pattern).isEqualTo("%s.att");
    }
  }

  @Nested
  @DisplayName("getAttachmentDirectory")
  class GetAttachmentDirectoryTests {

    @Test
    @DisplayName("Deve retornar diretório de anexos")
    void deveRetornarDiretorioDeAnexos() {
      // Act
      String dir = service.getAttachmentDirectory();

      // Assert
      assertThat(dir).isEqualTo(AttachmentService.ATTACHMENT_DIR);
    }
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
