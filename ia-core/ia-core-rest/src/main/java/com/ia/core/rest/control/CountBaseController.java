package com.ia.core.rest.control;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.CountBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * Interface base para controladores do tipo count.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 */
public interface CountBaseController<T extends BaseEntity, D extends DTO<?>>
  extends BaseController<T, D> {

  /**
   * Conta a quantidade de objetos que atendem aos critérios de busca.
   *
   * @param searchRequest {@link SearchRequest} - validado automaticamente via @Valid
   * @param request       {@link HttpServletRequest}
   * @return Quantidade total de objetos encontrados
   * @see CountBaseService#count(SearchRequestDTO)
   */
  @Operation(summary = "Conta a quantidade de objetos que atendem aos critérios de busca")
  @PostMapping("/count")
  default ResponseEntity<Integer> count(@Valid @RequestBody SearchRequestDTO searchRequest,
                                        HttpServletRequest request) {
    int result = ((CountBaseService<?, D>) getService())
        .count(searchRequest);
    return ResponseEntity.ok(result);
  }

}
