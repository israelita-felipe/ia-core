package com.ia.core.rest.control;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.FindBaseService;
import com.ia.core.service.dto.DTO;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface base para controladores do tipo find.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 */
public interface FindBaseController<T extends BaseEntity, D extends DTO<T>>
  extends BaseController<T, D> {

  /**
   * @param id      Id do objeto.
   * @param request {@link HttpServletRequest}
   * @return {@link DTO}
   * @see FindBaseService#find(UUID)
   */
  @GetMapping("/{id}")
  default ResponseEntity<D> find(@PathVariable("id") UUID id,
                                 HttpServletRequest request) {
    D response = ((FindBaseService<?, D>) getService()).find(id);
    return ResponseEntity.ok(response);
  }

}
