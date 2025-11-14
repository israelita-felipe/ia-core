package com.ia.core.view.manager;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.client.ListBaseClient;

/**
 * Interface base para serviços do tipo list.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface ListBaseManager<D extends Serializable>
  extends BaseManager<D> {

  /**
   * Se é possível listar dado uma requisição de busca
   *
   * @param request {@link SearchRequestDTO}
   * @return <code>true</code> por padrão
   */
  default boolean canList(SearchRequestDTO request) {
    return true;
  }

  /**
   * Busca os elementos.
   *
   * @param request {@link SearchRequest}
   * @return Page do tipo {@link DTO}.
   * @see ListBaseManager#findAll(SearchRequestDTO)
   */

  default Page<D> findAll(SearchRequestDTO request) {
    if (canList(request)) {
      if (Objects.isNull(request)) {
        request = SearchRequestDTO.builder().build();
      }
      return ((ListBaseClient<D>) getClient()).findAll(request);
    }
    return new PageImpl<>(Collections.emptyList());
  }
}
