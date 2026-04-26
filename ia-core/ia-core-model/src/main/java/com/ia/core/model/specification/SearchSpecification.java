package com.ia.core.model.specification;

import com.ia.core.model.filter.FilterRequest;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.model.filter.SortRequest;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação de {@link Specification} do Spring Data JPA para buscas dinâmicas.
 *
 * <p>Esta classe delega a construção de predicados ao enum {@link com.ia.core.model.filter.Operator}.
 * Esta abordagem respeita a estratégia encapsulada em cada operador e mantém a coesão:
 * cada operador conhece sua própria lógica de construção.
 *
 * <p><b>Por quê usar SearchSpecification?</b></p>
 * <ul>
 *   <li>Permite criar consultas dinâmicas com múltiplos filtros</li>
 *   <li>Suporta paginação e ordenação</li>
 *   <li>Separa a lógica de negocio da lógica de consulta</li>
 * </ul>
 *
 * <p><b>Exemplo de uso:</b></p>
 * {@code
 * SearchRequest request = SearchRequest.builder()
 *     .filters(List.of(
 *         FilterRequest.builder()
 *             .key("nome")
 *             .operator(Operator.LIKE)
 *             .fieldType(FieldType.STRING)
 *             .value("João")
 *             .build()
 *     ))
 *     .page(0)
 *     .size(20)
 *     .build();
 *
 * Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
 * List<Usuario> resultados = repository.findAll(new SearchSpecification<>(request), pageable);
 * }
 *
 * @param <T> Tipo da entidade
 * @author Israel Araújo
 * @see Specification
 * @see SearchRequest
 * @see FilterRequest
 * @see Operator
 * @since 1.0.0
 */
@AllArgsConstructor
public class SearchSpecification<T>
  implements Specification<T> {

  private static final long serialVersionUID = -9153865343320750644L;

  /**
   * Requisição de busca contendo filtros, ordenação e paginação.
   */
  private final SearchRequest request;

  /**
   * Cria um objeto {@link Pageable} para paginação.
   *
   * <p>Valores nulos são tratados com padrões:
   * <ul>
   *   <li>page padrão: 0</li>
   *   <li>size padrão: 100</li>
   * </ul>
   *
   * @param page Página atual. Índice 0 a infinito. {@code null} usa 0.
   * @param size Tamanho da página. {@code null} usa 100.
   * @return {@link Pageable} configurado para a requisição
   */
  public static Pageable getPageable(Integer page, Integer size) {
    return PageRequest.of(Optional.ofNullable(page).orElse(0),
                          Optional.ofNullable(size).orElse(100));
  }

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                               CriteriaBuilder cb) {
    // Inicializa predicate com disjunção padrão
    Predicate predicate = cb.equal(cb.literal(Boolean.TRUE),
                                   request.isDisjunction());

    // Aplica filtros normais através do enum Operator
    if (request.getFilters() != null) {
      for (FilterRequest filter : this.request.getFilters()) {
        predicate = filter.getOperator().build(root, cb, filter, predicate,
                                               request.isDisjunction());
      }
    }

    // Aplica contexto (ex.: segurança) também via operador
    if (request.getContext() != null) {
      for (FilterRequest context : this.request.getContext()) {
        predicate = cb.and(predicate,
                           context.getOperator()
                               .build(root, cb, context, cb
                                   .equal(cb.literal(Boolean.TRUE), true),
                                      true));
      }
    }

    // Ordenação
    List<Order> orders = new ArrayList<>();
    if (this.request.getSorts() != null) {
      for (SortRequest sort : this.request.getSorts()) {
        orders.add(sort.getDirection().build(root, cb, sort));
      }
    }

    query.orderBy(orders);
    return predicate;
  }
}
