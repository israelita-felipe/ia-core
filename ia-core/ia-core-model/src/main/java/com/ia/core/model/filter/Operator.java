package com.ia.core.model.filter;

import jakarta.persistence.criteria.*;

import java.util.Objects;

/**
 * Enum que representa os operadores de comparação para filtros de busca.
 *
 * <p>Cada valor define como um valor será comparado com o campo no banco de dados
 * ao construir consultas dinâmicas com JPA Criteria API.
 *
 * <p><b>Por quê usar Operator?</b></p>
 * <ul>
 *   <li>Abstrai a complexidade do JPA Criteria API</li>
 *   <li>Padroniza os operadores de comparação em toda a aplicação</li>
 *   <li>Suporta negação de operadores</li>
 * </ul>
 *
 * @author Israel Araújo
 * @see FilterRequest
 * @see FieldType
 * @since 1.0.0
 */
public enum Operator {

  /**
   * Operador de igualdade. Compara se o valor do campo é igual ao valor fornecido.
   *
   * <p>Equivalente ao operador {@code =} em SQL.
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
   * Operador de diferença. Compara se o valor do campo é diferente do valor fornecido.
   *
   * <p>Equivalente ao operador {@code <>} ou {@code !=} em SQL.
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
   * Operador de similaridade. Compara se o valor do campo contém o valor fornecido.
   *
   * <p>Equivalente ao operador {@code LIKE} em SQL, com wildcards automáticos.
   * A comparação é case-insensitive.
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
   * Operador de pertinência à lista. Compara se o valor do campo está contido
   * na lista de valores fornecida.
   *
   * <p>Equivalente ao operador {@code IN} em SQL.
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
   * Operador de maior que. Compara se o valor do campo é maior que o valor fornecido.
   *
   * <p>Equivalente ao operador {@code >} em SQL.
   * Funciona apenas com tipos {@link Comparable}.
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
   * Operador de menor que. Compara se o valor do campo é menor que o valor fornecido.
   *
   * <p>Equivalente ao operador {@code <} em SQL.
   * Funciona apenas com tipos {@link Comparable}.
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
   * Operador de maior ou igual. Compara se o valor do campo é maior ou igual
   * ao valor fornecido.
   *
   * <p>Equivalente ao operador {@code >=} em SQL.
   * Funciona apenas com tipos {@link Comparable}.
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
   * Operador de menor ou igual. Compara se o valor do campo é menor ou igual
   * ao valor fornecido.
   *
   * <p>Equivalente ao operador {@code <=} em SQL.
   * Funciona apenas com tipos {@link Comparable}.
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
   * Constrói o predicado JPA para este operador.
   *
   * <p>Método abstrato que deve ser implementado por cada operador para
   * criar o {@link Predicate} apropriado no JPA Criteria API.
   *
   * @param <T>         Tipo da entidade
   * @param root        Raiz da consulta (from clause)
   * @param cb          Construtor de critérios do JPA
   * @param request     Requisição de filtro com os parâmetros
   * @param predicate   Predicado anterior para composição
   * @param disjunction Se {@code true}, usa disjunção (OR), senão conjunção (AND)
   * @return O {@link Predicate} resultante da aplicação do operador
   */
  public abstract <T> Predicate build(Root<T> root, CriteriaBuilder cb,
                                      FilterRequest request,
                                      Predicate predicate,
                                      boolean disjunction);

  /**
   * Obtém o {@link Path} para um atributo, suportando caminhos compostos.
   *
   * <p>Permite navegar por atributos aninhados usando notação de ponto.
   * Exemplo: "endereco.cidade.nome" retorna o path para o atributo nome
   * dentro de cidade dentro de endereco.
   *
   * @param <T>           Tipo da entidade raiz
   * @param root          Raiz da consulta
   * @param attributeName Nome do atributo (pode ser composto, ex: "endereco.cidade")
   * @return O {@link Path} para o atributo final
   */
  public <T> Path<T> getPath(Root<T> root, String attributeName) {
    Path<T> path = root;
    for (String part : attributeName.split("\\.")) {
      path = path.get(part);
    }
    return path;
  }

}
