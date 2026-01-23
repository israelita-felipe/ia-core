package com.ia.core.rest.control;

import java.util.Collection;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.SaveBaseService;
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
  @Operation(summary = "Salva um objeto (inclusão ou alteração)")
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
  @Operation(summary = "Valida um objeto sem salvá-lo")
  @PostMapping("/validate")
  default ResponseEntity<Collection<String>> validate(@RequestBody D dto,
                                                      HttpServletRequest request)
    throws ServiceException {
    ((SaveBaseService<?, D>) getService()).validate(dto);
    return ResponseEntity.ok(Collections.emptyList());
  }

}
