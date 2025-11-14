package com.ia.core.security.service;

import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.CountBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que conta os elementos de um determinado
 * {@link BaseEntityRepository}
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface CountSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseSecuredService<T, D>, CountBaseService<T, D> {

  /**
   * @param requestDTO {@link SearchRequestDTO}
   * @return se pode contar
   */
  @Override
  default boolean canCount(SearchRequestDTO requestDTO) {
    return getAuthorizationManager().canRead(this);
  }

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    return Set.of(functionalityManager
        .addFunctionality(this, OperationEnum.READ));
  }
}
