package com.ia.core.view.service;

import java.io.InputStream;
import java.util.UUID;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.view.client.BaseClient;

import feign.Response;

/**
 * Serviço para anexos
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public class AttachmentService<T extends AttachmentDTO<?>>
  extends DefaultBaseService<T> {

  /**
   * @param client {@link BaseClient}
   */
  public AttachmentService(DefaultBaseServiceConfig<T> client) {
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
      throw serviceException;
    } catch (Exception e) {
      serviceException.add(e);
      throw serviceException;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public AttachmentClient<T> getClient() {
    return (AttachmentClient<T>) getConfig().getClient();
  }
}
