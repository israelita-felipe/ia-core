package com.ia.core.security.view.manager;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.manager.DeleteBaseManager;

import java.io.Serializable;

/**
 * Interface base para clientes do tipo delete.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface DeleteSecuredViewBaseManager<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewManager<D>, DeleteBaseManager<D> {
  @Override
  default boolean canDelete(Long id) {
    return getAuthorizationManager().canDelete(this, id);
  }
}
