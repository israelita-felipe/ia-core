package com.ia.core.rest.control;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.DeleteBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
   * Exclui um objeto pelo seu ID.
   *
   * @param id      ID do objeto a excluir
   * @param request {@link HttpServletRequest}
   * @return ResponseEntity de {@link Void} com status NO_CONTENT (204)
   * @throws ServiceException caso ocorra algum erro no serviço
   * @see DeleteBaseService#delete(Long)
   */
  @Operation(
      summary = "Exclui um objeto pelo seu Id",
      description = "Remove permanentemente um objeto do sistema pelo seu identificador"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "204",
          description = "Objeto excluído com sucesso"
      ),
      @ApiResponse(
          responseCode = "404",
          description = "Objeto não encontrado",
          content = @Content(mediaType = "application/json")
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Token inválido ou expirado"
      )
  })
  @SecurityRequirement(name = BaseController.TOKEN_AUTENTICACAO)
  @DeleteMapping("/{id}")
  default ResponseEntity<Void> delete(
      @Parameter(description = "ID do objeto a ser excluído", required = true, example = "1")
      @PathVariable("id") Long id,
      HttpServletRequest request)
    throws ServiceException {
    ((DeleteBaseService<?, D>) getService()).delete(id);
    return ResponseEntity.noContent().build();
  }
}
