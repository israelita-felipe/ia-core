package com.ia.core.rest.control;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.DeleteBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface base para controladores do tipo delete.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 */
public interface DeleteBaseController<T extends BaseEntity, D extends DTO<?>>
  extends BaseController<T, D> {

  /**
   * @param id      Id do objeto.
   * @param request {@link HttpServletRequest}
   * @return ResponseEntity de {@link Void}.
   * @throws ServiceException caso ocorra algum erro no serviço
   * @see DeleteBaseService#delete(UUID)
   */
  @Operation(summary = "Exclui um objeto pelo seu Id")
  @DeleteMapping("/{id}")
  default ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                      HttpServletRequest request)
    throws ServiceException {
    ((DeleteBaseService<?, D>) getService()).delete(id);
    return ResponseEntity.noContent().build();
  }

}
