package com.ia.core.security.view.service;

import java.io.Serializable;
import java.util.UUID;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.service.DeleteBaseService;

/**
 * Interface base para clientes do tipo delete.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface DeleteSecuredViewBaseService<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewService<D>, DeleteBaseService<D> {
  @Override
  default boolean canDelete(UUID id) {
    return getAuthorizationManager().canDelete(this);
  }
}
