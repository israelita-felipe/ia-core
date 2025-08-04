package com.ia.core.service.specification;

import java.util.function.Predicate;

import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.request.SearchRequestDTO;

import lombok.AllArgsConstructor;

/**
 * Classe de especificação para coleção
 *
 * @author Israel Araújo
 * @param <T> Tipo de dado
 */
@AllArgsConstructor
public class CollectionSearchSpecification<T> {

  /**
   * Serial UID.
   */
  public static final long serialVersionUID = -9153865343320750644L;

  /**
   * Requisição.
   */
  private final transient SearchRequestDTO request;

  /**
   * @return Predicado indicando a especificação para coleção
   */
  public Predicate<T> toPredicate() {
    Predicate<T> predicate = object -> true;

    for (FilterRequestDTO filter : this.request.getFilters()) {
      OperatorDTO operator = filter.getOperator();
      if (operator != null) {
        predicate = operator.build(filter, predicate,
                                   request.isDisjunction());
      }
    }

    return predicate;
  }

}
