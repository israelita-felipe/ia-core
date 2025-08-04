package com.ia.core.security.view.service;

import java.io.Serializable;
import java.util.UUID;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.service.FindBaseService;

/**
 * Interface base para clientes do tipo find.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface FindSecuredViewBaseService<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewService<D>, FindBaseService<D> {

  @Override
  default boolean canFind(UUID id) {
    return getAuthorizationManager().canRead(this);
  }
}
