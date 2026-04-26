package com.ia.core.security.view.manager;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.manager.FindBaseManager;

import java.io.Serializable;

/**
 * Interface base para clientes do tipo find.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface FindSecuredViewBaseManager<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewManager<D>, FindBaseManager<D> {

  @Override
  default boolean canFind(Long id) {
    return getAuthorizationManager().canRead(this, id);
  }
}
