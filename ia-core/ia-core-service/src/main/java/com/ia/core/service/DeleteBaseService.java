package com.ia.core.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ia.core.model.BaseEntity;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que deleta um {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface DeleteBaseService<T extends BaseEntity, D extends DTO<T>>
  extends BaseService<T, D> {

  /**
   * Verifica se um objeto pode ser excluído
   *
   * @param id {@link UUID} do objeto a ser excluído
   * @return por padrão retorna <code>true</code>
   */
  default boolean canDelete(UUID id) {
    return true;
  }

  /**
   * Deleta um {@link DTO} pelo seu {@link UUID}.
   *
   * @param id {@link UUID} da entidade <T>
   * @throws ServiceException caso ocorra alguma exceção
   */

  @Transactional(propagation = Propagation.REQUIRED)
  default void delete(UUID id)
    throws ServiceException {
    if (canDelete(id)) {
      try {
        getRepository().deleteById(id);
      } catch (Exception e) {
        ServiceException serviceException = new ServiceException();
        serviceException.add(e);
        throw serviceException;
      }
    }
  }

}
