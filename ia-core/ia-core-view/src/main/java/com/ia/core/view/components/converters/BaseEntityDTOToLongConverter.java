package com.ia.core.view.components.converters;

import com.ia.core.service.dto.entity.BaseEntityDTO;
import com.ia.core.view.manager.FindBaseManager;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

/**
 *
 */
/**
 * Conversor de dados para base entity d t o to long.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a BaseEntityDTOToLongConverter
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class BaseEntityDTOToLongConverter<T extends BaseEntityDTO<?>>
  implements Converter<T, Long> {

  private final FindBaseManager<T> finder;

  /**
   * @param finder
   */
  public BaseEntityDTOToLongConverter(FindBaseManager<T> finder) {
    super();
    this.finder = finder;
  }

  @Override
  public Result<Long> convertToModel(T value, ValueContext context) {
    if (value == null) {
      return Result.ok(null);
    }
    return Result.ok(value.getId());
  }

  @Override
  public T convertToPresentation(Long value, ValueContext context) {
    if (value == null) {
      return null;
    }
    return finder.find(value);
  }

}
