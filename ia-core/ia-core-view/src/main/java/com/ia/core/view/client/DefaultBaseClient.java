package com.ia.core.view.client;

import java.io.Serializable;

import com.ia.core.service.dto.DTO;

/**
 * Classe padrão para criação de um client com todos os serviços.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 * @see CountBaseClient
 * @see FindBaseClient
 * @see DeleteBaseClient
 * @see ListBaseClient
 * @see SaveBaseClient
 */
public interface DefaultBaseClient<D extends DTO<? extends Serializable>>
  extends CountBaseClient<D>, FindBaseClient<D>, DeleteBaseClient<D>,
  ListBaseClient<D>, SaveBaseClient<D> {

}
