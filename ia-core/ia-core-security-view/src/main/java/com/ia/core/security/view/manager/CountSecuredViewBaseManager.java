package com.ia.core.security.view.manager;

import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.manager.CountBaseManager;

import java.io.Serializable;

/**
 * Count client.
 *
 * @author Israel Araújo
 * @param <D> Tipo do {@link DTO}
 */
public interface CountSecuredViewBaseManager<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewManager<D>, CountBaseManager<D> {
  @Override
  default boolean canCount(SearchRequestDTO request) {
    return getAuthorizationManager().canRead(this, request);
  }
}
