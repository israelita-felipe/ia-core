package com.ia.core.view.client;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ia.core.service.dto.DTO;
import com.ia.core.view.service.SaveBaseService;

import jakarta.validation.Valid;

/**
 * Interface base para clientes do tipo save.
 *
 * @author Israel Araújo
 * @param <D> Tipo de dado {@link DTO}
 */
public interface SaveBaseClient<D extends Serializable>
  extends BaseClient<D> {

  /**
   * @param dto O objeto {@link Serializable} a ser salvo.
   * @return Objeto <D> do tipo {@link Serializable} salvo.
   * @see SaveBaseService#save(Serializable)
   */
  @PostMapping
  D save(@RequestBody @Valid D dto);

  /**
   * Realiza a validação de um objeto.
   *
   * @param dto {@link DTO} a ser validado.
   * @return Coleção de erros apurados.
   *         <ul>
   *         <li>{@link HttpStatus#OK} - {@link Collection} vazia</li>
   *         <li>{@link HttpStatus#BAD_REQUEST} - {@link Collection} com os
   *         erros</li>
   *         <li>{@link HttpStatus#INTERNAL_SERVER_ERROR} - {@link Collection}
   *         vazia</li>
   *         </ul>
   */
  @PostMapping("/validate")
  Collection<String> validate(@RequestBody D dto);

}
