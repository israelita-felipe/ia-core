package com.ia.core.view.components.filter.viewModel;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ia.core.service.dto.filter.FilterProperty;
import com.ia.core.service.dto.filter.FilterRequestDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * Implementação padrão de um view model para requisição de filtro
 *
 * @author Israel Araújo
 */
public class FilterRequestViewModel
  implements IFilterRequestViewModel {
  /** Filtros */
  @Getter
  private Map<FilterProperty, Collection<FilterRequestDTO>> filterMap = new HashMap<>();
  /** Modelo */
  @Getter
  @Setter
  private FilterRequestDTO model;
  /** Indicativo somente leitura */
  @Getter
  @Setter
  private boolean readOnly = false;
  /**
   * Tipo do objeto
   */
  private Class<?> objectType;

  /**
   * Construtor padrão
   */
  public FilterRequestViewModel() {
  }

  /**
   * @param objectType Tipo do objeto a ser filtrado
   * @param filterMap  {@link Map} de filtros
   */
  public FilterRequestViewModel(Class<?> objectType,
                                Map<FilterProperty, Collection<FilterRequestDTO>> filterMap) {
    this.filterMap = filterMap;
    this.objectType = objectType;
  }

  @Override
  public Class<?> getType() {
    if (objectType == null || getModel() == null) {
      return null;
    }
    try {
      Field field = null;
      for (String property : getModel().getKey().split("\\.")) {
        if (field == null) {
          field = objectType.getDeclaredField(property);
        } else {
          field = field.getDeclaringClass().getDeclaredField(property);
        }
      }
      return field.getType();
    } catch (Exception e) {
      throw new RuntimeException("Propriedade não localizada", e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends Enum<?>> getEnumType() {
    return (Class<? extends Enum<?>>) getType();
  }
}
