package com.ia.core.view.manager;

import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.view.client.BaseClient;

import feign.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * Serviço para anexos
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
@Slf4j
public class AttachmentManager<T extends AttachmentDTO<?>>
  extends DefaultBaseManager<T> {

  /**
   * @param client {@link BaseClient}
   */
  public AttachmentManager(DefaultBaseManagerConfig<T> client) {
    super(client);
  }

  /**
   * Realiza o download de um arquivo
   *
   * @param id {@link UUID} do arquivo
   * @return byte[] do conteúdo do arquivo
   * @throws ServiceException caso ocorra alguma exceção ao recuperar o arquivo
   */
  public byte[] download(UUID id)
    throws ServiceException {
    ServiceException serviceException = new ServiceException();
    try {
      Response response = getClient().download(id.toString());
      if (response.status() == 200) {
        InputStream inputStream = response.body().asInputStream();
        byte[] allBytes = inputStream.readAllBytes();
        return allBytes;
      }
      serviceException.add("Não foi possível baixar o arquivo");
    } catch (Exception e) {
      serviceException.add(e);
    }
    throw serviceException;
  }

  public T load(T object)
    throws ServiceException {
    try {
      byte[] content = download(object.getId());
      object.setContent(Base64.getEncoder().encodeToString(content));
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    }
    return object;
  }

  @SuppressWarnings("unchecked")
  @Override
  public AttachmentClient<T> getClient() {
    return (AttachmentClient<T>) getConfig().getClient();
  }
}
