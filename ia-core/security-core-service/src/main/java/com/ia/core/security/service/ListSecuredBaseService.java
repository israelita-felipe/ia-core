package com.ia.core.security.service;

import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.ListBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que busca uma {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface ListSecuredBaseService<T extends BaseEntity, D extends DTO<T>>
  extends BaseSecuredService<T, D>, ListBaseService<T, D> {

  /**
   * @param requestDTO {@link SearchRequestDTO}
   * @return se pode listar
   */
  @Override
  default boolean canList(SearchRequestDTO requestDTO) {
    return getAuthorizationManager().canRead(this);
  }

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    return Set.of(functionalityManager
        .addFunctionality(this, OperationEnum.READ));
  }
}
