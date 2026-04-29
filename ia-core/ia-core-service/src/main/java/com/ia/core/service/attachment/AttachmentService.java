package com.ia.core.service.attachment;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.DefaultBaseService;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.BaseEntityMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

/**
 * Serviço para manipulação de anexos/arquivos.
 *
 * <p>Este serviço estende {@link DefaultBaseService} para fornecer operações
 * CRUD de anexos, incluindo armazenamento em disco, compactação e
 * descompactação de arquivos.
 *
 * <p><b>Operações suportadas:</b></p>
 * <ul>
 *   <li>Salvar anexos com conteúdo em disco ({@link #save(DTO)})</li>
 *   <li>Deletar anexos e arquivos associados ({@link #delete(Long)})</li>
 *   <li>Carregar conteúdo de arquivos ({@link #load(DTO)})</li>
 *   <li>Compactar/descompactar conteúdo base64 ({@link #zip(DTO)}, {@link #unZip(DTO)})</li>
 * </ul>
 *
 * <p><b>Diretório padrão de armazenamento:</b></p>
 * <pre>
 * {user.dir}/attachments/{id}.att
 * </pre>
 *
 * @author Israel Araújo
 * @param <T> Tipo da entidade {@link Attachment}
 * @param <D> Tipo do {@link AttachmentDTO}
 * @see DefaultBaseService
 * @see AttachmentDTO
 * @since 1.0.0
 */
@Slf4j
public class AttachmentService<T extends Attachment, D extends AttachmentDTO<T>>
  extends DefaultBaseService<T, D> {

  /** Diretório padrão para armazenamento de anexos. */
  public static final String ATTACHMENT_DIR = System.getProperty("user.dir")
      + "/attachments";

  /** Padrão de nome de arquivo para ser utilizado ao salvar um anexo. */
  public static final String FILE_PATTERN = "%s.att";

  /**
   * Construtor do serviço de anexos.
   *
   * @param config configuração do serviço de anexos
   */
  public AttachmentService(AttachmentServiceConfig<T, D> config) {
    super(config);
  }

  @TransactionalWrite
  @Override
  public void delete(Long id)
    throws ServiceException {
    log.debug("Deletando anexo com id: {}", id);
    ServiceException ex = new ServiceException();
    try {
      super.delete(id);
      boolean deleted = getFile(id).delete();
      log.debug("Arquivo deletado: {}", deleted);
    } catch (Exception e) {
      log.error("Erro ao deletar anexo com id: {}", id, e);
      ex.add(e);
    }
    throwIfHasErrors(ex);
  }

  /**
   * Verifica se um arquivo existe em disco.
   *
   * @param id identificador do anexo
   * @return {@code true} se o arquivo existir em disco, {@code false} caso contrário
   */
  protected boolean exists(Long id) {
    return getFile(id).exists();
  }

  /**
   * Obtém o {@link File} correspondente ao anexo informado.
   *
   * @param id identificador do anexo
   * @return {@link File} com o caminho do arquivo
   */
  protected File getFile(Long id) {
    File dir = new File(getAttachmentDirectory());
    if (!dir.exists()) {
      boolean created = dir.mkdirs();
      log.debug("Diretório de anexos criado: {}", created);
    }
    return new File(String
        .format("%s/%s", getAttachmentDirectory(),
                String.format(getAttachmentFileNamePattern(), id)));
  }

  /**
   * Obtém o padrão de nome de arquivo para anexos.
   *
   * @return padrão de nome de arquivo ({@link #FILE_PATTERN})
   */
  protected String getAttachmentFileNamePattern() {
    return FILE_PATTERN;
  }

  /**
   * Obtém o diretório de armazenamento de anexos.
   *
   * @return caminho do diretório de anexos ({@link #ATTACHMENT_DIR})
   */
  protected String getAttachmentDirectory() {
    return ATTACHMENT_DIR;
  }

  /**
   * Carrega o conteúdo de um arquivo no DTO informado.
   *
   * @param dto DTO a ser carregado com o conteúdo do arquivo
   * @return DTO com conteúdo carregado
   */
  public D load(D dto) {
    log.debug("Carregando conteúdo do anexo com id: {}", dto.getId());
    try {
      dto.setContent(Files.readString(getFile(dto.getId()).toPath()));
      log.debug("Conteúdo do anexo carregado com sucesso");
    } catch (Exception e) {
      log.error("Erro ao carregar conteúdo do anexo com id: {}", dto.getId(), e);
    }
    return dto;
  }

  @TransactionalWrite
  @Override
  public D save(D toSave)
    throws ServiceException {
    log.debug("Salvando anexo: {}", toSave.getFilename());
    ServiceException ex = new ServiceException();
    D saved = null;
    try {
      saved = super.save(toSave);
      if (toSave.hasContent()) {
        Long id = saved.getId();
        log.debug("Escrevendo conteúdo do anexo no disco, id: {}", id);
        try (FileWriter fw = new FileWriter(getFile(id))) {
          fw.write(toSave.getContent());
        }
        log.info("Anexo salvo com sucesso no disco, id: {}", id);
      }
    } catch (Exception e) {
      if (saved != null) {
        Long id = saved.getId();
        // se existir arquivo salvo e for um arquivo novo deleta o arquivo.
        // Evita deletar quando ocorre erro em atualização.
        if (exists(id) && toSave.getId() == null) {
          try {
            log.debug("Rollback: deletando arquivo criado com erro, id: {}", id);
            delete(saved.getId());
          } catch (Exception e2) {
            log.error("Erro ao fazer rollback do arquivo, id: {}", id, e2);
            ex.add(e2);
          }
        }
      }
      log.error("Erro ao salvar anexo: {}", toSave.getFilename(), e);
      ex.add(e);
    }
    throwIfHasErrors(ex);
    return saved;
  }

  /**
   * Descompacta o conteúdo de um arquivo em base64.
   *
   * @param dto DTO com conteúdo compactado
   * @return conteúdo descompactado ou null se o DTO não tem conteúdo
   * @throws ServiceException caso ocorra erro ao descompactar o arquivo
   */
  public String unZip(D dto)
    throws ServiceException {
    try {
      if (!dto.hasContent()) {
        log.debug("DTO sem conteúdo para descompactar");
        return null;
      }
      log.debug("Descompactando conteúdo do anexo");
      return ZipUtil.unZipBase64(dto.getContent());
    } catch (Exception e) {
      log.error("Erro ao descompactar conteúdo do anexo", e);
      ServiceException serviceException = new ServiceException();
      serviceException.add(e);
      throw serviceException;
    }
  }

  /**
   * Compacta o conteúdo do arquivo informado em base64.
   *
   * @param dto DTO do arquivo com conteúdo
   * @return String contendo o arquivo compactado e em base64
   * @throws ServiceException caso ocorra alguma exceção
   */
  public String zip(D dto)
    throws ServiceException {
    try {
      log.debug("Compactando conteúdo do anexo");
      return ZipUtil.zipBase64(dto.getContent());
    } catch (Exception e) {
      log.error("Erro ao compactar conteúdo do anexo", e);
      ServiceException serviceException = new ServiceException();
      serviceException.add(e);
      throw serviceException;
    }
  }
}
