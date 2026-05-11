package com.ia.core.rest.control;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.FindBaseService;
import com.ia.core.service.dto.DTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
   * @see FindBaseService#find(Long)
   */
  @Operation(
      summary = "Recupera um objeto pelo seu Id",
      description = "Busca e retorna um objeto específico pelo seu identificador único"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Objeto encontrado",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Object.class))
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
  @GetMapping("/{id}")
  default ResponseEntity<D> find(
      @Parameter(description = "ID do objeto a ser recuperado", required = true, example = "1")
      @PathVariable("id") Long id,
      HttpServletRequest request) {
    D response = ((FindBaseService<?, D>) getService()).find(id);
    return ResponseEntity.ok(response);
  }
}
