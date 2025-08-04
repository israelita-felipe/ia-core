package com.ia.core.view.service;

import java.io.Serializable;
import java.util.Objects;

import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.client.CountBaseClient;

/**
 * Count client.
 *
 * @author Israel Araújo
 * @param <D> Tipo do {@link DTO}
 */
public interface CountBaseService<D extends Serializable>
  extends BaseService<D> {

  /**
   * Se é possível contar os objeto dado uma requisição de busca
   *
   * @param request {@link SearchRequestDTO}
   * @return <code>true</code> por padrão
   */
  default boolean canCount(SearchRequestDTO request) {
    return true;
  }

  /**
   * Realiza o count.
   *
   * @param searchRequest {@link SearchRequestDTO}
   * @return {@link Integer}
   */
  default int count(SearchRequestDTO searchRequest) {
    if (canCount(searchRequest)) {
      if (Objects.isNull(searchRequest)) {
        searchRequest = SearchRequestDTO.builder().build();
      }
      return ((CountBaseClient<D>) getClient()).count(searchRequest);
    }
    return 0;
  }
}
