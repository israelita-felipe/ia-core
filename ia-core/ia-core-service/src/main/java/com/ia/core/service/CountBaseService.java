package com.ia.core.service;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.model.specification.SearchSpecification;
import com.ia.core.resilience4j.annotation.Resilient;
import com.ia.core.resilience4j.profile.ResilienceProfile;
import com.ia.core.service.annotations.TransactionalReadOnly;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.request.SearchRequestDTO;
import com.ia.core.service.repository.BaseEntityRepository;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * Interface que conta os elementos de um determinado
 * {@link BaseEntityRepository}
 *
 * @param <T> {@link BaseEntity}
 * @param <D> {@link DTO}
 * @author Israel Araújo
 */
public interface CountBaseService<T extends Serializable, D extends DTO<?>>
    extends BaseService<T, D> {

    /**
     * Verifica se é possível contar a partir da requisição de busca
     *
     * @param requestDTO {@link SearchRequestDTO}
     * @return <code>true</code> por padrão
     */
    default boolean canCount(SearchRequestDTO requestDTO) {
        return true;
    }

    /**
     * Conta as entidades do repositório.
     *
     * @param requestDTO {@link SearchRequestDTO}
     * @return {@link Integer}
     */
    @TransactionalReadOnly
    @Resilient(ResilienceProfile.DATABASE)
    default int count(SearchRequestDTO requestDTO) {
        if (canCount(requestDTO)) {
            SearchRequest request = getSearchRequestMapper().toModel(requestDTO);
            request.setFilters(
                request.getFilters() != null
                    ? request.getFilters().stream()
                    .filter(filter -> filter.getKey() != null
                                      && filter.getOperator() != null)
                    .collect(Collectors.toList())
                    : null
            );
            // cria a especificação
            SearchSpecification<T> specification = new SearchSpecification<>(request);
            // realiza o count
            return (int) getRepository().count(specification);
        }
        return 0;
    }

}
