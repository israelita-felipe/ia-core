package com.ia.core.security.service;

import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.SaveBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que salva um {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface SaveSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseSecuredService<T, D>, SaveBaseService<T, D> {
  /**
   * @param toSave
   * @return
   */
  @Override
  default boolean canCreate(D toSave) {
    return getAuthorizationManager().canCreate(this, toSave);
  }

  /**
   * @param toSave
   * @return
   */
  @Override
  default boolean canUpdate(D toSave) {
    return getAuthorizationManager().canUpdate(this, toSave);
  }

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    return Set.of(functionalityManager.addFunctionality(this));
  }

  @Override
  default D save(D toSave)
    throws ServiceException {
    ServiceException ex = new ServiceException();
    D savedEntity = onTransaction(() -> {
      try {
        getLogOperationService().logBeforeSave(toSave, getRepository(),
                                               getMapper());
        D saved = SaveBaseService.super.save(toSave);
        getLogOperationService().logAfterSave(toSave, saved,
                                              getRepository(), getMapper());
        return saved;
      } catch (Exception e) {
        ex.add(e);
      }
      return null;
    });
    checkErrors(ex);
    return savedEntity;
  }
}
