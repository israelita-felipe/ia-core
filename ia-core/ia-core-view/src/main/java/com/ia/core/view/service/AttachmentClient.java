package com.ia.core.view.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ia.core.service.attachment.dto.AttachmentDTO;
import com.ia.core.view.client.DefaultBaseClient;

import feign.Response;

/**
 * Cliente para anexos
 *
 * @author Israel Araújo
 * @param <T> Tipo do anexo
 */
public interface AttachmentClient<T extends AttachmentDTO<?>>
  extends DefaultBaseClient<T> {

  /**
   * Realiza o download de um arquivo
   *
   * @param id Identificador do arquivo
   * @return {@link Response} contendo as informações de download do arquivo
   */
  @GetMapping("/download/{id}")
  public Response download(@PathVariable("id") String id);

}
