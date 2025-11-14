package com.ia.core.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ia.core.model.BaseEntity;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.model.specification.SearchSpecification;
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
public interface ListBaseService<T extends BaseEntity, D extends DTO<?>>
  extends BaseService<T, D> {

  /**
   * Verifica se podem ser listados de acordo o {@link SearchRequestDTO}
   *
   * @param requestDTO {@link SearchRequestDTO}
   * @return <code>true</code> por padrão
   */
  default boolean canList(SearchRequestDTO requestDTO) {
    return true;
  }

  /**
   * Busca elementos a partir de uma requisição de busca
   *
   * @param requestDTO {@link SearchRequest}
   * @return {@link Page} de dados do tipo <T>
   */
  default Page<D> findAll(SearchRequestDTO requestDTO) {
    if (canList(requestDTO)) {
      SearchRequest request = getSearchRequestMapper().toModel(requestDTO);
      request.setFilters(request.getFilters().stream()
          .filter(filter -> filter.getKey() != null
              && filter.getOperator() != null)
          .collect(Collectors.toList()));
      // cria a especificação
      SearchSpecification<T> specification = new SearchSpecification<>(request);
      // cria a paginação
      Pageable pageable = SearchSpecification
          .getPageable(request.getPage(), request.getSize());
      // realiza a busca convertendo para o dto.
      return getRepository().findAll(specification, pageable)
          .map(this::toDTO);
    }
    return Page.empty();
  }

}
