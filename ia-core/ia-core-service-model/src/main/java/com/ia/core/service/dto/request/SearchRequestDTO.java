package com.ia.core.service.dto.request;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ia.core.model.filter.FieldType;
import com.ia.core.model.filter.SearchRequest;
import com.ia.core.service.dto.DTO;
import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;
import com.ia.core.service.dto.filter.OperatorDTO;
import com.ia.core.service.dto.sort.SortRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * {@link DTO} para requisição de busca
 *
 * @author Israel Araújo
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequestDTO
  implements DTO<SearchRequest> {

  /**
   * SERIAL UID
   */
  private static final long serialVersionUID = 7409165422103786345L;

  /**
   * Filtros.
   */
  @Builder.Default
  private List<FilterRequestDTO> filters = new ArrayList<>();
  /**
   * Contexto.
   */
  @Builder.Default
  private List<FilterRequestDTO> context = new ArrayList<>();

  /**
   * Ordens.
   */
  @Builder.Default
  private List<SortRequestDTO> sorts = new ArrayList<>();

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
   * Indicativo de disjunção
   */
  @Builder.Default
  private boolean disjunction = true;

  @Override
  public SearchRequestDTO cloneObject() {
    return toBuilder().filters(new ArrayList<>(getFilters()))
        .sorts(new ArrayList<>(getSorts()))
        .context(new ArrayList<>(getContext())).build();
  }

  /**
   * @return Todos os filtros disponíveis
   */
  @Transient
  public Map<FilterProperty, Collection<FilterRequestDTO>> getAvaliableFilters() {
    return new HashMap<>();
  }

  /**
   * @return Os filtros da requisição: {@link #filters}.
   */
  public List<FilterRequestDTO> getFilters() {
    if (Objects.isNull(this.filters))
      return new ArrayList<>();
    return this.filters;
  }

  /**
   * @return Os filtros do contexto: {@link #context}.
   */
  public List<FilterRequestDTO> getContext() {
    if (Objects.isNull(this.context))
      return new ArrayList<>();
    return this.context;
  }

  /**
   * @return As ordens da requisição: {@link #sorts}.
   */
  public List<SortRequestDTO> getSorts() {
    if (Objects.isNull(this.sorts))
      return new ArrayList<>();
    return this.sorts;
  }

  /**
   * Filtros de propriedade
   *
   * @return Consjunto de filtros
   */
  public Set<String> propertyFilters() {
    var avaliableFilters = getAvaliableFilters();
    return avaliableFilters.keySet().stream()
        .filter(key -> avaliableFilters.get(key).stream()
            .anyMatch(value -> Objects.equals(FieldType.STRING,
                                              value.getFieldType())))
        .map(key -> key.getProperty()).collect(Collectors.toSet());
  }

  /**
   * Cria os filtros
   *
   * @param map       mapa de filtro
   * @param label     título
   * @param property  propriedade
   * @param type      tipo
   * @param operators operadores
   */
  public static void createFilters(Map<FilterProperty, Collection<FilterRequestDTO>> map,
                                   String label, String property,
                                   FieldType type,
                                   OperatorDTO... operators) {
    map.put(FilterProperty.builder().label(label).property(property)
        .build(), Stream.of(operators).map(operator -> {
          return FilterRequestDTO.builder().key(property).fieldType(type)
              .operator(operator).build();
        }).collect(Collectors.toList()));
  }

  /**
   * Cria os filtros
   *
   * @param map       mapa de filtro
   * @param label     título
   * @param property  propriedade
   * @param type      tipo
   * @param operators operadores
   */
  public static void createFilter(Map<FilterProperty, Collection<FilterRequestDTO>> map,
                                  String label, String property,
                                  FieldType type, Object value) {
    map.put(FilterProperty.builder().label(label).property(property)
        .build(), Stream.of(OperatorDTO.EQUAL).map(operator -> {
          return FilterRequestDTO.builder().key(property).fieldType(type)
              .operator(operator).value(value).build();
        }).collect(Collectors.toList()));
  }

  /**
   * Campos deste DTO
   */
  @SuppressWarnings("javadoc")
  public static final class CAMPOS {
    public static final String FILTROS = "filters";
    public static final String ORDENACAO = "sorts";
    public static final String PAGINA = "page";
    public static final String TAMANHO = "size";
  }
}
