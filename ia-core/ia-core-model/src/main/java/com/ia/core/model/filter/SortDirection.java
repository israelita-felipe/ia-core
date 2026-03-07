package com.ia.core.model.filter;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

/**
 * Enum que representa a direção de ordenação para consultas no banco de dados.
 *
 * <p>Usado em conjunto com {@link SortRequest} para definir a ordem dos
 * resultados de uma consulta.
 *
 * @author Israel Araújo
 * @see SortRequest
 * @see Order
 * @since 1.0.0
 */
public enum SortDirection {
  /**
   * Ordenação ascendente (A-Z, 0-9, mais antigos primeiro).
   *
   * <p>Equivalente ao {@code ORDER BY campo ASC} em SQL.
   */
  ASC {
    @Override
    public <T> Order build(Root<T> root, CriteriaBuilder cb,
                           SortRequest request) {
      return cb.asc(getPath(root, request.getKey()));
    }
  },
  /**
   * Ordenação descendente (Z-A, 9-0, mais recentes primeiro).
   *
   * <p>Equivalente ao {@code ORDER BY campo DESC} em SQL.
   */
  DESC {
    @Override
    public <T> Order build(Root<T> root, CriteriaBuilder cb,
                           SortRequest request) {
      return cb.desc(getPath(root, request.getKey()));
    }
  };

  /**
   * Constrói a ordenação JPA para esta direção.
   *
   * @param <T>     Tipo da entidade
   * @param root    Raiz da consulta (from clause)
   * @param cb      Construtor de critérios do JPA
   * @param request Requisição de ordenação
   * @return A {@link Order} resultante
   */
  public abstract <T> Order build(Root<T> root, CriteriaBuilder cb,
                                  SortRequest request);

  /**
   * Obtém o {@link Path} para um atributo, suportando caminhos compostos.
   *
   * <p>Permite navegar por atributos aninhados usando notação de ponto.
   * Exemplo: "endereco.cidade.nome" retorna o path para o atributo nome.
   *
   * @param <T>           Tipo da entidade raiz
   * @param root          Raiz da consulta
   * @param attributeName Nome do atributo (pode ser composto)
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
