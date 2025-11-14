package com.ia.core.rest.control;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.BaseService;
import com.ia.core.service.dto.DTO;

/**
 * Classe padrão para criação de um controlador com todos os serviços.
 *
 * @author Israel Araújo
 * @param <T> Tipo do modelo.
 * @param <D> Tipo de dado {@link DTO}
 * @see AbstractBaseController
 * @see CountBaseController
 * @see FindBaseController
 */
public abstract class DefaultBaseController<T extends BaseEntity, D extends DTO<?>>
  extends AbstractBaseController<T, D>
  implements CountBaseController<T, D>, FindBaseController<T, D>,
  DeleteBaseController<T, D>, ListBaseController<T, D>,
  SaveBaseController<T, D> {

  /**
   * @param service
   */
  public DefaultBaseController(BaseService<T, D> service) {
    super(service);
  }

}
