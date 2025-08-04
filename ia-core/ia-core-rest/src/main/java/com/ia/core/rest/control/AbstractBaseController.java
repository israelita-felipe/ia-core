package com.ia.core.rest.control;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.BaseService;
import com.ia.core.service.dto.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Classe abstrata base para um controlador.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> {@link DTO}
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractBaseController<T extends BaseEntity, D extends DTO<T>>
  implements BaseController<T, D> {

  /**
   * Serviço do controlador.
   */
  protected final BaseService<T, D> service;

}
