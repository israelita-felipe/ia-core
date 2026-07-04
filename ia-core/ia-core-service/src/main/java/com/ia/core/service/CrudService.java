package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.service.contract.HasMapper;
import com.ia.core.service.contract.HasRepository;
import com.ia.core.service.contract.HasSearchRequestMapper;
import com.ia.core.service.contract.HasTranslator;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.exception.ServiceException;
import com.ia.core.service.usecase.CrudUseCase;
import org.springframework.data.domain.Page;

/**
 * Interface base para serviços CRUD.
 * <p>
 * Combina as operações de Create, Read, Update e Delete com
 * resiliência para operações de banco de dados. Todos os métodos
 * são implementados como métodos default que delegam para as
 * interfaces especializadas.
 * </p>
 *
 * @param <T> Tipo da entidade JPA
 * @param <D> Tipo do DTO de transferência
 * @see com.ia.core.service.CountBaseService
 * @see com.ia.core.service.DeleteBaseService
 * @see com.ia.core.service.FindBaseService
 * @see com.ia.core.service.ListBaseService
 * @see com.ia.core.service.SaveBaseService
 */
public interface CrudService<T extends BaseEntity, D extends DTO<?>> extends HasRepository<T>, HasMapper<T, D>, HasSearchRequestMapper, HasTranslator, CountBaseService<T, D>, DeleteBaseService<T, D>, FindBaseService<T, D>, ListBaseService<T, D>, SaveBaseService<T, D>, CrudUseCase<D> {

    @Override
    @Resilient(ResilienceProfile.DATABASE)
    default int count(SearchRequestDTO requestDTO) {
        return CountBaseService.super.count(requestDTO);
    }

    @Resilient(ResilienceProfile.DATABASE)
    @Override
    default D save(D toSave) throws ServiceException {
        return SaveBaseService.super.save(toSave);
    }

    @Resilient(ResilienceProfile.DATABASE)
    @Override
    default void delete(Long id)
        throws ServiceException {
        DeleteBaseService.super.delete(id);
    }

    @Resilient(ResilienceProfile.DATABASE)
    @Override
    default Page<D> findAll(SearchRequestDTO requestDTO) {
        return ListBaseService.super.findAll(requestDTO);
    }

    @Resilient(ResilienceProfile.DATABASE)
    @Override
    default D find(Long id) {
        return FindBaseService.super.find(id);
    }
}
