package com.ia.core.security.service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.filter.FieldType;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.model.functionality.OperationEnum;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.CountBaseService;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.repository.BaseEntityRepository;
import com.ia.core.service.util.JsonUtil;

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
    return getAuthorizationManager().canRead(this, requestDTO);
  }

  @Override
  default int count(SearchRequestDTO requestDTO) {
    getAuthorizationManager().getCurrentContextDefinitions().definitions()
        .getContext().stream()
        .filter(up -> Objects.equals(up.getPrivilege().getName(),
                                     getContextName()))
        .flatMap(po -> po.getPrivilegeOperations().stream())
        .filter(op -> Objects.equals(op.getOperation(), OperationEnum.READ))
        .flatMap(op -> op.getContext().stream())
        .forEach(userContextDefinition -> {
          var key = userContextDefinition.getContextKey();
          Collection<String> values = userContextDefinition.getValues();
          // só adiciona filtro de houver valor
          if (!values.isEmpty()) {
            requestDTO.getContext()
                .add(FilterRequestDTO.builder().key(key)
                    .operator(OperatorDTO.IN).fieldType(FieldType.OBJECT)
                    .value(getContextDefinitionValue(key, values)).build());
          }
        });
    return CountBaseService.super.count(requestDTO);
  }

  @Override
  default Map<String, String> getContextValue(Object object) {
    Map<String, String> contextMap = BaseSecuredService.super.getContextValue(object);
    if (SearchRequestDTO.class.isInstance(object)) {
      ((SearchRequestDTO) object).getContext().forEach(filterContext -> {
        contextMap.put(filterContext.getKey(),
                       JsonUtil.toJson(filterContext.getValue()));
      });
    }
    return contextMap;
  }

  @Override
  default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
    return Set.of(functionalityManager.addFunctionality(this));
  }
}
