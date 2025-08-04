package com.ia.core.security.service;

import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.functionality.OperationEnum;
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
public interface SaveSecuredBaseService<T extends BaseEntity, D extends DTO<T>>
  extends BaseSecuredService<T, D>, SaveBaseService<T, D> {
  /**
   * @param toSave
   * @return
   */
  @Override
  default boolean canCreate(D toSave) {
    return getAuthorizationManager().canCreate(this);
  }

  /**
   * @param toSave
   * @return
   */
  @Override
  default boolean canUpdate(D toSave) {
    return getAuthorizationManager().canUpdate(this);
  }

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    return Set.of(
                  functionalityManager
                      .addFunctionality(this, OperationEnum.CREATE),
                  functionalityManager
                      .addFunctionality(this, OperationEnum.UPDATE));
  }

  @Transactional
  @Override
  default D save(D toSave)
    throws ServiceException {
    getLogOperationService().logBeforeSave(toSave, getRepository(),
                                           getMapper());
    D saved = SaveBaseService.super.save(toSave);
    getLogOperationService().logAfterSave(toSave, saved, getRepository(),
                                          getMapper());
    return saved;
  }
}
