package com.ia.core.security.service;

import java.util.Set;
import java.util.UUID;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.FindBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que busca uma {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface FindSecuredBaseService<T extends BaseEntity, D extends DTO<T>>
  extends BaseSecuredService<T, D>, FindBaseService<T, D> {

  @Override
  default boolean canFind(UUID id) {
    return getAuthorizationManager().canRead(this);
  }

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    return Set.of(functionalityManager
        .addFunctionality(this, OperationEnum.READ));
  }
}
