package com.ia.core.rest.control;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.BaseService;
import com.ia.core.service.dto.DTO;

/**
 * Interface base para um controller.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 */
public interface BaseController<T extends BaseEntity, D extends DTO<?>> {
  /**
   * Serviço do controlador {@link BaseController}.
   *
   * @param <R> Tipo do serviço: {@link BaseService}
   * @return Um {@link BaseService} de <R>.
   */
  <R extends BaseService<?, D>> R getService();

}
