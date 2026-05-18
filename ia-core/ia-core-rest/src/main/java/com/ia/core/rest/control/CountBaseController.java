package com.ia.core.rest.control;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.CountBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
   @Operation(
       summary = "Conta a quantidade de objetos que atendem aos critérios de busca",
       description = "Retorna a quantidade de objetos que correspondem aos filtros informados"
   )
   @ApiResponses(value = {
       @ApiResponse(
           responseCode = "200",
           description = "Contagem retornada com sucesso",
           content = @Content(mediaType = "application/json",
               schema = @Schema(implementation = Integer.class))
       ),
       @ApiResponse(
           responseCode = "400",
           description = "Parâmetros de busca inválidos",
           content = @Content(mediaType = "application/json")
       ),
       @ApiResponse(
           responseCode = "401",
           description = "Token inválido ou expirado"
       )
   })
   @SecurityRequirement(name = BaseController.TOKEN_AUTENTICACAO)
   @PostMapping("/count")
   default ResponseEntity<Integer> count(@Valid @RequestBody SearchRequestDTO searchRequest,
                                         HttpServletRequest request) {
     int result = ((CountBaseService<?, D>) getService())
         .count(searchRequest);
     return ResponseEntity.ok(result);
   }
}
