package com.ia.core.rest.control;

import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.ListBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * Interface base para controladores do tipo list.
 * <p>
 * Constante para o nome do esquema de segurança JWT.
 *
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 * @author Israel Araújo
 */
public interface ListBaseController<T extends Serializable, D extends DTO<?>>
    extends BaseController<T, D> {

    /**
     * Busca os elementos com paginação conforme critérios de busca.
     *
     * @param request     {@link SearchRequest} - validado automaticamente via @Valid
     * @param httpRequest {@link HttpServletRequest}
     * @return {@link Page} com status OK (200) e dados paginados
     * @see ListBaseService#findAll(SearchRequestDTO)
     */
    @Operation(
        summary = "Lista os objetos que atendem aos critérios de busca",
        description = "Retorna uma lista paginada de objetos conforme os filtros aplicados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Page.class))
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
    @PostMapping("/all")
    default ResponseEntity<Page<D>> findAll(@Valid @RequestBody SearchRequestDTO request,
                                            HttpServletRequest httpRequest) {
        Page<D> page = ((ListBaseService<?, D>) getService()).findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

}
