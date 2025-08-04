package com.ia.core.view.service;

import java.io.Serializable;
import java.util.UUID;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.DeleteBaseClient;

/**
 * Interface base para serviços do tipo delete.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface DeleteBaseService<D extends Serializable>
  extends BaseService<D> {

  /**
   * Se é possível deletar o objeto com ID fornecido
   *
   * @param id {@link UUID} do objeto
   * @return <code>true</code> por padrão
   */
  default boolean canDelete(UUID id) {
    return true;
  }

  /**
   * @param id Id do objeto.
   */
  default void delete(UUID id) {
    if (canDelete(id)) {
      ((DeleteBaseClient<D>) getClient()).delete(id);
    }
  }
}
