package com.ia.core.security.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.security.model.functionality.Functionality;
import com.ia.core.security.service.model.functionality.FunctionalityManager;
import com.ia.core.service.CrudService;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.annotations.TransactionalWrite;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface CrudSecuredService<T extends BaseEntity, D extends DTO<?>> extends CountSecuredBaseService<T, D>, DeleteSecuredBaseService<T, D>, FindSecuredBaseService<T, D>, ListSecuredBaseService<T, D>, SaveSecuredBaseService<T, D>, CrudService<T,D> {

    @Override
    default Map<String, String> getContextValue(Object object) {
        Map<String, String> contextValue = new HashMap<>();
        java.util.stream.Stream.<java.util.function.Supplier<Map<String, String>>>of(
            () -> CountSecuredBaseService.super.getContextValue(object),
            () -> DeleteSecuredBaseService.super.getContextValue(object),
            () -> FindSecuredBaseService.super.getContextValue(object),
            () -> ListSecuredBaseService.super.getContextValue(object)
        ).forEach(supplier -> contextValue.putAll(supplier.get()));
        return contextValue;
    }

    @Override
    default Set<Functionality> registryFunctionalities(FunctionalityManager functionalityManager) {
        Set<Functionality> result = new HashSet<>();
        result.addAll(CountSecuredBaseService.super.registryFunctionalities(functionalityManager));
        result.addAll(DeleteSecuredBaseService.super.registryFunctionalities(functionalityManager));
        result.addAll(FindSecuredBaseService.super.registryFunctionalities(functionalityManager));
        result.addAll(ListSecuredBaseService.super.registryFunctionalities(functionalityManager));
        result.addAll(SaveSecuredBaseService.super.registryFunctionalities(functionalityManager));
        return result;
    }



    @TransactionalWrite
    @Override
    default void delete(Long id)
        throws ServiceException {
        DeleteSecuredBaseService.super.delete(id);
    }

    @TransactionalWrite
    @Override
    default D save(D toSave)
        throws ServiceException {
        return SaveSecuredBaseService.super.save(toSave);
    }

    @TransactionalReadOnly
    @Override
    default int count(SearchRequestDTO requestDTO) {
        return CountSecuredBaseService.super.count(requestDTO);
    }

    @TransactionalReadOnly
    @Override
    default D find(Long id) {
        return FindSecuredBaseService.super.find(id);
    }

    @TransactionalReadOnly
    @Override
    default Page<D> findAll(SearchRequestDTO requestDTO) {
        return ListSecuredBaseService.super.findAll(requestDTO);
    }

}
