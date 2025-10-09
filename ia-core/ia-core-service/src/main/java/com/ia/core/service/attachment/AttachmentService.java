package com.ia.core.service.attachment;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.UUID;

import com.ia.core.model.attachment.Attachment;
import com.ia.core.service.DefaultBaseService;
import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.mapper.BaseMapper;
import com.ia.core.service.mapper.SearchRequestMapper;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.translator.Translator;
import com.ia.core.service.util.ZipUtil;

/**
 * Serviço para manipulação de anexos
 *
 * @author Israel Araújo
 * @param <T> Tipo do arquivo
 * @param <D> Tipo do {@link DTO} do arquivo.
 */
public class AttachmentService<T extends Attachment, D extends AttachmentDTO<T>>
  extends DefaultBaseService<T, D> {

  /**
   * Caminho padrão para armazenar os arquivos em disco
   */
  public static final String ATTACHMENT_DIR = System.getProperty("user.dir")
      + "/attachments";

  /**
   * Padrão de nome de arquivo para ser utilizado ao salvar um anexo.
   */
  public static final String FILE_PATTERN = "%s.att";

  /**
   * @param repository          {@link BaseEntityRepository}
   * @param mapper              {@link BaseMapper}
   * @param searchRequestMapper {@link SearchRequestMapper}
   * @param translator          {@link Translator}
   * @param validators          lista de validadores
   */
  public AttachmentService(AttachmentServiceConfig<T, D> config) {
    super(config);
  }

  @Override
  public void delete(UUID id)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    onTransaction(() -> {
      try {
        super.delete(id);
        getFile(id).delete();
      } catch (Exception e) {
        ex.add(e);
      }
      return id;
    });
    checkErrors(ex);
  }

  /**
   * Verifica se um arquivo existe.
   *
   * @param id {@link UUID} do anexo
   * @return <code>true</code> se o arquivo existir em disco.
   */
  protected boolean exists(UUID id) {
    return getFile(id).exists();
  }

  /**
   * @param id {@link UUID} do anexo
   * @return {@link File} com o {@link UUID} informado.
   */
  protected File getFile(UUID id) {
    File dir = new File(getAttachmentDirectory());
    if (!dir.exists()) {
      dir.mkdir();
    }
    return new File(String
        .format("%s/%s", getAttachmentDirectory(),
                String.format(getAttachmentFileNamePattern(), id)));
  }

  /**
   * @return {@link #FILE_PATTERN}
   */
  protected String getAttachmentFileNamePattern() {
    return FILE_PATTERN;
  }

  /**
   * @return {@link #ATTACHMENT_DIR}
   */
  protected String getAttachmentDirectory() {
    return ATTACHMENT_DIR;
  }

  /**
   * Carrega um arquivo no {@link DTO} informado
   *
   * @param dto {@link DTO} a ser carregado
   * @return {@link DTO} carregado.
   * @throws ServiceException caso ocorra algum erro
   */
  public D load(D dto)
    throws ServiceException {
    try {
      dto.setContent(Files.readString(getFile(dto.getId()).toPath()));
      return dto;
    } catch (Exception e) {
      ServiceException serviceException = new ServiceException();
      serviceException.add(e);
      throw serviceException;
    }
  }

  @Override
  public D save(D toSave)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    D savedEntity = onTransaction(() -> {
      D saved = null;
      try {
        saved = super.save(toSave);
        UUID id = saved.getId();
        FileWriter fw = new FileWriter(getFile(id));
        fw.write(toSave.getContent());
        fw.close();
      } catch (Exception e) {
        UUID id = saved.getId();
        if (exists(id)) {
          try {
            delete(saved.getId());
          } catch (Exception e2) {
            ex.add(e2);
          }
        }
        ex.add(e);
      }
      return saved;
    });
    checkErrors(ex);
    return savedEntity;
  }

  /**
   * Extrai um arquivo
   *
   * @param dto {@link DTO} a ser extraído
   * @return o conteúdo do arquivo em base64
   * @throws ServiceException caso ocorra erro ao extrair o arquivo.
   */
  public String unZip(D dto)
    throws ServiceException {
    try {
      return ZipUtil.unZipBase64(dto.getContent());
    } catch (Exception e) {
      ServiceException serviceException = new ServiceException();
      serviceException.add(e);
      throw serviceException;
    }
  }

  /**
   * Compacta o arquivo informado
   *
   * @param dto {@link DTO} do arquivo
   * @return String contendo o arquivo compactado e em base64
   * @throws ServiceException caso ocorra alguma exceção.
   */
  public String zip(D dto)
    throws ServiceException {
    try {
      return ZipUtil.zipBase64(dto.getContent());
    } catch (Exception e) {
      ServiceException serviceException = new ServiceException();
      serviceException.add(e);
      throw serviceException;
    }
  }
}
