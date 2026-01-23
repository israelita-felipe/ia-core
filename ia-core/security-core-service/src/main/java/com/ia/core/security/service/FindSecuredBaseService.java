package com.ia.core.security.service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.FindBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que busca uma {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository}
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface FindSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseSecuredService<T, D>, FindBaseService<T, D> {

  @Override
  default boolean canFind(Long id) {
    return getAuthorizationManager().canRead(this, id);
  }

  @Override
  default Map<String, String> getContextValue(Object object) {
    Map<String, String> contextMap = BaseSecuredService.super.getContextValue(object);
    if (Long.class.isInstance(object)) {
      contextMap.put(AbstractBaseEntityDTO.CAMPOS.ID,
                     Objects.toString(object));
    }
    return contextMap;
  }

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    return Set.of(functionalityManager.addFunctionality(this));
  }
}
