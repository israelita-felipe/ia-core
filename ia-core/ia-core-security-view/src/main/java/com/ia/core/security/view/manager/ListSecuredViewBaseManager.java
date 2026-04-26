package com.ia.core.security.view.manager;

import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.manager.ListBaseManager;

import java.io.Serializable;

/**
 * Interface base para clientes do tipo delete.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface ListSecuredViewBaseManager<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewManager<D>, ListBaseManager<D> {

  @Override
  default boolean canList(SearchRequestDTO request) {
    return getAuthorizationManager().canRead(this, request);
  }
}
