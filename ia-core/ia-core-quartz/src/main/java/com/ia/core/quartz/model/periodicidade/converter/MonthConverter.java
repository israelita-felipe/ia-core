package com.ia.core.quartz.model.periodicidade.converter;

import java.time.Month;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Israel Araújo
 */
@Converter(autoApply = true)
public class MonthConverter
  implements AttributeConverter<Month, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Month attribute) {
    if (attribute == null) {
      return null;
    }
    return attribute.getValue();
  }

  @Override
  public Month convertToEntityAttribute(Integer dbData) {
    if (dbData == null) {
      return null;
    }
    return Month.of(dbData);
  }

}
