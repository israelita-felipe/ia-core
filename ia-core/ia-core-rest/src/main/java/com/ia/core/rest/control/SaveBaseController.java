package com.ia.core.rest.control;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.SaveBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.Collections;

/**
 * Interface base para controladores do tipo save.
 *
 * Constante para o nome do esquema de segurança JWT.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 */
public interface SaveBaseController<T extends BaseEntity, D extends DTO<?>>
  extends BaseController<T, D> {

  /**
   * Salva um novo objeto ou atualiza um existente.
   *
   * @param dto     O objeto {@link DTO} a ser salvo
   * @param request {@link HttpServletRequest}
   * @return Objeto <D> do tipo {@link DTO} salvo com status CREATED (201)
   * @throws ServiceException caso ocorra algum erro de serviço
   * @see SaveBaseService#save(DTO)
   */
  @Operation(
      summary = "Salva um objeto (inclusão ou alteração)",
      description = "Cria um novo objeto ou atualiza um objeto existente"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Objeto salvo com sucesso",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = Object.class))
      ),
      @ApiResponse(
          responseCode = "400",
          description = "Dados inválidos",
          content = @Content(mediaType = "application/json")
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Token inválido ou expirado"
      )
  })
  @SecurityRequirement(name = BaseController.TOKEN_AUTENTICACAO)
  @PostMapping
  default ResponseEntity<D> save(@RequestBody D dto,
                                 HttpServletRequest request)
    throws ServiceException {
    D response = ((SaveBaseService<?, D>) getService()).save(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Realiza a validação de um objeto sem salvá-lo.
   *
   * @param dto     {@link DTO} a ser validado
   * @param request {@link HttpServletRequest}
   * @return Coleção de erros apurados (vazia se válido)
   * @throws ServiceException caso ocorra alguma erro de serviço
   */
  @Operation(
      summary = "Valida um objeto sem salvá-lo",
      description = "Verifica se os dados do objeto são válidos sem persisti-lo"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Validação concluída (vazio se válido)",
          content = @Content(mediaType = "application/json")
      ),
      @ApiResponse(
          responseCode = "401",
          description = "Token inválido ou expirado"
      )
  })
  @SecurityRequirement(name = BaseController.TOKEN_AUTENTICACAO)
  @PostMapping("/validate")
  default ResponseEntity<Collection<String>> validate(@RequestBody D dto,
                                                       HttpServletRequest request)
    throws ServiceException {
    ((SaveBaseService<?, D>) getService()).validate(dto);
    return ResponseEntity.ok(Collections.emptyList());
  }

}
