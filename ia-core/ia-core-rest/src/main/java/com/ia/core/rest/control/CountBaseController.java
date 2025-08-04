package com.ia.core.rest.control;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.CountBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface base para controladores do tipo count.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 */
public interface CountBaseController<T extends BaseEntity, D extends DTO<T>>
  extends BaseController<T, D> {

  /**
   * @param searchRequest {@link SearchRequest}
   * @param request       {@link HttpServletRequest}
   * @return {@link Integer}
   * @see CountBaseService#count(SearchRequestDTO)
   */
  @PostMapping("/count")
  default ResponseEntity<Integer> count(@RequestBody SearchRequestDTO searchRequest,
                                        HttpServletRequest request) {
    int result = ((CountBaseService<?, D>) getService())
        .count(searchRequest);
    return ResponseEntity.ok(result);
  }

}
