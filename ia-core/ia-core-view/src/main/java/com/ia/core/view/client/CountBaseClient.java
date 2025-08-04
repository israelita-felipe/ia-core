package com.ia.core.view.client;

import java.io.Serializable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.service.dto.request.SearchRequestDTO;

/**
 * Count client.
 *
 * @author Israel Araújo
 * @param <D> Tipo do {@link Serializable}
 */
public interface CountBaseClient<D extends Serializable>
  extends BaseClient<D> {

  /**
   * Realiza o count.
   *
   * @param searchRequest {@link SearchRequestDTO}
   * @return {@link Integer}
   */
  @PostMapping("/count")
  int count(@RequestBody SearchRequestDTO searchRequest);

}
