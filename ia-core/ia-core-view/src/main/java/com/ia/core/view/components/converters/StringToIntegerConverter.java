package com.ia.core.view.components.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

/**
 *
 */
/**
 * Conversor de dados para string to integer.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a StringToIntegerConverter
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class StringToIntegerConverter
  implements Converter<String, Integer> {

  @Override
  public Result<Integer> convertToModel(String value,
                                        ValueContext context) {
    if (value == null || "".equals(value)) {
      return Result.ok(null);
    }
    return Result.ok(Integer.valueOf(value));
  }

  @Override
  public String convertToPresentation(Integer value, ValueContext context) {
    if (value == null) {
      return null;
    }
    return String.valueOf(value);
  }

}
