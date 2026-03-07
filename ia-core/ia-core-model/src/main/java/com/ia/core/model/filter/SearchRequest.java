package com.ia.core.model.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Classe de requisição de busca completa.
 *
 * <p>Encapsula todos os parâmetros necessários para executar uma busca paginada
 * e ordenada no banco de dados, incluindo filtros, ordenação e contexto.
 *
 * <p><b>Por quê usar SearchRequest?</b></p>
 * <ul>
 *   <li>Padroniza requisições de busca na API</li>
 *   <li>Suporta paginação e ordenação</li>
 *   <li>Permite filtros compostos com lógica de conjunção/disjunção</li>
 * </ul>
 *
 * <p><b>Exemplo de uso:</b></p>
 * {@code
 * SearchRequest busca = SearchRequest.builder()
 *     .filters(List.of(
 *         FilterRequest.builder()
 *             .key("nome")
 *             .operator(Operator.LIKE)
 *             .fieldType(FieldType.STRING)
 *             .value("João")
 *             .build()
 *     ))
 *     .sorts(List.of(
 *         SortRequest.builder()
 *             .key("dataCriacao")
 *             .direction(SortDirection.DESC)
 *             .build()
 *     ))
 *     .page(0)
 *     .size(20)
 *     .build();
 * }
 *
 * @author Israel Araújo
 * @see FilterRequest
 * @see SortRequest
 * @see SortDirection
 * @since 1.0.0
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest
  implements Serializable {
  /**
   * Identificador de versão para serialização.
   */
  private static final long serialVersionUID = 8514625832019794838L;

  /**
   * Lista de filtros a serem aplicados na busca.
   * Usados para filtrar os resultados da consulta.
   */
  @Builder.Default
  private List<FilterRequest> filters = new ArrayList<>();

  /**
   * Lista de ordenações a serem aplicadas na busca.
   * Define a ordem dos resultados.
   */
  @Builder.Default
  private List<SortRequest> sorts = new ArrayList<>();

  /**
   * Contexto adicional para a busca.
   * Usado para filtros globais que se aplicam a todas as consultas.
   */
  @Builder.Default
  private List<FilterRequest> context = new ArrayList<>();

  /**
   * Número da página a ser retornada (zero-based).
   * A primeira página é zero.
   */
  @Builder.Default
  private Integer page = 0;

  /**
   * Tamanho de cada página (quantidade de elementos por página).
   */
  @Builder.Default
  private Integer size = 100;

  /**
   * Indica se os filtros devem ser combinados com OR (disjunção) ou AND (conjunção).
   *
   * <p>Se {@code true}, os filtros são combinados com OR.
   * Se {@code false}, os filtros são combinados com AND.
   */
  @Builder.Default
  private boolean disjunction = true;

  /**
   * Obtém a lista de filtros da requisição.
   *
   * <p>Retorna uma lista vazia se {@code filters} for {@code null},
   * garantindo que o retorno nunca seja {@code null}.
   *
   * @return lista de filtros, nunca {@code null}
   */
  public List<FilterRequest> getFilters() {
    if (Objects.isNull(this.filters))
      return new ArrayList<>();
    return this.filters;
  }

  /**
   * Obtém a lista de ordenações da requisição.
   *
   * <p>Retorna uma lista vazia se {@code sorts} for {@code null},
   * garantindo que o retorno nunca seja {@code null}.
   *
   * @return lista de ordenações, nunca {@code null}
   */
  public List<SortRequest> getSorts() {
    if (Objects.isNull(this.sorts))
      return new ArrayList<>();
    return this.sorts;
  }

  /**
   * Obtém o contexto da requisição.
   *
   * <p>Retorna uma lista vazia se {@code context} for {@code null},
   * garantindo que o retorno nunca seja {@code null}.
   *
   * @return lista de contexto, nunca {@code null}
   */
  public List<FilterRequest> getContext() {
    if (Objects.isNull(this.context))
      return new ArrayList<>();
    return this.context;
  }
}
