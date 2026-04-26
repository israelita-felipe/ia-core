package com.ia.core.view.client;

import com.ia.core.service.dto.request.SearchRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

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
