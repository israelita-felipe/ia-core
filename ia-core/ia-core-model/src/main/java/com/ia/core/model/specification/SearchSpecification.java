package com.ia.core.model.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ia.core.model.filter.FilterRequest;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.model.filter.SortRequest;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

/**
 * Classe de {@link Specification}.
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado da {@link Specification}
 * @see Specification
 */
@AllArgsConstructor
public class SearchSpecification<T>
  implements Specification<T> {

  /**
   * Serial UID.
   */
  private static final long serialVersionUID = -9153865343320750644L;

  /**
   * Cria a paginação.
   *
   * @param page Página atual. Índice 0 a infinito.
   * @param size Tamanho da página.
   * @return {@link Pageable} da requisição.
   */
  public static Pageable getPageable(Integer page, Integer size) {
    return PageRequest.of(Optional.ofNullable(page).orElse(0),
                          Optional.ofNullable(size).orElse(100));
  }

  /**
   * Requisição.
   */
  private final transient SearchRequest request;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                               CriteriaBuilder cb) {
    Predicate predicate = cb.equal(cb.literal(Boolean.TRUE),
                                   request.isDisjunction());

    for (FilterRequest filter : this.request.getFilters()) {
      predicate = filter.getOperator().build(root, cb, filter, predicate,
                                             request.isDisjunction());
    }

    List<Order> orders = new ArrayList<>();
    for (SortRequest sort : this.request.getSorts()) {
      orders.add(sort.getDirection().build(root, cb, sort));
    }

    query.orderBy(orders);
    return predicate;
  }

}
