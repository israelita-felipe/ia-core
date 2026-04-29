package com.ia.core.view.components.converters;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.math.BigDecimal;

/**
 *
 */
/**
 * Conversor de dados para string to big decimal.
 * <p>
 * Responsável por gerenciar as funcionalidades relacionadas a StringToBigDecimalConverter
 * dentro do sistema.
 *
 * @author IA
 * @since 1.0
 */

public class StringToBigDecimalConverter
  implements Converter<String, BigDecimal> {

  @Override
  public Result<BigDecimal> convertToModel(String value,
                                           ValueContext context) {
    if (value == null || "".equals(value)) {
      return Result.ok(null);
    }
    value = value.replaceAll(",", ".");
    return Result.ok(new BigDecimal(value));
  }

  @Override
  public String convertToPresentation(BigDecimal value,
                                      ValueContext context) {
    if (value == null) {
      return null;
    }
    return String.valueOf(value).replace(".",",");
  }

}
