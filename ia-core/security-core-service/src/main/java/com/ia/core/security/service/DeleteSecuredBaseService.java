package com.ia.core.security.service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.authorization.CoreAuthorizationManager;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.DeleteBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.entity.AbstractBaseEntityDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.repository.BaseEntityRepository;

/**
 * Interface que deleta um {@link BaseEntity} por meio de um
 * {@link BaseEntityRepository} com suporte a segurança.
 * <p>
 * Os callbacks de evento são herdados de {@link DeleteBaseService}.
 * </p>
 *
 * @author Israel Araújo
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 */
public interface DeleteSecuredBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseSecuredService<T, D>, DeleteBaseService<T, D> {

  /**
   * @param id identificador do objeto
   * @return <code>true</code> se for possível deletar. Delega para
   *         {@link CoreAuthorizationManager#canDelete(com.ia.core.security.service.model.functionality.HasFunctionality)}
   */
  @Override
  default boolean canDelete(Long id) {
    return getAuthorizationManager().canDelete(this, id);
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
