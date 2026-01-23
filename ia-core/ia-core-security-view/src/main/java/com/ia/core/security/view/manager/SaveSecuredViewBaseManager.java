package com.ia.core.security.view.manager;

import java.io.Serializable;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.manager.SaveBaseManager;

/**
 * Interface base para clientes do tipo save.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface SaveSecuredViewBaseManager<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewManager<D>, SaveBaseManager<D> {

  @Override
  default boolean canEdit(D toSave) {
    return getAuthorizationManager().canUpdate(this, toSave);
  }

  @Override
  default boolean canInsert(D toInsert) {
    return getAuthorizationManager().canCreate(this, toInsert);
  }

}
