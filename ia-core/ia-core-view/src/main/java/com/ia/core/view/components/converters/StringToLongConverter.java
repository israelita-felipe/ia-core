package com.ia.core.view.components.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

/**
 *
 */
/**
 * Conversor de dados para string to long.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a StringToLongConverter
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class StringToLongConverter
  implements Converter<String, Long> {

  @Override
  public Result<Long> convertToModel(String value, ValueContext context) {
    if (value == null || "".equals(value)) {
      return Result.ok(null);
    }
    return Result.ok(Long.valueOf(value));
  }

  @Override
  public String convertToPresentation(Long value, ValueContext context) {
    if (value == null) {
      return null;
    }
    return String.valueOf(value);
  }

}
