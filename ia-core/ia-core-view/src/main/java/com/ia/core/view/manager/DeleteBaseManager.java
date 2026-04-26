package com.ia.core.view.manager;

import com.ia.core.model.exception.ValidationException;
import com.ia.core.service.dto.DTO;
import com.ia.core.view.client.DeleteBaseClient;

import java.io.Serializable;
import java.util.UUID;

/**
 * Interface base para serviços do tipo delete.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface DeleteBaseManager<D extends Serializable>
  extends BaseManager<D> {

  /**
   * Se é possível deletar o objeto com ID fornecido
   *
   * @param id {@link UUID} do objeto
   * @return <code>true</code> por padrão
   */
  default boolean canDelete(Long id) {
    return true;
  }

  /**
   * @param id Id do objeto.
   */
  default void delete(Long id)
    throws ValidationException {
    if (canDelete(id)) {
      ((DeleteBaseClient<D>) getClient()).delete(id);
    }
  }
}
