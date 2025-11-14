package com.ia.core.view.manager;

import java.io.Serializable;
import java.util.UUID;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.FindBaseClient;

/**
 * Interface base para serviços do tipo find.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface FindBaseManager<D extends Serializable>
  extends BaseManager<D> {

  /**
   * Se é possível selecionar o objeto de ID fornecido
   *
   * @param id {@link UUID} do objeto
   * @return <code>true</code> por padrão
   */
  default boolean canFind(UUID id) {
    return true;
  }

  /**
   * @param id Id do objeto.
   * @return {@link DTO}
   */
  default D find(UUID id) {
    if (canFind(id)) {
      return ((FindBaseClient<D>) getClient()).find(id);
    }
    return null;
  }
}
