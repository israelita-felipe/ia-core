package com.ia.core.rest.control;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.ListBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface base para controladores do tipo delete.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 */
public interface ListBaseController<T extends BaseEntity, D extends DTO<T>>
  extends BaseController<T, D> {

  /**
   * Busca os elementos.
   *
   * @param request     {@link SearchRequest}
   * @param httpRequest {@link HttpServletRequest}
   * @return {@link Page} do tipo {@link DTO} salvo.
   * @see ListBaseService#findAll(SearchRequestDTO)
   */
  @Operation(summary = "Lista os objetos que atendem aos critérios de busca")
  @PostMapping("/all")
  default ResponseEntity<Page<D>> findAll(@RequestBody SearchRequestDTO request,
                                          HttpServletRequest httpRequest) {
    Page<D> page = ((ListBaseService<?, D>) getService()).findAll(request);
    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(page);
  }

}
