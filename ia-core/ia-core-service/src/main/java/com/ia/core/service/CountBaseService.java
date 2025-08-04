package com.ia.core.service;

import java.util.stream.Collectors;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.model.specification.SearchSpecification;
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
public interface CountBaseService<T extends BaseEntity, D extends DTO<T>>
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
  default int count(SearchRequestDTO requestDTO) {
    if (canCount(requestDTO)) {
      SearchRequest request = getSearchRequestMapper().toModel(requestDTO);
      request.setFilters(request.getFilters().stream()
          .filter(filter -> filter.getKey() != null
              && filter.getOperator() != null)
          .collect(Collectors.toList()));
      // cria a especificação
      SearchSpecification<T> specification = new SearchSpecification<>(request);
      // realiza o count
      return (int) getRepository().count(specification);
    }
    return 0;
  }

}
