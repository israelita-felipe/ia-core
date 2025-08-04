package com.ia.core.security.view.service;

import java.io.Serializable;

import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.service.ListBaseService;

/**
 * Interface base para clientes do tipo delete.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface ListSecuredViewBaseService<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewService<D>, ListBaseService<D> {

  @Override
  default boolean canList(SearchRequestDTO request) {
    return getAuthorizationManager().canRead(this);
  }
}
