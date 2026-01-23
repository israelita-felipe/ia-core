package com.ia.core.model.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Classe de requisição de busca.
 *
 * @author Israel Araújo
 * @see FilterRequest
 * @see SortRequest
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest
  implements Serializable {
  /**
   * Serial UID.
   */
  private static final long serialVersionUID = 8514625832019794838L;

  /**
   * Filtros.
   */
  @Builder.Default
  private List<FilterRequest> filters = new ArrayList<>();

  /**
   * Ordens.
   */
  @Builder.Default
  private List<SortRequest> sorts = new ArrayList<>();
  /**
   * Contexto
   */
  @Builder.Default
  private List<FilterRequest> context = new ArrayList<>();

  /**
   * Página.
   */
  @Builder.Default
  private Integer page = 0;

  /**
   * Tamanho da página.
   */
  @Builder.Default
  private Integer size = 100;
  /**
   * Indicativo de união dos resultados
   */
  @Builder.Default
  private boolean disjunction = true;

  /**
   * @return Os filtros da requisição: {@link #filters}.
   */
  public List<FilterRequest> getFilters() {
    if (Objects.isNull(this.filters))
      return new ArrayList<>();
    return this.filters;
  }

  /**
   * @return As ordens da requisição: {@link #sorts}.
   */
  public List<SortRequest> getSorts() {
    if (Objects.isNull(this.sorts))
      return new ArrayList<>();
    return this.sorts;
  }

  /**
   * @return As ordens da requisição: {@link #sorts}.
   */
  public List<FilterRequest> getContext() {
    if (Objects.isNull(this.context))
      return new ArrayList<>();
    return this.context;
  }
}
