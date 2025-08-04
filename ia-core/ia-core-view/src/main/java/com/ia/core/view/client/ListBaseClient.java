package com.ia.core.view.client;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.view.service.ListBaseService;

/**
 * Interface base para clientes do tipo delete.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link Serializable}
 */
public interface ListBaseClient<D extends Serializable>
  extends BaseClient<D> {

  /**
   * Busca os elementos.
   *
   * @param request {@link SearchRequest}
   * @return Page do tipo {@link Serializable}.
   * @see ListBaseService#findAll(SearchRequestDTO)
   */
  @PostMapping("/all")
  Page<D> findAll(@RequestBody SearchRequestDTO request);

}
