package com.ia.core.view.components.converters;

import java.math.BigDecimal;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

/**
 *
 */
public class StringToBigDecimalConverter
  implements Converter<String, BigDecimal> {

  @Override
  public Result<BigDecimal> convertToModel(String value,
                                           ValueContext context) {
    if (value == null || "".equals(value)) {
      return Result.ok(null);
    }
    return Result.ok(new BigDecimal(value));
  }

  @Override
  public String convertToPresentation(BigDecimal value,
                                      ValueContext context) {
    if (value == null) {
      return null;
    }
    return String.valueOf(value);
  }

}
