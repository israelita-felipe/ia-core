package com.ia.core.view.client.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * Interface de um cliente para coleção
 *
 * @author Israel Araújo
 * @param <D> Tipo do dado
 */
public interface CollectionBaseClient<D extends Serializable> {
  /**
   * @return coleção de dados do cliente
   */
  Collection<D> getData();

  /**
   * Captura o {@link UUID} de um objeto
   *
   * @param object Objeto a ser avaliado
   * @return {@link UUID}
   */
  Long getId(D object);
}
