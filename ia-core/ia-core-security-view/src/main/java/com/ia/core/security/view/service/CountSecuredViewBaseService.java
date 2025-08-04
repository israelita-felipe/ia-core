package com.ia.core.security.view.service;

import java.io.Serializable;

import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.service.CountBaseService;

/**
 * Count client.
 *
 * @author Israel Araújo
 * @param <D> Tipo do {@link DTO}
 */
public interface CountSecuredViewBaseService<D extends DTO<? extends Serializable>>
  extends BaseSecuredViewService<D>, CountBaseService<D> {
  @Override
  default boolean canCount(SearchRequestDTO request) {
    return getAuthorizationManager().canRead(this);
  }

}
