package com.ia.core.security.service;

import java.util.Set;
import java.util.UUID;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.authorization.CoreAuthorizationManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.DeleteBaseService;
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
public interface DeleteSecuredBaseService<T extends BaseEntity, D extends DTO<T>>
  extends BaseSecuredService<T, D>, DeleteBaseService<T, D> {

  /**
   * @param id identificador do objeto
   * @return <code>true</code> se for possível deletar. Delega para
   *         {@link CoreAuthorizationManager#canDelete(com.ia.core.security.service.model.functionality.HasFunctionality)}
   */
  @Override
  default boolean canDelete(UUID id) {
    return getAuthorizationManager().canDelete(this);
  }

  @Override
  default void delete(UUID id)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    onTransaction(() -> {
      try {
        D dto = getLogOperationService()
            .logBeforeDelete(id, getRepository(), getMapper());
        DeleteBaseService.super.delete(id);
        getLogOperationService().logAfterDelete(dto, getRepository(),
                                                getMapper());
      } catch (Exception e) {
        ex.add(ex);
      }
      return id;
    });
    checkErrors(ex);
  }

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    return Set.of(functionalityManager
        .addFunctionality(this, OperationEnum.DELETE));
  }
}
