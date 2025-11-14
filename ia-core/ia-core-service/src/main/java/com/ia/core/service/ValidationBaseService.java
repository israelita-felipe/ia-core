package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.validators.HasValidation;

/**
 * Interface de Serviço de validação.
 *
 * @author Israel Araújo
 * @param <T> tipo de dado {@link BaseEntity}
 * @param <D> tipo de dado {@link DTO}
 */
public interface ValidationBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseService<T, D>, HasValidation<D> {

  /**
   * @param dto objeto a ser validado
   * @return <code>true</code> por padrão
   */
  default boolean canValidade(D dto) {
    return true;
  }

  @Override
  default void validate(D object)
    throws ServiceException {
    if (canValidade(object)) {
      HasValidation.super.validate(object);
    }
  }

}
