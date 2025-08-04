package com.ia.core.model.filter;

import java.util.Objects;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * Operador a ser realizado no filtro.
 *
 * @author Israel Araújo
 */
public enum Operator {

  /**
   * Igual
   */
  EQUAL {
    @Override
    public <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                               FilterRequest request, Predicate predicate,
                               boolean disjunction) {
      Object value = request.getFieldType().parse(request.getValue());
      Expression<?> key = getPath(root, request.getKey());
      Predicate newPredicate = cb.equal(key, value);
      if (request.isNegate()) {
        newPredicate = newPredicate.not();
      }

      if (disjunction) {
        return cb.and(newPredicate, predicate);
      }
      return cb.or(cb.equal(key, value), predicate);
    }

  },
  /**
   * Diferente
   */
  NOT_EQUAL {
    @Override
    public <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                               FilterRequest request, Predicate predicate,
                               boolean disjunction) {
      Object value = request.getFieldType().parse(request.getValue());
      Expression<?> key = getPath(root, request.getKey());
      Predicate newPredicate = cb.notEqual(key, value);
      if (request.isNegate()) {
        newPredicate = newPredicate.not();
      }
      if (disjunction) {
        return cb.and(newPredicate, predicate);
      }
      return cb.or(cb.notEqual(key, value), predicate);
    }

  },

  /**
   * Like/Semelhante
   */
  LIKE {
    @Override
    public <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                               FilterRequest request, Predicate predicate,
                               boolean disjunction) {
      Expression<String> key = getPath(root, request.getKey())
          .as(String.class);
      Predicate newPredicate = cb
          .like(cb.upper(key), "%" + Objects.toString(
                                                      request.getFieldType()
                                                          .parse(request
                                                              .getValue()),
                                                      "")
              .toUpperCase() + "%");
      if (request.isNegate()) {
        newPredicate = newPredicate.not();
      }
      if (disjunction) {
        return cb.and(newPredicate, predicate);
      }
      return cb.or(newPredicate, predicate);

    }

  },
  /**
   * Em lista
   */
  IN {
    @Override
    public <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                               FilterRequest request, Predicate predicate,
                               boolean disjunction) {
      CriteriaBuilder.In<Object> inClause = cb
          .in(getPath(root, request.getKey()));
      inClause.value(request.getFieldType().parse(request.getValue()));
      Predicate newPredicate = inClause;
      if (request.isNegate()) {
        newPredicate = newPredicate.not();
      }
      if (disjunction) {
        return cb.and(newPredicate, predicate);
      }
      return cb.or(newPredicate, predicate);
    }

  },
  /**
   * Maior que
   */
  GREATER_THAN {
    @SuppressWarnings({ "unchecked", "rawtypes" })

    @Override
    public <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                               FilterRequest request, Predicate predicate,
                               boolean disjunction) {
      Comparable value = (Comparable) request.getFieldType()
          .parse(request.getValue());
      Expression<Comparable> key = (Expression<Comparable>) getPath(root,
                                                                    request
                                                                        .getKey());
      Predicate newPredicate = cb.greaterThan(key, value);
      if (request.isNegate()) {
        newPredicate = newPredicate.not();
      }
      if (disjunction) {
        return cb.and(newPredicate, predicate);
      }
      return cb.or(newPredicate, predicate);

    }

  },
  /**
   * Menor que
   */
  LESS_THAN {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                               FilterRequest request, Predicate predicate,
                               boolean disjunction) {
      Comparable value = (Comparable) request.getFieldType()
          .parse(request.getValue());
      Expression<Comparable> key = (Expression<Comparable>) getPath(root,
                                                                    request
                                                                        .getKey());
      Predicate newPredicate = cb.lessThan(key, value);
      if (request.isNegate()) {
        newPredicate = newPredicate.not();
      }
      if (disjunction) {
        return cb.and(newPredicate, predicate);
      }
      return cb.or(newPredicate, predicate);
    }
  },
  /**
   * Maior ou igual
   */
  GREATER_THAN_OR_EQUAL_TO {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                               FilterRequest request, Predicate predicate,
                               boolean disjunction) {
      Comparable value = (Comparable) request.getFieldType()
          .parse(request.getValue());
      Expression<Comparable> key = (Expression<Comparable>) getPath(root,
                                                                    request
                                                                        .getKey());
      Predicate newPredicate = cb.greaterThanOrEqualTo(key, value);
      if (request.isNegate()) {
        newPredicate = newPredicate.not();
      }
      if (disjunction) {
        return cb.and(newPredicate, predicate);
      }
      return cb.or(newPredicate, predicate);
    }
  },
  /**
   * Menor ou igual
   */
  LESS_THAN_OR_EQUAL_TO {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                               FilterRequest request, Predicate predicate,
                               boolean disjunction) {
      Comparable value = (Comparable) request.getFieldType()
          .parse(request.getValue());
      Expression<Comparable> key = (Expression<Comparable>) getPath(root,
                                                                    request
                                                                        .getKey());
      Predicate newPredicate = cb.lessThanOrEqualTo(key, value);
      if (request.isNegate()) {
        newPredicate = newPredicate.not();
      }
      if (disjunction) {
        return cb.and(newPredicate, predicate);
      } else {
        return cb.or(newPredicate, predicate);
      }
    }
  };

  /**
   * Realiza o build.
   *
   * @param <T>         Tipo de dado.
   * @param root        {@link Root}
   * @param cb          {@link CriteriaBuilder}a
   * @param request     {@link FilterRequest}
   * @param predicate   {@link Predicate}
   * @param disjunction indicativo de disjunção ou conjunção
   * @return O {@link Predicate} final.
   */
  public abstract <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                                      FilterRequest request,
                                      Predicate predicate,
                                      boolean disjunction);

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
