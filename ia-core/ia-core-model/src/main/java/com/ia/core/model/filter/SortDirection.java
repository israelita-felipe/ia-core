package com.ia.core.model.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

/**
 * Enum de representação de sorting.
 *
 * @author Israel Araújo
 * @see Order
 */
public enum SortDirection {
  /**
   * Ascendente.
   */
  ASC {
    @Override
    public <T> Order build(Root<T> root, CriteriaBuilder cb,
                           SortRequest request) {
      return cb.asc(getPath(root, request.getKey()));
    }
  },
  /**
   * Descendente.
   */
  DESC {
    @Override
    public <T> Order build(Root<T> root, CriteriaBuilder cb,
                           SortRequest request) {
      return cb.desc(getPath(root, request.getKey()));
    }
  };

  /**
   * Realiza o build da {@link Order}
   *
   * @param <T>     Tipo de dado.
   * @param root    {@link Root}
   * @param cb      {@link CriteriaBuilder}
   * @param request {@link SortRequest}
   * @return A {@link Order} final.
   */
  public abstract <T> Order build(Root<T> root, CriteriaBuilder cb,
                                  SortRequest request);

  /**
   * Captura um {@link Path} em relação a propriedades compostas
   *
   * @param <T>           Tido do objeto
   * @param root          {@link Root}
   * @param attributeName nome do atributo, simples ou composto
   * @return {@link Path} final (folha) do objeto.
   */
  public <T> Path<T> getPath(Root<T> root, String attributeName) {
    Path<T> path = root;
    for (String part : attributeName.split("\\.")) {
      path = path.get(part);
    }
    return path;
  }

}
