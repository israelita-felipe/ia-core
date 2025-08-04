package com.ia.core.security.view.service;

import java.io.Serializable;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.service.SaveBaseService;

/**
 * Interface base para clientes do tipo save.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface SaveSecuredViewBaseService<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewService<D>, SaveBaseService<D> {

  @Override
  default boolean canEdit(D toSave) {
    return getAuthorizationManager().canUpdate(this);
  }

  @Override
  default boolean canInsert(D toInsert) {
    return getAuthorizationManager().canCreate(this);
  }

}
