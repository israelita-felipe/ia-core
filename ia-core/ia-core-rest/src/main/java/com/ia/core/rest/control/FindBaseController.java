package com.ia.core.rest.control;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.FindBaseService;
import com.ia.core.service.dto.DTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface base para controladores do tipo find.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 */
public interface FindBaseController<T extends BaseEntity, D extends DTO<?>>
  extends BaseController<T, D> {

  /**
   * Recupera um objeto pelo seu ID.
   *
   * @param id      ID do objeto a recuperar
   * @param request {@link HttpServletRequest}
   * @return {@link DTO} com status OK
   * @throws javax.persistence.EntityNotFoundException se não encontrado
   * @see FindBaseService#find(UUID)
   */
  @Operation(summary = "Recupera um objeto pelo seu Id")
  @GetMapping("/{id}")
  default ResponseEntity<D> find(@PathVariable("id") Long id,
                                 HttpServletRequest request) {
    D response = ((FindBaseService<?, D>) getService()).find(id);
    return ResponseEntity.ok(response);
  }

}
